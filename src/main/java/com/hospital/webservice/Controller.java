package com.hospital.webservice;

import java.io.BufferedReader;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.hospital.config.APIKeyStore;
import com.hospital.constants.HospitalConstants.LoginStatus;
import com.hospital.dto.Authorisation;
import com.hospital.dto.Bank;
import com.hospital.dto.City;
import com.hospital.dto.Department;
import com.hospital.dto.Doctor;
import com.hospital.dto.LoginResponse;
import com.hospital.dto.RegisterRequest;
import com.hospital.dto.RegisterResponse;
import com.hospital.dto.Source;
import com.hospital.dto.LoginRequest;
import com.hospital.factory.ObjectFactory;
import com.hospital.filter.RequestFilter;
import com.hospital.service.LoginService;
import com.hospital.service.LoginServiceImpl;
import com.hospital.service.PatientRegistrationService;
import com.hospital.service.PatientRegistrationServiceImpl;

/**
 * @author Shivam Khare
 */
@CrossOrigin
@EnableAutoConfiguration
@RestController
@SpringBootApplication
public class Controller {

	private static final String REFERRAL = "REFERRAL";
	private static final String ORG = "ORG";

	private static boolean initialized = false;
	private LoginService loginService;
	private PatientRegistrationService patientRegistrationService;
	private APIKeyStore apiKeyStore;

	private AtomicInteger turnNumber;

	@RequestMapping("/")
	public ResponseEntity<Map<String, Object>> welcome(HttpServletRequest request, HttpServletResponse response) {

		if (!initialized) {
			init();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Cookie loginCookie = null;
			Cookie tokenCookie = null;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("token")) {
						tokenCookie = cookie;
						break;
					}
				}

