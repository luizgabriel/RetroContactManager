package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class ApplicationUI extends BaseConsoleView {

    private IUI currentView;

    public ApplicationUI(ConsoleRenderer renderer) {
        super(renderer);
    }

    public void setCurrentView(IUI currentView) {
        if (this.currentView != null)
            this.currentView.onFinish();

        this.currentView = currentView;
        this.currentView.onStart();
        setup();
    }

    @Override
    public void onStart() {
        setup();
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
        getRenderer()
                .clear()
                .animatedText(">_ SESSION TERMINATED", 0, 1)
                .wait(1000);
    }

    private void setup() {
        int width = getRenderer().getScreenWidth();
        int height = getRenderer().getScreenHeight();

        getRenderer()
                .line(0, 0, width, 0)
                .line(0, height, width, height)
                .text(" IFCE - CONTACT MANAGER", 0, 0, true)
                .text(" [A] ADD CONTACT   [G] ADD GROUP    [X] REMOVE   [F10]: EXIT", 0, height, true);
    }

    private void poolInput() throws ApplicationExitException {
        KeyStroke keyStroke = getRenderer().pollInput();

        if (keyStroke != null) {
            if (keyStroke.getKeyType() == KeyType.F10) {
                throw new ApplicationExitException();
            }
        }
    }

}
