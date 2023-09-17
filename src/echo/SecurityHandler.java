package echo;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SecurityHandler extends ProxyHandler{
    private static SafeTable users = new SafeTable();
    private boolean loggedIn;
    public SecurityHandler(Socket s){
        super(s);
        loggedIn = false;
    }
    public SecurityHandler(){
        super();
        loggedIn = false;
    }
    protected String response(String request) throws Exception {
        String result = "";
        if (loggedIn) {
            result = super.response(request);
        } else {
            String[] args = request.split("\\s+");
            if (args[0].equalsIgnoreCase("new")) {
                if (users.get(args[1]) != null) {
                    result += "Username taken";
                } else {
                    users.update(args[1], args[2]);
                    result = "Account created";
                }
            } else if (args[0].equalsIgnoreCase("login")) {
                if (users.get(args[1]).equals(args[2])) {
                    loggedIn = true;
                    result = "Login successful";
                } else {
                    result = "Login failed";
                }
            }
            else {
                result = "Please log in";
            }
        }
        return result;
    }
}
