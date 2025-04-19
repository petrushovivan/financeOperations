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

    @Autowired
    public JWTUtilsTest(JWTUtils utils) {
        this.utils = utils;
        validJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJteUVtYWlsJCMkdXNlcjEiLCJleHAiOjE3NDQ5OTAwNTd9.upv_L0Pa6vftikzi5GjK8U-0BpvNPCMMHyJBJx-YeAg";
    }

    @Test
    public void validateJWTMethodTest1() {
        String jwtKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJteUVtYWlsJCMkdXNlcjEiLCJleHAiOjE3NDQ5Nzg3MjF9.eEIsHOdyy55zOpSQ3U_uhONfbxKNfjTAyJ3XR6rfLtQ";
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
