package com.techsophy.vsc.bo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.bo.model.OutscanRequestPayload;
import com.techsophy.vsc.bo.model.OutscanResponsePayload;
import com.techsophy.vsc.bo.utils.Utilities;
import com.techsophy.vsc.entity.BoBatch;
import com.techsophy.vsc.entity.BoBatchDetail;
import com.techsophy.vsc.entity.ProcessBoStatus;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.enums.BoProcessStatus;
import com.techsophy.vsc.repository.BoBatchRepository;
import com.techsophy.vsc.repository.ProcessBoStatusRepository;
import com.techsophy.vsc.repository.UserDetailsRepository;

/**
 * 
 * @author thirupathi
 *
 */
@Service
public class OutscanService {
	private final Logger logger = LoggerFactory.getLogger(OutscanService.class);

	@Autowired
	private BoBatchRepository boBatchRepository;

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	ProcessBoStatusRepository processBoStatusRepository;

	@Autowired
	BoTrackerService boTrackerService;

	public OutscanResponsePayload getBatchDetails(String batchNo, String operation) {
		logger.info("In getBatchDetails() service");
		OutscanResponsePayload outscanResponse = new OutscanResponsePayload();
		try {
			List<String> eNumbersList = new ArrayList<String>();
			String status = operation.concat("B");
			if (BoProcessStatus.contains(status)) {
				BoBatch boBatchList =null;
				Integer statusId = BoProcessStatus.valueOf(status).getValue();
				Optional<BoBatch> boBatch = boBatchRepository.findByBatchNoAndProcessBoStatusId(batchNo, statusId);
				if (boBatch.isPresent()) {	
					boBatchList = boBatch.get();
					List<BoBatchDetail> batchList = boBatchList.getBoBatchDetails();
					for (BoBatchDetail batchDetails : batchList) {
						if(batchDetails.getBoScanDetail().getProcessBoStatus().getId()!=statusId) {
							eNumbersList = new ArrayList<String>();
							throw new ResourceNotFoundException(
									"Can not perform outscan on this batch due to some of the enumbers are moved from batch " +batchNo);
						}else {
							eNumbersList.add(batchDetails.getBoScanDetail().getEnumber());
						}												
					}
				}else {
					if(status.equalsIgnoreCase(BoProcessStatus.HMB.name())) {
						Integer statusId1 = BoProcessStatus.SHO.getValue();
						Optional<BoBatch> boBatch2 = boBatchRepository.findByBatchNoAndProcessBoStatusId(batchNo, statusId1);
						if (boBatch2.isPresent()) {	
							boBatchList = boBatch2.get();
							List<BoBatchDetail> batchList = boBatchList.getBoBatchDetails();
							for (BoBatchDetail batchDetails : batchList) {
								if(batchDetails.getBoScanDetail().getProcessBoStatus().getId()!=BoProcessStatus.SHI.getValue()) {
									eNumbersList = new ArrayList<String>();
									throw new ResourceNotFoundException(
											"Can not perform outscan on this batch due to some of the enumbers are moved from batch " +batchNo);
								}else {
									eNumbersList.add(batchDetails.getBoScanDetail().getEnumber());
								}
							}	
						}
					}
				}

				if (!eNumbersList.isEmpty()) {					
					outscanResponse.seteNumbers(eNumbersList);
					outscanResponse.setBatchNo(batchNo);
					outscanResponse.setVscCode(boBatchList.getServiceCenter().getCode());
					outscanResponse.setVscCity(boBatchList.getServiceCenter().getCity());
					outscanResponse.setStatus(boBatchList.getProcessBoStatus().getCode());
					outscanResponse.setDestVscCity(boBatchList.getOutscanServiceCenter().getCity());
				} else {
					throw new ResourceNotFoundException(
							"No data available with given batchno " + batchNo + " and operation " + operation);
				}
			} else {
				throw new ResourceNotFoundException("Given operation code " + operation + " is invalid");
			}
		} catch (Exception e) {
			logger.error("Failed to fetch the batch details" + e);
			throw e;
		}
		return outscanResponse;
	}

