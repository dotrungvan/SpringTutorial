package com.example.SpringTutorial.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringTutorial.util.APIConst;
import com.example.SpringTutorial.util.JsonHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class HomeController {

	@Autowired
	private JsonHandler jsonHandler;

	@GetMapping(value = "/", produces = "application/json")
	public String index() throws Exception {
		return "index";
	}

	@GetMapping(value = "/home", produces = "application/json")
	public String home(@Nullable @RequestParam(value = APIConst.ACCESS_TOKEN) String accessToken,
			HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		if (StringUtils.hasText(accessToken)) {
			Map<String, String> token = new HashMap<String, String>();
			token.put(APIConst.ACCESS_TOKEN, accessToken);
			return jsonHandler.toJson(token);
		} else {
			return "hello";
		}
	}
}
