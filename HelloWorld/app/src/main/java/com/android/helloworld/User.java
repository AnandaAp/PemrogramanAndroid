package com.android.helloworld;

public class User {
    int ID;
    String email,password;
    public User(int ID,String Email,String Password){
        setEmail(Email);
        setID(ID);
        setPassword(Password);
    }
    public User(String Email,String Password){
        setEmail(Email);
        setID(ID);
        setPassword(Password);
    }

    private void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){ return password; }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public int getID(){
        return ID;
    }
    public void setID(int id){
        this.ID = id;
    }
}
