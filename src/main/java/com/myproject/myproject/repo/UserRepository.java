package com.myproject.myproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.myproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserName(String userName);
}
