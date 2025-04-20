package com.adhish.FinSec.Repo;

import com.adhish.FinSec.Entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {}

