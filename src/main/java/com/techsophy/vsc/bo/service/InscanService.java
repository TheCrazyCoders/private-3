package com.techsophy.vsc.bo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.vsc.bo.controller.InscanController;
import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.bo.model.InscanDetailsReponsePayload;
import com.techsophy.vsc.bo.model.InscanRequestPayload;
import com.techsophy.vsc.bo.model.InscanResponsePayload;
import com.techsophy.vsc.bo.model.InscanforEnumberRequestPayload;
import com.techsophy.vsc.bo.model.MasterResponsePayload;
import com.techsophy.vsc.bo.utils.Utilities;
import com.techsophy.vsc.entity.Application;
import com.techsophy.vsc.entity.BoBatch;
import com.techsophy.vsc.entity.BoBatchDetail;
import com.techsophy.vsc.entity.BoScanDetail;
import com.techsophy.vsc.entity.ProcessBoStatus;
import com.techsophy.vsc.entity.ServiceCenter;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.entity.VscBiometricDetail;
import com.techsophy.vsc.entity.VscDeliveryDetail;
import com.techsophy.vsc.enums.AuditStatuses;
import com.techsophy.vsc.enums.BoProcessStatus;
import com.techsophy.vsc.enums.DeliveryStatuses;
import com.techsophy.vsc.enums.DeliveryTypes;
import com.techsophy.vsc.enums.SearchType;
import com.techsophy.vsc.repository.ApplicationDetailsRepository;
import com.techsophy.vsc.repository.AuditStatusRepository;
import com.techsophy.vsc.repository.BiometricDetailsRepository;
import com.techsophy.vsc.repository.BoBatchDetailRepository;
import com.techsophy.vsc.repository.BoBatchRepository;
import com.techsophy.vsc.repository.BoScanDetailRepository;
import com.techsophy.vsc.repository.DeliveryDetailsRepository;
import com.techsophy.vsc.repository.DeliveryStatusRepository;
import com.techsophy.vsc.repository.DeliveryTypeRepository;
import com.techsophy.vsc.repository.ProcessBoStatusRepository;
import com.techsophy.vsc.repository.ServiceCenterDetailsRepository;
import com.techsophy.vsc.repository.VisaStatusRepository;

@Transactional
@Service
public class InscanService {
	private final Logger logger = LoggerFactory.getLogger(InscanController.class);

	@Autowired
	private BiometricDetailsRepository biometricDetailsRepository;

	@Autowired
	private ApplicationDetailsRepository applicationDetailsRepository;

	@Autowired
	private BoScanDetailRepository boScanDetailRepository;

	@Autowired
	private BoBatchRepository boBatchRepository;

	@Autowired
	private BoBatchDetailRepository boBatchDetailRepository;

	@Autowired
	private ProcessBoStatusRepository processBoStatusRepository;

	@Autowired
	private AuditStatusRepository auditStatusRepository;

	@Autowired
	private DeliveryDetailsRepository deliveryDetailsRepository;

	@Autowired
	private DeliveryStatusRepository deliveryStatusRepository;

	@Autowired
	private DeliveryTypeRepository deliveryTypeRepository;

	@Autowired
	private VisaStatusRepository visaStatusRepository;

	@Autowired
	private BoTrackerService boTrackerService;

	@Autowired
	private ServiceCenterDetailsRepository serviceCenterDetailsRepository;

	@Transactional
	public InscanResponsePayload getEnumberDetails(String searchType, String searchValue, String operation,
			User user) {
		logger.info("In getEnumberDetails() service");
		InscanResponsePayload inscanResponsePayload = new InscanResponsePayload();
		try {
			String operationStatus = operation.concat("O");

			if (operation.startsWith("F")) {
				inscanResponsePayload = getEnumberDetailsFromFo(searchType, searchValue, user);
			} else {
				if (BoProcessStatus.contains(operationStatus)) {
					inscanResponsePayload = getInscanEnumberDetails(searchType, searchValue, operationStatus);
				}
			}
		} catch (Exception e) {
			logger.error("failed to fetch the enumber details:" + e);
			throw e;
		}
		return inscanResponsePayload;
	}

