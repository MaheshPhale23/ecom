package com.ms.service;

import java.util.List;

import com.ms.entity.User;
import com.ms.exception.UserException;

public interface UserService {
	
	public User findUserById(long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> findAllUsers();

}
