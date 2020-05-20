package com.udacity.jdnd.course3.critter.repository;


import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /*@Query( "select e from Employee e" +
            " join com.udacity.jdnd.course3.critter.user.EmployeeSkill es on es.id = e.id " +
            " where es.skills in :skills" )*/
    Set<Employee> findBySkillsInAndDaysAvailable(Set<EmployeeSkill> employeeSkill, DayOfWeek daysAvailable);

    /*@Query( "select e from Employee e " +
            " where e.skills in ( :skills )" )
    List<Employee> hasSkills(@Param("skills") Set<EmployeeSkill> employeeSkill);*/
    //Set<Employee> findBySkillsAndDaysAvailable(@Param("skills") Set<EmployeeSkill> employeeSkill,@Param("daysAvailable") Set<DayOfWeek> daysAvailable);

}
