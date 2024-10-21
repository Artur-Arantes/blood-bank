package br.com.blood.bank.controller;

import br.com.blood.bank.domain.User;
import br.com.blood.bank.dto.LoginInPutDto;
import br.com.blood.bank.dto.TokenDto;
import br.com.blood.bank.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final private AuthenticationManager authorizationManager;

    final private TokenService tokenService;

    public AuthController(AuthenticationManager authorizationManager, TokenService tokenService) {
        this.authorizationManager = authorizationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> auth(@RequestBody final LoginInPutDto loginInPutDto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                loginInPutDto.login(),
                loginInPutDto.password()
        );
        Authentication auth = authorizationManager.authenticate(usernamePassword);
        String token = tokenService.tokenGenerate((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDto(token));
    }
}