package com.loose.coupling;

public class UserDatabase {
    public String getUserDetails(){
        //directly access the db here
        return "User details from DB";
    }
}
