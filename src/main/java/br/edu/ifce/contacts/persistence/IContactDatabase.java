package br.edu.ifce.contacts.persistence;

import br.edu.ifce.contacts.models.ContactGroup;

public interface IContactDatabase {
    ContactGroup getRoot();
    void saveChanges();
}
