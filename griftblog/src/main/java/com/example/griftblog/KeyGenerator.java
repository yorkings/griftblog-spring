package com.example.griftblog;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // Generate 32 bytes (256 bits) of randomness, which is the minimum secure size.
        // The Base64 output will be 43 characters long. Use more bytes for a longer key.
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[64]; // Use 64 bytes for a 512-bit key (~86 Base64 chars)
        random.nextBytes(bytes);

        String secret = Base64.getEncoder().encodeToString(bytes);
        System.out.println(secret);
    }
}