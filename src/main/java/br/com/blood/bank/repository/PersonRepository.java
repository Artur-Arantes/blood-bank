package br.com.blood.bank.repository;

import br.com.blood.bank.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO person (nam, cpf, rg, brt, gnd, mot, fat, eml) " +
            "VALUES (:name, :cpf, :rg, :birth, :gender, :motherName, :fatherName, :email) " +
            "ON DUPLICATE KEY UPDATE " +
            "nam = VALUES(nam), rg = VALUES(rg), brt = VALUES(brt), " +
            "gnd = VALUES(gnd), mot = VALUES(mot), fat = VALUES(fat), eml = VALUES(eml)",
            nativeQuery = true)
    void upsertPerson(@Param("name") String name, @Param("cpf") String cpf,
                      @Param("rg") String rg, @Param("birth") LocalDate birth, @Param("gender") String gender,
                      @Param("motherName") String motherName, @Param("fatherName") String fatherName,
                      @Param("email") String email);

    Optional<Person> findByCpf(String cpf);

    @Transactional
    @Query(value = "SELECT * FROM person WHERE brt <= DATE_SUB(CURDATE(), INTERVAL 16 YEAR) AND " +
            "brt >= DATE_SUB(CURDATE(), INTERVAL 69 YEAR)", nativeQuery = true)
    List<Person> findAllValidCandidates();


}
