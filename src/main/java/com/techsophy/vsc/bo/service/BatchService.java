package com.techsophy.vsc.bo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.vsc.bo.exception.InvalidDataException;
import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.bo.model.ENumberDetailsForBatchResponsePayload;
import com.techsophy.vsc.bo.model.PrepareToBatchRequestPayload;
import com.techsophy.vsc.bo.model.PrepareToBatchResponsePayload;
import com.techsophy.vsc.bo.utils.Utilities;
import com.techsophy.vsc.entity.Application;
import com.techsophy.vsc.entity.AuditStatus;
import com.techsophy.vsc.entity.BoBatch;
import com.techsophy.vsc.entity.BoBatchDetail;
import com.techsophy.vsc.entity.BoScanDetail;
import com.techsophy.vsc.entity.ProcessBoStatus;
import com.techsophy.vsc.entity.ServiceCenter;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.enums.AuditStatuses;
import com.techsophy.vsc.enums.CentersOrder;
import com.techsophy.vsc.repository.ApplicationDetailsRepository;
import com.techsophy.vsc.repository.AuditStatusRepository;
import com.techsophy.vsc.repository.BoBatchRepository;
import com.techsophy.vsc.repository.BoScanDetailRepository;
import com.techsophy.vsc.repository.ProcessBoStatusRepository;
import com.techsophy.vsc.repository.ServiceCenterDetailsRepository;

/**
 * @author srikanth
 *
 */
