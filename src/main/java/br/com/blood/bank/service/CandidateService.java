package br.com.blood.bank.service;

import br.com.blood.bank.domain.Address;
import br.com.blood.bank.domain.Contact;
import br.com.blood.bank.domain.MedicalInformation;
import br.com.blood.bank.domain.Person;
import br.com.blood.bank.dto.CandidateDto;
import br.com.blood.bank.repository.PersonRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CandidateService {

    private final PersonRepository personRepository;
    private final ContactService contactService;
    private final MedicalInformationService medicalInformationService;
    private final AddressService addressService;


    public CandidateService(PersonRepository personRepository, ContactService contactService,
                            MedicalInformationService medicalInformationService,
                            AddressService addressService) {
        this.personRepository = personRepository;
        this.contactService = contactService;
        this.medicalInformationService = medicalInformationService;
        this.addressService = addressService;
    }

    public void add(@NonNull final List<CandidateDto> listCandidateDto) {
        List<Person> personList = listCandidateDto.stream()
                .map(CandidateDto::toPerson).toList();
        Map<CandidateDto, Long> candidateIdMap = new HashMap<>();

        personList.forEach(person -> {
            savePerson(person);
            long id = getId(person);

            fillMap(listCandidateDto, person, candidateIdMap, id);

            savePersonDetails(candidateIdMap);
        });
    }

    public List<Person> getValidCandidates(){
        List<Person> listCandidateCanDonateByAge = personRepository.findAllValidCandidates();
        List<MedicalInformation>  listCandidateCanDonateByWeight =medicalInformationService.getPersonAbove50Weight();

        return listCandidateCanDonateByAge.stream()
                .filter(person -> listCandidateCanDonateByWeight.stream()
                        .anyMatch(medicalInfo -> medicalInfo.getIdPerson().equals(person.getId())))
                .toList();


    }


    private Long getId(Person person) {
        return personRepository.findByCpf(person.getCpf()).get().getId();
    }

    private void savePerson(Person person) {
         personRepository.upsertPerson(person.getName(), person.getCpf(), person.getRg(), person.getBirth(),
                person.getGender(), person.getMotherName(), person.getFatherName(), person.getEmail());
    }

    private void fillMap(List<CandidateDto> listCandidateDto, Person person, Map<CandidateDto, Long> candidateIdMap, long id) {
        listCandidateDto.stream()
                 .filter(candidate -> candidate.cpf().equals(person.getCpf()))
                 .findFirst()
                 .ifPresent(candidateDto -> candidateIdMap.put(candidateDto, id));
    }

    private void savePersonDetails(Map<CandidateDto, Long> candidateIdMap) {
        for (Map.Entry<CandidateDto, Long> entry : candidateIdMap.entrySet()) {
            CandidateDto candidateDto = entry.getKey();
            Long idEntry = entry.getValue();
            Contact dtoContact =candidateDto.toContact();
            Address dtoAddress = candidateDto.toAddress();
            MedicalInformation dtoMedicalInformation = candidateDto.toMedicalInformation();


            contactService.saveContact(idEntry,dtoContact);

            addressService.saveAddress(idEntry,dtoAddress);

            medicalInformationService.saveMedicalInformation(idEntry,dtoMedicalInformation);
        }
    }
}
