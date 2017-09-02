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

    public void ShowMarker(Point[] points, String[] markers) {
        for (int i = 0; i < points.length; i++) {
            StringBuilder marker = new StringBuilder(markers[i]);
            boolean collision = false;
            for (int j = 0; j < points.length; j++ ) {
                if (j == i) continue;
                if (points[i].equals(points[j])) {
                    collision = true;
                    marker.append(markers[j]);
                }
            }
            ShowMarker(points[i],collision, marker.toString());
        }
    }

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

    public Point MoveOne() {

        int newRow=0;
        int newCol=0;
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
        return new Point(newRow, newCol);
    }

    private Label GetLabelInPlane(int row, int col) {
        return _labels[row + 1][col + 1];
    }

    public void ShowMarker (Point point, boolean isCollision, String marker ) {
        ShowMarker(point.x, point.y, isCollision, marker);
    }

    public void ShowMarker(int row, int col, boolean isCollision) {
        ShowMarker(row, col, isCollision, "");
    }

    public void InitAll() {
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                SetEmptyFormat(row, col);
            }
        }
    }

    public void ShowMarker(int row, int col, boolean isCollision, String marker) {
        Label label = GetLabelInPlane(row, col);
        label.setText(String.format(labelFormat, marker.equals("") ? _marker : marker));
        SetOccupiedFormat(label, isCollision);
        if (_movement != Movement.Set && _movement != Movement.Random) {
            SetEmptyFormat(_atRow, _atCol);
            _atRow = row;
            _atCol = col;
        }
    }
}
