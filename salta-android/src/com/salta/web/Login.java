package com.salta.web;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class Login {

    private String email;
    private String password;
    private boolean checked;

    public Login(String email, String password, boolean checked) {
        this.email = email;
        this.password = password;
        this.checked = checked;
    }

    public void authenticate() {
//    	DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
//    	HttpPost httpPost = new HttpPost();
//    	httpPost.setU
    }
}
