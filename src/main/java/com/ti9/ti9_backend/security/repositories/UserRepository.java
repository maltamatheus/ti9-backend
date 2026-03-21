package com.ti9.ti9_backend.security.repositories;

import com.ti9.ti9_backend.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,String> {
    UserDetails findByLogin(String login);
}
