package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
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


@Transactional
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    public Customer create(Customer customer) {

        customer = customerRepository.save(customer);

        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    public Customer getByPetId(Long petId) {

        Optional<Customer> optionalCustomer = customerRepository.findByPetsId(petId);
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);
        return customer;
    }


    public Customer getById(Long Id){
        Optional<Customer> optionalCustomer = customerRepository.findById(Id);
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);
        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        return customerDTO;
    }

}
