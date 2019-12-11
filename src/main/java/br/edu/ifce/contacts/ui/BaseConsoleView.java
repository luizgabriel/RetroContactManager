package br.edu.ifce.contacts.ui;


public abstract class BaseConsoleView implements IView {
    private ConsoleRenderer renderer;

    public BaseConsoleView(ConsoleRenderer renderer) {
        this.renderer = renderer;
    }

    protected ConsoleRenderer getRenderer() {
        return renderer;
    }
}