	public void updateOutscanDetails(OutscanRequestPayload requestPayload, User user) {
		logger.info("In updateOutscanDetails() service");
		try {
			String status = requestPayload.getOperation().concat("B");
			if (BoProcessStatus.contains(status)) {
				String updateStatus = requestPayload.getOperation().concat("O");
				if (!BoProcessStatus.contains(updateStatus)) {
					throw new ResourceNotFoundException("Empty or invalid batch numbers");
				}
				Integer statusId = BoProcessStatus.valueOf(status).getValue();
				Optional<ProcessBoStatus> boStatus = processBoStatusRepository.findByCode(updateStatus);
				if(!boStatus.isPresent()) {
					throw new ResourceNotFoundException("No status found to update with "+updateStatus +" status code");
				}						
				List<String> batchNos = requestPayload.getBatchNumbers();
				if (batchNos.isEmpty()) {
					throw new ResourceNotFoundException("Empty or invalid batch numbers ");
				}
				batchNos.forEach(batchNo -> {

					BoBatch boBatchDetails = null;
					List<String> eNumbers = new ArrayList<>();
					Optional<BoBatch> boBatch = boBatchRepository.findByBatchNoAndProcessBoStatusId(batchNo, statusId);

					if (boBatch.isPresent()) {							
						boBatchDetails = boBatch.get();
						List<BoBatchDetail> batchList = boBatchDetails.getBoBatchDetails();
						for (BoBatchDetail batchDetails : batchList) {
							if(batchDetails.getBoScanDetail().getProcessBoStatus().getId()!=statusId) {
								eNumbers = new ArrayList<>();
								throw new ResourceNotFoundException(
										"Can not perform outscan on this batch due to some of the enumbers are moved from batch " +batchNo);
							}else {
								batchDetails.getBoScanDetail().setProcessBoStatus(boStatus.get());
								batchDetails.getBoScanDetail().setUpdatedBy(user);
								batchDetails.getBoScanDetail().setUpdatedOn(new Date());							
								eNumbers.add(batchDetails.getBoScanDetail().getEnumber());
							}
						}												
					}else if(status.equalsIgnoreCase(BoProcessStatus.HMB.name())) {
						Integer statusId1 = BoProcessStatus.SHO.getValue();
						Optional<BoBatch> boBatch2 = boBatchRepository.findByBatchNoAndProcessBoStatusId(batchNo, statusId1);
						if (boBatch2.isPresent()) {	
							boBatchDetails = boBatch2.get();
							List<BoBatchDetail> batchList = boBatchDetails.getBoBatchDetails();
							for (BoBatchDetail batchDetails : batchList) {
								if(batchDetails.getBoScanDetail().getProcessBoStatus().getId()!=BoProcessStatus.SHI.getValue()) {
									eNumbers = new ArrayList<>();
									throw new ResourceNotFoundException(
											"Can not perform outscan on this batch due to some of the enumbers are moved from batch " + batchNo);
								}else {
									batchDetails.getBoScanDetail().setProcessBoStatus(boStatus.get());
									batchDetails.getBoScanDetail().setUpdatedBy(user);
									batchDetails.getBoScanDetail().setUpdatedOn(new Date());							
									eNumbers.add(batchDetails.getBoScanDetail().getEnumber());
								}
							}	
						}
					}

					if(!eNumbers.isEmpty()){
						boBatchDetails.setUpdatedBy(user);
						boBatchDetails.setProcessBoStatus(boStatus.get());
						boBatchDetails.setUpdatedBy(user);
						boBatchDetails.setRemarks(requestPayload.getComments());
						boBatchDetails.setAwbNumber(requestPayload.getAwbNumber());
						boBatchDetails.setUpdatedOn(new Date());
						boBatchRepository.save(boBatchDetails);
						try {
							boTrackerService.saveTackingDetails(eNumbers, user, boStatus.get(),
									Utilities.getBoProcessStage(requestPayload.getOperation().substring(0, 1)));
						} catch (Exception e) {
							logger.error("bo tracking failed for enumbers : " + eNumbers + ",vscUser : " + user
									+ "bostatus : " + boStatus.get().getValue() + "process stage is :"
									+ Utilities.getBoProcessStage(requestPayload.getOperation().substring(0, 1)));
						}
					}
				});

			} else {
				throw new ResourceNotFoundException(
						"Given operation code " + requestPayload.getOperation() + " is invalid");
			}
		} catch (Exception e) {
			logger.error("Failed to to update Outscan Details" + e);
			throw e;
		}
	}
}
