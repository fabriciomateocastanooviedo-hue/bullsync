package com.fco.bullsync.repository;

import com.fco.bullsync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
