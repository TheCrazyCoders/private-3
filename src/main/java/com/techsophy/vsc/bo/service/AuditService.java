package com.techsophy.vsc.bo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.bo.model.ApplicationTypeResponsePayload;
import com.techsophy.vsc.bo.model.AuditApplicantResponsePayload;
import com.techsophy.vsc.bo.model.AuditAppointmentResponsePayload;
import com.techsophy.vsc.bo.model.AuditGetInfoResponsePayload;
import com.techsophy.vsc.bo.model.AuditHoldENumberRequestPayload;
import com.techsophy.vsc.bo.model.AuditMasterResponsePayload;
import com.techsophy.vsc.bo.model.AuditTypeResponsePayload;
import com.techsophy.vsc.bo.model.HoldReasonResponsePayload;
import com.techsophy.vsc.bo.model.MasterResponsePayload;
import com.techsophy.vsc.entity.Application;
import com.techsophy.vsc.entity.Appointment;
import com.techsophy.vsc.entity.Audit;
import com.techsophy.vsc.entity.AuditDetail;
import com.techsophy.vsc.entity.AuditHoldReason;
import com.techsophy.vsc.entity.AuditStatus;
import com.techsophy.vsc.entity.AuditType;
import com.techsophy.vsc.entity.BoScanDetail;
import com.techsophy.vsc.entity.Country;
import com.techsophy.vsc.entity.Gender;
import com.techsophy.vsc.entity.MaritalStatus;
import com.techsophy.vsc.entity.ModeOfEntry;
import com.techsophy.vsc.entity.PortOfEntry;
import com.techsophy.vsc.entity.ProcessType;
import com.techsophy.vsc.entity.Religion;
import com.techsophy.vsc.entity.ServiceCenter;
import com.techsophy.vsc.entity.ServiceCenterConfig;
import com.techsophy.vsc.entity.TravelType;
import com.techsophy.vsc.entity.TypeOfEntry;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.entity.VisaType;
import com.techsophy.vsc.enums.ActiveStatuses;
import com.techsophy.vsc.enums.AuditStatuses;
import com.techsophy.vsc.enums.AuditTypes;
import com.techsophy.vsc.enums.BoProcessStatus;
import com.techsophy.vsc.repository.ApplicationDetailsRepository;
import com.techsophy.vsc.repository.AppointmentDetailsRepository;
import com.techsophy.vsc.repository.AuditDetailsRepository;
import com.techsophy.vsc.repository.AuditHoldReasonsRepository;
import com.techsophy.vsc.repository.AuditStatusRepository;
import com.techsophy.vsc.repository.AuditTypeRepository;
import com.techsophy.vsc.repository.AuditsRepository;
import com.techsophy.vsc.repository.BoScanDetailRepository;
import com.techsophy.vsc.repository.CountryDetailsRepository;
import com.techsophy.vsc.repository.GenderDetailsRepository;
import com.techsophy.vsc.repository.MaritalStatusesDetailsRepository;
import com.techsophy.vsc.repository.ModeOfEntryDetailsRepository;
import com.techsophy.vsc.repository.PortOfEntryDetailsRepository;
import com.techsophy.vsc.repository.ProcessTypeRepository;
import com.techsophy.vsc.repository.ReligionDetailsRepository;
import com.techsophy.vsc.repository.ServiceCenterConfigRepository;
import com.techsophy.vsc.repository.ServiceCenterDetailsRepository;
import com.techsophy.vsc.repository.TravelTypeRepository;
import com.techsophy.vsc.repository.TypeOfEntryDetailsRepository;
import com.techsophy.vsc.repository.VisaTypeRepository;

