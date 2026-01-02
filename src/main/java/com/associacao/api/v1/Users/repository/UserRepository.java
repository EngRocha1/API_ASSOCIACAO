package com.associacao.api.v1.Users.repository;

import com.associacao.api.v1.Users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Users, String>, CrudRepository<Users, String> {

    UserDetails findByLogin(String login);


}
