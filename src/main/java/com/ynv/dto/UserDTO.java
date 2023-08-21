package com.ynv.dto;

import com.ynv.domain.Role;
import com.ynv.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

//	private Long id;

	private String firstName;

	private String lastName;

	private String userName;

	private String address;

	private String phone;

	private LocalDate birthDate;

	private String email;

	private String password;

	private Boolean builtIn;

	private Set<String> roles;

	public void setRoles(Set<Role> roles) {
		Set<String> rolesStr = new HashSet<>();

		roles.forEach(r -> {
			if (r.getName().equals(RoleType.ADMIN))
				rolesStr.add("Administrator");
			else if(r.getName().equals(RoleType.CUSTOMER))
				rolesStr.add("Customer");
			else
				rolesStr.add("Anonymous");
		});

		this.roles=rolesStr;
	}
	
}