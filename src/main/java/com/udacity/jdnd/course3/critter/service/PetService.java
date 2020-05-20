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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public PetDTO create(PetDTO petDTO){
        //Pet pet = new Pet();
        Optional<Customer> optionalCustomer = customerRepository.findById(petDTO.getOwnerId());
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);

        Pet pet = convertPetDTOToPet(petDTO);

        pet = petRepository.save(pet);
        customer.getPets().add(pet);

        PetDTO savedPetDTO = new PetDTO();
        BeanUtils.copyProperties(pet, savedPetDTO);

        return convertPetToPetDTO(pet);
    }

    public PetDTO getById(Long id){
        Optional<Pet> optionalPet = petRepository.findById(id);
        Pet pet = optionalPet.orElseThrow(PetNotFoundException::new);

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;

    }

    public List<PetDTO> getAllCustomers() {
        List<Pet> pets = petRepository.findAll();
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet p : pets){
            petDTOs.add(convertPetToPetDTO(p));
        }
        return petDTOs;


    }

    public List<PetDTO> getPetsByOwner(Long ownerId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(ownerId);
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);



        List<CustomerDTO> customerDTOS = new ArrayList<>();


        List<Pet> pets = petRepository.findAll();
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet p : customer.getPets()){
            petDTOs.add(convertPetToPetDTO(p));
        }
        return petDTOs;


    }



    private PetDTO convertPetToPetDTO(Pet pet){

        Optional<Customer> optionalCustomer = customerRepository.findById(pet.getCustomer().getId());
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(customer.getId());
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){

        Optional<Customer> optionalCustomer = customerRepository.findById(petDTO.getOwnerId());
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);

        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customer);
        return pet;
    }


}
