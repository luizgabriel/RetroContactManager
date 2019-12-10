package br.edu.ifce.contacts.models;

public class Contact implements IContactItem {
    private String name;
    private String email;
    private String tel;

    public Contact(String tel) {
        this.tel = tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return this.name;
    }


}