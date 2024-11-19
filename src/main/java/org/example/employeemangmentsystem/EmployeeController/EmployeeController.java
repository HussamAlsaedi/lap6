package org.example.employeemangmentsystem.EmployeeController;


import jakarta.validation.Valid;
import org.example.employeemangmentsystem.Model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {


    ArrayList<Employee> employees = new ArrayList<>();


    @GetMapping("/get")
    public ResponseEntity getEmployees() {

        return ResponseEntity.ok(employees);
    }


    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors) {

        if (!errors.hasErrors()) {
            employees.add(employee);
            return ResponseEntity.status(200).body("added employee successfully");
        } else {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }


    }

    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index, @RequestBody Employee employee, Errors errors) {
        if (!errors.hasErrors()) {
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getId() == index) {
                    employees.set(i, employee);
                    return ResponseEntity.status(200).body("Updated employee successfully");
                }
            }
            return ResponseEntity.status(404).body("Employee not found");
        } else {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int index) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == index) {
                employees.remove(i);
                return ResponseEntity.status(200).body("Deleted employee successfully");
            }
        }

        return ResponseEntity.status(404).body("Employee not found");
    }


    @GetMapping("/ByPosition/{ByPosition}")
    public ArrayList<Employee> SearchEmployeesByPosition(@PathVariable String ByPosition)
    {
        ArrayList<Employee> matchingPosition = new ArrayList<>();

        for (Employee loop : employees)
        {
            if (loop.getPosition().equals(ByPosition))
            {
                matchingPosition.add(loop);
            }
        }
        return matchingPosition;
    }

    @GetMapping("/ByAge/{Age}")
    public ArrayList<Employee> SearchEmployeesByAge(@PathVariable int Age)
    {
        ArrayList<Employee> matchingAge = new ArrayList<>();

        for (Employee loop : employees)
        {
            if (loop.getAge() == Age)
            {
                matchingAge.add(loop);
            }
        }
        return matchingAge;
    }

    @GetMapping("/annualLeave/{index}")
    public ResponseEntity<String> annualLeave(@PathVariable int index) {
        if (index >= 1) {
            for (Employee emp : employees) {
                if (emp.getId() == index) {

                    if (!emp.isOnLeave() && emp.getAnnualLeave() > 0) {
                        emp.setOnLeave(true);
                        emp.setAnnualLeave(emp.getAnnualLeave() - 1);
                        return ResponseEntity.status(200).body("Annual leave done successfully");
                    } else {
                        return ResponseEntity.status(400).body("Employee is already on leave or has insufficient annual leave days");
                    }
                }
            }
        }
        return ResponseEntity.status(404).body("Please input a correct ID");
    }

    @GetMapping("/OnLeave")
    public ArrayList<Employee> EmployeesNoAnnualLeave()
    {
        ArrayList<Employee> matchingOnLeave = new ArrayList<>();

        for (Employee emp : employees)
        {
            if (!emp.isOnLeave())
            {
                matchingOnLeave.add(emp);
            }
        }
        return matchingOnLeave;
    }

    @GetMapping("/Promote/{index}/{index2}")
    public ResponseEntity<String> Promote(@PathVariable int index,@PathVariable int index2)
    {
        Employee supervisor = null;
        Employee candidate = null;


        for (Employee emp : employees) {
            if (emp.getId() == index && emp.getPosition().equals("supervisor")) {
                supervisor = emp;
            }
            if (emp.getId() == index2 && emp.getAge() >= 30 && !emp.isOnLeave()) {
                candidate = emp;
            }
        }

        if (supervisor != null && candidate != null) {
            candidate.setPosition("supervisor");
            return ResponseEntity.status(200)
                    .body("You successfully promoted " + candidate.getName() + ", they are now a supervisor.");
        }else {
            return ResponseEntity.status(404).body("employee not fond");
        }
        return ResponseEntity.status(404).body("you not allow to apple promote");
    }

}