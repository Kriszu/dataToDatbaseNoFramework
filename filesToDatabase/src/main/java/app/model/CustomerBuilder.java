package app.model;

import java.util.*;

public class CustomerBuilder {

    private ArrayList<String> onlyContatcs(ArrayList<String> customer){
        ArrayList<String> temp = (ArrayList<String>) customer.clone();
        Iterator<String> it = temp.iterator();
        int i = 0;
        while(i<3){
            it.next();
            it.remove();

            i++;
        }
        return temp;
    }

    private ArrayList<String> onlyContatcsException(ArrayList<String> customer){
        ArrayList<String> temp = (ArrayList<String>) customer.clone();
        Iterator<String> it = temp.iterator();
        int i = 0;
        while(i<2){
            it.next();
            it.remove();

            i++;
        }
        return temp;
    }

    private Customer createCustomer(Integer id, ArrayList<String> customer) {



       try{
           ArrayList<String> contacs = onlyContatcs(customer);

           Customer result = new Customer(id,
                   customer.get(0),
                   customer.get(1),
                   Integer.parseInt(customer.get(2)),
                   contacs);
           return result;
       } catch (NumberFormatException e){
           ArrayList<String> contacs = onlyContatcsException(customer);


           Customer result = new Customer(id,
                   customer.get(0),
                   customer.get(1),
                  0,
                   contacs) ;
           return result;
       }


    }

    public List<Customer> createAllCustomers(Map<Integer, ArrayList<String>> customers) {

        List<Customer> result = new ArrayList<>();

        for (Map.Entry<Integer, ArrayList<String>> entry : customers.entrySet()
        ) {
            result.add(createCustomer(entry.getKey(), customers.get(entry.getKey())));
        }
        return result;
    }
}

