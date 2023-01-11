package pl.trytek.easytrip.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.trytek.easytrip.data.domain.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long>,
        JpaSpecificationExecutor<Role> {

    Role findByName(String name);

    Set<Role> findAllByNameIn(Set<String> names);
}

