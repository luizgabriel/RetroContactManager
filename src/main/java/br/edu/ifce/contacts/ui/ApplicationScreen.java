package br.edu.ifce.contacts.ui;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class ApplicationScreen {

    private Screen screen;
    private ConsoleRenderer renderer;

    public ApplicationScreen() throws IOException {
        this.screen = new DefaultTerminalFactory().createScreen();
        this.renderer = new ConsoleRenderer(this.screen);
    }

    public Screen getScreen() {
        return screen;
    }

    public ConsoleRenderer getRenderer() {
        return renderer;
    }

    public void start() throws IOException {
        screen.startScreen();
    }

    public void stop() throws IOException {
        screen.stopScreen();
    }

}
