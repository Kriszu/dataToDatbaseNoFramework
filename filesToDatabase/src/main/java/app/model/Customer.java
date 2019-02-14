package app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer {

    private int id;
    private String name;
    private String surname;
    private int age;
    private Contact contats;

    public Customer(int id, String name, String surname, int age, ArrayList<String> contactsCustomer) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        Map<Integer, String> contacts = new HashMap<>();
        for(int i = 0; i< contactsCustomer.size(); ++i) {

                if (i < contactsCustomer.size()-1 && contactsCustomer.get(i + 1).equals("jbr")) {
                    contacts.put(3, contactsCustomer.get(i));
                    i++;
                } else if (contactsCustomer.get(i).contains("@")) {
                    contacts.put(1, contactsCustomer.get(i));
                } else if (contactsCustomer.get(i).matches("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")) {
                    contacts.put(2, contactsCustomer.get(i));
                } else {
                    contacts.put(0, contactsCustomer.get(i));
                }

        }
        this.contats = new Contact(this.id, contacts);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Contact getContats() {
        return contats;
    }

    public void setContats(Contact contats) {
        this.contats = contats;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", contats=" + contats +
                '}';
    }
}
