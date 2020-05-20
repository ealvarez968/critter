package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;

import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer = customerRepository.save(customer);

        return convertCustomerToCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : customers){
            customerDTOS.add(convertCustomerToCustomerDTO(customer));
        }
        return customerDTOS;
    }

    public CustomerDTO getByPetId(Long petId) {

        Optional<Customer> optionalCustomer = customerRepository.findByPetsId(petId);
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);
        return convertCustomerToCustomerDTO(customer);
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        return customerDTO;
    }

}
