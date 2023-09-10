package com.example.havecoworkproject.Service;

import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.Repository.AuthRepository;
import com.example.havecoworkproject.Repository.ClientRepository;

import com.example.havecoworkproject.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class userDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final ClientRepository clientRepository;
    private final AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUsersByUsername(username);
        if(user == null){
            throw new ApiException("User not found");
        }
        return user;
    }
}
