package szp.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import szp.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import szp.repository.EmployeeRepository;
import szp.repository.WorkstationRepository;
import szp.service.EmployeeService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/hr")
public class HRController{
    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;
    private final WorkstationRepository workstationRepository;

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











}
