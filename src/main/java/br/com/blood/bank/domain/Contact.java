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
@Table(name = "contact")
@ToString
@EqualsAndHashCode(of = "idPerson")
@Entity
public class Contact {
    @Id
    @Column(name = "id_per")
    private Long idPerson;
    @Column(name = "hom_pho")
    private String homePhone;
    @Column(name = "cel")
    private String cel;

}
