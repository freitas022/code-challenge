package freitas.codechallenge.user;

import lombok.Data;

@Data
public class UserMinDto {

    private Long id;
    private String name;

    public UserMinDto(User entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
