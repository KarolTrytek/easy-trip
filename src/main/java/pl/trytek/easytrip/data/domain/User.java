package pl.trytek.easytrip.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.trytek.easytrip.data.converter.UserRoleEnumConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "easyTrip")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User{// implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "rola")
    @Convert(converter = UserRoleEnumConverter.class)
    private UserRoleEnum role;

    public User(String login,
                String password,
                UserRoleEnum role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
//        return Collections.singleton(authority);
//    }
//
//    @Override
//    public String getUsername() {
//        return login;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}