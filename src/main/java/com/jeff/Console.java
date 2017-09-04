package com.jeff;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Collections;

public class Console {
    public Console() throws IOException {
        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        _screen = new TerminalScreen(terminal);
        _screen.startScreen();

        _gui = new MultiWindowTextGUI(_screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        _window = new BasicWindow();
        _mainPanel = new Panel(new BorderLayout());
        AddStatusPanel();
        _window.setComponent(_mainPanel);
        _window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        _gui.addWindow(_window);
        _gui.setActiveWindow(_window);
    }

    public void Wait() {
        System.out.println("about to wait");

        KeyStroke key;
        try {
            WriteLine("\r\nPRESS ENTER TO EXIT");
            while ((key = _screen.readInput()).getKeyType() != KeyType.Enter) {
                _statusBox.handleKeyStroke(key);
                _gui.updateScreen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("done waiting");
    }

    public void Refresh() throws IOException {
        Refresh(0);
    }

    public void Refresh(int waitMs) throws IOException {
        _gui.updateScreen();
        try {
            if (waitMs > 0) Thread.sleep(waitMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Reset() {
        _statusBox.setText("");
    }

    public void WriteLine(String output) {
        _statusBox.addLine(output);
        _statusBox.setCaretPosition(_statusBox.getLineCount() - 1, 1);
        try {
            _gui.updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(output);
    }

    private void AddStatusPanel() {
        Panel panel = new Panel();
        _mainPanel.addComponent(panel);
        _statusBox = new TextBox(new TerminalSize(100, 100), TextBox.Style.MULTI_LINE);
        panel.addComponent(_statusBox);
        panel.setLayoutData(BorderLayout.Location.BOTTOM);
    }

    private MultiWindowTextGUI _gui;
    private BasicWindow  _window;
    private Screen _screen;
    private Panel _mainPanel;
    private TextBox _statusBox;
}

