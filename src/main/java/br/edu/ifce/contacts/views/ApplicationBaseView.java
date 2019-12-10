package br.edu.ifce.contacts.views;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class ApplicationBaseView {
    private Terminal terminal;
    private Screen screen;
    private WindowBasedTextGUI gui;

    public ApplicationBaseView() throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(this.terminal);
        this.gui = new MultiWindowTextGUI(screen);
    }

    public void startScreen() throws IOException {
        this.screen.startScreen();
    }

    public WindowBasedTextGUI getGui() {
        return gui;
    }

    public void stopScreen() throws IOException {
        this.screen.stopScreen();
    }
}
