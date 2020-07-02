package mrs.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "usr")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String userId;

	private String password;
	
	private String firstName;

	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private RoleName roleName;
	
	
}
