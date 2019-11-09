package com.luo.seller.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SignService {

    static Map<String, String> PUBLIC_KEY = new HashMap();

    static {
        PUBLIC_KEY.put("1000", "key");
    }

    public String getPublicKey(String authId) {
        return PUBLIC_KEY.get(authId);
    }
}
