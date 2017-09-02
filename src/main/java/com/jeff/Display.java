package com.jeff;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Display {
    public Display(int cols) throws IOException {
        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        _screen = new TerminalScreen(terminal);
        _screen.startScreen();

        _gui = new MultiWindowTextGUI(_screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        _window = new BasicWindow();
        _mainPanel = new Panel(new BorderLayout());
        _planePanel = new Panel(new GridLayout(cols));
        _mainPanel.addComponent(_planePanel, BorderLayout.Location.CENTER);
        AddStatusPanel();
        _window.setComponent(_mainPanel);
        _window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        _gui.addWindow(_window);
    }

    public void Refresh() throws IOException {
        _gui.updateScreen();
    }

    public void UpdateStatus(String text) {
        if (text.length() > 99) text = text.substring(0, 99);
        _statusBox.setText(" " + text);
    }

    private void AddStatusPanel() {
        Panel panel = new Panel();
        _mainPanel.addComponent(panel);
        _statusBox = new Label("");
        _statusBox.setSize(new TerminalSize(100, 5));
        panel.addComponent(_statusBox);
        panel.setLayoutData(BorderLayout.Location.BOTTOM);
    }

    private MultiWindowTextGUI _gui;
    private BasicWindow  _window;
    private Screen _screen;
    private Panel _mainPanel;
    private Panel _planePanel;
    private Label _statusBox;
    private boolean _isStarted;


    public Plane AddPlane(String title, Plane.Movement movement, String marker) {
        // Create panel to hold components
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(8));

        Label[][] labels = new Label[Plane.rows + 1][Plane.cols + 1];
        for (int row = 0; row <= Plane.rows; row++){
            for (int col = 0; col <= Plane.cols; col++) {
                Label newLabel;
                if (row == 0 && col == 0) {
                    newLabel = new Label(Plane.emptyFormat);
                } else if (row == 0) {
                    newLabel = new Label(String.format(Plane.labelFormat, col - 1));
                } else if (col == 0) {
                    newLabel = new Label(String.format(Plane.labelFormat, row - 1));
                } else {
                    newLabel = new Label("");
                    Plane.SetEmptyFormat(newLabel);
                }
                newLabel.setLabelWidth(Plane.emptyFormat.length());
                panel.addComponent(newLabel);
                labels[row][col] = newLabel;
            }
        }
        panel.setSize(new TerminalSize(8,9));
        _planePanel.addComponent(panel.withBorder(Borders.singleLine(title)));
        return new Plane(marker, movement, labels);
    }
}

