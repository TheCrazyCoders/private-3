package com.techsophy.vsc.bo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsophy.vsc.entity.Application;
import com.techsophy.vsc.entity.Appointment;
import com.techsophy.vsc.entity.ProcessFoStage;
import com.techsophy.vsc.entity.ProcessFoStatus;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.entity.VscFoTracker;
import com.techsophy.vsc.enums.FoProcessStage;
import com.techsophy.vsc.enums.FoProcessStatus;
import com.techsophy.vsc.repository.ProcessTrackersRepository;

@Service
public class ProcessTrackerService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProcessTrackersRepository processTrackersRepository;

	@Autowired
	private UtilityService utilityService;

	public VscFoTracker addToTracker(Appointment appointment, Application application, User user,
			FoProcessStage foProcessStage, FoProcessStatus foProcessStatus, String comments) {
		try {
			VscFoTracker vscFoTracker = new VscFoTracker();
			vscFoTracker.setAppointment(appointment);
			vscFoTracker.setApplication(application);
			vscFoTracker.setComment(comments);
			vscFoTracker.setVscUser(user);

			ProcessFoStage processFoStage = utilityService.getProcessFoStage(foProcessStage.name());
			vscFoTracker.setProcessFoStage(processFoStage);

			ProcessFoStatus processFoStatus = utilityService.getProcessFoStatus(foProcessStatus.name());
			vscFoTracker.setProcessFoStatus(processFoStatus);

			VscFoTracker response = processTrackersRepository.save(vscFoTracker);
			return response;
		} catch (Exception e) {
			logger.error("error in addToTracker method : " + e);
			throw e;
		}
	}
}
