package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;



    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Customer customer = customerService.getById(petDTO.getOwnerId());
        pet.setCustomer(customer);
        pet = petService.create(pet, customer);
        PetDTO newPetDTO = new PetDTO();
        BeanUtils.copyProperties(pet, newPetDTO);
        return newPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet pet = petService.getById(petId);
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){

        List<PetDTO> petDTOs = new ArrayList<>();
        List<Pet> pets = petService.getAllCustomers();
        for (Pet p : pets){
            petDTOs.add(convertPetToPetDTO(p));
        }

        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        Customer customer = petService.getPetsByOwner(ownerId);

        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet p : customer.getPets()){
            petDTOs.add(convertPetToPetDTO(p));
        }

        return petDTOs;
    }

    private PetDTO convertPetToPetDTO(Pet pet){

        Customer customer = customerService.getById(pet.getCustomer().getId());

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(customer.getId());
        return petDTO;
    }
}
