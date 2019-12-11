package br.edu.ifce.contacts.views;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;

public interface IView {
    void onStart();
    void onUpdate() throws ApplicationExitException;
    void onFinish();
}