				String authtoken = tokenCookie.getValue();

				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(authtoken)) {
						loginCookie = cookie;
						break;
					}
				}
			}
			if (loginCookie != null) {
				res.put("status", "success");
				res.put("username", loginCookie.getValue());
			} else {
				res.put("status", "failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 ResponseEntity<Map<String, Object>> x = new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		return x;
	}

	@RequestMapping("/department")
	public ResponseEntity<Map<String, Object>> department(HttpServletRequest request, HttpServletResponse response) {

		if (!initialized) {
			init();
		}
		Gson gson = new Gson();
		List<Department> departments = null;
		try {
			departments = patientRegistrationService.getDepartments();
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(departments));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ResponseEntity<Map<String, Object>> x = new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		return null;
	}

	@RequestMapping("/doctor")
	public ResponseEntity<Map<String, Object>> doctor(HttpServletRequest request, HttpServletResponse response) {

		if (!initialized) {
			init();
		}
		Map<String, String> params = paramsAsMap(request);
		String departmentId = params.get("departmentid");
		/*
		 * if(StringUtils.isBlank(departmentId)) { return null; }
		 */

		Gson gson = new Gson();
		List<Doctor> doctors = null;
		try {
			doctors = patientRegistrationService.getDoctors(departmentId);
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(doctors));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ResponseEntity<Map<String, Object>> x = new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		return null;
	}

	@RequestMapping("/bank")
	public ResponseEntity<Map<String, Object>> banks(HttpServletRequest request, HttpServletResponse response) {

		Gson gson = new Gson();
		if (!initialized) {
			init();
		}
		try {
			List<Bank> banks = patientRegistrationService.getBanks();
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(banks));
			// String data = IOUtils.toString(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/source")
	public ResponseEntity<Map<String, Object>> source(HttpServletRequest request, HttpServletResponse response) {

		Gson gson = new Gson();
		if (!initialized) {
			init();
		}
		try {
			Map<String, String> params = paramsAsMap(request);
			String source = params.get("source");
			if (REFERRAL.equals(source) || ORG.equals(source)) {
				String src = REFERRAL.equals(source) ? "Doctor" : "Organisation";
				List<Source> sources = patientRegistrationService.getSource(src);
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(sources));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/city")
	public ResponseEntity<Map<String, Object>> city(HttpServletRequest request, HttpServletResponse response) {

		Gson gson = new Gson();
		if (!initialized) {
			init();
		}
		try {
			Map<String, String> params = paramsAsMap(request);
			String prefix = params.get("prefix");

			List<City> cities = patientRegistrationService.getCities(prefix);
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(cities));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/turnno")
	public ResponseEntity<Map<String, Object>> turnNo(HttpServletRequest request, HttpServletResponse response) {

		Gson gson = new Gson();
		if (!initialized) {
			init();
		}
		try {
			int turn = patientRegistrationService.getTurnNo();
			response.setContentType("application/json");
			response.getWriter().write("{\"turn\":" + turn+"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/autorisation")
	public ResponseEntity<Map<String, Object>> discountAuthorisation(HttpServletRequest request, HttpServletResponse response) {

		Gson gson = new Gson();
		if (!initialized) {
			init();
		}
		try {
			List<Authorisation> banks = patientRegistrationService.getAuthorisations();
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(banks));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(HttpServletRequest request, HttpServletResponse response) {

		if (!initialized) {
			init();
		}
		ResponseEntity<Map<String, Object>> x = welcome(request, response);
		String status = (String)x.getBody().get("status");
		if("success".equals(status)) {
			return x;
		}
		// String apiKey = request.getHeader("api-key");
		boolean isValid = true;// verifyApiKey(apiKey);
		String username = null;
		String password = null;

		Gson gson = new Gson();
		String jsonBody = "";
		try {
			jsonBody = getJsonBody(request);
			LoginRequest req = gson.fromJson(jsonBody,LoginRequest.class);
			username = req.getUsername();
			password = req.getPassword();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Json is : " + jsonBody);
		Map<String, String> params = paramsAsMap(request);

		HttpStatus httpStatus = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (isValid) {
			LoginResponse loginResponse = loginService.doLogin(username, password);

			String token = null;
			if (LoginStatus.SUCCESS == loginResponse.getStatus()) {
				HttpSession session = request.getSession();
				token = UUID.randomUUID().toString().toUpperCase() + "|" + loginResponse.getUserId() + "|" + Calendar.getInstance().getTimeInMillis();
				session.setAttribute(token, username);
			}
			if (token != null) {
				Cookie loginCookie = new Cookie(token, username);
				Cookie tokenCookie = new Cookie("token", token);

				// setting cookie to expiry in 4 hrs
				loginCookie.setMaxAge(4 * 60 * 60);
				tokenCookie.setMaxAge(4 * 60 * 60);
				response.addCookie(loginCookie);
				response.addCookie(tokenCookie);
			}
			responseMap.put("status", loginResponse.getStatus().name());
			responseMap.put("description", loginResponse.getDescription());
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.UNAUTHORIZED;
		}

		//System.out.println("This is running fine !!");
		 ResponseEntity<Map<String, Object>> httpResponse = new ResponseEntity<Map<String, Object>>(responseMap, httpStatus);
		return httpResponse;
	}

	@RequestMapping("/patient/register")
	public ResponseEntity<Map<String, Object>> patientRegister(HttpServletRequest request, HttpServletResponse response) {

		if (!initialized) {
			init();
		}
		
		String jsonBody = "";
		try {
			jsonBody = getJsonBody(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(jsonBody);

		System.out.println("====================================================================");

		try {
			Gson gson = new Gson();
			RegisterRequest registerRequest = gson.fromJson(jsonBody,RegisterRequest.class);
			//Map<String,Object> map = new ObjectMapper().readValue(jsonBody, Map.class);
			//RegisterRequest registerRequest = new ObjectMapper().readValue(jsonBody, RegisterRequest.class);
			System.out.println(registerRequest.toString());
			
			RegisterResponse resp = patientRegistrationService.register(registerRequest);
			
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(resp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return null;
	}

	private boolean verifyApiKey(String apiKey) {
		return apiKeyStore.isValidKey(apiKey);
	}

	public static void main(String[] args) {
		SpringApplication.run(Controller.class, args);
	}

	public void init() {

		initialized = true;

		//apiKeyStore = new APIKeyStore();
		//apiKeyStore.loadAPIKeys();
		ObjectFactory objectFactory = new ObjectFactory();
		objectFactory.initialize();

		loginService = new LoginServiceImpl(objectFactory);
		patientRegistrationService = new PatientRegistrationServiceImpl(objectFactory);

	}

	private String getJsonBody(HttpServletRequest request) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}
		return sb.toString();
	}

	private Map<String, String> paramsAsMap(HttpServletRequest request) {
		
		Map<String, String> parameters = new HashMap<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			parameters.put(paramName, paramValue);
		}
		return parameters;
	}
}
