package com.udacity.jdnd.course3.critter.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "customer" , fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Long getId() {
        return Id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public String getNotes() {
        return notes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
