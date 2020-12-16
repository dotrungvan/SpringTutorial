package com.example.SpringTutorial.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class HomeController {

//	@Autowired
//	private JsonHandler jsonHandler;

	@GetMapping(value = "/", produces = "application/json")
	public String index() throws Exception {
		return "index";
	}

//	@GetMapping(value = "/home", produces = "application/json")
//	public String home(@Nullable @RequestParam(value = APIConst.ACCESS_TOKEN) String accessToken,
//			HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
//		if (StringUtils.hasText(accessToken)) {
//			Map<String, String> token = new HashMap<String, String>();
//			token.put(APIConst.ACCESS_TOKEN, accessToken);
//			return jsonHandler.toJson(token);
//		} else {
//			return "hello";
//		}
//	}

	@GetMapping(value = "/home2", produces = "application/json")
	public String home2(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		return "home2";
	}
//
//	@GetMapping(value = "/login", produces = "application/json")
//	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("Login Controller");
//		return "login";
//	}
}
