package com.udacity.jdnd.course3.critter.entity;


import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    private List<Pet> pets = new ArrayList<>();

    private LocalDate date;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "EmployeeSkill", joinColumns = @JoinColumn(name = "schedule_id", nullable=true))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public List<Pet> getPets() {
        return pets;
    }
}
