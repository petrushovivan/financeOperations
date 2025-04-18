package com.example.financeOperations;

import com.example.financeOperations.security.JWTUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTUtilsTest {
    private final JWTUtils utils;

    private String validJWT;
    private String invalidToken;

    @Autowired
    public JWTUtilsTest(JWTUtils utils) {
        this.utils = utils;
        validJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJteUVtYWlsJCMkdXNlcjEiLCJleHAiOjE3NDUxNzc5MTJ9.Aec-Gog6AZhBDmRsrWVDlLRDee1lWohyrBueK6OvRVc";
        invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJteUVtYWlsJCMkdXNlcjEiLCJleHAiOjE3NDQ5Nzg3MjF9.eEIsHOdyy55zOpSQ3U_uhONfbxKNfjTAyJ3XR6rfLtQ";
    }

    @Test
    public void validateJWTMethodTest1() {
        String jwtKey = invalidToken;
        Assertions.assertFalse(utils.validateJWT(jwtKey));
    }

    @Test
    public void validateJWTMethodTest2() {
        String jwtKey = validJWT;
        Assertions.assertTrue(utils.validateJWT(jwtKey));
    }

    @Test
    public void getEmailFromJWTMethodTest1() {
        Assertions.assertEquals(utils.getEmailFromJWT(validJWT), "myEmail");
    }
}
