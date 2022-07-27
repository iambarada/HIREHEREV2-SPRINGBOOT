package com.centroxy.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroxy.entities.Interview;
import com.centroxy.repositories.InterviewRepository;

/**
 * @author Jogesh Dash 13-Jul-2022 2:30:02 pm
 */
@Service
public class InterviewServiceImpl implements IInterviewService {
	private final InterviewRepository interviewRepository;

	// Constructor injection has implemented here
	@Autowired
	public InterviewServiceImpl(InterviewRepository interviewRepository) {
		this.interviewRepository = interviewRepository;
	}

	// Logic for save Employee Data in Database
	@Override
	public Interview createInterview(Interview interview) {
		return interviewRepository.save(interview);
	}

	// Logic for update Employee Data in Database
	@Override
	public Interview updateInterview(Interview interview) {
		Interview savedInterview = null;
		try {

			savedInterview = interviewRepository.save(interview);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedInterview;
	}

	@Override
	public void deleteInterview(String interviewId) {
		interviewRepository.deleteById(interviewId);
	}

	@Override
	public boolean existsById(String interviewId) {
		return interviewRepository.existsById(interviewId);
	}

	/**
	 * @author Diptiranjan Balabanataray 12-Jul-2022 5:21:51 pm
	 */
	@Override
	public Interview findById(String id) {
		Interview interview = null;
		Optional<Interview> findById = interviewRepository.findById(id);
		if (findById == null) {

			throw new NoSuchElementException();
		} else {
			interview = findById.get();
		}
		return interview;
	}

	@Override
	public List<Interview> getAllInterviews() {
		return interviewRepository.findAll();
	}
	
	@Override
	public Interview getInterviewId(String id) {
		return interviewRepository.findById(id).get();
	}


}