@Transactional
@Service
public class AuditService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Integer PAGE_SIZE = 10;

	@Autowired
	private ProcessTypeRepository processTypeRepository;

	@Autowired
	private AuditTypeRepository auditTypeRepository;

	@Autowired
	private AuditsRepository auditsRepository;

	@Autowired
	private AuditDetailsRepository auditDetailsRepository;

	@Autowired
	private AuditHoldReasonsRepository auditHoldReasonsRepository;

	@Autowired
	private ApplicationDetailsRepository applicationDetailsRepository;

	@Autowired
	private AppointmentDetailsRepository appointmentDetailsRepository;

	@Autowired
	private ModeOfEntryDetailsRepository modeOfEntryDetailsRepository;

	@Autowired
	private PortOfEntryDetailsRepository portOfEntryDetailsRepository;

	@Autowired
	private TypeOfEntryDetailsRepository typeOfEntryDetailsRepository;

	@Autowired
	private VisaTypeRepository visaTypeRepository;

	@Autowired
	private CountryDetailsRepository countryDetailsRepository;
	@Autowired
	private ReligionDetailsRepository religionDetailsRepository;
	@Autowired
	private MaritalStatusesDetailsRepository maritalStatusesDetailsRepository;
	@Autowired
	private GenderDetailsRepository genderDetailsRepository;

	@Autowired
	private TravelTypeRepository travelTypeRepository;

	@Autowired
	private ServiceCenterDetailsRepository serviceCenterDetailsRepository;

	@Autowired
	private AuditStatusRepository auditStatusRepository;

	@Autowired
	private BoScanDetailRepository boScanDetailRepository;

	@Autowired
	private EscalationService escalationService;

	@Autowired
	private ServiceCenterConfigRepository serviceCenterConfigRepository;

	public AuditMasterResponsePayload getMasterList() {
		AuditMasterResponsePayload responsePayload = new AuditMasterResponsePayload();
		try {
			List<MasterResponsePayload> list = new ArrayList<MasterResponsePayload>();
			Pageable limit = PageRequest.of(0, 2);
			Page<ProcessType> processTypeList = processTypeRepository.findAll(limit);
			processTypeList.forEach(appType -> {
				list.add(new MasterResponsePayload(appType.getId(), appType.getCode(), appType.getValue()));
			});
			responsePayload.setApplicationType(list);

			List<MasterResponsePayload> auditlist = new ArrayList<MasterResponsePayload>();
			List<AuditType> auditTypeList = auditTypeRepository.findAll();
			auditTypeList.forEach(auditType -> {
				auditlist.add(new MasterResponsePayload(auditType.getId(), auditType.getCode(), auditType.getValue()));
			});
			responsePayload.setAuditType(auditlist);

			List<MasterResponsePayload> holdReasonsList = new ArrayList<MasterResponsePayload>();
			List<AuditHoldReason> holdReasonList = auditHoldReasonsRepository.findAll();
			holdReasonList.forEach(holdReason -> {
				holdReasonsList.add(
						new MasterResponsePayload(holdReason.getId(), holdReason.getCode(), holdReason.getValue()));
			});
			responsePayload.setHoldReason(holdReasonsList);

			return responsePayload;

		} catch (DataAccessException e) {
			logger.error("failed to get the application type details in audit due to - " + e);
			throw e;

		}

	}

	public List<ApplicationTypeResponsePayload> getApplicationType() {
		try {
			Pageable limit = PageRequest.of(0, 2);

			Page<ProcessType> processTypeList = processTypeRepository.findAll(limit);
//			List<ProcessType> processTypeList = processTypeRepository.findAll();
			final List<ApplicationTypeResponsePayload> responsePayloadList = new ArrayList<ApplicationTypeResponsePayload>();
			processTypeList.forEach(processType -> {
				ApplicationTypeResponsePayload responsePayload = new ApplicationTypeResponsePayload();
				responsePayload.setId(processType.getId());
				responsePayload.setCode(processType.getCode());
				responsePayload.setValue(processType.getValue());
				responsePayloadList.add(responsePayload);
			});

			return responsePayloadList;
		} catch (DataAccessException e) {
			logger.error("failed to get the application type details in audit due to - " + e);
			throw e;

		}

	}

	public List<AuditTypeResponsePayload> getAuditTypes() {
		try {
			List<AuditType> auditTypeList = auditTypeRepository.findAll();
			final List<AuditTypeResponsePayload> auditTypeResponseList = new ArrayList<AuditTypeResponsePayload>();
			auditTypeList.forEach(auditType -> {
				AuditTypeResponsePayload auditTypeResponse = new AuditTypeResponsePayload();
				auditTypeResponse.setId(auditType.getId());
				auditTypeResponse.setCode(auditType.getCode());
				auditTypeResponse.setValue(auditType.getValue());
				auditTypeResponseList.add(auditTypeResponse);
			});
			return auditTypeResponseList;
		} catch (DataAccessException e) {
			logger.error("failed to get the Audit types in audit due to - " + e);
			throw e;
		}
	}

	public List<HoldReasonResponsePayload> getHoldReason() {
		try {
			List<AuditHoldReason> holdReasonsList = auditHoldReasonsRepository.findAll();
			final List<HoldReasonResponsePayload> holdReasonResponsesList = new ArrayList<HoldReasonResponsePayload>();
			holdReasonsList.forEach(holdReason -> {
				HoldReasonResponsePayload holdReasonResponses = new HoldReasonResponsePayload();
				holdReasonResponses.setId(holdReason.getId());
				holdReasonResponses.setCode(holdReason.getCode());
				holdReasonResponses.setValue(holdReason.getValue());
				holdReasonResponsesList.add(holdReasonResponses);
			});
			return holdReasonResponsesList;

		} catch (DataAccessException e) {
			logger.error("failed to get Reason to hold in audit due to - " + e);
			throw e;

		}
	}

	public AuditAppointmentResponsePayload getApplicantDetailsByENumberAndUserName(String eNumber, User user) {

		try {
			Application applicationDetails = applicationDetailsRepository
					.findByENumberAndAppointmentServiceCenterId(eNumber, user.getServiceCenter().getId());
			if (applicationDetails == null) {
				throw new ResourceNotFoundException("Applicant details with E no " + eNumber + " does not exist");
			}

			AuditApplicantResponsePayload applicantInfo = entityToModelMapper(applicationDetails);
			Optional<Appointment> apptDetails = appointmentDetailsRepository.findByApplicationsIdAndServiceCenterId(
					applicationDetails.getId(), user.getServiceCenter().getId());

			if (!apptDetails.isPresent()) {
				throw new ResourceNotFoundException("Appointment details with E no " + eNumber + " does not exist");
			}

			AuditAppointmentResponsePayload auditApptResPayload = entityToModelMapper(apptDetails.get(), applicantInfo);
			System.out.println("Applicant info is" + auditApptResPayload);
			return auditApptResPayload;
		} catch (DataAccessException e) {
			logger.error("failed to get Applicant details by e-number in audit due to - " + e);
			throw e;

		}

	}

	private AuditAppointmentResponsePayload entityToModelMapper(Appointment apptDetails,
			AuditApplicantResponsePayload auditApplicantReponsePayload) {
		AuditAppointmentResponsePayload auditApptResponsePayload = new AuditAppointmentResponsePayload();

		Optional<VisaType> visaTypeDetails = visaTypeRepository.findById(apptDetails.getVisaType().getId());
		if (!visaTypeDetails.isPresent()) {
			throw new ResourceNotFoundException("Visa Type does not exist");
		}
		auditApptResponsePayload.setVisaType(visaTypeDetails.get().getValue());

		Optional<TypeOfEntry> typeOfEntryDetails = typeOfEntryDetailsRepository
				.findById(apptDetails.getTypeOfEntry().getId());
		if (!typeOfEntryDetails.isPresent()) {
			throw new ResourceNotFoundException("Type of Entry does not exist");
		}
		auditApptResponsePayload.setNoOfVisits(typeOfEntryDetails.get().getValue());

		Optional<PortOfEntry> portOfEntryDetails = portOfEntryDetailsRepository
				.findById(apptDetails.getPortOfEntry().getId());
		if (!portOfEntryDetails.isPresent()) {
			throw new ResourceNotFoundException("Port of entry does not exist");
		}
		auditApptResponsePayload.setPortOfEntry(portOfEntryDetails.get().getValue());

		Optional<ModeOfEntry> modeOfEntry = modeOfEntryDetailsRepository.findById(apptDetails.getModeOfEntry().getId());
		if (!modeOfEntry.isPresent()) {
			throw new ResourceNotFoundException("Mode of entry does not exist");
		}
		auditApptResponsePayload.setModeOfEntry(modeOfEntry.get().getValue());

//		Optional<ServiceCenter> serviceCenterDetails = serviceCenterDetailsRepository
//				.findById(apptDetails.getServiceCenter().getId());
//		if (!serviceCenterDetails.isPresent()) {
//			throw new ResourceNotFoundException("Service center details does not exist");
//		}
		Optional<ServiceCenter> missionDetails = serviceCenterDetailsRepository.findById(apptDetails.getSaudiMission());
		if (!missionDetails.isPresent()) {
			throw new ResourceNotFoundException("mission details does not exist");
		}
		// auditApptResponsePayload.setSaudiMission(serviceCenterDetails.get().getCity());
		auditApptResponsePayload.setSaudiMission(missionDetails.get().getCity());

		Optional<TravelType> travelTypeOpt = travelTypeRepository.findById(apptDetails.getTravelType().getId());
		if (!travelTypeOpt.isPresent()) {
			throw new ResourceNotFoundException("Travel type does not exist");
		}
		auditApptResponsePayload.setTravellingAs(travelTypeOpt.get().getValue());
		auditApptResponsePayload.setNoOfApplicant(apptDetails.getNoApplicants());
		auditApptResponsePayload.setApplicantData(auditApplicantReponsePayload);
		auditApptResponsePayload.setSponserId(apptDetails.getSponsorId());
		auditApptResponsePayload.setInvitationNo(apptDetails.getInvitationNo());
		return auditApptResponsePayload;
	}

	public AuditApplicantResponsePayload entityToModelMapper(Application applDetails) {
		AuditApplicantResponsePayload auditApplResPayload = new AuditApplicantResponsePayload();

		auditApplResPayload.setPassportNo(applDetails.getPassportNo());
		auditApplResPayload.setPassportType(applDetails.getPassportType().getValue());
		auditApplResPayload.setDateOfIssue(applDetails.getDateOfIssue());
		auditApplResPayload.setPlaceOfIssue(applDetails.getPlaceOfIssue());
		auditApplResPayload.setDateOfExpiry(applDetails.getDateOfExpiry());

		Optional<MaritalStatus> maritalStatusOpt = maritalStatusesDetailsRepository
				.findById(applDetails.getMaritalStatus().getId());
		if (!maritalStatusOpt.isPresent())
			throw new ResourceNotFoundException("Marital status does not exist");

		Optional<Gender> genderDetailsOpt = genderDetailsRepository.findById(applDetails.getGender().getId());
		if (!genderDetailsOpt.isPresent())
			throw new ResourceNotFoundException("Gender details does not exist");

		Optional<Country> countryDetailsOpt = countryDetailsRepository.findById(applDetails.getCountry().getId());
		if (!countryDetailsOpt.isPresent())
			throw new ResourceNotFoundException("Country details does not exist");

		Optional<Religion> religionDetailsOpt = religionDetailsRepository.findById(applDetails.getReligion().getId());
		if (!religionDetailsOpt.isPresent()) {
			throw new ResourceNotFoundException("Religion details does not exist");
		}

		auditApplResPayload.setFirstName(applDetails.getFirstName());
		auditApplResPayload.setLastName(applDetails.getLastName());
		auditApplResPayload.setFatherName(applDetails.getFatherName());
		auditApplResPayload.setGender(genderDetailsOpt.get().getValue());
		auditApplResPayload.setMaritalStatus(maritalStatusOpt.get().getValue());
		auditApplResPayload.setReligion(religionDetailsOpt.get().getValue());
		auditApplResPayload.setPlaceOfBirth(applDetails.getPlaceOfBirth());
		auditApplResPayload.setDateOfBirth(applDetails.getDateOfBirth());
		auditApplResPayload.setQualification(applDetails.getQualification());
		auditApplResPayload.setOccupation(applDetails.getOccupation());
		auditApplResPayload.setEmail(applDetails.getEmail());
		auditApplResPayload.setMobile(applDetails.getMobileNo());
		auditApplResPayload.setNationality(countryDetailsOpt.get().getValue());

		auditApplResPayload.setHouseNo(applDetails.getHouseNo());
		auditApplResPayload.setBuilding(applDetails.getBuildingNo());
		auditApplResPayload.setAddressCity(applDetails.getAddressCity());
		auditApplResPayload.setArea(applDetails.getArea());
		auditApplResPayload.setState(applDetails.getState());
		auditApplResPayload.setAddressCountry(applDetails.getCountry().getValue());
		auditApplResPayload.setPincode(applDetails.getPincode());

		return auditApplResPayload;
	}

	public void auditHoldENumber(AuditHoldENumberRequestPayload auditStatusDetails, User user) {
		logger.info("In auditHoldENumber()");

		try {

			Optional<AuditStatus> auditStatus = auditStatusRepository.findByCode(auditStatusDetails.getAuditStatus());
			if (!auditStatus.isPresent()) {
				throw new ResourceNotFoundException(
						"No audit status id found with " + auditStatusDetails.getAuditStatus());
			}

			Optional<BoScanDetail> boScanDetails = boScanDetailRepository
					.findByEnumberAndServiceCenterId(auditStatusDetails.geteNumber(), user.getServiceCenter().getId());
			BoScanDetail boScanDetail = boScanDetails.get();

			if (boScanDetails.get().getEnumber().contains(auditStatusDetails.geteNumber())) {
				boScanDetail.setAuditStatus(auditStatus.get());
				boScanDetailRepository.save(boScanDetail);

			}
			escalationService.addToEscalation(user, auditStatusDetails.geteNumber(),
					auditStatusDetails.getHoldReason());

		} catch (Exception e) {
			logger.error("error in auditHoldENumber method : " + e);
			throw e;
		}
	}

	public List<AuditGetInfoResponsePayload> getListOfApplicationForAudit(User user, String auditType) {
		logger.info("In getListOfApplicationForAudit() service");
		List<BoScanDetail> scanDetails = new ArrayList<BoScanDetail>();
		List<AuditGetInfoResponsePayload> responsePayloadList = new ArrayList<AuditGetInfoResponsePayload>();
		if (StringUtils.isEmpty(auditType)) {
			throw new ResourceNotFoundException("No audit type supplied to fetch data");
		}
		Optional<Audit> auditOpt = auditsRepository.findByAuditStatusCodeAndServiceCenterCodeAndAuditTypeCode(
				AuditStatuses.START.name(), user.getServiceCenter().getCode(), auditType);
		List<Integer> statusList = new ArrayList<>();
		statusList.add(BoProcessStatus.FSI.getValue());
		statusList.add(BoProcessStatus.FHI.getValue());
		if (auditOpt.isPresent()) {
			List<AuditDetail> auditDetails = auditDetailsRepository.findAllByAudit(auditOpt.get());
			auditDetails.forEach(auditDetail -> {
				AuditGetInfoResponsePayload responsePayload = new AuditGetInfoResponsePayload(auditDetail.getENumber(),
						auditDetail.getApplicantName(), auditDetail.getPassportNo(),
						auditDetail.getNationality().getValue(), auditDetail.getVisaType().getValue());
				responsePayloadList.add(responsePayload);
			});
		} else {
			if (auditType.equals(AuditTypes.RAND.name())) {

				List<BoScanDetail> scanDetailList = boScanDetailRepository
						.findAllByProcessBoStatusIdInAndServiceCenterIdAndAuditStatusIdAndIsAuditMandate(statusList,
								user.getServiceCenter().getId(), AuditStatuses.OPEN.getValue(),
								ActiveStatuses.INACTIVE.getValue());

				if (scanDetailList.size() > 0) {
					scanDetailList = updateStatus(scanDetailList, AuditStatuses.START.getValue());
					boScanDetailRepository.saveAll(scanDetailList);
					// TODO: need to fetch from config table

					Optional<ServiceCenterConfig> serviceCenterConfigOpt = serviceCenterConfigRepository
							.findByServiceCenterId(user.getServiceCenter().getId());

					if (!serviceCenterConfigOpt.isPresent()) {
						throw new ResourceNotFoundException("service center config not properly done");
					}

					ServiceCenterConfig serviceCenterConfig = serviceCenterConfigOpt.get();

					Integer noOfApplicationToAudit = serviceCenterConfig.getAuditPercentage();
					if (scanDetailList.size() <= 10) {
						scanDetails.addAll(scanDetailList);
					} else {
						if (scanDetailList.size() > 10 && scanDetailList.size() <= 50) {
							int index = 1;
							for (BoScanDetail scanDetail : scanDetailList) {
								if (index % 4 == 0) {
									scanDetails.add(scanDetail);
								}
								index++;
							}
							if (scanDetails.size() < noOfApplicationToAudit) {
								for (BoScanDetail scanDetail : scanDetailList) {
									if (index % 3 == 0) {
										scanDetails.add(scanDetail);
									}
									index++;
								}
							}
						} else {
							if (scanDetailList.size() > 10 && scanDetailList.size() <= 50) {
								int index = 1;
								for (BoScanDetail scanDetail : scanDetailList) {
									if (index % 4 == 0) {
										scanDetails.add(scanDetail);
									}
									index++;
								}
								if (scanDetails.size() < noOfApplicationToAudit) {
									for (BoScanDetail scanDetail : scanDetailList) {
										if (index % 3 == 0) {
											scanDetails.add(scanDetail);
										}
										index++;
									}
								}
							} else {
								if (scanDetailList.size() > 50 && scanDetailList.size() <= 100) {
									int index = 1;
									for (BoScanDetail scanDetail : scanDetailList) {
										if (index % 8 == 0) {
											scanDetails.add(scanDetail);
										}
										index++;
									}
									if (scanDetails.size() < noOfApplicationToAudit) {
										for (BoScanDetail scanDetail : scanDetailList) {
											if (index % 7 == 0) {
												scanDetails.add(scanDetail);
											}
										}
										index++;
									}
								} else {
									int index = 1;
									for (BoScanDetail scanDetail : scanDetailList) {
										if (index % 10 == 0) {
											scanDetails.add(scanDetail);
										}
										index++;
									}
								}
							}
						}
						scanDetails.addAll(scanDetailList);
					}
				}
			} else if (auditType.equals(AuditTypes.COMP.name())) {
				List<BoScanDetail> scanDetailMandatoryList = boScanDetailRepository
						.findAllByProcessBoStatusIdInAndServiceCenterIdAndAuditStatusIdAndIsAuditMandate(statusList,
								user.getServiceCenter().getId(), AuditStatuses.OPEN.getValue(), (byte) 1);
				scanDetailMandatoryList = updateStatus(scanDetailMandatoryList, AuditStatuses.START.getValue());
				boScanDetailRepository.saveAll(scanDetailMandatoryList);
				scanDetails.addAll(scanDetailMandatoryList);
			}
			if (scanDetails.size() > 0) {
				// Save data into Audit table
				Audit audit = new Audit();
				audit.setAuditDate(new Date());
				audit.setCreatedBy(user);
				audit.setServiceCenter(user.getServiceCenter());
				audit.setNoOfApplications(responsePayloadList.size());
				audit.setAuditStatus(
						auditStatusRepository.findByCode(AuditStatuses.START.name()).orElseGet(AuditStatus::new));
				Optional<AuditType> auditTypes = auditTypeRepository.findByCode(auditType);
				if (auditTypes.isPresent()) {
					audit.setAuditType(auditTypes.get());
				}
				Audit result = auditsRepository.save(audit);

				List<AuditDetail> auditDetailList = new ArrayList<AuditDetail>();
				scanDetails.forEach(scanDetail -> {
					AuditGetInfoResponsePayload responsePayload = new AuditGetInfoResponsePayload(
							scanDetail.getEnumber(), scanDetail.getApplicantName(), scanDetail.getPassportNo(),
							scanDetail.getNationality().getValue(), scanDetail.getVisaType().getValue());

					AuditDetail auditDetail = new AuditDetail();
					auditDetail.setApplicantName(scanDetail.getApplicantName());
					auditDetail.setAudit(result);
					auditDetail.setAuditStatus(
							auditStatusRepository.findByCode(AuditStatuses.START.name()).orElseGet(AuditStatus::new));
					auditDetail.setENumber(scanDetail.getEnumber());
					auditDetail.setNationality(scanDetail.getNationality());
					auditDetail.setPassportNo(scanDetail.getPassportNo());
					auditDetail.setCreatedBy(user);
					auditDetail.setCreatedOn(new Date());
					auditDetail.setVisaType(scanDetail.getVisaType());
					auditDetailList.add(auditDetail);

					responsePayloadList.add(responsePayload);
				});
				auditDetailsRepository.saveAll(auditDetailList);
			} else {
				throw new ResourceNotFoundException("No data available for audit");
			}
		}
		return responsePayloadList;
	}

	public String getCompleteAudit(User user) {
		List<BoScanDetail> scanDetailList = boScanDetailRepository
				.findAllByProcessBoStatusIdAndServiceCenterIdAndAuditStatusId(BoProcessStatus.FSI.getValue(),
						user.getServiceCenter().getId(), AuditStatuses.START.getValue());

		List<BoScanDetail> scanDetailListHub = boScanDetailRepository
				.findAllByProcessBoStatusIdAndServiceCenterIdAndAuditStatusId(BoProcessStatus.FHI.getValue(),
						user.getServiceCenter().getId(), AuditStatuses.START.getValue());
		scanDetailList.addAll(scanDetailListHub);

		if (scanDetailList.isEmpty()) {
			return "No records found";
		} else {
			List<BoScanDetail> resultList = updateStatus(scanDetailList, AuditStatuses.COMPLETE.getValue());
			boScanDetailRepository.saveAll(resultList);

			Optional<Audit> auditOpt = auditsRepository.findByAuditStatusCodeAndServiceCenterCode(
					AuditStatuses.START.name(), user.getServiceCenter().getCode());
			if (auditOpt.isPresent()) {
				List<AuditDetail> auditDetails = auditDetailsRepository.findAllByAudit(auditOpt.get());
				auditDetails = updateStatusAudit(auditDetails, AuditStatuses.COMPLETE.getValue());
				auditDetailsRepository.saveAll(auditDetails);
				Audit audit = auditOpt.get();
				audit.setAuditStatus(auditStatusRepository.findByCode(AuditStatuses.COMPLETE.name()).get());
				auditsRepository.save(audit);
			}
		}
		return "";
	}

	private List<BoScanDetail> updateStatus(List<BoScanDetail> scanDetailList, Integer status) {
		if (status == AuditStatuses.START.getValue()) {
			AuditStatus auditStatus = auditStatusRepository.findById(status).get();
			scanDetailList.forEach(scanDetail -> {
				scanDetail.setAuditStatus(auditStatus);
			});
		} else {
			AuditStatus auditStatus = auditStatusRepository.findById(AuditStatuses.COMPLETE.getValue()).get();
			scanDetailList.forEach(scanDetail -> {
				if (scanDetail.getAuditStatus().getId() == AuditStatuses.START.getValue()) {
					scanDetail.setAuditStatus(auditStatus);
				}
			});
		}
		return scanDetailList;
	}

	private List<AuditDetail> updateStatusAudit(List<AuditDetail> auditDetailList, Integer status) {
		final List<AuditDetail> listToUpdate = new ArrayList<AuditDetail>();
		if (status == AuditStatuses.COMPLETE.getValue()) {
			AuditStatus auditStatus = auditStatusRepository.findById(status).get();
			auditDetailList.forEach(scanDetail -> {
				scanDetail.setAuditStatus(auditStatus);
				listToUpdate.add(scanDetail);
			});
		}
		return listToUpdate;
	}
}
