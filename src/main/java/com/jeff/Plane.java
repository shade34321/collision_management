package com.jeff;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;

import java.awt.*;

public class Plane {

    public static final String emptyFormat = "   ";
    public static final String labelFormat = " %s ";
    public static final char emptyCell = '+';
    public static final int rows = 8;
    public static final int cols = 7;

    public enum Movement {
        X,
        Y,
        Z,
        Set,
        Random
    }

    public Plane(String marker, Movement movement,  Label[][] labels) {
        _movement = movement;
        _marker = marker;
        _labels = labels;
    }

    private final String _marker;
    private final Label[][] _labels;
    private int _atRow = -1;
    private int _atCol = -1;
    private final Movement _movement;

    public void Initialize(int x, int y) {
        ShowMarker(x, y, false);
    }

    public void SetEmptyFormat(int row, int col) {
        if (row == -1 || col == -1) return;
        Label label = GetLabelInPlane(row, col);
        SetEmptyFormat(label);
    }

    public static void SetEmptyFormat(Label label) {
        label.setText(String.format(labelFormat, Plane.emptyCell));
        label.setForegroundColor(TextColor.ANSI.CYAN);
        label.setBackgroundColor(TextColor.ANSI.WHITE);
    }

    private void SetOccupiedFormat(Label label, boolean isCollision) {
        if (isCollision) {
            label.setForegroundColor(TextColor.ANSI.WHITE);
            label.setBackgroundColor(TextColor.ANSI.RED);
        }
        else
        {
            label.setForegroundColor(TextColor.ANSI.BLUE);
            label.setBackgroundColor(TextColor.ANSI.CYAN);
        }
    }

    public String[][] MoveOne() {

        int newRow;
        int newCol;
        switch (_movement) {
            case X:
                newRow = (_atRow + 1) % rows;
                newCol = (_atCol + 1) % cols;
                ShowMarker(newRow, newCol, false);
                break;
            case Y:
                newRow = (_atRow + 1) % rows;
                newCol = 2;
                ShowMarker(newRow, newCol, false);
                break;
            case Z:
                newRow = 3;
                newCol = (_atCol + 1) % cols;
                ShowMarker(newRow, newCol, false);
                break;
            case Set:
                break;
            case Random:
                break;
        }
        return GetState();
    }

    private Label GetLabelInPlane(int row, int col) {
        return _labels[row + 1][col + 1];
    }

    public void ShowMarker(int row, int col, boolean isCollision) {
        ShowMarker(row, col, isCollision, "");
    }

    public void ResetDisplay() {
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                SetEmptyFormat(row, col);
            }
        }
    }

    public String GetMarker(int row, int col) {
        Label label = GetLabelInPlane(row, col);
        String contents = label.getText();

        //if contents holds an empty marker, return nothing
        if (contents.equals(String.format(labelFormat, Plane.emptyCell))) {
            return "";
        } else {
            return contents.trim();
        }
    }

    public void ShowMarker(int row, int col, boolean isCollision, String marker) {
        Label label = GetLabelInPlane(row, col);
        marker = String.format(labelFormat, marker.equals("") ? _marker : marker);
        marker = marker.length() > labelFormat.length() ? marker.trim() : marker;
        label.setText(marker);
        SetOccupiedFormat(label, isCollision);
        if (_movement != Movement.Set && _movement != Movement.Random) {
            SetEmptyFormat(_atRow, _atCol);
            _atRow = row;
            _atCol = col;
        }
    }

    public String[][] GetState() {
        String[][] result = new String[rows][cols];
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                result[row][col] = (col == _atCol && row == _atRow) ? _marker : "";
            }
        }
        return result;
    }
}
