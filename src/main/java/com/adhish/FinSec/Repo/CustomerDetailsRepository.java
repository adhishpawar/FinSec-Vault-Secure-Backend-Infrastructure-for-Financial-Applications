package com.adhish.FinSec.Repo;

import com.adhish.FinSec.Model.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {}

