package com.centroxy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.centroxy.entities.Employee;

/**
 * @author CHINMAYEE MOHAPATRA 7:10:51 pm 06-Jul-2022
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
