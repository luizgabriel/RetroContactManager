package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;

public interface IUI {
    void onStart();
    void onUpdate() throws ApplicationExitException;
    void onFinish();
}
