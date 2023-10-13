package r2s.MockProject.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint(20)")
	private Integer id;
	@Column(name = "name", columnDefinition = "varchar(50)")
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Set<Account> accounts;
}
