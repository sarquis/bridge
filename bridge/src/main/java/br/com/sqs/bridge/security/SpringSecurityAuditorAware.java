package br.com.sqs.bridge.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

	// @formatter:off
	//
	// Em uma forma mais legível:
	//
	// Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
	//
	// if (authentication == null || !authentication.isAuthenticated())
	// 	return null;
	//
	// return ((UserDetails) authentication.getPrincipal()).getUsername();
	//
	// @formatter:on

	// Em uma forma mais "moderna":

	return Optional.ofNullable(SecurityContextHolder.getContext())
		.map(SecurityContext::getAuthentication)
		.filter(Authentication::isAuthenticated)
		.map(Authentication::getPrincipal)
		.map(UserDetails.class::cast)
		.map(u -> u.getUsername());
    }

}