	private InscanResponsePayload getEnumberDetailsFromFo(String searchType, String searchValue, User user) {
		logger.info("In getEnumberDetailsFromFo() service");
		InscanResponsePayload inscanResponsePayload = new InscanResponsePayload();

		try {
			List<InscanDetailsReponsePayload> inscanDetailResponseList = new ArrayList<>();
			if (searchType.equalsIgnoreCase(SearchType.eNumber.name())) {
				InscanDetailsReponsePayload inscanDetailResponsePayload = new InscanDetailsReponsePayload();
				Application application = applicationDetailsRepository.findByENumber(searchValue);
				if (application != null) {
					Optional<BoScanDetail> boScanDetailOptional = boScanDetailRepository.findByEnumber(searchValue);
					if (boScanDetailOptional.isPresent()
							&& boScanDetailOptional.get().getAuditStatus().getCode() != AuditStatuses.HOLD.name()) {
						List<ServiceCenter> serviceCenterList = serviceCenterDetailsRepository
								.findByParentId(String.valueOf(user.getServiceCenter().getParentId()));
						Map<Integer, ServiceCenter> serviceCenterMap = new HashMap<>();
						for (ServiceCenter serviceCenter : serviceCenterList) {
							serviceCenterMap.put(serviceCenter.getId(), serviceCenter);
						}
						ServiceCenter serviceCenter = serviceCenterMap
								.get(boScanDetailOptional.get().getInscanServiceCenter().getId());
						if (serviceCenter != null) {
							throw new ResourceNotFoundException("E-Number already in-scanned");
						} else {
							throw new ResourceNotFoundException("ENumber doesnot belongs to service center with "
									+ user.getServiceCenter().getCode() + "");
						}

					}

					int applicantId = application.getId();
					Optional<VscBiometricDetail> biometricDetailOpt = biometricDetailsRepository
							.findByApplicationId(applicantId);
					if (!biometricDetailOpt.isPresent()) {
						throw new ResourceNotFoundException("Biometric details doesnot exist");
					}
					VscBiometricDetail biometricDetail = biometricDetailOpt.get();
					if (biometricDetail.getStatus() == 1) {
						inscanDetailResponsePayload
								.setApplicantName(application.getFirstName() + " " + application.getLastName());
						inscanDetailResponsePayload.seteNumber(application.getENumber());
						inscanDetailResponsePayload.setNationality(application.getNationality().getValue());
						inscanDetailResponsePayload.setPassportNo(application.getPassportNo());
						inscanDetailResponsePayload.setVisaType(application.getAppointment().getVisaType().getValue());
						inscanDetailResponsePayload
								.setBiometricDoneBy(biometricDetailOpt.get().getCreatedBy().getUserName());
						inscanDetailResponsePayload.setIsAuditMandate(application.getIsAuditMandate());
						inscanDetailResponsePayload
								.setSourceVscCenter(application.getAppointment().getServiceCenter().getCity());
						inscanDetailResponsePayload
								.setDestVscCenter(application.getAppointment().getServiceCenter().getCity());
						inscanDetailResponseList.add(inscanDetailResponsePayload);
					}
					inscanResponsePayload.setInscanData(inscanDetailResponseList);

				}
			}
		} catch (Exception e) {
			logger.error("failed to fetch the enumber details from FO:" + e);
			throw e;
		}
		return inscanResponsePayload;

	}

