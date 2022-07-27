package com.centroxy.services;

import java.util.List;

import com.centroxy.entities.InterviewPanel;

/**
 * @author sumita sethy 12-Jul-2022 10:51:01 am
 */
public interface IInterviewPanelService {
	public InterviewPanel saveInterviewPanel(InterviewPanel interviewPanel);

	List<InterviewPanel> getAllInterviewPanel();

	InterviewPanel getInterviewPaneById(Long panelId);

	void deleteInterviewPanelById(long panelId);

	void updateInterviewPanelById(InterviewPanel interviewPanel);

}
