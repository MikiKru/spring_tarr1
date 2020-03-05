package pl.tarr1.spring_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tarr1.spring_app.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
