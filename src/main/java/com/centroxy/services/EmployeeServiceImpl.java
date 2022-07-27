package com.centroxy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centroxy.entities.Employee;
import com.centroxy.repositories.EmployeeRepository;
import com.centroxy.exceptions.InvalidDataByIdException;

/**
 * @author Mahesh 07-Jul-2022
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

	private final EmployeeRepository employeeRepo;

	// Constructor injection has implemented here
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	// Logic for save Employee Data in Database
	@Override
	public Employee save(Employee employee) {
		Employee savedEmployee = null;
		try {
			savedEmployee = employeeRepo.save(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedEmployee;

	}

	// Here i have developed the business logic to get employee data based on empNo.

	@Override
	public Employee findEmployeeById(String empNo) throws InvalidDataByIdException {

		Optional<Employee> employeeById = employeeRepo.findById(empNo);
		Employee employee = null;
		if (employeeById == null) {
			throw new InvalidDataByIdException();
		} else {
			employee = employeeById.get();
		}
		return employee;
	}

	// Helps us to get all employee details from database.

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> Employees = null;
		try {
			Employees = employeeRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Employees;
	}

}
