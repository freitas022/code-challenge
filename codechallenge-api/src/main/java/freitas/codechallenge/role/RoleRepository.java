package freitas.codechallenge.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

   Role findByAuthority(String authority);
}