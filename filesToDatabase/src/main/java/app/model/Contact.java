package app.model;

import java.util.ArrayList;
import java.util.Map;

public class Contact extends ArrayList<String> {

    private long customerId;
    Map<Integer, String> contats;


    public Contact( long customerId, Map<Integer, String> contats) {
        this.customerId = customerId;
        this.contats = contats;
    }


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Map<Integer, String> getContats() {
        return contats;
    }

    public void setContats(Map<Integer, String> contats) {
        this.contats = contats;
    }
}
