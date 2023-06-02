package com.bestbuy.TransactionApp.queue.responses;

public class AuthenticationResponse {

    String userName;
    Long userId;
    Boolean isLoggedIn;
    String role;
    Boolean isValid;

    public AuthenticationResponse(String str) {
        String[] strArr = str.split(";");
        userName = strArr[0];
        if(userName.length() == 0){
            isValid = false;
            return;
        }
        isValid = true;
        userId = Long.parseLong(strArr[1]);
        isLoggedIn = strArr[2].equals("true");
        role = strArr[3];
    }

    public Boolean isAdmin(){
        return isValid && role.equals("admin");
    }

    public Boolean isValid(){
        return this.isValid;
    }

    public Long getUserId(){
        return this.userId;
    }

    
}
