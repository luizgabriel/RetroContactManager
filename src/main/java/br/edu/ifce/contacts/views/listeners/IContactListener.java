package br.edu.ifce.contacts.views.listeners;

import br.edu.ifce.contacts.models.Contact;

public interface IContactListener {
    void onRequestCreate();
    void onRequestCreateGroup();

    void onCreate(Contact contact);
}
