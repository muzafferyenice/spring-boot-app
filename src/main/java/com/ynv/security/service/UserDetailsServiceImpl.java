package com.ynv.security.service;

import com.ynv.domain.User;
import com.ynv.exception.message.ErrorMessage;
import com.ynv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
        String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,username)
        ));

        return UserDetailsImpl.create(user);
    }

    public UserDetailsImpl loadUserById(Long id){
        User user = userRepository.findById(id).get();
        return UserDetailsImpl.create(user);
    }

}
