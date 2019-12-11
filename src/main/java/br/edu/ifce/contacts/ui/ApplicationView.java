package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class ApplicationView extends BaseConsoleView {

    private IView currentView;

    public ApplicationView(ConsoleRenderer renderer) {
        super(renderer);
    }

    public void setCurrentView(IView currentView) {
        this.currentView = currentView;
    }

    @Override
    public void onStart() {
        setup();
        this.currentView.onStart();
    }

    @Override
    public void onUpdate() throws ApplicationExitException {
        poolInput();

        getRenderer().resetCursor();

        currentView.onUpdate();

        getRenderer().refreshScreen();
    }

    @Override
    public void onFinish() {
        this.currentView.onFinish();

        getRenderer()
                .clear()
                .wait(600)
                .animatedText(">_ SYSTEM EXIT HOTKEY ON", 0, 0)
                .wait(1000)
                .animatedText(">_ SESSION TERMINATED", 0, 1)
                .wait(1000);
    }

    private void setup() {
        int width = getRenderer().getScreenWidth();
        int height = getRenderer().getScreenHeight();

        getRenderer()
                .bar(0, 0, width, 0)
                .bar(0, height, width, height)
                .text("GERENCIADOR DE CONTATOS", 0, 0)
                .text("F10: SAIR", 0, height);
    }

    private void poolInput() throws ApplicationExitException {
        KeyStroke keyStroke = getRenderer().pollInput();

        if (keyStroke != null && keyStroke.getKeyType() == KeyType.F10) {
            throw new ApplicationExitException();
        }
    }

}
