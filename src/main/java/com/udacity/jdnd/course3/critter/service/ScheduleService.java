package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exception.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    /*public Schedule create(Schedule schedule){

        schedule = scheduleRepository.save(schedule);

        return schedule;
    }*/

    public Schedule create(Schedule schedule){



        /*for( Pet pet : schedule.getPets()){
            Optional<Pet> optionalPet = petRepository.findById(pet.getId());
            Pet p = optionalPet.orElseThrow(PetNotFoundException::new);

            p.getSchedules().add(schedule);
        }


        for( Employee employee : schedule.getEmployees()){

            Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
            Employee e = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);

            e.getSchedules().add(schedule);

        }*/

        schedule = scheduleRepository.save(schedule);

        return schedule;
    }

    public List<Schedule> getAllSchedules(){

        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules;

    }

    public List<Schedule> getScheduleForEmployee(Employee employee){

        List<Schedule> schedules =  scheduleRepository.findAllByEmployees(employee);
        return schedules;
    }


     public List<Schedule> getScheduleForPet(Pet pet){

        List<Schedule> schedules =  scheduleRepository.findAllByPets(pet);

        return schedules;

    }


    public List<Schedule> getScheduleForCustomer(Customer customer) {

        List<Pet> optionalPet = petRepository.findAllByCustomer(customer);

        List<Schedule> schedules =  scheduleRepository.findAllByPetsIn(optionalPet);

        return schedules;

    }

}
