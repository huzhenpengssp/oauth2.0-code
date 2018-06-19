package com.authorization.domain;

import java.io.Serializable;

public class Code implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7732020421674063862L;

	private String code;

    private byte[] authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}