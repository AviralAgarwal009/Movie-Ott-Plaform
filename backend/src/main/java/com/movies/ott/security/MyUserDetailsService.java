package com.movies.ott.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movies.ott.Service.UserService;


@Service
public class MyUserDetailsService  implements UserDetailsService{

	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("Helloss");
		//find the specified username's password
		String password=userService.getPassword(username);
		System.out.println("Password"+password);
		return new User(username, password,new ArrayList<>());
		
	}
	
	

}