@Service
public class BatchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProcessBoStatusRepository processBoStatusRepository;

	@Autowired
	private BoScanDetailRepository boScanDetailRepository;

	@Autowired
	private AuditStatusRepository auditStatusRepository;

	@Autowired
	private ServiceCenterDetailsRepository serviceCenterDetailsRepository;

	@Autowired
	private ApplicationDetailsRepository applicationDetailsRepository;

	@Autowired
	private BoBatchRepository boBatchRepository;

	@Autowired
	private BoTrackerService boTrackerService;

	/**
	 * @param eNumber
	 * @param operation
	 * @return
	 */
	public ENumberDetailsForBatchResponsePayload getENumberDetailsByBoStatus(User user, String eNumber,
			String operation) {
		logger.info("inside getENumberDetailsByBoStatus method");
		try {
			String operationBatchStatusCode = operation + "B";
			Optional<ProcessBoStatus> operationBatchStatusOpt = processBoStatusRepository
					.findByCode(operationBatchStatusCode.toUpperCase());
			if (!operationBatchStatusOpt.isPresent()) {
				throw new ResourceNotFoundException("operation " + operation + " not found");
			}
			// ServiceCenter outScanToServiceCenter = null;

			/*
			 * this code is to check whether it is forward or reverse flow
			 */

			char[] operationArray = operation.toCharArray();

			Character fromCode = operationArray[0];
			Character toCode = operationArray[1];

			CentersOrder fromCenter = CentersOrder.valueOf(fromCode.toString());
			int fromValue = fromCenter.getValue();

			CentersOrder toCenter = CentersOrder.valueOf(toCode.toString());
			int toValue = toCenter.getValue();

			List<Integer> inscanCenterStatusIds = new ArrayList<>();

			if (fromValue < toValue) {
				/*
				 * it is in the forward flow , so the out scan service center id will be the
				 * current service center parent id
				 */

				try {
					for (int i = 1; i < fromValue; i++) {

						CentersOrder inCenter = CentersOrder.getCentersOrder(i);
						String temp = inCenter.name() + fromCode + "I";

						Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(temp);
						if (!processStatusOpt.isPresent()) {
							throw new ResourceNotFoundException("process status" + temp + " not found");
						}
						inscanCenterStatusIds.add(processStatusOpt.get().getId());
					}
				} catch (Exception e) {
					throw new ResourceNotFoundException(
							"no inscan stage codes found for batch code " + operationBatchStatusCode);
				}

			} else {
				/*
				 * it is in the reverse flow , so the out scan service center id will be the
				 * enumber's service center id
				 */
				/* need to verify it once again ...by srikanth */

				/* assuming all the applicants are belongs to same service center */

				if (fromCode.toString().equalsIgnoreCase(CentersOrder.M.name())) {
					String temp = toCode.toString() + fromCode.toString() + "I";

					Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(temp);
					if (!processStatusOpt.isPresent()) {
						throw new ResourceNotFoundException("process status" + temp + " not found");
					}
					inscanCenterStatusIds.add(processStatusOpt.get().getId());
				} else {
					try {
						for (int i = fromValue + 1; i <= 4; i++) {

							CentersOrder inCenter = CentersOrder.getCentersOrder(i);
							String temp = inCenter.name() + fromCode + "I";

							Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(temp);
							if (!processStatusOpt.isPresent()) {
								throw new ResourceNotFoundException("process status" + temp + " not found");
							}
							inscanCenterStatusIds.add(processStatusOpt.get().getId());
						}
					} catch (Exception e) {
						throw new ResourceNotFoundException(
								"no inscan stage codes found for batch code " + operationBatchStatusCode);
					}
				}
			}

			Optional<AuditStatus> auditStatusOpt = auditStatusRepository.findByCode(AuditStatuses.COMPLETE.name());
			if (!auditStatusOpt.isPresent()) {
				throw new ResourceNotFoundException(
						"audit status not found with status " + AuditStatuses.COMPLETE.name());
			}

			/* still pending ....service center validation */
			/* get all child and parent service centers by passing a service center */
			Optional<BoScanDetail> boScanDetailOpt = boScanDetailRepository
					.findByEnumberAndProcessBoStatusIdInAndAuditStatusId(eNumber, inscanCenterStatusIds,
							auditStatusOpt.get().getId());

			if (!boScanDetailOpt.isPresent()) {

				StringBuilder builder = new StringBuilder();
				for (Integer status : inscanCenterStatusIds) {
					Optional<ProcessBoStatus> boStatusOpt = processBoStatusRepository.findById(status);
					if (boStatusOpt.isPresent()) {
						builder.append(boStatusOpt.get().getCode() + ", ");
					}

				}

				throw new ResourceNotFoundException("enumber not found with statuses " + builder.toString());
			}

			BoScanDetail boScanDetail = boScanDetailOpt.get();

			ENumberDetailsForBatchResponsePayload payload = new ENumberDetailsForBatchResponsePayload();
			payload.seteNumber(boScanDetail.getEnumber());
			payload.setApplicantName(boScanDetail.getApplicantName());
			payload.setNationality(boScanDetail.getNationality().getValue());
			payload.setPassportNo(boScanDetail.getPassportNo());
			payload.setVisaType(boScanDetail.getVisaType().getValue());
			payload.setStatus(boScanDetail.getProcessBoStatus().getValue());
			payload.setVscCode(boScanDetail.getServiceCenter().getCode());
			payload.setVscCenter(boScanDetail.getServiceCenter().getCity());

			if (boScanDetail.getServiceCenter().getParentId() == null) {
				payload.setDestVscCenter(null);
			} else {
				Optional<ServiceCenter> parentServiceCenterOpt = serviceCenterDetailsRepository
						.findById(Integer.parseInt(boScanDetail.getServiceCenter().getParentId()));
				if (!parentServiceCenterOpt.isPresent()) {
					throw new ResourceNotFoundException(
							"No visa service center with id : " + boScanDetail.getServiceCenter().getParentId());
				}
				payload.setDestVscCenter(parentServiceCenterOpt.get().getCity());

			}

			int sizeOfBoBatch = boScanDetail.getBoBatchDetails().size();
			if (sizeOfBoBatch > 0) {
				BoBatch boBatch = boScanDetail.getBoBatchDetails().get(sizeOfBoBatch - 1).getBoBatch();
				payload.setVscCenter(boBatch.getServiceCenter().getCity());
				payload.setDestVscCenter(boBatch.getOutscanServiceCenter().getCity());
			}

			return payload;
		} catch (Exception e) {
			logger.error("error in getENumberDetailsByBoStatus method" + e);
			throw e;
		}
	}

	/**
	 * @param user
	 * @param batchDetails
	 * @return
	 */
	public PrepareToBatchResponsePayload attachEnumbersToBatch(User user, String batchDetails) {
		logger.info("inside attachEnumbersToBatch method");

		PrepareToBatchResponsePayload responsePayload = new PrepareToBatchResponsePayload();
		ObjectMapper mapper = new ObjectMapper();
		PrepareToBatchRequestPayload requestPayload;
		try {
			requestPayload = mapper.readValue(batchDetails, PrepareToBatchRequestPayload.class);
		} catch (Exception e) {
			logger.error("error in attachEnumbersToBatch method " + e);
			throw new InvalidDataException("invalid request body ");
		}

		try {

			Optional<BoBatch> existingBatchOpt = boBatchRepository.findByBatchNo(requestPayload.getBatchNo());
			if (existingBatchOpt.isPresent()) {
				throw new InvalidDataException("Batch no is already exist with : " + requestPayload.getBatchNo());
			}

			List<String> eNumbers = requestPayload.geteNumbers();

			if (eNumbers.size() <= 0) {
				throw new ResourceNotFoundException("no enumbers found");
			}

			String operationBatchStatusCode = requestPayload.getOperation() + "B";
			Optional<ProcessBoStatus> operationBatchStatusOpt = processBoStatusRepository
					.findByCode(operationBatchStatusCode.toUpperCase());
			if (!operationBatchStatusOpt.isPresent()) {
				throw new ResourceNotFoundException("operation " + requestPayload.getOperation() + " not found");
			}
			ProcessBoStatus operationBatchStatus = operationBatchStatusOpt.get();
			ServiceCenter outScanToServiceCenter = null;

			/*
			 * this code is to check whether it is forward or reverse flow
			 */

			char[] operationArray = requestPayload.getOperation().toCharArray();

			Character fromCode = operationArray[0];
			Character toCode = operationArray[1];

			CentersOrder fromCenter = CentersOrder.valueOf(fromCode.toString());
			int fromValue = fromCenter.getValue();

			CentersOrder toCenter = CentersOrder.valueOf(toCode.toString());
			int toValue = toCenter.getValue();

			List<Integer> inscanCenterStatusIds = new ArrayList<>();

			/* if ((fromValue < toValue) && toValue < 4) { */
			if (fromValue < toValue) {
				/*
				 * it is in the forward flow , so the out scan service center id will be the
				 * current service center parent id
				 */

				try {
					for (int i = 1; i < fromValue; i++) {

						CentersOrder inCenter = CentersOrder.getCentersOrder(i);
						String temp = inCenter.name() + fromCode + "I";

						Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(temp);
						if (!processStatusOpt.isPresent()) {
							throw new ResourceNotFoundException("process status" + temp + " not found");
						}
						inscanCenterStatusIds.add(processStatusOpt.get().getId());
					}
				} catch (Exception e) {
					throw new ResourceNotFoundException(
							"no inscan stage codes found for batch code " + operationBatchStatusCode);
				}

				String parentServiceCenterId = user.getServiceCenter().getParentId();
				if (parentServiceCenterId == null || parentServiceCenterId.equalsIgnoreCase("")) {
					/* if it is null then this service center is of type mission */
				} else {

					try {
						Optional<ServiceCenter> outScanToServiceCenterOpt = serviceCenterDetailsRepository
								.findById(Integer.parseInt(parentServiceCenterId.trim()));

						if (!outScanToServiceCenterOpt.isPresent()) {
							throw new ResourceNotFoundException(
									"no service center found with this id : " + parentServiceCenterId);
						}
						outScanToServiceCenter = outScanToServiceCenterOpt.get();

					} catch (Exception e) {
						throw new ResourceNotFoundException(
								"no service center found with this id : " + parentServiceCenterId);
					}

				}

			} else {
				/*
				 * it is in the reverse flow , so the out scan service center id will be the
				 * enumber's service center id
				 */
				/* need to verify it once again ...by srikanth */

				/* assuming all the applicants are belongs to same service center */

				if (fromCode.toString().equalsIgnoreCase(CentersOrder.M.name())) {
					String temp = toCode.toString() + fromCode.toString() + "I";

					Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(temp);
					if (!processStatusOpt.isPresent()) {
						throw new ResourceNotFoundException("process status" + temp + " not found");
					}
					inscanCenterStatusIds.add(processStatusOpt.get().getId());
				} else {
					try {
						for (int i = fromValue + 1; i <= 4; i++) {

							CentersOrder inCenter = CentersOrder.getCentersOrder(i);
							String temp = inCenter.name() + fromCode + "I";

							Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(temp);
							if (!processStatusOpt.isPresent()) {
								throw new ResourceNotFoundException("process status" + temp + " not found");
							}
							inscanCenterStatusIds.add(processStatusOpt.get().getId());
						}
					} catch (Exception e) {
						throw new ResourceNotFoundException(
								"no inscan stage codes found for batch code " + operationBatchStatusCode);
					}
				}
				/*
				 * need to verify all the appointemnts are belongs to same service center or not
				 * in reverse flow only if the stage is hub to spoke only
				 */

				List<Application> applicationList = applicationDetailsRepository.findByENumberIn(eNumbers);
				if (applicationList.size() == eNumbers.size()) {
					outScanToServiceCenter = applicationList.get(0).getAppointment().getServiceCenter();
					for (Application application : applicationList) {
						if (application.getAppointment().getServiceCenter().getId() != outScanToServiceCenter.getId()) {
							throw new ResourceNotFoundException("applications are not belongs to same service center ");
						}
					}
				} else {
					throw new ResourceNotFoundException("some enumbers are not valid pls check and rebatch");
				}
			}

			/*
			 * this code is to check whether it is forward or reverse flow
			 */

			BoBatch boBatch = new BoBatch();
			boBatch.setBatchNo(requestPayload.getBatchNo());
			boBatch.setServiceCenter(user.getServiceCenter());
			boBatch.setOutscanServiceCenter(outScanToServiceCenter);
			boBatch.setRemarks("nothing");
			boBatch.setProcessBoStatus(operationBatchStatus);
			boBatch.setCreatedBy(user);
			boBatch.setCreatedOn(new Date());

			Optional<AuditStatus> auditStatusOpt = auditStatusRepository.findByCode(AuditStatuses.COMPLETE.name());
			if (!auditStatusOpt.isPresent()) {
				throw new ResourceNotFoundException(
						"audit status not found with status " + AuditStatuses.COMPLETE.name());
			}

			AuditStatus auditStatus = auditStatusOpt.get();

			List<BoBatchDetail> boBatchDetailList = new ArrayList<>();
			for (String eNumber : eNumbers) {
				BoBatchDetail boBatchDetail = new BoBatchDetail();
				boBatchDetail.setBoBatch(boBatch);

				Optional<BoScanDetail> boScanDetailOpt = boScanDetailRepository
						.findByEnumberAndProcessBoStatusIdInAndAuditStatusId(eNumber, inscanCenterStatusIds,
								auditStatus.getId());
				if (!boScanDetailOpt.isPresent()) {
					throw new ResourceNotFoundException(
							"this enumber is not avalilable for batching at this stage :" + eNumber);
				}
				boScanDetailOpt.get().setProcessBoStatus(operationBatchStatus);
				boBatchDetail.setBoScanDetail(boScanDetailOpt.get());
				boBatchDetail.setCreatedBy(user);
				boBatchDetail.setCreatedOn(new Date());

				boBatchDetailList.add(boBatchDetail);
			}

			if (boBatchDetailList.size() <= 0) {
				throw new ResourceNotFoundException("Enumbers provided are not valid ,no batch done");
			}

			boBatch.setNoOfApplications(boBatchDetailList.size());
			boBatch.setBoBatchDetails(boBatchDetailList);

			BoBatch response;
			try {
				response = boBatchRepository.save(boBatch);
			} catch (Exception e) {
				logger.error("error in attachEnumbersToBatch method : " + e);
				throw new InternalError("unable to create batch");
			}

			try {
				boTrackerService.saveTackingDetails(eNumbers, user, operationBatchStatus,
						Utilities.getBoProcessStage(fromCode.toString()));
			} catch (Exception e) {
				logger.error("bo tracking failed for enumbers : " + eNumbers + ",vscUser : " + user + "bostatus : "
						+ operationBatchStatus + "process stage is :"
						+ Utilities.getBoProcessStage(fromCode.toString()));
			}

			responsePayload.setBatchNo(response.getBatchNo());
			responsePayload.setNoOfAddedApplications(response.getBoBatchDetails().size());

			return responsePayload;

		} catch (ResourceNotFoundException e) {
			logger.error("error in attachEnumbersToBatch method : " + e);
			throw e;
		} catch (InvalidDataException e) {
			logger.error("error in attachEnumbersToBatch method : " + e);
			throw e;
		} catch (Exception e) {
			logger.error("error in attachEnumbersToBatch method : " + e);
			throw new InternalError("unable to create batch");
		}
	}
}
