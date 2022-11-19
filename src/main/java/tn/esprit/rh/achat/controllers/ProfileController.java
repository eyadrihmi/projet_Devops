package tn.esprit.rh.achat.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import tn.esprit.rh.achat.AppProfile;
@RestController
@Api(tags = "Gestion des profiles")
@RequestMapping("/profiles")
@CrossOrigin("*")

public class ProfileController {

	@Autowired
	AppProfile app;

	// http://localhost:8089/SpringMVC/profiles/getProfile
	@GetMapping("/")
	@ResponseBody
	public String getProfile() {
		return app.getMessage();
	}


}
