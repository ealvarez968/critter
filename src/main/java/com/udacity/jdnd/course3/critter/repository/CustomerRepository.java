package com.udacity.jdnd.course3.critter.repository;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
   /*@Query("select c from Customer c  " +
            " join Pet p on p.customer.id  = c.id" +
            " where p.id = :petId  ")
    Optional<Customer> findByPetsId(@Param("petId") Long petId);*/


    //Optional<Customer> findByPetsId(Long petsId);



}
