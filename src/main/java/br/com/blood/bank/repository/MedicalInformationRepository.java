package br.com.blood.bank.repository;

import br.com.blood.bank.domain.MedicalInformation;
import br.com.blood.bank.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MedicalInformationRepository extends JpaRepository<MedicalInformation, Long> {

    @Transactional
    @Query(value = "SELECT mi FROM MedicalInformation mi WHERE mi.weight > 50")
    List<MedicalInformation> findAllAbove50Weight();
}
