package com.enigma_machine.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EnigmaVisualiser {

    private GraphicsContext gc;
    static final int Y_OFFSET = 8;  
    static final int DOT_SIZE = 5;
    static final int BOX_WIDTH = 50;
    static final int BOX_HEIGHT = 182;
    static final int PLUGBOARD_BOX_X = 700;
    static final int RIGHT_ROTOR_BOX_X = 500;
    static final int MIDDLE_ROTOR_BOX_X = 375;
    static final int LEFT_ROTOR_BOX_X = 250;
    static final int REFLECTOR_BOX_X = 50;
    static double CANVAS_WIDTH;
    static double CANVAS_HEIGHT;

    public EnigmaVisualiser(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        CANVAS_HEIGHT = canvas.getHeight();
        CANVAS_WIDTH = canvas.getWidth();
    }

    private void drawRectangles() {
        gc.setFill(Color.rgb(204, 204, 255));
        gc.fillRect(PLUGBOARD_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(RIGHT_ROTOR_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(MIDDLE_ROTOR_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(LEFT_ROTOR_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(REFLECTOR_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
    }

    private void drawDots() {
        gc.setFill(Color.BLACK);
        // Generate Rectangles
        for (int i = 0; i < 26; i++) {
            int DOT_OFFSET_Y = Y_OFFSET + (i * 7) + DOT_SIZE/2;
            // PLUGBOARD
            gc.fillOval(PLUGBOARD_BOX_X - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);
            gc.fillOval(PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);
            // ROTORS
            gc.fillOval(RIGHT_ROTOR_BOX_X - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);
            gc.fillOval(RIGHT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);

            gc.fillOval(MIDDLE_ROTOR_BOX_X - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);
            gc.fillOval(MIDDLE_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);

            gc.fillOval(LEFT_ROTOR_BOX_X - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);
            gc.fillOval(LEFT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);
            // REFLECTOR
            gc.fillOval(REFLECTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, DOT_OFFSET_Y, DOT_SIZE, DOT_SIZE);
        }
    }

    // TODO: Take a log string such as "A -> C -> G -> Z ...." and draw the wires
    public void drawWiringDiagram() {
        clearVisualisation();
        
        drawRectangles();
        drawDots();
        
        drawActiveWire();
        drawActiveText();
    }

    private void drawActiveWire() {
        /*
         * TODO : Draw red wire to show passage of current
         */
    }

    private void drawActiveText() {
        /*
         * TODO : Draw text to show what letter goes in and what goes out
         */
    }

    public void clearVisualisation() {
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        return;
    }
}
