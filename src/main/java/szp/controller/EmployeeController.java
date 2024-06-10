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

/**
 * This is the EmployeeController class that handles all the employee related requests.
 * It is annotated with @Controller, meaning it's ready for use by Spring MVC to handle web requests.
 * @RequestMapping("/employee") maps all HTTP operations by default.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;

    VacationRepository vacationRepository;
    JwtService jwtService;

    /**
     * This method extracts the employee from the request.
     * @param request HttpServletRequest object to get request information for HTTP servlets.
     * @return EmployeeModel object representing the employee.
     */
    private EmployeeModel extractEmployeeFrom(HttpServletRequest request) {
        String token = jwtService.getToken(request, ACCESS_TOKEN).orElseThrow();
        String username = jwtService.extractUsername(token);
        return employeeRepository.findByLogin(username).orElseThrow();
    }

    /**
     * This method returns the home page for the employee.
     * It is mapped to the URL "/employee/" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @param request HttpServletRequest object to get request information for HTTP servlets.
     * @return String representing the home page.
     */
    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request){
        EmployeeModel user = extractEmployeeFrom(request);
        model.addAttribute("currentUser", user);
        return "employee/employee";
    }

    /**
     * This method shows the update form for the employee.
     * It is mapped to the URL "/employee/{id}" via HTTP GET method.
     * @param id Integer representing the employee id.
     * @param model Model object to add attributes to the view.
     * @return String representing the update form.
     */
    @GetMapping("/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        EmployeeModel employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "employee/updateEmployee";
    }

    /**
     * This method updates the employee.
     * It is mapped to the URL "/employee/{id}/save" via HTTP POST method.
     * @param id Integer representing the employee id.
     * @param model Model object to add attributes to the view.
     * @param employeeDTO Data transfer object for employee.
     * @return String representing the redirect URL.
     */
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

    /**
     * This method sends a vacation request.
     * It is mapped to the URL "/employee/vacationRequest/{id}" via HTTP POST method.
     * @param id Integer representing the employee id.
     * @param model Model object to add attributes to the view.
     * @param vacationRequestDTO Data transfer object for vacation request.
     * @return String representing the success page.
     */
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

    /**
     * This method shows the vacation request form.
     * It is mapped to the URL "/employee/vacation-request" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @param request HttpServletRequest object to get request information for HTTP servlets.
     * @return String representing the vacation request form.
     */
    @GetMapping("/vacation-request")
    public String showVacationRequestForm(Model model, HttpServletRequest request){
        EmployeeModel user = extractEmployeeFrom(request);
        model.addAttribute("employee", user);
        return "employee/vacationRequest";
    }

    /**
     * This method shows the vacation for the employee.
     * It is mapped to the URL "/employee/vacation/{id}" via HTTP GET method.
     * @param id Integer representing the employee id.
     * @param model Model object to add attributes to the view.
     * @return String representing the vacation page.
     */
    @GetMapping("/vacation/{id}")
    public String showVacation(@PathVariable("id") Integer id, Model model){
        List<VacationModel> vacations = vacationRepository.findByEmployee_EmployeeId(id);
        model.addAttribute("vacations", vacations);
        return "employee/vacation";
    }
}