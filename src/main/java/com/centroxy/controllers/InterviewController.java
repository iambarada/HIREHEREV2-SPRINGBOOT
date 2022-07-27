package com.centroxy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.centroxy.entities.Interview;
import com.centroxy.services.IInterviewService;

@RestController
@RequestMapping("/interview")
@CrossOrigin(origins = "http://")
public class InterviewController {
	private IInterviewService interviewService;
	
	@Autowired
	public InterviewController(IInterviewService interviewService) {
		this.interviewService=interviewService;
	}
	
	
	@PostMapping("/addInterview")
	public ResponseEntity<String> saveInterview(@RequestPart Interview interview){
		System.out.println(interview);
		return new ResponseEntity<>("Interview saved successfully",HttpStatus.CREATED);
		
	}
	
	@GetMapping("/details/{id}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable String id) {
		Interview interviewObject = interviewService.findById(id);
		return new ResponseEntity<Interview>(interviewObject,HttpStatus.OK);   
    }
	
	@GetMapping("/all")
	public List<Interview> getInterviews() {
		return interviewService.getAllInterviews();
    }

}
