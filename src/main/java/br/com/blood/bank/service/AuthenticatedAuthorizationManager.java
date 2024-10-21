package br.com.blood.bank.service;

import br.com.blood.bank.repository.UserRepository;
import br.com.blood.bank.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticatedAuthorizationManager implements UserDetailsService {

    UserRepository userRepository;


    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {

        return userRepository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException(StringUtils.USER_NOT_FOUND));
    }
}