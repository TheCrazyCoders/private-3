package com.techsophy.vsc.bo.controller;

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

import com.mysql.jdbc.StringUtils;
import com.techsophy.vsc.bo.exception.InvalidDataException;
import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.bo.model.ENumberDetailsForBatchResponsePayload;
import com.techsophy.vsc.bo.model.PrepareToBatchResponsePayload;
import com.techsophy.vsc.bo.service.BatchService;
import com.techsophy.vsc.bo.service.UserService;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.model.ApiErrorsResponse;
import com.techsophy.vsc.model.ApiResponse;
import com.techsophy.vsc.model.IApiResponse;

/**
 * @author srikanth
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BatchController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BatchService batchService;

	@Autowired
	private UserService userService;

	@Secured({ "ROLE_BackOffice" })
	@PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> attachToBatch(Principal currentUser, @RequestBody String batchDetails) {
		logger.info("In attachToBatch method");

		try {
			User user = userService.getUserByUserId(currentUser.getName());
			PrepareToBatchResponsePayload payload = batchService.attachEnumbersToBatch(user, batchDetails);
			return ResponseEntity.ok().body(new ApiResponse(payload, true, "Enumbers added to batch successfully"));

		} catch (ResourceNotFoundException e) {
			logger.error("error in attachToBatch method" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));

		} catch (InvalidDataException e) {
			logger.error("error in attachToBatch method" + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));

		} catch (Exception e) {
			logger.error("error in attachToBatch method" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}
	}

	/*
	 * here we need to pass one more parameter like where to send this application.
	 */

	@Secured({ "ROLE_BackOffice" })
	@GetMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IApiResponse> getEnumberDetailsForBatch(Principal currentUser,
			@RequestParam("eNumber") String eNumber, @RequestParam("operation") String operation) {
		logger.info("In getEnumberDetailsForBatch method");

		try {
			if (!StringUtils.isNullOrEmpty(eNumber)) {
				User user = userService.getUserByUserId(currentUser.getName());
				ENumberDetailsForBatchResponsePayload payload = batchService.getENumberDetailsByBoStatus(user, eNumber,
						operation);

				return ResponseEntity.ok().body(new ApiResponse(payload, true, "eNumber details fetched successfully"));

			} else {
				return ResponseEntity.badRequest().body(new ApiErrorsResponse("", false, "eNumber is required"));
			}

		} catch (ResourceNotFoundException e) {
			logger.error("error in getEnumberDetailsForBatch method" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));

		} catch (Exception e) {
			logger.error("error in getEnumberDetailsForBatch method" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiErrorsResponse(null, false, e.getMessage()));
		}
	}
}