package br.com.blood.bank.service;

import br.com.blood.bank.domain.MedicalInformation;
import br.com.blood.bank.enums.EnumBloodType;
import br.com.blood.bank.repository.MedicalInformationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MedicalInformationService {
    private final MedicalInformationRepository medicalInformationRepository;
    private final int LIMIT_FOR_OBESITY = 30;

    public MedicalInformationService(MedicalInformationRepository medicalInformationRepository) {
        this.medicalInformationRepository = medicalInformationRepository;
    }


    public void saveMedicalInformation(final Long idEntry, MedicalInformation dtoMedicalInformation) {
        medicalInformationRepository.findById(idEntry)
                .map(medicalInformation -> {
                    medicalInformation.setBloodType(dtoMedicalInformation.getBloodType());
                    medicalInformation.setHeight(dtoMedicalInformation.getHeight());
                    medicalInformation.setWeight(dtoMedicalInformation.getWeight());
                    return medicalInformationRepository.save(medicalInformation);
                })
                .orElseGet(() -> {
                    dtoMedicalInformation.setIdPerson(idEntry);
                    return medicalInformationRepository.save(dtoMedicalInformation);
                });
    }

    public List<MedicalInformation> getPersonAbove50Weight() {
        return medicalInformationRepository.findAllAbove50Weight();
    }

    public BigDecimal getImc(final Long id) {
        MedicalInformation medicalInformation = medicalInformationRepository.findById(id).get();
        return medicalInformation.getWeight()
                .divide(medicalInformation.getHeight().multiply(medicalInformation.getHeight()), 2, RoundingMode.HALF_UP);
    }

    public boolean isCandidateObese(final long id) {
        MedicalInformation medicalInformation = medicalInformationRepository.findById(id).get();
        BigDecimal imc = medicalInformation.getWeight()
                .divide(medicalInformation.getHeight().multiply(medicalInformation.getHeight()), 2, RoundingMode.HALF_UP);

        return imc.compareTo(BigDecimal.valueOf(LIMIT_FOR_OBESITY)) > 0;
    }

    public EnumBloodType getBloodType(final Long id){
        MedicalInformation medicalInformation = medicalInformationRepository.findById(id).get();

        return EnumBloodType.getByDescription(medicalInformation.getBloodType());
    }
}