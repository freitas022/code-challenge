package freitas.codechallenge.user;

import freitas.codechallenge.department.Department;
import freitas.codechallenge.department.DepartmentRepository;
import freitas.codechallenge.role.Role;
import freitas.codechallenge.role.RoleRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void insert(@Valid UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        var authority = roleRepository.findByAuthority(Role.RoleName.CUSTOMER.name());
        var department = departmentRepository.getReferenceById(userDto.getDepartment().getId());

        User user = userFactory(userDto, authority, department);
        userRepository.save(user);
    }

    public User findById(@Positive final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public User myProfile(JwtAuthenticationToken jwt) {
        return findById(Long.valueOf(jwt.getToken().getSubject()));
    }

    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public void delete(@Positive final long id) {
        userRepository.deleteById(id);
    }

    private User userFactory(UserDto userDto, Role authority, Department department) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsActive(true);
        user.getRoles().add(authority);
        user.setDepartment(department);
        return user;
    }
}
