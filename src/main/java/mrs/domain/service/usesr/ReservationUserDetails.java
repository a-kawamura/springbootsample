package mrs.domain.service.usesr;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import mrs.domain.model.User;

@Data
public class ReservationUserDetails implements UserDetails {

	private final User user;

	public ReservationUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils
				.createAuthorityList("ROLE_" + this.user.getRoleName().name());
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationUserDetails other = (ReservationUserDetails) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.getUserId().equals(other.user.getUserId()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((user == null) ? 0 : user.getUserId().hashCode());
		return result;
	}
}
