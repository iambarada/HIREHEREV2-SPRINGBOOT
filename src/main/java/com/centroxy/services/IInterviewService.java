package com.centroxy.services;

import java.util.List;

import com.centroxy.entities.Interview;

/**
 * @author Jogesh Dash 13-Jul-2022 6:00:46 am
 */
public interface IInterviewService {

	Interview createInterview(Interview interview);

	Interview updateInterview(Interview interview);

	void deleteInterview(String interviewId);

	boolean existsById(String interviewId);

	Interview findById(String id);

	List<Interview> getAllInterviews();

	Interview getInterviewId(String id);

}
