package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;
import br.edu.ifce.contacts.views.IContactCreateView;
import br.edu.ifce.contacts.views.listeners.IContactListener;

public class CreateContactView extends BaseConsoleView implements IContactCreateView {
    private IContactListener listener;

    public CreateContactView(ConsoleRenderer renderer) {
        super(renderer);
    }

    @Override
    public void setContactListener(IContactListener listener) {
        this.listener = listener;
    }

    @Override
    public void onUpdate() throws ApplicationExitException {
        int width = getRenderer().getScreenWidth();
        int height = getRenderer().getScreenHeight();

        getRenderer().clearRect(0, 1, width, height - 2, false);
    }

}
