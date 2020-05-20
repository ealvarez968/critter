package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pet {
    @Id
    @GeneratedValue
    private Long Id;

    private PetType petType;

    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    @JoinTable(
            name = "pet_schedule",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private List<Schedule> schedules = new ArrayList<>();


    public String getNotes() {
        return notes;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
