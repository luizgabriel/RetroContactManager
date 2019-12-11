package br.edu.ifce.contacts.persistence;

import br.edu.ifce.contacts.persistence.models.ContactGroup;

public interface IContactPersistance {
    ContactGroup getRoot();
    void saveChanges();
}
