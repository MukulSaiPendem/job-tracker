package com.app.job_tracker.repository;

import com.app.job_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo  extends JpaRepository<User, UUID> {

    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(String email);

    public Optional<User> deleteUserByUsername(String Username);
}
