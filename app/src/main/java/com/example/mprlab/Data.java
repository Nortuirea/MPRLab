package com.example.mprlab;

import java.util.HashMap;

public class Data {
    public String UID;
    public String username;
    public String email;
    public String password;

    public Data(){

    }

    public Data(String UID, String username, String email, String password){
        this.UID = UID;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public HashMap<String, String> toObject(){
        HashMap<String, String> temp = new HashMap<>();
        temp.put("UID", this.UID);
        temp.put("username", this.username);
        temp.put("email", this.email);
        temp.put("password", this.password);
        return temp;
    }
}
