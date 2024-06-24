package freitas.codechallenge.department;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDto {

    private Long id;
    private String name;

    public DepartmentDto(Department entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
