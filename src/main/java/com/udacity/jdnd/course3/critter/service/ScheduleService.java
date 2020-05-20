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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ScheduleDTO create(ScheduleDTO scheduleDTO){
        //Pet pet = new Pet();

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);


        for( Long petId : scheduleDTO.getPetIds()){
            Optional<Pet> optionalPet = petRepository.findById(petId);
            Pet pet = optionalPet.orElseThrow(PetNotFoundException::new);

            schedule.getPets().add(pet);

            pet.getSchedules().add(schedule);
        }

        for( Long employeeId : scheduleDTO.getEmployeeIds()){
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
            Employee employee = optionalEmployee.orElseThrow(PetNotFoundException::new);
            schedule.getEmployees().add(employee);
            employee.getSchedules().add(schedule);

        }



        scheduleRepository.save(schedule);



        return convertScheduleToScheduleDTO(schedule);
    }

    public List<ScheduleDTO> getAllSchedules(){



        List<Schedule> schedules = scheduleRepository.findAll();

        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        for(Schedule s: schedules){
            schedulesDTO.add(convertScheduleToScheduleDTO(s));
        }

        return schedulesDTO;

    }

    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId){

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);
        List<Schedule> schedules =  scheduleRepository.findAllByEmployees(employee);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule s : schedules ){
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }

        return scheduleDTOS;
    }


     public List<ScheduleDTO> getScheduleForPet( Long petId){
        Optional<Pet> optionalPet = petRepository.findById(petId);
        Pet pet = optionalPet.orElseThrow(PetNotFoundException::new);

        List<Schedule> schedules =  scheduleRepository.findAllByPets(pet);


        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule s : schedules ){
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }

        return scheduleDTOS;

    }


    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Customer customer = customerOptional.orElseThrow(CustomerNotFoundException::new);

        List<Pet> optionalPet = petRepository.findAllByCustomer(customer);

        List<Schedule> schedules =  scheduleRepository.findAllByPetsIn(optionalPet);


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
