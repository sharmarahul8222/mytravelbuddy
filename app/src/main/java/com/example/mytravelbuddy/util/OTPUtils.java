package com.example.mytravelbuddy.util;

public class OTPUtils {

    public static String generateOTP() {
        int otp = 100000 + (int)(Math.random() * 900000);
        return String.valueOf(otp);
    }
}
