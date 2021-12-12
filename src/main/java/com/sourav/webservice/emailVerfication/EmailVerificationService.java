package com.sourav.webservice.emailVerfication;

import java.time.Instant;
import java.util.Optional;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailVerificationService {

	private final long validity = 500000;//in mills
	private final int missingLimit = 2;
	private static final org.jboss.logging.Logger logger = LoggerFactory.logger(EmailVerificationService.class);
	
	@Autowired
	private EmailJpaRepository repo;

    public EmailDetails getCode(String addr) {
    	Optional<EmailDetails> emailDetails = Optional.ofNullable(repo.getById(addr));
    	if (!emailDetails.isPresent()) {
    		return null;
    	}
    	return emailDetails.get();
	}

	public EmailDetails createCode(EmailDetails emailDetails) {
			//create it
		    String email = emailDetails.getAddr();
		    int code = email.hashCode();
		    EmailDetails createdEmailDetails = new EmailDetails(email, code);
		    repo.save(createdEmailDetails);
		    logger.info(String.format("Email mapping has been created successfully : %s", createdEmailDetails.toString()));
		    return createdEmailDetails;
	}

	public boolean verify(EmailDetails emailDetails) {
		EmailDetails existingEmailDetails = getCode(emailDetails.getAddr());
		if (existingEmailDetails == null) {
			logger.info(String.format("Email does not exist in the system : %s", emailDetails.getAddr()));
			return false;
		} else {
			//exist
			Instant currTime = Instant.now();
			if (existingEmailDetails.getCreationTime() + validity < currTime.toEpochMilli()) {
				logger.info(String.format("Email code validity [ %d ]expired : %s", validity, existingEmailDetails.toString()));
				return false;
			}
			if (existingEmailDetails.getCode() != emailDetails.getCode()) {
				existingEmailDetails.setUnsuccessfulVerification(existingEmailDetails.getUnsuccessfulVerification()+1);
				repo.save(existingEmailDetails);
				logger.info(String.format("Email code does not match : %s", existingEmailDetails.toString()));
				return false;
			}
			if (missingLimit < existingEmailDetails.getUnsuccessfulVerification()) {
				logger.info(String.format("Email code match miss rate expired : %s", existingEmailDetails.toString()));
				return false;
			}
			logger.info(String.format("Email mapping has been verified successfully : %s", existingEmailDetails.toString()));
			return true;
		}
	}
}
