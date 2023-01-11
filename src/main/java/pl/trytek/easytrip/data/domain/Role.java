package pl.trytek.easytrip.data.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rola", schema = "easyTrip")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa")
    private String name;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="role", fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;
}
