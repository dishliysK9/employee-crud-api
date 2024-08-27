package com.springcourse.crudemployees.dao;

import com.springcourse.crudemployees.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    //define field for entity manager
    private EntityManager entityManager;

    //set up constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    public List<Employee> findAll() {

        //create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("FROM Employee ", Employee.class);

        //execute and get result list
        List<Employee> employees = theQuery.getResultList();

        //return results
        return employees;
    }

    @Override
    public Employee findById(int theId) {

        //get the employee
        Employee theEmployee = entityManager.find(Employee.class, theId);

        //return the employee
        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {
        //save the employee

        /*"merge" means if the id == 0 it will save , otherwise it will update ||| later if we get request
        body including an id (which is not right) we can set the id to 0 in the postmapping method so it
        will perform a save for a new object and not actually update another which we dont want to be updated */

        Employee dbEmployee = entityManager.merge(theEmployee);

        //return db employee
        return dbEmployee;
    }

    @Override
    public void deleteById(int theId) {
        //find the employee
        Employee theEmployee = entityManager.find(Employee.class, theId);

        //delete it
        entityManager.remove(theEmployee);
    }
}
