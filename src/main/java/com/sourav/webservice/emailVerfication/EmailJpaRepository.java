package com.sourav.webservice.emailVerfication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailJpaRepository extends JpaRepository<EmailDetails, String> {

}
