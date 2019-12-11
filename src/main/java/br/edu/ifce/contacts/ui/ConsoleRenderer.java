package br.edu.ifce.contacts.ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class ConsoleRenderer {
    private final Screen screen;
    private TextColor foreColor, backColor;
    private TextCharacter emptyCharacter;
    private KeyStroke lastKey;

    public ConsoleRenderer(Screen screen) {
        this.screen = screen;
        this.setTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE);
    }

    public void setTheme(TextColor foreColor, TextColor backColor) {
        this.foreColor = foreColor;
        this.backColor = backColor;
        this.emptyCharacter = new TextCharacter(' ').withBackgroundColor(backColor).withForegroundColor(foreColor);
    }

    public int getScreenWidth() {
        return screen.getTerminalSize().getColumns() - 1;
    }

    public int getScreenHeight() {
        return screen.getTerminalSize().getRows() - 1;
    }

    public ConsoleRenderer resetCursor() {
        screen.setCursorPosition(null);
        return this;
    }

    public ConsoleRenderer cursor(int x, int y) {
        screen.setCursorPosition(new TerminalPosition(x, y));
        return this;
    }

    public ConsoleRenderer wait(int millis) {
        try {
            screen.refresh();
            Thread.sleep(millis);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public ConsoleRenderer line(int fromX, int fromY, int toX, int toY) {
        screen.newTextGraphics().drawLine(fromX, fromY, toX, toY, emptyCharacter);

        return this;
    }

    public ConsoleRenderer text(String text, int x, int y, boolean invertedTheme) {
        screen.newTextGraphics()
                .setBackgroundColor(invertedTheme ? backColor : foreColor)
                .setForegroundColor(invertedTheme ? foreColor : backColor)
                .putCSIStyledString(x, y, text);

        return this;
    }

    public ConsoleRenderer text(String text, int x, int y) {
        return text(text, x, y, false);
    }

    public ConsoleRenderer animatedText(String text, int x, int y, int delay) {
        for (int i = 0; i < text.length(); i++) {
            cursor(x + i, y);
            screen.setCharacter(x + i, y, new TextCharacter(text.charAt(i), foreColor, backColor));

            wait(ThreadLocalRandom.current().nextInt(delay));
        }

        return this;
    }

    public ConsoleRenderer animatedText(String text, int x, int y) {
        return animatedText(text, x, y, 30);
    }

    public ConsoleRenderer clear() {
        screen.clear();

        return this;
    }

    public void refreshScreen() {
        try {
            screen.refresh();
            lastKey = screen.pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KeyStroke pollInput() {
        return lastKey;
    }

    public ConsoleRenderer clearRect(int x, int y, int width, int height, boolean filled) {
        for (int i = y; i <= y + height; i++) {
            text(" ".repeat(width + 1), x, i, filled);
        }

        return this;
    }

    public ConsoleRenderer rectangle(int x, int y, int width, int height, boolean filled) {
        return
                clearRect(x, y, width, height, filled)
                .line(x, y, x + width, y)
                .line(x + width, y, x + width, y + height)
                .line(x, y + height, x + width, y + height)
                .line(x, y, x, y + height);

    }
}
