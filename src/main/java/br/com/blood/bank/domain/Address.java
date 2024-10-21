package br.com.blood.bank.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@ToString
@EqualsAndHashCode(of = "idPerson")
@Entity
public class Address {
    @Id
    @Column(name = "id_per")
    private Long idPerson;
    @Column(name = "zip_nmb")
    private String zipNumber;
    @Column(name = "adr")
    private String address;
    @Column(name = "nmb")
    private int number;
    @Column(name = "ngh")
    private String neighborhood;
    @Column(name = "cit")
    private String city;
    @Column(name = "stt")
    private String state;
}
