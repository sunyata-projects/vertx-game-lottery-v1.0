package org.sunyata.octopus;

import java.io.Serializable;

/**
 * Created by leo on 17/4/18.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 4184871401453031406L;
    private String id;
    private String name;
    private String password;

    public User() {
    }

//    public User(String name) {
//        super();
//        this.id = name;
//        this.name = name;
//        //this.password = password;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password
                + "]";
    }

}

