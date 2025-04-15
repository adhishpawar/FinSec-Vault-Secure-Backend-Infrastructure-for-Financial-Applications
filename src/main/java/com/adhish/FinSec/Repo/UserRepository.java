package com.adhish.FinSec.Repo;

import com.adhish.FinSec.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
