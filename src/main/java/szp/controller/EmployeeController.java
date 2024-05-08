package szp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import szp.model.EmployeeDTO;
import szp.model.EmployeeModel;
import szp.model.Role;
import szp.repository.EmployeeRepository;
import szp.service.EmployeeService;
import szp.service.JwtService;

import static szp.service.JwtService.TokenType.ACCESS_TOKEN;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;
    JwtService jwtService;
    private EmployeeModel extractEmployeeFrom(HttpServletRequest request) {
        String token = jwtService.getToken(request, ACCESS_TOKEN).orElseThrow();
        String username = jwtService.extractUsername(token);
        return employeeRepository.findByLogin(username).orElseThrow();
    }

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request){
        EmployeeModel user = extractEmployeeFrom(request);
        model.addAttribute("currentUser", user);
        return "employee/employee";
    }



    @GetMapping("/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        EmployeeModel employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "employee/updateEmployee";
    }

    @PostMapping("/{id}/save")
    public String updateEmployee(@PathVariable("id") Integer id, Model model, @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO) {
        EmployeeModel employee = employeeRepository.findById(id).orElseThrow();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setSecondName(employeeDTO.getSecondName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setPesel(employeeDTO.getPesel());
        employee.setLogin(employeeDTO.getLogin());
        employeeRepository.save(employee);
        return "redirect:/employee/";
    }

}
