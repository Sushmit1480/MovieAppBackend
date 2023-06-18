package com.cts.MovieBookingApp.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
	
	User user1 = new User();
	
	@Test
	@DisplayName("Checking If User Class Loading or not")
	void userIsLoadingOrNot() {
		assertThat(user1).isNotNull();
	}
	
	@Test
	@DisplayName("Cheking If User Class constructor responding correctly or not.")
	void testUserClass() {
		User user = new User(101, "Amit", "Kumar", "amit.kumar@gmail.com", "dummy", "9988776655");
		
		assertEquals(101, user.getLoginId());
		assertEquals("Amit", user.getFirstName());
		assertEquals("Kumar", user.getLastName());
		assertEquals("amit.kumar@gmail.com", user.getEmail());
		assertEquals("dummy", user.getPassword());
		assertEquals("9988776655", user.getContactNumber());
	}
	
	@Test
	@DisplayName("Cheking If User Class Getter and Setter responding correctly or not.")
	void testGetterSetterofUserClass() {
		User user = new User();
		
		user.setLoginId(101);
		user.setFirstName("Amit");
		user.setLastName("Kumar");
		user.setEmail("amit.kumar@gmail.com");
		user.setPassword("dummy");
		user.setContactNumber("9988776655");
		
		assertEquals(101, user.getLoginId());
		assertEquals("Amit", user.getFirstName());
		assertEquals("Kumar", user.getLastName());
		assertEquals("amit.kumar@gmail.com", user.getEmail());
		assertEquals("dummy", user.getPassword());
		assertEquals("9988776655", user.getContactNumber());
	}
}
