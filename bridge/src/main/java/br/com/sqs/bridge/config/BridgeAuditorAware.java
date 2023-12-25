package br.com.sqs.bridge.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BridgeAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

	if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
	    return Optional.of("");

	return Optional.ofNullable(SecurityContextHolder.getContext())
		.map(SecurityContext::getAuthentication)
		.filter(Authentication::isAuthenticated)
		.map(Authentication::getPrincipal)
		.map(UserDetails.class::cast)
		.map(u -> u.getUsername());
    }

}
