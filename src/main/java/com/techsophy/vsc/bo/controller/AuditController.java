package com.techsophy.vsc.bo.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import com.techsophy.vsc.bo.model.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.bo.service.AuditService;
import com.techsophy.vsc.bo.service.UserService;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.model.ApiErrorsResponse;
import com.techsophy.vsc.model.ApiResponse;
import com.techsophy.vsc.model.IApiResponse;

/*
 * @author: Sakshi
 * 
 */

@RestController
public class AuditController {
	private final Logger logger = LoggerFactory.getLogger(AuditController.class);

	@Autowired
	AuditService auditService;

	@Autowired
	private UserService userService;

	/**
	 * Below endpoint is called to get application type
	 * 
	 * @param currentUser
	 * 
	 * @return
	 */

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit/masterdata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getAuditMasterData(Principal currentUser) {
		logger.info("In getAuditMasterData()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			AuditMasterResponsePayload masterResponse = auditService.getMasterList();

			return ResponseEntity.ok()
					.body(new ApiResponse(masterResponse, true, "Master response fetched successfully"));

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}
	}

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit/applications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getApplicationType(Principal currentUser) {
		logger.info("In auditor's getApplicantType");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			List<ApplicationTypeResponsePayload> response = auditService.getApplicationType();
			return ResponseEntity.ok()
					.body(new ApiResponse(response, true, "Application Type details fetched successfully"));

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}
	}

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit/audittypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getAuditTypes(Principal currentUser) {
		logger.info("In getAuditType()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			List<AuditTypeResponsePayload> response = auditService.getAuditTypes();
			return ResponseEntity.ok().body(new ApiResponse(response, true, "Audit Types fetched successfully"));

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}

	}

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit/holdreasons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getHoldReason(Principal currentUser) {
		logger.info("In getHoldReason()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			List<HoldReasonResponsePayload> holdReasonResponse = auditService.getHoldReason();
			return ResponseEntity.ok()
					.body(new ApiResponse(holdReasonResponse, true, "Reason to hold fetched successfully"));

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}

	}

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit/applicantdetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getApplicantDetails(Principal currentUser,
			@RequestParam("eNumber") String eNumber) {
		logger.info("In getApplicantDetails()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			if (eNumber == null || eNumber == "") {
				return ResponseEntity.badRequest().body(new ApiResponse(null, false, "ENumber cannot be blank"));
			}
			User user=userService.getUserByUserId(currentUser.getName());
			AuditAppointmentResponsePayload applicantDetails = auditService
					.getApplicantDetailsByENumberAndUserName(eNumber, user);

			return ResponseEntity.ok()
					.body(new ApiResponse(applicantDetails, true, "Applicant info fetched successfully"));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}

	}

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit/holdenumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> auditHoldENumber(Principal currentUser, @RequestBody String requestObj) {
		logger.info("In auditHoldENumber()");
		ObjectMapper mapper = new ObjectMapper();
		User user=userService.getUserByUserId(currentUser.getName());
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			AuditHoldENumberRequestPayload auditStatusDetails = mapper.readValue(requestObj,
					AuditHoldENumberRequestPayload.class);
			auditService.auditHoldENumber(auditStatusDetails, user);
			return ResponseEntity.created(location)
					.body(new ApiResponse(null, true, "Audit status updated sucessfully"));
		} catch (ResourceNotFoundException e) {
			logger.error("error in auditHoldENumber() method : " + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiErrorsResponse(null, false, "Unable to update audit e-number status details"));
		}

	}

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getInfoForAudit(Principal currentUser, @RequestParam("auditType") String auditType,
			Pageable pageable) {

		logger.info("In getInfoForAudit()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			User user=userService.getUserByUserId(currentUser.getName());
			List<AuditGetInfoResponsePayload> response = auditService
					.getListOfApplicationForAudit(user,auditType);
			if (response == null) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
						.body(new ApiErrorsResponse(null, false, "No record found for audit"));
			}
			else {
				Page<AuditGetInfoResponsePayload> page = new PageImpl<>(response, pageable,
						response.size());
				return ResponseEntity.ok().body(new ApiResponse(page, true, "Audit records fetched successfully"));
			}

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, "Unable to fetch data for Audit"));
		} catch (Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).location(location)
					.body(new ApiErrorsResponse(null, false, "Unable to fetch data for Audit"));
		}

	}

	@Secured("ROLE_Auditor")
	@RequestMapping(value = "/audit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getCompleteAudit(Principal currentUser) {

		logger.info("In getCompleteAudit()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audit/")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			User user=userService.getUserByUserId(currentUser.getName());
			String result = auditService.getCompleteAudit(user);

			if (result != "") {
				return ResponseEntity.badRequest().body(new ApiResponse("", true, "No records found"));
			}
			return ResponseEntity.ok().body(new ApiResponse(result, true, "Audit completed successfully"));

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).location(location)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}

	}
}
