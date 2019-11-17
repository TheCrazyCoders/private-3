package com.techsophy.vsc.bo.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.json.JSONException;
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
import com.techsophy.vsc.bo.model.InscanRequestPayload;
import com.techsophy.vsc.bo.model.InscanResponsePayload;
import com.techsophy.vsc.bo.model.MasterResponsePayload;
import com.techsophy.vsc.bo.service.InscanService;
import com.techsophy.vsc.bo.service.UserService;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.model.ApiErrorsResponse;
import com.techsophy.vsc.model.ApiResponse;
import com.techsophy.vsc.model.IApiResponse;

/**
 * @author Sravani
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class InscanController {
	private final Logger logger = LoggerFactory.getLogger(InscanController.class);

	@Autowired
	private InscanService inscanService;
	
	@Autowired
	private UserService userService;

	/**
	 * Below endpoint is called to get Enumber details
	 * 
	 * @param currentUser
	 * @param searchType
	 * @param searchValue
	 * @param operation
	 * @return
	 * @throws JSONException
	 */
	@Secured({ "ROLE_BackOffice" })
	@GetMapping(value = "/inscans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getEnumberDetails(Principal currentUser,
			@RequestParam("searchType") String searchType, @RequestParam("searchValue") String searchValue,
			@RequestParam("operation") String operation) throws JSONException {
		logger.info("In getEnumberDetails()");
		try {
			
			User user=userService.getUserByUserId(currentUser.getName());
			InscanResponsePayload inscanFromFoResponsePayload = inscanService.getEnumberDetails(searchType, searchValue,
					operation,user);
			if (!inscanFromFoResponsePayload.getInscanData().isEmpty()) {
				return ResponseEntity.ok()
						.body(new ApiResponse(inscanFromFoResponsePayload, true, "Data fetch successfully"));

			} else {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body(new ApiResponse(inscanFromFoResponsePayload, false, "Data doesnot exist"));

			}

		} catch (ResourceNotFoundException ex) {

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ApiErrorsResponse(null, false, ex.getMessage()));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiErrorsResponse(null, false, "Unable to get enumber details"));
		}
	}

	/**
	 * Below end point is called to save enumber details
	 * 
	 * @param currentUser
	 * @param enumberObj
	 * @return
	 */
	@Secured({ "ROLE_BackOffice" })
	@PostMapping(value = "/inscans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> saveEnumberDetails(Principal currentUser, @RequestBody String enumberObj) {
		logger.info("In saveEnumberDetails()");
		ObjectMapper mapper = new ObjectMapper();
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/bo/inscan")
				.buildAndExpand(currentUser.getName()).toUri();
		logger.info("In saveEnumberDetails()");
		try {
			
			User user=userService.getUserByUserId(currentUser.getName());
			Integer updateEnumCount = inscanService.saveEnumberDetails(enumberObj,user);
			InscanRequestPayload inscanRequestPayload = mapper.readValue(enumberObj, InscanRequestPayload.class);
			String message = "ENumber added to Backoffice";
			if(inscanRequestPayload.getOperation().endsWith("S") && !inscanRequestPayload.getOperation().startsWith("F")) {
				message = "Application(s) sent for delivery";
			}
			return ResponseEntity.created(location)
					.body(new ApiResponse(updateEnumCount, true, message));
		} catch (ResourceNotFoundException ex) {

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ApiErrorsResponse(null, false, ex.getMessage()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiErrorsResponse(null, false, "Unable to save enumber's"));
		}
	}

	@Secured({ "ROLE_BackOffice" })
	@GetMapping(value = "/inscans/masters", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getVisaStatusList(Principal currentUser) {
		logger.info("In getEnumberDetails()");
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/bo/inscan")
				.buildAndExpand(currentUser.getName()).toUri();
		try {
			List<MasterResponsePayload> visaStatuses = inscanService.getVisaStatusList();

			return ResponseEntity.ok()
					.body(new ApiResponse(visaStatuses, true, "Visa Status List"));
		} catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiErrorsResponse(null, false, "Unable to fetch visa status"));
		}
	}

}