	private InscanResponsePayload getInscanEnumberDetails(String searchType, String searchValue, String operatn) {
		logger.info("In getInscanEnumberDetails() service");
		InscanResponsePayload inscanResponsePayload = new InscanResponsePayload();
		try {
			Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(operatn.toUpperCase());
			if (!processStatusOpt.isPresent()) {
				throw new ResourceNotFoundException("Process status doesnot exist for operation " + operatn + "");
			}
			List<InscanDetailsReponsePayload> inscanDetailResponseList = new ArrayList<>();
			if (searchType.equalsIgnoreCase(SearchType.eNumber.name())) {
				InscanDetailsReponsePayload inscanDetailResponsePayload = new InscanDetailsReponsePayload();
				Optional<BoScanDetail> boScanDetailOpt = boScanDetailRepository
						.findByEnumberAndProcessBoStatusId(searchValue, processStatusOpt.get().getId());
				if (!boScanDetailOpt.isPresent()) {
					throw new ResourceNotFoundException("Scan details doesnot exist for operation " + operatn + "");
				}
				/*
				 * Optional<BoBatchDetail> boBatchDetailOpt = boBatchDetailRepository
				 * .findByBoScanDetailId(boScanDetailOpt.get().getId()); if
				 * (!boBatchDetailOpt.isPresent()) { throw new
				 * ResourceNotFoundException("Scan details doesnot exist for operation " +
				 * operatn + ""); }
				 */
				BoScanDetail boScanDetail = boScanDetailOpt.get();

				inscanDetailResponsePayload.setApplicantName(boScanDetail.getApplicantName());
				inscanDetailResponsePayload.seteNumber(boScanDetail.getEnumber());
				inscanDetailResponsePayload.setNationality(boScanDetail.getNationality().getValue());
				inscanDetailResponsePayload.setPassportNo(boScanDetail.getPassportNo());
				inscanDetailResponsePayload.setVisaType(boScanDetail.getVisaType().getValue());

				int sizeOfBoBatch = boScanDetail.getBoBatchDetails().size();
				if (sizeOfBoBatch > 0) {
					BoBatch boBatch = boScanDetail.getBoBatchDetails().get(sizeOfBoBatch - 1).getBoBatch();
					inscanDetailResponsePayload.setSourceVscCenter(boBatch.getServiceCenter().getCity());
					inscanDetailResponsePayload.setDestVscCenter(boBatch.getOutscanServiceCenter().getCity());
				}

				inscanDetailResponseList.add(inscanDetailResponsePayload);
				inscanResponsePayload.setInscanData(inscanDetailResponseList);
			} else if (searchType.equalsIgnoreCase(SearchType.batchNo.name())) {
				Optional<BoBatch> boBatchOpt = boBatchRepository.findByBatchNoAndProcessBoStatusId(searchValue,
						processStatusOpt.get().getId());
				if (!boBatchOpt.isPresent()) {
					throw new ResourceNotFoundException("Batch doesnot exist for operation " + operatn + "");
				}
				int boBatchId = boBatchOpt.get().getId();
				List<BoBatchDetail> boBatchDetailLst = boBatchDetailRepository.findByBoBatchId(boBatchId);
				if (boBatchDetailLst.isEmpty()) {
					throw new ResourceNotFoundException("Batch details doesnot exist for operation " + operatn + "");
				}
				boBatchDetailLst.forEach(boBatchDetail -> {
					InscanDetailsReponsePayload inscanDetailResponsePayload = new InscanDetailsReponsePayload();
					BoScanDetail boScanDetail = boBatchDetail.getBoScanDetail();
					inscanDetailResponsePayload.setApplicantName(boScanDetail.getApplicantName());
					inscanDetailResponsePayload.seteNumber(boScanDetail.getEnumber());
					inscanDetailResponsePayload.setNationality(boScanDetail.getNationality().getValue());
					inscanDetailResponsePayload.setPassportNo(boScanDetail.getPassportNo());
					inscanDetailResponsePayload.setVisaType(boScanDetail.getVisaType().getValue());
					/* added for validation */
					inscanDetailResponsePayload.setBatchNo(boBatchDetail.getBoBatch().getBatchNo());

					inscanDetailResponsePayload
							.setSourceVscCenter(boBatchDetail.getBoBatch().getServiceCenter().getCity());
					inscanDetailResponsePayload
							.setDestVscCenter(boBatchDetail.getBoBatch().getOutscanServiceCenter().getCity());

					inscanDetailResponseList.add(inscanDetailResponsePayload);
				});
				inscanResponsePayload.setInscanData(inscanDetailResponseList);
			}

		} catch (Exception e) {
			logger.error("failed to fetch the enumber details during inscan:" + e);
			throw e;
		}
		return inscanResponsePayload;
	}

