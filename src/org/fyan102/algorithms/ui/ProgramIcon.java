package org.fyan102.algorithms.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;

public class ProgramIcon {
    public static Group createIcon(double size) {
        Group icon = new Group();
        
        // Create the main search path (magnifying glass)
        SVGPath searchGlass = new SVGPath();
        searchGlass.setContent("M 15.5 14 h -0.79 l -0.28 -0.27 C 15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3 S 3 5.91 3 9.5 5.91 16 9.5 16 c 1.61 0 3.09 -0.59 4.23 -1.57 l 0.27 0.28 v 0.79 l 5 4.99 L 20.49 19 l -4.99 -5 z m -6 0 C 7.01 14 5 11.99 5 9.5 S 7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14 z");
        searchGlass.setFill(Color.web("#4A90E2"));
        
        // Create the path visualization lines
        SVGPath pathLines = new SVGPath();
        pathLines.setContent("M 2 2 L 8 8 M 8 8 L 14 4 M 14 4 L 20 10");
        pathLines.setStroke(Color.web("#2C3E50"));
        pathLines.setStrokeWidth(2);
        
        // Scale the icon to the desired size
        double scale = size / 24.0; // 24 is the original size of the SVG
        searchGlass.setScaleX(scale);
        searchGlass.setScaleY(scale);
        pathLines.setScaleX(scale);
        pathLines.setScaleY(scale);
        
        icon.getChildren().addAll(searchGlass, pathLines);
        return icon;
    }
} 