package com.revs.secapp.user;

import com.revs.secapp.user.request.ChangePasswordRequest;
import com.revs.secapp.user.request.ProfileUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    void updateProfileInfo(ProfileUpdateRequest request, String userId);

    void changePassword(ChangePasswordRequest request, String userId);

    void deactivateAccount(String userId);

    void reactivateAccount(String userId);

    void deleteAccount(String userId);
}
