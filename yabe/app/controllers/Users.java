package controllers;

/**
 * Created by PB5N0097 on 5/20/2015.
 */
import play.mvc.*;

@Check("admin")
@With(Secure.class)
public class Users extends CRUD{
}