	@Transactional
	public Integer saveEnumberDetails(String enumberObj, User user) throws Exception {
		logger.info("In saveEnumberDetails() service");
		ObjectMapper mapper = new ObjectMapper();
		Integer updatedEnumCount = 0;
		try {
			List<String> enumberList = new ArrayList<>();
			InscanRequestPayload inscanRequestPayload = mapper.readValue(enumberObj, InscanRequestPayload.class);
			String operationStatus = inscanRequestPayload.getOperation();
			String operationStatusNotFromFO = operationStatus;
			if (operationStatus.startsWith("F")) {
				String operatinStatus = operationStatus.concat("I");
				List<InscanforEnumberRequestPayload> inscanEnumberList = inscanRequestPayload.geteNumbers();
				Optional<ProcessBoStatus> processStatusOpt = processBoStatusRepository.findByCode(operatinStatus);
				if (!processStatusOpt.isPresent()) {
					throw new ResourceNotFoundException(
							"Process status doesnot exist for operation " + operationStatus + "");
				}

				/*
				 * inscanEnumberList.forEach(inscanEnum -> {
				 *
				 * enumberList.add(inscanEnum); });
				 */
				for (InscanforEnumberRequestPayload enumber : inscanEnumberList) {
					enumberList.add(enumber.geteNumber());
				}
				List<VscBiometricDetail> applicationList = biometricDetailsRepository
						.findByApplicationENumberInAndStatus(enumberList, (byte) 1);
				for (VscBiometricDetail application : applicationList) {
					Application applicatin = application.getApplication();
					BoScanDetail boScanDetail = new BoScanDetail();
					boScanDetail.setApplicantName(applicatin.getFirstName() + " " + applicatin.getLastName());
					boScanDetail.setNationality(applicatin.getNationality());
					boScanDetail.setPassportNo(applicatin.getPassportNo());
					boScanDetail.setEnumber(applicatin.getENumber());
					boScanDetail.setVisaType(applicatin.getAppointment().getVisaType());
					boScanDetail.setCreatedBy(user);
					boScanDetail.setProcessBoStatus(processStatusOpt.get());
					boScanDetail.setServiceCenter(user.getServiceCenter());
					boScanDetail.setInscanServiceCenter(application.getAppointment().getServiceCenter());
					boScanDetail.setAuditStatus(auditStatusRepository.findById(AuditStatuses.OPEN.getValue()).get());
					boScanDetail.setIsAuditMandate(applicatin.getIsAuditMandate());
					BoScanDetail boscanDetail = boScanDetailRepository.save(boScanDetail);
					if (boscanDetail != null) {
						updatedEnumCount++;
					}
				}

				try {
					boTrackerService.saveTackingDetails(enumberList, user, processStatusOpt.get(),
							Utilities.getBoProcessStage(inscanRequestPayload.getOperation().substring(1)));
				} catch (Exception e) {
					logger.error("bo tracking failed for enumbers : " + enumberList + ",vscUser : " + user
							+ "bostatus : " + processStatusOpt.get().getValue() + "process stage is :"
							+ Utilities.getBoProcessStage(inscanRequestPayload.getOperation().substring(1)));
				}
			} else if (operationStatusNotFromFO.concat("O").endsWith("O")) {
				String operatnStatus = operationStatus.concat("O");
				String updatedStatus = operationStatus.concat("I");
				Optional<ProcessBoStatus> operationPocessStatusOpt = processBoStatusRepository
						.findByCode(operatnStatus);
				if (!operationPocessStatusOpt.isPresent()) {
					throw new ResourceNotFoundException(
							"Process status doesnot exist for operation " + operatnStatus + "");
				}
				Optional<ProcessBoStatus> updatedStatusOpt = processBoStatusRepository.findByCode(updatedStatus);
				if (!updatedStatusOpt.isPresent()) {
					throw new ResourceNotFoundException(
							"Process status doesnot exist for operation " + updatedStatus + "");
				}
				List<InscanforEnumberRequestPayload> eNumbersReqPaload = inscanRequestPayload.geteNumbers();
				for (InscanforEnumberRequestPayload inscanEnumReqPayload : eNumbersReqPaload) {
					enumberList.add(inscanEnumReqPayload.geteNumber());
				}
				if (enumberList.size() > 0) {

					updatedEnumCount = boScanDetailRepository.updateBoScanDetail(enumberList,
							updatedStatusOpt.get().getId(), operationPocessStatusOpt.get().getId());

					try {
						boTrackerService.saveTackingDetails(enumberList, user, operationPocessStatusOpt.get(),
								Utilities.getBoProcessStage(inscanRequestPayload.getOperation().substring(1)));
					} catch (Exception e) {
						logger.error("bo tracking failed for enumbers : " + enumberList + ",vscUser : " + user
								+ "bostatus : " + operationPocessStatusOpt.get().getValue() + "process stage is :"
								+ Utilities.getBoProcessStage(inscanRequestPayload.getOperation().substring(1)));
					}
				}
			}
			/*this can be used when enumbers scan from hub to fo direct
			 * if ((operationStatus.endsWith("S") && !operationStatus.startsWith("F")) ||
			 * operationStatus.endsWith("H") && !operationStatus.startsWith("F") ||
			 * operationStatus.endsWith("H") && !operationStatus.startsWith("S")) {
			 */
			if (operationStatus.endsWith("S") && !operationStatus.startsWith("F")) {
				sendEnumberForDelivery(user, inscanRequestPayload);
				updatedEnumCount = inscanRequestPayload.geteNumbers().size();
			}
		} catch (Exception e) {
			logger.error("failed to save the enumber details due to:" + e);
			throw e;
		}
		return updatedEnumCount;
	}

