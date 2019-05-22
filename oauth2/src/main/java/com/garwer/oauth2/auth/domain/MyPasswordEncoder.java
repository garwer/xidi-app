package com.garwer.oauth2.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Garwer
 * @Date: 2019/5/21 12:08 AM
 * @Version 1.0
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
