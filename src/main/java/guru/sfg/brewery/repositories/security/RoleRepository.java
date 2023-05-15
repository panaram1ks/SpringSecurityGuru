package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author E.Parominsky 15/05/2023 08:30
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
