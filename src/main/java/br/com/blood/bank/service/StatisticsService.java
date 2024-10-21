package br.com.blood.bank.service;

import br.com.blood.bank.domain.Address;
import br.com.blood.bank.dto.ImcByAgeOutPutDto;
import br.com.blood.bank.domain.Person;
import br.com.blood.bank.dto.ObesityPercentageOutPutDto;
import br.com.blood.bank.enums.EnumBloodType;
import br.com.blood.bank.enums.EnumGender;
import br.com.blood.bank.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class StatisticsService {

    private final AddressRepository addressRepository;
    private final CandidateService candidateService;
    private List<Person> validCandidates;
    private final MedicalInformationService medicalInformationService ;

    public StatisticsService(AddressRepository addressRepository, CandidateService candidateService,
                             MedicalInformationService medicalInformationService) {
        this.addressRepository = addressRepository;
        this.candidateService = candidateService;
        this.medicalInformationService = medicalInformationService;
    }

    public Map<String, Long> getCandidatesByState() {
        getValidCandidates();

        Map<String, Long> stateMap = new HashMap<>();

        validCandidates.forEach(candidate -> {
            Optional<Address> address = addressRepository.findById(candidate.getId());

            address.ifPresent(o -> {
                stateMap.merge(o.getState(), 1L, Long::sum);
            });
        });
           return stateMap;
    }

    public List<ImcByAgeOutPutDto> getAverageImcByAge() {
        getValidCandidates();

       List<Person> candidates = new ArrayList<>(this.validCandidates);
        HashMap<Integer, Long> ageGroupCountNumberCandidates = new HashMap<>();
        HashMap<Integer, BigDecimal> imcMap = new HashMap<>();
        List<ImcByAgeOutPutDto> resultList = new ArrayList<>();

        int ageGroup = 11;

        while (!candidates.isEmpty()) {
            List<Person> processedCandidates = new ArrayList<>();
            for (Person p : candidates) {
                int age = Period.between(p.getBirth(), LocalDate.now()).getYears();
                if (age >= ageGroup && age < ageGroup + 10) {
                    ageGroupCountNumberCandidates.merge(ageGroup, 1L, Long::sum);

                    BigDecimal imc = medicalInformationService.getImc(p.getId());
                    imcMap.merge(ageGroup, imc, BigDecimal::add);

                    processedCandidates.add(p);
                }
            }
            candidates.removeAll(processedCandidates);
            ageGroup = ageGroup + 10;
        }
        for(Map.Entry<Integer, Long> entry : ageGroupCountNumberCandidates.entrySet()){
            divideImcTotalByNumberOfCandidates(entry, imcMap);
        }
        for(Map.Entry<Integer, BigDecimal> entry : imcMap.entrySet()){
            fillImcDto(entry, resultList);
        }
        return resultList;
    }

    private static void fillImcDto(Map.Entry<Integer, BigDecimal> entry, List<ImcByAgeOutPutDto> list) {
        ImcByAgeOutPutDto imc = new ImcByAgeOutPutDto(entry.getKey(), entry.getKey()+9, entry.getValue());
        list.add(imc);
    }

    private static void divideImcTotalByNumberOfCandidates(Map.Entry<Integer, Long> entry, HashMap<Integer, BigDecimal> imcMap) {
        Integer entryKey = entry.getKey();
        BigDecimal imcTotal = imcMap.get(entryKey);
        BigDecimal averageImc = imcTotal.divide(BigDecimal.valueOf(entry.getValue()), 2, RoundingMode.HALF_UP);
        imcMap.replace(entryKey, averageImc);
    }

    public ObesityPercentageOutPutDto getObesityPercentage() {
        getValidCandidates();
        Map<String, Long> mapObeseGender = new HashMap<>();
        this.validCandidates.forEach( candidate ->{
            countNumberOfObese(candidate, mapObeseGender);
        });
        long femaleCount = countNumberOfFemaleInTheValidCandidates();

        long maleCount = countNumberOfMale(femaleCount);

        BigDecimal femalePercentage = getFemalePercentage(mapObeseGender, femaleCount);

        BigDecimal malePercentage = getMalePercentage(mapObeseGender, maleCount);

        return  new ObesityPercentageOutPutDto(malePercentage, femalePercentage);
    }

    private long countNumberOfMale(long femaleCount) {
        long totalCandidates = this.validCandidates.size();
        return totalCandidates - femaleCount;
    }

    private long countNumberOfFemaleInTheValidCandidates() {
        return this.validCandidates.stream()
                .filter(candidate -> EnumGender.FEMALE.getDescription().equals(candidate.getGender()))
                .count();
    }

    private void countNumberOfObese(Person candidate, Map<String, Long> mapObeseGender) {
        if(medicalInformationService.isCandidateObese(candidate.getId())){
            mapObeseGender.merge(candidate.getGender(),1L, Long::sum);
        }
    }

    private static BigDecimal getFemalePercentage(Map<String, Long> mapObeseGender, long femaleCount) {
        Long obeseFemales = mapObeseGender.getOrDefault(EnumGender.FEMALE.getDescription(), 0L);
        return BigDecimal.valueOf(obeseFemales)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(femaleCount), 2, RoundingMode.HALF_UP);
    }

    private static BigDecimal getMalePercentage(Map<String, Long> mapObeseGender, long maleCount) {
        Long obeseMales = mapObeseGender.getOrDefault(EnumGender.MALE.getDescription(), 0L);
       return BigDecimal.valueOf(obeseMales)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(maleCount), 2, RoundingMode.HALF_UP);
    }

    public Map<EnumBloodType, BigDecimal> getAverageAgeByBloodType() {
        getValidCandidates();
        Map<EnumBloodType, Integer> mapBloodTypeByAge = new HashMap<>();
        Map<EnumBloodType, Integer> countNumberOfCandidateByBloodType = new HashMap<>();
        Map<EnumBloodType, BigDecimal> result = new HashMap<>();
        this.validCandidates.forEach(candidate ->{
            fillMaps(candidate, mapBloodTypeByAge, countNumberOfCandidateByBloodType);
        });

        for(Map.Entry<EnumBloodType, Integer> entry : mapBloodTypeByAge.entrySet()){
            BigDecimal averageAge = getAverageAge(entry, countNumberOfCandidateByBloodType);
            result.put(entry.getKey(), averageAge);
        }

        return result;
    }

    private static BigDecimal getAverageAge(Map.Entry<EnumBloodType, Integer> entry, Map<EnumBloodType, Integer> countNumberOfCandidateByBloodType) {
        int numberOfCandidate = countNumberOfCandidateByBloodType.get(entry.getKey());
        int totalAge = entry.getValue();

        return BigDecimal.valueOf(totalAge).divide(BigDecimal.valueOf(numberOfCandidate)
                ,2,RoundingMode.HALF_UP);

    }

    private void fillMaps(Person candidate, Map<EnumBloodType, Integer> mapBloodTypeByAge, Map<EnumBloodType, Integer> countNumberOfCandidateByBloodType) {
        EnumBloodType bloodType = medicalInformationService.getBloodType(candidate.getId());
        int age = Period.between(candidate.getBirth(), LocalDate.now()).getYears();
        mapBloodTypeByAge.merge(bloodType,age, Integer::sum);
        countNumberOfCandidateByBloodType.merge(bloodType, 1, Integer::sum);
    }

    public Map<EnumBloodType, Long> getPotentialDonors() {
        getValidCandidates();

        Map<EnumBloodType, Long> bloodTypeCount = new HashMap<>();
        this.validCandidates.forEach(candidate -> {
            EnumBloodType bloodType = medicalInformationService.getBloodType(candidate.getId());
            bloodTypeCount.merge(bloodType, 1L, Long::sum);
        });

        Map<EnumBloodType, Long> potentialDonors = new HashMap<>();

        for (EnumBloodType recipient : EnumBloodType.values()) {
            long donorCount = 0;

            List<EnumBloodType> compatibleDonors = recipient.getPossibleDonors();
            for (EnumBloodType donor : compatibleDonors) {
                donorCount += bloodTypeCount.getOrDefault(donor, 0L);
            }

            potentialDonors.put(recipient, donorCount);
        }

        return potentialDonors;
    }


    public List<Person> getValidCandidates() {
        if(this.validCandidates == null || this.validCandidates.isEmpty()){
            validCandidates = candidateService.getValidCandidates();
        }
        return this.validCandidates;
    }
}
