package szp.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import szp.model.EmployeeModel;
import szp.model.Role;

public record EmployeeDTO(@NotNull Integer employeeId,
                          @NotNull Role role,
                          @NotBlank String firstName,
                          @NotNull String secondName,
                          @NotBlank String lastName,
                          @NotNull java.sql.Date  birthDate,
                          @NotBlank String pesel) {
    public EmployeeDTO(EmployeeModel employeeModel) {
        this(employeeModel.getEmployeeId(),
                employeeModel.getRole(),
                employeeModel.getFirstName(),
                employeeModel.getSecondName(),
                employeeModel.getLastName(),
                employeeModel.getBirthDate(),
                employeeModel.getPesel());
    }
}
