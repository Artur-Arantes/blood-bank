package br.com.blood.bank.config.security;


import br.com.blood.bank.domain.User;
import br.com.blood.bank.exception.ObjectNotFoundException;
import br.com.blood.bank.repository.UserRepository;
import br.com.blood.bank.service.TokenService;
import br.com.blood.bank.utils.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class VerifyToken extends OncePerRequestFilter {
    @Autowired
    private TokenService service;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (!Objects.isNull(authHeader)) {
            String token = authHeader.replace("Bearer", "").trim();
            String login = service.verifyToken(token);
            UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(login);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String login) {

        User user = userRepository.findByLogin(login).
                orElseThrow(() -> new ObjectNotFoundException(StringUtils.USER_NOT_FOUND));
        return new UsernamePasswordAuthenticationToken(user,
                null, user.getAuthorities());
    }
}