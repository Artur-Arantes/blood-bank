package br.com.blood.bank.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_information")
@ToString
@EqualsAndHashCode(of = "idPerson")
@Entity
public class MedicalInformation {
    @Id
    @Column(name = "id_per")
    private Long idPerson;
    @Column(name = "hgt")
    private BigDecimal height;
    @Column(name = "wgt")
    private BigDecimal weight;
    @Column(name = "bld_typ")
    private String bloodType;
}
