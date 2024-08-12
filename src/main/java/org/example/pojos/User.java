package org.example.pojos;

/**
 * USer pojo used for logging in session without conflictions
 */
public class User {

    private String username;
   private String password;
   private String vcc;

    public String getUserName() {
        return username;
    }

    public void setUserNme(String userNme) {
        this.username = userNme;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
