package com.gdsc.wero.global.auth.oauth;


import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Getter
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsernameAndProvider(String email, String provider) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAndProvider(email, provider)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(user);
    }

}
