package com.techsophy.vsc.bo.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.entity.Application;
import com.techsophy.vsc.entity.ApplicationStatus;
import com.techsophy.vsc.entity.Escalation;
import com.techsophy.vsc.entity.EscalationType;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.enums.EscalationTypes;
import com.techsophy.vsc.enums.FoApplicationStatus;
import com.techsophy.vsc.enums.FoProcessStage;
import com.techsophy.vsc.enums.FoProcessStatus;
import com.techsophy.vsc.repository.ApplicationDetailsRepository;
import com.techsophy.vsc.repository.ApplicationStatusRepository;
import com.techsophy.vsc.repository.EscalationRepository;
import com.techsophy.vsc.repository.EscalationTypeRepository;

@Service
public class EscalationService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private EscalationTypeRepository escalationTypeRepository;

	@Autowired
	private EscalationRepository escalationRepository;

	@Autowired
	private ApplicationStatusRepository aplicationStatusRepository;

	@Autowired
	private ApplicationDetailsRepository applicationDetailsRepository;

	@Autowired
	private ProcessTrackerService processTrackerService;

	public Escalation addToEscalation(User user, String eNumber, String reason) {
		try {
			Escalation escalationRes = null;

			Optional<EscalationType> escalationTypeOPt = escalationTypeRepository
					.findByCode(EscalationTypes.ADT.name());
			if (!escalationTypeOPt.isPresent()) {
				throw new ResourceNotFoundException("Escalation type doesnot exist");
			}
			if (eNumber != null) {

				Escalation escalation = new Escalation();
				Optional<Application> applicationOPt = applicationDetailsRepository
						.findByAppointmentServiceCenterIdAndENumber(user.getServiceCenter().getId(), eNumber);

				if (applicationOPt.isPresent()) {
					escalation.setApplication(applicationOPt.get());
				}
				escalation.setAppointment(applicationOPt.get().getAppointment());

				escalation.setEscalationType(escalationTypeOPt.get());
				escalation.setEscalationDate(new Date());
				escalation.setCreatedBy(user);
				escalation.setReason(reason);
				escalation.setServiceCenter(user.getServiceCenter());
				escalationRes = escalationRepository.save(escalation);

			}

			Optional<ApplicationStatus> applicationStatusOPt = aplicationStatusRepository
					.findByCode(FoApplicationStatus.ESCALATION.name());
			if (!applicationStatusOPt.isPresent()) {
				throw new ResourceNotFoundException("Application status doesnot exist");
			}

			if (eNumber != null) {
				Optional<Application> applicationOPt = applicationDetailsRepository
						.findByAppointmentServiceCenterIdAndENumber(user.getServiceCenter().getId(), eNumber);
				if (applicationOPt.isPresent()) {
					Application application = applicationOPt.get();
					application.setApplicationStatus(applicationStatusOPt.get());
					applicationDetailsRepository.save(application);
					try {
						processTrackerService.addToTracker(application.getAppointment(), application, user,
								FoProcessStage.ADT, FoProcessStatus.ESCL, reason);

					} catch (Exception e) {
						logger.error("error in addToEscalation method : " + e);
					}
				}
			}
			return escalationRes;

		} catch (ResourceNotFoundException e) {
			logger.error("error in addToEscalation method : " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("error in addToEscalation method : " + e);
			throw e;
		}

	}
}
