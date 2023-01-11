package pl.trytek.easytrip.data.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "rola_uzytkownik", schema = "easyTrip")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
            //cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="uzytkownik_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="rola_id")
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

//    public UserRole(Long id,User user, Role role) {
//        this.id = id;
//        this.user = user;
//        this.role = role;
//    }

}
