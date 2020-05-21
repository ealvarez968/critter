package com.udacity.jdnd.course3.critter.service;



import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {

        employee = employeeRepository.save(employee);

        return employee;
    }

    public Employee getById(Long id) {
        Optional<Employee> optionalEmployee =employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);

        return employee;
    }


    public void setAvailability(Set<DayOfWeek> daysAvailable,long employeeId){

        Optional<Employee> optionalEmployee =employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);

        employee.setDaysAvailable(daysAvailable);

        employeeRepository.save(employee);
    }

    public Set<Employee>  findEmployeesForService(Employee emp){
        Set<Employee> employees =employeeRepository.findBySkillsInAndDaysAvailable(emp.getSkills(), emp.getDate().getDayOfWeek());

        return employees;

    }

    /*public List<EmployeeDTO>  findEmployeesForService(EmployeeRequestDTO employeeDTO){

        Employee emp = new Employee();
        BeanUtils.copyProperties(employeeDTO, emp);
        List<EmployeeSkill> mainList = new ArrayList<>();
        mainList.addAll(emp.getSkills());

        Set<DayOfWeek> dayOfWeek = new HashSet<>();
        dayOfWeek.add(employeeDTO.getDate().getDayOfWeek());
       Set<Employee> employees =employeeRepository.findBySkillsInAndDaysAvailable(emp.getSkills(), employeeDTO.getDate().getDayOfWeek());


       // List<Customer> customers = customerRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        for (Employee employee : employees){

            if(emp.getSkills().size() == 1){
                employeeDTOS.add(convertEmployeeToEmployeeDTO(employee));
            }else if ( emp.getSkills().size() >= 2 ){

                if(employee.getSkills().containsAll(emp.getSkills())){
                    employeeDTOS.add(convertEmployeeToEmployeeDTO(employee));
                }

            }



        }
        return employeeDTOS;

    }*/




}
