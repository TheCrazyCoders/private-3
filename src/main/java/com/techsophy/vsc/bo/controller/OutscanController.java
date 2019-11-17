package com.techsophy.vsc.bo.controller;
import java.net.URI;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.bo.model.OutscanRequestPayload;
import com.techsophy.vsc.bo.model.OutscanResponsePayload;
import com.techsophy.vsc.bo.service.OutscanService;
import com.techsophy.vsc.bo.service.UserService;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.model.ApiErrorsResponse;
import com.techsophy.vsc.model.ApiResponse;
import com.techsophy.vsc.model.IApiResponse;
/**
 * 
 * @author thirupathi
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class OutscanController {
	private final Logger logger = LoggerFactory.getLogger(OutscanController.class);

	@Autowired
	OutscanService outscanService;

	@Autowired
	private UserService userService;
	/**
	 * @param currentUser
	 * @param batchNo
	 * @param operation
	 * @return
	 */
	@Secured({ "ROLE_BackOffice" })
	@GetMapping(value = "/outscan/batches", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getBatchDetails(Principal currentUser,@RequestParam String batchNo,@RequestParam String operation){
		logger.info("In getBatchDetails()");		
		try {
			if (operation==null||batchNo==null||batchNo.isEmpty()||operation.isEmpty()) {
				return ResponseEntity.badRequest().body(new ApiErrorsResponse("", false, "Invalid request"));
			}
			else {
				OutscanResponsePayload outscanResponse =outscanService.getBatchDetails(batchNo, operation);
				return ResponseEntity.ok()
						.body(new ApiResponse(outscanResponse, true, "Batch Details fetched successfully"));
			}
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApiErrorsResponse(null, false, ex.getMessage()));
		}
		catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorsResponse(null, false, ex.getMessage()));
		}
	}
	
	/**
	 * @param currentUser
	 * @param requestBody
	 * @return
	 */
	@Secured({"ROLE_BackOffice"})
	@PostMapping(value = "/outscans",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> submitOutscan(Principal currentUser, @RequestBody String requestBody){
		ObjectMapper mapper = new ObjectMapper();
		logger.info("In outscan()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/outscans")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			User user=userService.getUserByUserId(currentUser.getName());
			OutscanRequestPayload requestPayload = mapper.readValue(requestBody, OutscanRequestPayload.class);		
			outscanService.updateOutscanDetails(requestPayload, user);
			return ResponseEntity.created(location).body(new ApiResponse(null, true, "Batch numbers submitted successfully"));
		}
		catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorsResponse(null, false, ex.getMessage()));
		}
	}

}
