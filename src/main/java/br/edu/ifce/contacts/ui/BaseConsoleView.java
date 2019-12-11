package br.edu.ifce.contacts.ui;


import br.edu.ifce.contacts.exceptions.ApplicationExitException;

public abstract class BaseConsoleView implements IUI {
    private ConsoleRenderer renderer;

    public BaseConsoleView(ConsoleRenderer renderer) {
        this.renderer = renderer;
    }

    protected ConsoleRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void onStart() {
        //
    }

    @Override
    public void onFinish() {
        //
    }

    @Override
    public void onUpdate() throws ApplicationExitException {
        //
    }
}
