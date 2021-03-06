package com.globallogic.car.dashboard.controller;

import static com.globallogic.car.dashboard.controller.CarDashboardEndpointResources.REST_API_PREFIX;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.http.HttpStatus.OK;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.car.dashboard.dto.UserDto;
import com.globallogic.car.dashboard.service.spi.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(REST_API_PREFIX)
@OpenAPIDefinition(security = { @SecurityRequirement(name = "bearerScheme") })
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping(value = "/user", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
	private ResponseEntity<UserDto> saveUser(@RequestBody final UserDto userDto) {
		return new ResponseEntity<>(userService.saveUser(userDto), OK);
	}
	
	@GetMapping(value = "/user", produces = APPLICATION_JSON)
	private ResponseEntity<UserDto> getUser(@RequestHeader("userId") Long userId) {
		return new ResponseEntity<>(userService.getUserById(userId), OK);
	}

	@GetMapping(value = "/users/info", produces = APPLICATION_JSON)
	private ResponseEntity<Set<UserDto>> getUsersInforation() {
		return new ResponseEntity<>(userService.getAllUsers(), OK);
	}
	
	@DeleteMapping(value = "/user", produces = APPLICATION_JSON)
	private ResponseEntity<UserDto> deleteUser(@RequestHeader("userId") Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(OK);
	}
}
