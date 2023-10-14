package r2s.MockProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import r2s.MockProject.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
	@Query("SELECT a FROM Account a WHERE a.userName = :username")
    Account findByUsername(@Param("username") String username);
}
