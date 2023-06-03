package com.FAM.messageApp.queue.responses;

public class AuthenticationResponse {

    String userName;
    String userId;
    Boolean isLoggedIn;
    String role;
    Boolean isValid;

    public AuthenticationResponse(String str) {
        // authenticate;userName;userId;isLoggedIn;role
        String[] strArr = str.split(";");
        userName = strArr[1];
        if(userName.length() == 0){
            isValid = false;
            return;
        }
        isValid = true;
        userId = strArr[2];
        isLoggedIn = strArr[3].equals("true");
        role = strArr[4];
    }

    public Boolean isAdmin(){
        return isValid && role.toLowerCase().equals("admin");
    }

    public Boolean isValid(){
        return this.isValid;
    }

    public String getUserId(){
        return this.userId;
    }
}