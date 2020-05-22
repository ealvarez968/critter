package com.udacity.jdnd.course3.critter.entity;


import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ElementCollection(fetch = FetchType.LAZY)
    //@CollectionTable(name = "DayOfWeek", joinColumns = @JoinColumn(name = "employee_id") )
    private Set<DayOfWeek> daysAvailable;

    private LocalDate date;

    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    //@CollectionTable( name = "EmployeeSkill", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    @JoinTable(
            name = "employee_schedule",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private List<Schedule> schedules = new ArrayList<>();


    public List<Schedule> getSchedules() {
        return schedules;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return Id;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
