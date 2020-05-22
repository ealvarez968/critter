package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);


        for( Long petId : scheduleDTO.getPetIds()){

            Pet pet = petService.getById(petId);
            schedule.getPets().add(pet);
            pet.getSchedules().add(schedule);
        }


        for( Long employeeId : scheduleDTO.getEmployeeIds()){
            Employee employee = employeeService.getById(employeeId);
            schedule.getEmployees().add(employee);
            employee.getSchedules().add(schedule);

        }

        schedule = scheduleService.create(schedule);

        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        List<Schedule> schedules = scheduleService.getAllSchedules();

        for(Schedule s: schedules){
            schedulesDTO.add(convertScheduleToScheduleDTO(s));
        }
        return schedulesDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable Long petId) {
        //** validar que devuelva
        Pet pet = petService.getById(petId);

        List<Schedule> schedules = scheduleService.getScheduleForPet(pet);

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule s : schedules ){
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }


        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long employeeId) {
        Employee employee = employeeService.getById(employeeId);

        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employee);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule s : schedules ){
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }

        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getById(customerId);

        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customer);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule s : schedules ){
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }
        return scheduleDTOS;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream()
                .map(employee -> employee.getId()).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        return scheduleDTO;
    }
}
