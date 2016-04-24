package com.campusTalk.Utilities;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;

public class Hashing {

	private String password;
	
	public Hashing(String password){
		this.password = password;
	}
	
	public String getHash(){
		return DigestUtils.sha1Hex(password);
	}
}
