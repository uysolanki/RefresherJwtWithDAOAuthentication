package com.excelr.RefresherBatchJwtToken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excelr.RefresherBatchJwtToken.entity.AuthenticationRequest;
import com.excelr.RefresherBatchJwtToken.entity.AuthenticationResponse;
import com.excelr.RefresherBatchJwtToken.security.MyUserDetailsService;
import com.excelr.RefresherBatchJwtToken.util.JwtUtil;

@Controller
public class JwtController {
	
	@Autowired
	JwtUtil jwtTokenUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	 @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
	        }
	        catch (BadCredentialsException e) {
	            throw new Exception("Incorrect username or password", e);
	        }

	        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

	        final String jwt = jwtTokenUtil.generateToken(userDetails);

	        return ResponseEntity.ok(new AuthenticationResponse(jwt));
	    }
	 
	 @GetMapping("/create1")
	 public String create1()
	 {
		 return "create1";
	 }
	 
	 @GetMapping("/read1")
	 public String read1()
	 {
		 return "read1";
	 }
	 
	 @GetMapping("/update1")
	 public String update1()
	 {
		 return "update1";
	 }
	 
	 @GetMapping("/delete1")
	 public String delete1()
	 {
		 return "delete1";
	 }
	 
	 @RequestMapping("/403")
		public ModelAndView accesssDenied(Principal user) {

			ModelAndView model = new ModelAndView();

			if (user != null) {
				model.addObject("msg", "Hi " + user.getName() 
				+ ", you do not have permission to access this page!");
			} else {
				model.addObject("msg", 
				    "you do not have permission to access this page!");
			}

			model.setViewName("403");
			return model;

		}

}
