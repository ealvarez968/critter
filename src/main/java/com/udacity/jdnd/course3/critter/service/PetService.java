package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
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
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet create(Pet pet, Customer customer){

        pet = petRepository.save(pet);
        customer.getPets().add(pet);

        return pet;
    }

    public Pet create(Pet pet){

        pet = petRepository.save(pet);

        return pet;
    }

    public Pet getById(Long id){
        Optional<Pet> optionalPet = petRepository.findById(id);
        Pet pet = optionalPet.orElseThrow(PetNotFoundException::new);

        return pet;

    }

    public List<Pet> getAllCustomers() {
        List<Pet> pets = new ArrayList<>();
        pets.addAll(petRepository.findAll());

        return pets;

    }

    public Customer getPetsByOwner(Long ownerId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(ownerId);
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);

        return customer;


    }

    public Pet getPetById(Long Id){
        Optional<Pet> optionalPet = petRepository.findById(Id);
        Pet pet = optionalPet.orElseThrow(PetNotFoundException::new);
        return pet;
    }



    private PetDTO convertPetToPetDTO(Pet pet){

        Optional<Customer> optionalCustomer = customerRepository.findById(pet.getCustomer().getId());
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(customer.getId());
        return petDTO;
    }




}