package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;

import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;

    @Transactional
    public Customer create(Customer customer) {

        customer = customerRepository.save(customer);

        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    public Customer getByPetId(Pet pet) {

        Optional<Customer> optionalCustomer = customerRepository.findById(pet.getCustomer().getId());
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);
        return customer;
    }


    public Customer getById(Long Id){
        Optional<Customer> optionalCustomer = customerRepository.findById(Id);
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);
        return customer;
    }

    public List<Long> getPetIds(List<Customer> customers){
        List<Long> ids = new ArrayList<>();
        for(Customer c: customers){
            ids.addAll(c.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        }

        return ids;


    }


}
