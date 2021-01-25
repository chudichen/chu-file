package com.chudichen.chufile.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author chudichen
 * @date 2021-01-25
 */
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
