package com.centroxy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroxy.entities.InterviewPanel;
import com.centroxy.repositories.InterviewPanelRepository;

/**
 * @author sumita sethy 12-Jul-2022 10:50:39 am
 */
@Service
public class InterviewPanelServiceImpl implements IInterviewPanelService {

	private final InterviewPanelRepository interviewRepository;

	@Autowired
	public InterviewPanelServiceImpl(InterviewPanelRepository interviewRepository) {
		this.interviewRepository = interviewRepository;
	}

	@Override
	public InterviewPanel saveInterviewPanel(InterviewPanel interviewPanel) {
		return interviewRepository.save(interviewPanel);
	}

	// This is the implementation class by using this we can delete the data in
	// database.

	@Override
	public void deleteInterviewPanelById(long panelId) {
		interviewRepository.deleteById(panelId);
	}

	// This is the implementation class by using this we can update the data in
	// database.

	@Override
	public void updateInterviewPanelById(InterviewPanel interviewPanel) {
		interviewRepository.save(interviewPanel);
	}

	@Override
	public List<InterviewPanel> getAllInterviewPanel() {
		return interviewRepository.findAll();
	}

	@Override
	public InterviewPanel getInterviewPaneById(Long panelId) {
		Optional<InterviewPanel> findById = interviewRepository.findById(panelId);
		InterviewPanel interviewPanel = findById.get();
		return interviewPanel;
	}

}
