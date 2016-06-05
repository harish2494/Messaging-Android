package test.messaging.harish.messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinayagar on 6/3/2016.
 */
public class SMSData implements Serializable {
    private String number;
    private List<String> body = new ArrayList<String>();

    private String date;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void addBody(String body){
        this.body.add(body);
    }

    public List<String> getBody(){
        return this.body;
    }

}
