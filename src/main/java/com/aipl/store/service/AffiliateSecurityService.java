package com.aipl.store.service;

public interface AffiliateSecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
