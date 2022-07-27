package com.centroxy.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.centroxy.entities.Employee;
import com.centroxy.services.IEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * @author Mahesh 07-Jul-2022
 */
@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	private final IEmployeeService employeeService;

	private final SimpMessagingTemplate template;

	@Autowired
	public EmployeeController(IEmployeeService employeeService, SimpMessagingTemplate template) {
		this.employeeService = employeeService;
		this.template = template;
	}

	// Creating New Employee
	@PostMapping(value = "/saveemployee", consumes = { "application/json", "multipart/form-data" })
	public ResponseEntity<String> saveEmployee(@Valid @RequestPart("employee") String employeeJson,
			@RequestParam("file") MultipartFile file) throws IOException {
		ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
		Employee employee = mapper.readValue(employeeJson, Employee.class);

		employee.setEmpProfileImage(file.getBytes());
		employee.setCreationDateTime(LocalDateTime.now());
		employee.setAvailable(true);

		Employee savedEmployee = employeeService.save(employee);

		if (savedEmployee != null) {
			return new ResponseEntity<String>("Employee Saved Suceessfully", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Employee Details Failed to save", HttpStatus.BAD_REQUEST);
		}
		

	}

	// This method is responsible for fetch the data from database based on the
	// primary key(empNo).
	@GetMapping("/getemployee/{empNo}")
	public Employee findEmployeeById(@PathVariable String empNo) throws UnsupportedEncodingException {
		Employee employeeDetails = employeeService.findEmployeeById(empNo);
		byte[] base64EncodedImage = Base64.getEncoder().encode(employeeDetails.getEmpProfileImage());
		String base64Encoded = new String(base64EncodedImage, "UTF-8");
		return employeeDetails;

	}
	//Retrieving all employees details through web socket call
	@SuppressWarnings("unchecked")
	@MessageMapping("/extractAllEmployees")
	public void fetchAllEmployees() throws Exception {

		System.out.println("API called...............");

		List<Employee> allEmployee = employeeService.getAllEmployee();

		JSONObject details = new JSONObject();
		
		details.put("employees", allEmployee);

		template.convertAndSend("/message/allemployee", details);

	}

	// This method helps us to fetch all the employee data from the database.
	@GetMapping("/getAllEmployees")
	public List<Employee> getAllEmployee() {
		List<Employee> allEmployee = employeeService.getAllEmployee();
		return allEmployee;
	}

	/**
	 * @author Balab 07-Jul-2022 5:32:06 pm
	 */
	// update the employee details
	@PutMapping(value = "/updateEmployee", consumes = { "application/json", "multipart/form-data" })
	public ResponseEntity<String> UpdateEmployee(@Valid @RequestPart("employee") Employee employee,
			@RequestParam("file") MultipartFile file) throws IOException {

		employee.setEmpProfileImage(file.getBytes());

		
		Employee updatedEmployee = employeeService.save(employee);
		
		if (updatedEmployee != null) {
			return new ResponseEntity<String>("Employee Updated Suceessfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Employee Details Failed to update", HttpStatus.BAD_REQUEST);

	}

}
