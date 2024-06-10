package szp.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import szp.model.*;
import org.springframework.web.bind.annotation.*;
import szp.repository.*;
import szp.service.EmployeeService;
import szp.service.VacationService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This is the HRController class that handles all the HR related requests.
 * It is annotated with @Controller, meaning it's ready for use by Spring MVC to handle web requests.
 * @RequestMapping("/hr") maps all HTTP operations by default.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/hr")
public class HRController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final WorkstationRepository workstationRepository;
    private final AssignmentRepository assignmentRepository;
    private final ClientRepository clientRepository;
    private final VacationService vacationService;

    /**
     * This method returns the home page for HR.
     * It is mapped to the URL "/hr/" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @return String representing the home page.
     */
    @GetMapping("/")
    public String getHomePage(Model model) {
        return "hr/hr";
    }

    /**
     * This method returns the employees info page.
     * It is mapped to the URL "/hr/employees" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @return String representing the employees info page.
     */
    @GetMapping("/employees")
    public String getEmployeesInfo(Model model) {
        List<EmployeeModel> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "hr/employees";
    }

    /**
     * This method deletes an employee.
     * It is mapped to the URL "/hr/employees" via HTTP POST method.
     * @param model Model object to add attributes to the view.
     * @param idDTO Data transfer object for id.
     * @return String representing the redirect URL.
     */
    @PostMapping("/employees")
    public String deleteEmployee(Model model, @ModelAttribute("idDTO") IdDTO idDTO) {
        employeeRepository.deleteById(idDTO.getId());
        return "redirect:/hr/employees";
    }

    /**
     * This method returns the employee leaves page.
     * It is mapped to the URL "/hr/leaves" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @param startDate Optional LocalDate representing the start date.
     * @param endDate Optional LocalDate representing the end date.
     * @param includeAllMatchning boolean representing whether to include all matching or not.
     * @return String representing the employee leaves page.
     */
    @GetMapping("/leaves")
    public String employeeLeavesPage(Model model,
                                     @RequestParam(value = "startDate") Optional<LocalDate> startDate,
                                     @RequestParam(value = "endDate") Optional<LocalDate> endDate,
                                     @RequestParam(value = "includeAllMatching", defaultValue = "false") boolean includeAllMatchning) {
        startDate.ifPresent(from -> model.addAttribute("startDate", from));
        endDate.ifPresent(to -> model.addAttribute("endDate", to));
        model.addAttribute("includeAllMatching", includeAllMatchning);
        List<VacationModel> vacations = vacationService.getAllVacationsInRange(startDate.orElse(LocalDate.now().minusYears(10)),
                endDate.orElse(LocalDate.now().plusYears(10)), includeAllMatchning);
        model.addAttribute("vacations", vacations);
        return "hr/leaves";
    }

    /**
     * This method returns the workstations page.
     * It is mapped to the URL "/hr/workstations" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @return String representing the workstations page.
     */
    @GetMapping("/workstations")
    public String getAllWorkstations(Model model){
        List<WorkstationModel> workstations = workstationRepository.findAll();
        model.addAttribute("workstations", workstations);
        return "hr/workstations";
    }

    /**
     * This method adds a workstation.
     * It is mapped to the URL "/hr/workstations" via HTTP POST method.
     * @param model Model object to add attributes to the view.
     * @param workstationDTO Data transfer object for workstation.
     * @return String representing the redirect URL.
     */
    @PostMapping("/workstations")
    public String addWorkstation(Model model, @ModelAttribute("workstationDTO") WorkstationDTO workstationDTO){
        WorkstationModel workstationModel = new WorkstationModel();
        workstationModel.setName(workstationDTO.getName());
        workstationRepository.save(workstationModel);
        return "redirect:/hr/workstations";
    }

    /**
     * This method deletes a workstation.
     * It is mapped to the URL "/hr/workstations/delete" via HTTP POST method.
     * @param model Model object to add attributes to the view.
     * @param idDTO Data transfer object for id.
     * @return String representing the redirect URL.
     */
    @PostMapping("/workstations/delete")
    public String deleteWorkstation(Model model, @ModelAttribute("idDTO") IdDTO idDTO){
        workstationRepository.deleteById(idDTO.getId());
        return "redirect:/hr/workstations";
    }

    /**
     * This method returns the assignments page.
     * It is mapped to the URL "/hr/assignments" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @return String representing the assignments page.
     */
    @GetMapping("/assignments")
    public String getAllAssignments(Model model){
        List<AssignmentModel> assignments = assignmentRepository.findAll();
        model.addAttribute("assignments", assignments);
        return "hr/assignments";
    }

    /**
     * This method returns the assignment form page.
     * It is mapped to the URL "/hr/assignments/add" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @return String representing the assignment form page.
     */
    @GetMapping("/assignments/add")
    public String getAssignmentForm(Model model){
        return "hr/assignment-form";
    }

    /**
     * This method adds an assignment.
     * It is mapped to the URL "/hr/assignments/add" via HTTP POST method.
     * @param model Model object to add attributes to the view.
     * @param assignmentDTO Data transfer object for assignment.
     * @return String representing the success page.
     */
    @PostMapping("/assignments/add")
    public String addAssignment(Model model, @ModelAttribute("assignmentDTO") AssignmentDTO assignmentDTO ){
        AssignmentModel assignment = new AssignmentModel();
        EmployeeModel employee = employeeRepository.findById(assignmentDTO.getEmployeeId()).orElseThrow();
        WorkstationModel workstation = workstationRepository.findById(assignmentDTO.getWorkstationId()).orElseThrow();
        ClientModel client = clientRepository.findById(assignmentDTO.getCustomerId()).orElseThrow();
        assignment.setCost(assignmentDTO.getCost());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setEmployee(employee);
        assignment.setWorkstation(workstation);
        assignment.setClient(client);
        assignment.setState(State.IN_PROGRESS);
        assignment.setAssignDate(Date.valueOf(LocalDate.now()));

        assignmentRepository.save(assignment);
        return "hr/success-assignment";
    }

    /**
     * This method changes the assignment status.
     * It is mapped to the URL "/hr/assignments/status" via HTTP POST method.
     * @param model Model object to add attributes to the view.
     * @param assignmentStatusDTO Data transfer object for assignment status.
     * @return String representing the redirect URL.
     */
    @PostMapping("/assignments/status")
    public String changeAssignmentStatus(Model model, @ModelAttribute("AssignmentStatusDTO") AssignmentStatusDTO assignmentStatusDTO){
        AssignmentModel assignment = assignmentRepository.findById(assignmentStatusDTO.getId()).orElseThrow();
        assignment.setState(State.valueOf(assignmentStatusDTO.getStatus()));
        assignmentRepository.save(assignment);
        return "redirect:/hr/assignments";
    }

    /**
     * This method returns the customers page.
     * It is mapped to the URL "/hr/customers" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @return String representing the customers page.
     */
    @GetMapping("/customers")
    public String getAllCustomers(Model model){
        List<ClientModel> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "hr/clients";
    }

    /**
     * This method adds a customer.
     * It is mapped to the URL "/hr/customers/add" via HTTP POST method.
     * @param model Model object to add attributes to the view.
     * @param clientDTO Data transfer object for client.
     * @return String representing the redirect URL.
     */
    @PostMapping("/customers/add")
    public String addCustomer(Model model, @ModelAttribute("ClientDTO") ClientDTO clientDTO){
        ClientModel client  = new ClientModel();
        client.setBankAccount(clientDTO.getBankAccount());
        client.setEmail(clientDTO.getEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setSecondName(clientDTO.getSecondName());
        clientRepository.save(client);
        return "redirect:/hr/customers";
    }

    /**
     * This method returns the customer form page.
     * It is mapped to the URL "/hr/customers/add" via HTTP GET method.
     * @param model Model object to add attributes to the view.
     * @return String representing the customer form page.
     */
    @GetMapping("/customers/add")
    public String addCustomerForm(Model model){
        return "hr/customer-form";
    }

    /**
     * This method removes a customer.
     * It is mapped to the URL "/hr/customers/remove" via HTTP POST method.
     * @param model Model object to add attributes to the view.
     * @param idDTO Data transfer object for id.
     * @return String representing the redirect URL.
     */
    @PostMapping("/customers/remove")
    public String removeCustomer(Model model, @ModelAttribute("idDTO") IdDTO idDTO){
        clientRepository.deleteById(idDTO.getId());
        return "redirect:/hr/customers";
    }
}