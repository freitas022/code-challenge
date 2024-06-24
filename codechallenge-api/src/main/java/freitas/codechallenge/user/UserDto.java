package freitas.codechallenge.user;

import freitas.codechallenge.department.DepartmentDto;
import freitas.codechallenge.role.RoleDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    @Email
    private String email;
    @Size(min = 8)
    private String password;
    private Boolean isActive;
    private Set<RoleDto> roles;
    private DepartmentDto department;

    public UserDto(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        password = entity.getPassword();
        isActive = entity.getIsActive();
        roles = entity.getRoles().stream().map(RoleDto::new).collect(Collectors.toSet());
        department = new DepartmentDto(entity.getDepartment());
    }
}
