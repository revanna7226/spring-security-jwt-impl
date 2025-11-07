package com.revs.secapp.user;

import com.revs.secapp.auth.request.RegistrationRequest;
import com.revs.secapp.user.request.ProfileUpdateRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public void mergeUserInfo(final User user, final ProfileUpdateRequest request) {
        if (StringUtils.isNoneBlank(request.getFirstName())
            && !user.getFirstName().equals(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }
        if (StringUtils.isNotBlank(request.getLastName()) && !user.getLastName()
            .equals(request.getLastName())) {
            user.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null && !request.getDateOfBirth()
            .equals(user.getDateOfBirth())) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
    }

    public User toUser(final RegistrationRequest request) {
        return User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phoneNumber(request.getPhoneNumber())
            .password(this.passwordEncoder.encode(request.getPassword()))
            .enabled(true)
            .locked(false)
            .credentialsExpired(false)
            .phoneVerified(false)
            .emailVerified(false)
            .build();
    }
}
