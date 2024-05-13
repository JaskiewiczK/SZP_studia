package szp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import szp.model.*;
import szp.repository.EmployeeRepository;
import szp.repository.VacationRepository;
import szp.service.EmployeeService;
import szp.service.JwtService;

import java.util.List;

import static szp.service.JwtService.TokenType.ACCESS_TOKEN;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;

    VacationRepository vacationRepository;
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

    @PostMapping("/vacationRequest/{id}")
    public String sendVacationRequest(@PathVariable("id") Integer id, Model model, @ModelAttribute("vacationRequestDTO")VacationRequestDTO vacationRequestDTO){
        VacationModel vacation = new VacationModel();
        EmployeeModel employee = employeeRepository.findById(id).orElseThrow();
        vacation.setEmployee(employee);
        vacation.setBeginning(vacationRequestDTO.getBeginning());
        vacation.setEnding(vacationRequestDTO.getEnding());
        vacation.setType(vacationRequestDTO.getType());
        vacationRepository.save(vacation);
        return "employee/success";
    }

    @GetMapping("/vacation-request")
    public String showVacationRequestForm(Model model, HttpServletRequest request){
        EmployeeModel user = extractEmployeeFrom(request);
        model.addAttribute("employee", user);
        return "employee/vacationRequest";
    }

    @GetMapping("/vacation/{id}")
    public String showVacation(@PathVariable("id") Integer id, Model model){
        List<VacationModel> vacations = vacationRepository.findByEmployee_EmployeeId(id);
        model.addAttribute("vacations", vacations);
        return "employee/vacation";
    }





}
