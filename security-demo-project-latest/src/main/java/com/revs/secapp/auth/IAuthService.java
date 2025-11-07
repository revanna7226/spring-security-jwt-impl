package com.revs.secapp.auth;

import com.revs.secapp.auth.request.AuthRequest;
import com.revs.secapp.auth.request.RefreshRequest;
import com.revs.secapp.auth.request.RegistrationRequest;
import com.revs.secapp.auth.response.AuthResponse;

public interface IAuthService {

    AuthResponse login(AuthRequest request);

    void register(RegistrationRequest request);

    AuthResponse refreshToken(RefreshRequest refreshToken);

}
