package br.com.blood.bank.domain;

import br.com.blood.bank.enums.EnumUserPermission;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
@ToString
@EqualsAndHashCode(of = "id")
@Entity
public class Permission implements GrantedAuthority {

    @Id
    @Column(name = "id_prm")
    private long id;

    @Column(name = "nam")
    @Enumerated(EnumType.STRING)
    private EnumUserPermission name;

    @Override
    public String getAuthority() {
        return name.getRole();
    }
}
