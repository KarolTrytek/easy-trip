package pl.trytek.easytrip.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.trytek.easytrip.data.domain.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>,
        JpaSpecificationExecutor<UserRole> {
}
