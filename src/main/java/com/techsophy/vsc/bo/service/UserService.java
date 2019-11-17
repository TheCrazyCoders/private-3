package com.techsophy.vsc.bo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsophy.vsc.bo.exception.ResourceNotFoundException;
import com.techsophy.vsc.entity.User;
import com.techsophy.vsc.enums.UserStatuses;
import com.techsophy.vsc.repository.UserDetailsRepository;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public User getUserByUserId(String userId) {
		User user = null;
		try {
			Optional<User> vscUserOpt = userDetailsRepository.findByUserIdAndUserStatusId(userId,
					UserStatuses.ACTIVE.getValue());
			if (!vscUserOpt.isPresent()) {
				throw new ResourceNotFoundException("no user found with userId : " + userId);
			}
			user = vscUserOpt.get();
		} catch (Exception e) {
			logger.error("Exception in getUserByUserId() due to " + e);
			throw e;
		}
		return user;
	}

}
