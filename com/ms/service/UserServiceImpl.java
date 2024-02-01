package com.ms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.config.JwtProvider;
import com.ms.entity.User;
import com.ms.exception.UserException;
import com.ms.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	JwtProvider jwtProvider;

	@Override
	public User findUserById(long userId) throws UserException {
		
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		
		throw new UserException("User not found with id - "+ userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepo.findByEmail(email);
		
		if(user == null) {
			throw new UserException("User not found with email - "+ email);
		}
		
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAllByOrderByCreatedAtDesc();
	}

}
