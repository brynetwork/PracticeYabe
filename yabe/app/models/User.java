package models;

import java.util.*;
import javax.persistence.*;

import net.sf.oval.constraint.Email;
import play.data.validation.*;
import play.db.jpa.*;

/**
 * Created by PB5N0097 on 5/6/2015.
 */
@Entity
public class User extends Model{

    @Email
    @Required
    public String email;

    @Required
    public String password;

    public String fullname;
    public boolean isAdmin;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public String toString(){
        //String test = email + fullname;
        //return test;
        return email;
    }
    public static User connect(String email, String password){
        return find("byEmailAndPassword", email, password).first();
    }

}
