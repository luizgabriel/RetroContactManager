package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.IContactBusiness;

public interface IContactView extends IUI {
    void setContactListener(IContactBusiness listener);
}
