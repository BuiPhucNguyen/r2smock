package r2s.MockProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import r2s.MockProject.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query("SELECT a FROM Role a WHERE a.name = :name")
    Role getRoleByName(@Param("name") String name);
}
