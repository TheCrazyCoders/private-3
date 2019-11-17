package com.techsophy.vsc.bo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsophy.vsc.entity.Application;
import com.techsophy.vsc.entity.ProcessBoStage;
import com.techsophy.vsc.entity.ProcessBoStatus;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.entity.VscBoTracker;
import com.techsophy.vsc.enums.BoProcessStage;
import com.techsophy.vsc.repository.ApplicationDetailsRepository;
import com.techsophy.vsc.repository.BoTrackerRepository;
import com.techsophy.vsc.repository.ProcessBoStageRepository;

/**
 * @author thirupathi
 *
 */
@Service
public class BoTrackerService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BoTrackerRepository boTrackerRepository;

	@Autowired
	private ProcessBoStageRepository processboStageRepository;

	@Autowired
	private ApplicationDetailsRepository applicationDetailsRepository;

	@Transactional
	public int saveTackingDetails(List<String> eNumbers, User user, ProcessBoStatus boStatus,
			BoProcessStage boProcessStage) {
		logger.info("In saveTackingDetails() service");
		
		try {
			List<VscBoTracker> botrakerList = new ArrayList<>();
			ProcessBoStage processBoStage = processboStageRepository.findByCode(boProcessStage.name())
					.orElseGet(ProcessBoStage::new);
			eNumbers.forEach(eNumber -> {
				Application application = applicationDetailsRepository.findByENumber(eNumber);
				VscBoTracker botracker = new VscBoTracker();
				botracker.setENumber(eNumber);
				botracker.setActionTime(new Date());
				// botracker.setMessage("test msg");thirupathi implement
				botracker.setProcessBoStage(processBoStage);
				botracker.setVscUser(user);
				botracker.setProcessBoStatus(boStatus);
				botracker.setApplication(application);
				botracker.setStatusCode(boStatus.getValue());
				botrakerList.add(botracker);
			});
			if (!botrakerList.isEmpty()) {
				boTrackerRepository.saveAll(botrakerList);
				return 0;
			} else {
				return botrakerList.size();
			}
		} catch (Exception e) {
			logger.error("Failed to save the tracking details" + e);
			throw e;
		}
	}

	public List<VscBoTracker> fetchBoTracker(String eNumber) {
		logger.info("in fetchBoTracker method");

		try {
			List<VscBoTracker> boTrackerList = boTrackerRepository.findByENumber(eNumber);
			return boTrackerList;
		} catch (Exception e) {
			logger.error("error in fetchBoTracker method " + e);
		}
		return null;
	}

	public List<VscBoTracker> fetchBoTracker(Integer id) {
		logger.info("in fetchBoTracker method");

		try {
			List<VscBoTracker> boTrackerList = boTrackerRepository.findByApplicationId(id);
			return boTrackerList;
		} catch (Exception e) {
			logger.error("error in fetchBoTracker method " + e);
		}
		return null;
	}
}
