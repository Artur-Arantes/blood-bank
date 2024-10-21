package br.com.blood.bank.service;

import br.com.blood.bank.domain.Address;
import br.com.blood.bank.domain.Person;
import br.com.blood.bank.dto.ImcByAgeOutPutDto;
import br.com.blood.bank.dto.ObesityPercentageOutPutDto;
import br.com.blood.bank.enums.EnumBloodType;
import br.com.blood.bank.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StaticsServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CandidateService candidateService;
    @Mock
    private List<Person> validCandidates;
    @Mock
    private MedicalInformationService medicalInformationService;
    @InjectMocks
    private StatisticsService service;
    List<Person> listMock = new ArrayList<>();


    @BeforeEach
    void setUp() {

        for (int i = 1; i < 10; i++) {
            Person person = new Person();
            person.setId(((long) i));
            person.setBirth(LocalDate.of(1990 + i, i, i));
            person.setGender(i % 2 == 0 ? "Masculino" : "Feminino");
            this.listMock.add(person);
        }

    }

    @Nested
    @DisplayName("Unit Test getCandidateByState")
    class GetCandidateByState {

        @Test
        void testSuccessGetCandidatesByState() {


            Address addressSP1 = new Address();
            addressSP1.setState("SP");

            Address addressMG2 = new Address();
            addressMG2.setState("MG");

            Address addressRJ3 = new Address();
            addressRJ3.setState("RJ");

            Address addressBA4 = new Address();
            addressBA4.setState("BA");

            Address addressSP5 = new Address();
            addressSP5.setState("SP");

            Address addressMG6 = new Address();
            addressMG6.setState("MG");

            Address addressRJ7 = new Address();
            addressRJ7.setState("RJ");

            Address addressBA8 = new Address();
            addressBA8.setState("BA");

            Address addressSP9 = new Address();
            addressSP9.setState("SP");

            Map<String, Long> expected = new HashMap<>();
            expected.put("SP", 3L);
            expected.put("MG", 2L);
            expected.put("BA", 2L);
            expected.put("RJ", 2L);

            when(candidateService.getValidCandidates()).thenReturn(listMock);
            when(addressRepository.findById(1L)).thenReturn(Optional.of(addressSP1));
            when(addressRepository.findById(2L)).thenReturn(Optional.of(addressMG2));
            when(addressRepository.findById(3L)).thenReturn(Optional.of(addressRJ3));
            when(addressRepository.findById(4L)).thenReturn(Optional.of(addressBA4));
            when(addressRepository.findById(5L)).thenReturn(Optional.of(addressSP5));
            when(addressRepository.findById(6L)).thenReturn(Optional.of(addressMG6));
            when(addressRepository.findById(7L)).thenReturn(Optional.of(addressRJ7));
            when(addressRepository.findById(8L)).thenReturn(Optional.of(addressBA8));
            when(addressRepository.findById(9L)).thenReturn(Optional.of(addressSP9));

            assertThat(service.getCandidatesByState())
                    .hasSameSizeAs(expected)
                    .isNotEmpty()
                    .isNotNull()
                    .isEqualTo(expected);

        }
    }

    @Nested
    @DisplayName("Unit Test getObesityPercentage")
    class GetObesityPercentage {


        @Test
        void testGetObesityPercentage() {

            when(candidateService.getValidCandidates()).thenReturn(listMock);
            when(medicalInformationService.isCandidateObese(1L)).thenReturn(true);
            when(medicalInformationService.isCandidateObese(2L)).thenReturn(true);
            when(medicalInformationService.isCandidateObese(3L)).thenReturn(false);
            when(medicalInformationService.isCandidateObese(4L)).thenReturn(false);
            when(medicalInformationService.isCandidateObese(5L)).thenReturn(true);
            when(medicalInformationService.isCandidateObese(6L)).thenReturn(true);
            when(medicalInformationService.isCandidateObese(7L)).thenReturn(false);
            when(medicalInformationService.isCandidateObese(8L)).thenReturn(false);
            when(medicalInformationService.isCandidateObese(9L)).thenReturn(true);

            BigDecimal expectedMalePercentage = new BigDecimal("60.00"); // 60%
            BigDecimal expectedFemalePercentage = new BigDecimal("50.00"); // 50%
            ObesityPercentageOutPutDto expected = new ObesityPercentageOutPutDto(expectedFemalePercentage,
                    expectedMalePercentage);

            assertThat(service.getObesityPercentage())
                    .isNotNull()
                    .isEqualTo(expected);

        }

    }
    @Nested
    @DisplayName("Unit Test getAverageImcByAGE")
    class GetAverageImcByAGE{

        @Test
            void testGetAverageImcByAge() {
                when(candidateService.getValidCandidates()).thenReturn(listMock);

                when(medicalInformationService.getImc(1L)).thenReturn(BigDecimal.valueOf(25.0));
                when(medicalInformationService.getImc(2L)).thenReturn(BigDecimal.valueOf(30.0));
                when(medicalInformationService.getImc(3L)).thenReturn(BigDecimal.valueOf(22.0));
                when(medicalInformationService.getImc(4L)).thenReturn(BigDecimal.valueOf(21.0));
                when(medicalInformationService.getImc(5L)).thenReturn(BigDecimal.valueOf(20.0));
                when(medicalInformationService.getImc(6L)).thenReturn(BigDecimal.valueOf(19.0));
                when(medicalInformationService.getImc(7L)).thenReturn(BigDecimal.valueOf(18.0));
                when(medicalInformationService.getImc(8L)).thenReturn(BigDecimal.valueOf(31.0));
                when(medicalInformationService.getImc(9L)).thenReturn(BigDecimal.valueOf(24.0));

            List<ImcByAgeOutPutDto> expected = List.of(
                    new ImcByAgeOutPutDto(21, 30, BigDecimal.valueOf(22.17)),
                    new ImcByAgeOutPutDto(31, 40, BigDecimal.valueOf(25.67)));

            assertThat(service.getAverageImcByAge())
                    .hasSameSizeAs(expected)
                    .isNotNull()
                    .isEqualTo(expected);

        }
    }
    @Nested
    @DisplayName("Unit Test getPotentialDonors")
    class GetPotentialDonors{
        @Test
        void testGetPotentialDonors() {
            Person person = new Person();
            person.setId(1L);
            when(candidateService.getValidCandidates()).thenReturn(Collections.singletonList(person));

            when(medicalInformationService.getBloodType(1L)).thenReturn(EnumBloodType.O_POSITIVE);

            Map<EnumBloodType, Long> expected = new HashMap<>();

            expected.put(EnumBloodType.A_POSITIVE, 1L);
            expected.put(EnumBloodType.O_POSITIVE, 1L);
            expected.put(EnumBloodType.AB_POSITIVE, 1L);
            expected.put(EnumBloodType.B_POSITIVE, 1L);
            expected.put(EnumBloodType.A_NEGATIVE, 0L);
            expected.put(EnumBloodType.O_NEGATIVE, 0L);
            expected.put(EnumBloodType.B_NEGATIVE, 0L);
            expected.put(EnumBloodType.AB_NEGATIVE, 0L);

                    assertThat(service.getPotentialDonors())
                            .isNotEmpty()
                            .isNotNull()
                            .hasSameSizeAs(expected)
                            .isEqualTo(expected);
        }

    }
}
