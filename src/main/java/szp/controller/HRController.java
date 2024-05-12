package szp.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import szp.model.*;
import org.springframework.web.bind.annotation.*;
import szp.repository.AssignmentRepository;
import szp.repository.ClientRepository;
import szp.repository.EmployeeRepository;
import szp.repository.WorkstationRepository;
import szp.service.EmployeeService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/hr")
public class HRController{
    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;
    private final WorkstationRepository workstationRepository;

    private final AssignmentRepository assignmentRepository;

    private final ClientRepository clientRepository;

    @GetMapping("/")
    public String getHomePage(Model model) {
        return "hr/hr";
    }

    @GetMapping("/employees")
    public String getEmployeesInfo(Model model) {

        List<EmployeeModel> employees = employeeService.getAllEmployees();

        model.addAttribute("employees", employees);

        return "hr/employees";
    }

    @PostMapping("/employees")
    public String deleteEmployee(Model model, @ModelAttribute("idDTO") IdDTO idDTO) {
        employeeRepository.deleteById(idDTO.getId());
        return "redirect:/hr/employees";
    }

    @GetMapping("/workstations")
    public String getAllWorkstations(Model model){
        List<WorkstationModel> workstations = workstationRepository.findAll();
        model.addAttribute("workstations", workstations);
        return "hr/workstations";
    }

    @PostMapping("/workstations")
    public String addWorkstation(Model model, @ModelAttribute("workstationDTO") WorkstationDTO workstationDTO){
        WorkstationModel workstationModel = new WorkstationModel();
        workstationModel.setName(workstationDTO.getName());
        workstationRepository.save(workstationModel);
        return "redirect:/hr/workstations";
    }


    @PostMapping("/workstations/delete")
    public String deleteWorkstation(Model model, @ModelAttribute("idDTO") IdDTO idDTO){
        workstationRepository.deleteById(idDTO.getId());
        return "redirect:/hr/workstations";
    }

    @GetMapping("/assignments")
    public String getAllAssignments(Model model){
        List<AssignmentModel> assignments = assignmentRepository.findAll();
        model.addAttribute("assignments", assignments);
        return "hr/assignments";
    }

    @GetMapping("/assignments/add")
    public String getAssignmentForm(Model model){
        return "hr/assignment-form";
    }

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

    @PostMapping("/assignments/status")
    public String changeAssignmentStatus(Model model, @ModelAttribute("AssignmentStatusDTO") AssignmentStatusDTO assignmentStatusDTO){
        AssignmentModel assignment = assignmentRepository.findById(assignmentStatusDTO.getId()).orElseThrow();
        assignment.setState(State.valueOf(assignmentStatusDTO.getStatus()));
        assignmentRepository.save(assignment);
        return "redirect:/hr/assignments";
    }


    @GetMapping("/customers")
    public String getAllCustomers(Model model){
        List<ClientModel> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "hr/clients";
    }

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

    @GetMapping("/customers/add")
    public String addCustomerForm(Model model){
        return "hr/customer-form";
    }

    @PostMapping("/customers/remove")
    public String removeCustomer(Model model, @ModelAttribute("idDTO") IdDTO idDTO){
        clientRepository.deleteById(idDTO.getId());
        return "redirect:/hr/customers";
    }

}
