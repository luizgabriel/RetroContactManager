package br.edu.ifce.contacts.models;

import java.util.*;

public class ContactGroup implements IContactItem {
    private List<IContactItem> items;
    private String name;

    public ContactGroup(String name) {
        this.items = new ArrayList<>();
        this.name = name;
    }

    public void add(IContactItem item) {
        this.items.add(item);
    }

    public void remove(IContactItem item) {
        this.items.remove(item);
    }

    public boolean contains(IContactItem item) {
        return this.items.contains(item);
    }

    public int size() {
        return this.items.size();
    }

    @Override
    public String getName() {
        return name;
    }


    public List<IContactItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
