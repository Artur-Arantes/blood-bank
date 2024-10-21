package br.com.blood.bank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
@ToString
@EqualsAndHashCode(of = "id")
@Entity
public class Person {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_per")
    @Id
    private Long id;
    @Column(name = "nam")
    private String name;
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    @Column(name = "rg", nullable = false, unique = true)
    private String rg;
    @Column(name = "brt")
    private LocalDate birth;
    @Column(name = "gnd")
    private String gender;
    @Column(name = "mot")
    private String motherName;
    @Column(name = "fat")
    private String fatherName;
    @Column(name = "eml")
    private String email;
}
