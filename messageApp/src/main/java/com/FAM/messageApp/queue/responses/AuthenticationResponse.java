package com.FAM.messageApp.queue.responses;

public class AuthenticationResponse {

    String userName;
    Long userId;
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
        userId = Long.parseLong(strArr[2]);
        isLoggedIn = strArr[3].equals("true");
        role = strArr[4];
    }

    public Boolean isAdmin(){
        return isValid && role.toLowerCase().equals("admin");
    }

    public Boolean isValid(){
        return this.isValid;
    }

    public Long getUserId(){
        return this.userId;
    }

    
}
