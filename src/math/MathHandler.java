package math;

import echo.*;

import java.net.*;

public class MathHandler extends RequestHandler {
    private int total;
    public MathHandler(Socket sock){
        super(sock);
        init();
    }
    public MathHandler(){
        super();
        init();
    }
    private void init(){
        total = 0;
    }
    @Override
    protected String response(String request) throws Exception {
        String result = "";
        String[] individualCmmd = request.split(" ");
        if (request.equalsIgnoreCase("command")) {
            init();
            result = "operator num num etc.";
        } else if (request.equalsIgnoreCase("operator")){
            result = "add | mul | sub | div";
        } else if (request.equalsIgnoreCase("num")){
            result = "any number";
        } else if (individualCmmd[0].trim().equalsIgnoreCase("add")){
            int sum = 0;
            for (int i = 1; i < individualCmmd.length; i++){
                int value = Integer.parseInt(individualCmmd[i]);
                sum += value;
            }
            result = String.valueOf(sum);
        } else if (individualCmmd[0].trim().equalsIgnoreCase("mul")){
            int prod = 1;
            for (int i = 1; i < individualCmmd.length; i++){
                int value = Integer.parseInt(individualCmmd[i]);
                prod *= value;
            }
            result = String.valueOf(prod);
        } else if (individualCmmd[0].trim().equalsIgnoreCase("sub")){
            int diff = Integer.parseInt(individualCmmd[1]);
            for (int i = 2; i < individualCmmd.length; i++){
                int value = Integer.parseInt(individualCmmd[i]);
                diff -= value;
            }
            result = String.valueOf(diff);
        } else if (individualCmmd[0].trim().equalsIgnoreCase("div")){
            int quotient = Integer.parseInt(individualCmmd[1]);
            for (int i = 2; i < individualCmmd.length; i++){
                int value = Integer.parseInt(individualCmmd[i]);
                quotient /= value;
            }
            result = String.valueOf(quotient);
        } else {
            result = "Unrecognized command: " + request;
        }
        return result;
    }
}
