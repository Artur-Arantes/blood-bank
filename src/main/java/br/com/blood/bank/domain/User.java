package br.com.blood.bank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@Entity
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usr")
    private Long id;

    @Column(name = "log")
    private String login;

    @Column(name = "pwd")
    @Getter(onMethod = @__(@Override))
    private String password;

    @Column(name = "act")
    @Getter(onMethod = @__(@Override))
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "id_usr", referencedColumnName = "id_usr"),
            inverseJoinColumns = @JoinColumn(name = "id_prm", referencedColumnName = "id_prm"))
    List<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }
}