	/***
	 * After Inscan from the MISSIOn/ HUB to Spoke Need to send enmubers for
	 * Assumption: Currently assuming that there is no enumber with
	 * re-work/re-proccess
	 * 
	 * @param user
	 * @param inscanRequestPayload
	 * @return
	 */
	private Boolean sendEnumberForDelivery(User user, InscanRequestPayload inscanRequestPayload) {
		List<String> enumberList = new ArrayList<>();
		List<InscanforEnumberRequestPayload> eNumbersReqPaload = inscanRequestPayload.geteNumbers();
		for (InscanforEnumberRequestPayload inscanEnumReqPayload : eNumbersReqPaload) {
			enumberList.add(inscanEnumReqPayload.geteNumber());
			Application application = applicationDetailsRepository.findByENumber(inscanEnumReqPayload.geteNumber());
			List<VscDeliveryDetail> deliveryList = deliveryDetailsRepository
					.findAllByApplicationIdAndApplicationAppointmentServiceCenterId(application.getId(),
							user.getServiceCenter().getId());

			if (deliveryList.size() > 0) {
				return false;
			}
			VscDeliveryDetail deliveryDetails = new VscDeliveryDetail();
			deliveryDetails.setApplication(application);
			deliveryDetails.setCreatedBy(user);
			deliveryDetails.setCreatedOn(new Date());
			if (application.getAppointment().getIsCourierEnabled() == 1) {
				deliveryDetails.setDeliveryType(deliveryTypeRepository.findById(DeliveryTypes.CD.getValue()).get());
			} else {
				deliveryDetails.setDeliveryType(deliveryTypeRepository.findById(DeliveryTypes.ID.getValue()).get());
			}
			deliveryDetails.setRemarks("Ready for Delivery");

			deliveryDetails.setDeliveryStatus(deliveryStatusRepository.findById(DeliveryStatuses.RD.getValue()).get());
			deliveryDetailsRepository.save(deliveryDetails);

		}

		return true;
	}

	public List<MasterResponsePayload> getVisaStatusList() {
		final List<MasterResponsePayload> masterList = new ArrayList<MasterResponsePayload>();
		visaStatusRepository.findAll().forEach(master -> {
			masterList.add(new MasterResponsePayload(master.getId(), master.getCode(), master.getValue()));
		});
		return masterList;
	}
}
