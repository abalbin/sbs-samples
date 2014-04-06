package com.mycompany.rest.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.mycompany.rest.client.domain.Person;
import com.mycompany.rest.client.domain.PersonList;

@Controller
public class RestClientController {

	protected static Logger logger = Logger.getLogger("RestClientController");

	private RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getPerson(@RequestParam("id") Long id, Model model) {
		logger.debug("Retrieve person with id: " + id);
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_XML);
		
		// Prepare header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<Person> entity = new HttpEntity<Person>(headers);

		// Send the request as GET
		try {
			ResponseEntity<Person> result = restTemplate.exchange(
					"http://localhost:8080/spring-rest-servidor/agenda/person/{id}", 
					HttpMethod.GET, entity, Person.class, id);
			model.addAttribute("person", result.getBody());
		} catch (Exception e) {
			logger.error(e);
		}
		
		// This will resolve to /WEB-INF/jsp/getpage.jsp
		return "getpage";
	}
	
	
	
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public String getAll(Model model) {
		// Preparamos el formato de datos
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_XML);

		// Preparar header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<Person> entity = new HttpEntity<Person>(headers);
		try {
			ResponseEntity<PersonList> result = restTemplate.exchange(
					"http://localhost:8080/spring-rest-servidor/agenda/persons",
					HttpMethod.GET, entity, PersonList.class);
			model.addAttribute("persons", result.getBody().getData());
		} catch (Exception ex) {
			logger.error(ex);
		}
		return "personspage";
	}

	
	/**
     * Retrieves the JSP add page
     */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddPage(Model model) {
		logger.debug("Received request to show add page");

		// Create new Person and add to model
		// This is the formBackingOBject
		model.addAttribute("personAttribute", new Person());

		// This will resolve to /WEB-INF/jsp/addpage.jsp
		return "addpage";
	}
	
	/**
	 * Sends a new record to the REST provider
	 * based on the information submitted from the JSP add page.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("personAttribute") Person person,
						 Model model) {
		logger.debug("Add new person");
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_XML);
		
		// Prepare header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		// Pass the new person and header
		HttpEntity<Person> entity = new HttpEntity<Person>(person, headers);

		// Send the request as POST
		try {
			ResponseEntity<Person> result = restTemplate.exchange("http://localhost:8080/spring-rest-servidor/agenda/person", HttpMethod.POST, entity, Person.class);
		} catch (Exception e) {
			logger.error(e);
		}
		
		// This will resolve to /WEB-INF/jsp/resultpage.jsp
		return "resultpage";
	}
	
	/**
     * Retrieves the JSP update page
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String getUpdatePage(@RequestParam(value="id", required=true) Integer id,  
    										Model model) {
    	logger.debug("Received request to show edit page");
    
    	// Retrieve existing Person and add to model
    	// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_XML);
		
		// Prepare header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<Person> entity = new HttpEntity<Person>(headers);

		// Send the request as GET
		try {
			ResponseEntity<Person> result = restTemplate.exchange("http://localhost:8080/spring-rest-servidor/agenda/person/{id}", HttpMethod.GET, entity, Person.class, id);
			// Add to model
			model.addAttribute("personAttribute", result.getBody());
			
		} catch (Exception e) {
			logger.error(e);
		}
    	
    	// This will resolve to /WEB-INF/jsp/updatepage.jsp
    	return "updatepage";
	}
	
	/**
	 * Sends an update request to the REST provider
	 * based on the information submitted from the JSP update page.
	 */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePerson(@ModelAttribute("personAttribute") Person person,
						@RequestParam(value="id",  required=true) Long id,
						Model model) {
		logger.debug("Update existing person");
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_XML);
		
		// Prepare header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		// Pass the new person and header
		HttpEntity<Person> entity = new HttpEntity<Person>(person, headers);
		
		// Send the request as PUT
		ResponseEntity<String> result = restTemplate.exchange("http://localhost:8080/spring-rest-servidor/agenda/person/{id}", HttpMethod.PUT, entity, String.class, id);

		// This will resolve to /WEB-INF/jsp/resultpage.jsp
		return "resultpage";
    }
    
    /**
	 * Sends a delete request to the REST provider
	 */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deletePerson(@RequestParam("id") Long id,
										Model model) {
    	logger.debug("Delete existing person");
    	
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_XML);
		
		// Prepare header
    	HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<String> entity = new HttpEntity<String>(headers);	
		
		// Send the request as DELETE
		ResponseEntity<String> result = restTemplate.exchange("http://localhost:8080/spring-rest-servidor/agenda/person/{id}", HttpMethod.DELETE, entity, String.class, id);
		
		// This will resolve to /WEB-INF/jsp/resultpage.jsp
		return "resultpage";
    }
}
