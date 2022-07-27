package com.centroxy.services;

import java.util.List;

import com.centroxy.entities.Employee;

/**
 * @author Mahesh 07-Jul-2022
 */
public interface IEmployeeService {
	public Employee save(Employee employee);

	public Employee findEmployeeById(String empNo);

	public List<Employee> getAllEmployee();
}
