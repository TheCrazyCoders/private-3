package com.techsophy.vsc.bo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.entity.ApplicationStatus;
import com.techsophy.vsc.entity.AppointmentStatus;
import com.techsophy.vsc.entity.BookingType;
import com.techsophy.vsc.entity.EscalationAction;
import com.techsophy.vsc.entity.EscalationType;
import com.techsophy.vsc.entity.ProcessFoStage;
import com.techsophy.vsc.entity.ProcessFoStatus;
import com.techsophy.vsc.repository.ApplicationStatusRepository;
import com.techsophy.vsc.repository.AppointmentStatusRepository;
import com.techsophy.vsc.repository.BookingTypeRepository;
import com.techsophy.vsc.repository.EscalationActionRepository;
import com.techsophy.vsc.repository.EscalationTypeRepository;
import com.techsophy.vsc.repository.ProcessFoStageRepository;
import com.techsophy.vsc.repository.ProcessFoStatusRepository;

@Service
public class UtilityService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProcessFoStageRepository processFoStageRepository;

	@Autowired
	private ProcessFoStatusRepository processFoStatusRepository;

	@Autowired
	private BookingTypeRepository bookingTypeRepository;



	@Autowired
	private AppointmentStatusRepository appointmentStatusRepository;

	@Autowired
	private ApplicationStatusRepository aplicationStatusRepository;

	@Autowired
	private EscalationTypeRepository escalationTypeRepository;

	@Autowired
	private EscalationActionRepository escalationActionRepository;

	public ProcessFoStage getProcessFoStage(String stageCode) {
		try {
			stageCode = stageCode.toUpperCase();
			Optional<ProcessFoStage> stageOpt = processFoStageRepository.findByCode(stageCode);
			if (!stageOpt.isPresent()) {
				throw new ResourceNotFoundException("No process stage with " + stageCode);
			}
			return stageOpt.get();
		} catch (Exception e) {
			logger.error("error in getProcessFoStage method : " + e);
			throw new ResourceNotFoundException("unable to found process stage for " + stageCode);
		}
	}

	public ProcessFoStatus getProcessFoStatus(String statusCode) {
		try {
			statusCode = statusCode.toUpperCase();
			Optional<ProcessFoStatus> statusOpt = processFoStatusRepository.findByCode(statusCode);
			if (!statusOpt.isPresent()) {
				throw new ResourceNotFoundException("No process status with " + statusCode);
			}
			return statusOpt.get();
		} catch (Exception e) {
			logger.error("error in getProcessFoStatus method : " + e);
			throw new ResourceNotFoundException("unable to found process status for " + statusCode);
		}
	}

	public BookingType getBookingType(String statusCode) {
		try {
			statusCode = statusCode.toUpperCase();
			Optional<BookingType> bookingTypeOpt = bookingTypeRepository.findByCode(statusCode);
			if (!bookingTypeOpt.isPresent()) {
				throw new ResourceNotFoundException("No bookingType with " + statusCode);
			}
			return bookingTypeOpt.get();
		} catch (Exception e) {
			logger.error("error in getBookingType method : " + e);
			throw new ResourceNotFoundException("unable to found bookingType for " + statusCode);
		}
	}

	
	public AppointmentStatus getAppointmentStatus(String statusCode) {
		try {
			statusCode = statusCode.toUpperCase();
			Optional<AppointmentStatus> statusOpt = appointmentStatusRepository.findByCode(statusCode);
			if (!statusOpt.isPresent()) {
				throw new ResourceNotFoundException("Appointment status does not exist with " + statusCode);
			}
			return statusOpt.get();
		} catch (DataAccessException e) {
			logger.error("error in getAppointmentStatus method : " + e);
			throw new ResourceNotFoundException("unable to found Appointment status for " + statusCode);
		}
	}

	public ApplicationStatus getApplicationStatus(String statusCode) {
		try {
			statusCode = statusCode.toUpperCase();
			Optional<ApplicationStatus> statusOpt = aplicationStatusRepository.findByCode(statusCode);
			if (!statusOpt.isPresent()) {
				throw new ResourceNotFoundException("Application status does not exist with " + statusCode);
			}
			return statusOpt.get();
		} catch (DataAccessException e) {
			logger.error("error in getApplicationStatus method : " + e);
			throw new ResourceNotFoundException("unable to found Application status for " + statusCode);
		}
	}

	public EscalationType getEscalationType(String statusCode) {
		try {
			statusCode = statusCode.toUpperCase();
			Optional<EscalationType> statusOpt = escalationTypeRepository.findByCode(statusCode);
			if (!statusOpt.isPresent()) {
				throw new ResourceNotFoundException("Escalation type doesnot exist");
			}
			return statusOpt.get();
		} catch (DataAccessException e) {
			logger.error("error in getEscalationType method : " + e);
			throw new InternalError("unable to access data");
		}
	}

	public EscalationAction getEscalationAction(String statusCode) {
		try {
			statusCode = statusCode.toUpperCase();
			Optional<EscalationAction> statusOpt = escalationActionRepository.findByCode(statusCode);
			if (!statusOpt.isPresent()) {
				throw new ResourceNotFoundException("Escalation Action doesnot exist");
			}
			return statusOpt.get();
		} catch (DataAccessException e) {
			logger.error("error in getEscalationAction method : " + e);
			throw new InternalError("unable to access data");
		}
	}
}
