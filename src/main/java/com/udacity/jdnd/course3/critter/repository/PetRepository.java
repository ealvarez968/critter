package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByCustomer(Customer customer);

    @Query("select p from Pet p " +
            " join Customer c on c = p.customer" +
            " where c.id = :customer_id and p.id = :pet_id ")
    Pet findByCustomer(@Param("customer_id") Long customer_id, @Param("pet_id") Long pet_id);

}
