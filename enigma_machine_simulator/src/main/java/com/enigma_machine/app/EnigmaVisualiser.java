package com.enigma_machine.app;

import java.util.HashMap;
import java.util.List;

import com.enigma_machine.enigma.EnigmaLogger;
import com.enigma_machine.tools.Constants;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class EnigmaVisualiser {

    private GraphicsContext gc;

    static final int Y_OFFSET = 8;  
    static final int DOT_SIZE = 4;
    static final int DOT_GAP = 7;
    static final int BOX_WIDTH = 50;
    static final int REFLECTOR_BOX_WIDTH = 83;
    static final int BOX_HEIGHT = 182;
    static final int PLUGBOARD_BOX_X = 695;
    static final int RIGHT_ROTOR_BOX_X = 495;
    static final int MIDDLE_ROTOR_BOX_X = 370;
    static final int LEFT_ROTOR_BOX_X = 245;
    static final int REFLECTOR_BOX_X = 45;
    static final int INPUT_STUB_LENGTH = 30;
    static final int FONT_SIZE = 10;
    static double CANVAS_WIDTH;
    static double CANVAS_HEIGHT;
    static HashMap<Integer,Integer> railHeightMap = new HashMap<>();

    public EnigmaVisualiser(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        CANVAS_HEIGHT = canvas.getHeight();
        CANVAS_WIDTH = canvas.getWidth();
    }

    private void drawRectangles() {
        // Draw background box to act as a border
        final int REFLECTOR_CORRECTION = 33;
        gc.setFill(Color.BLACK);
        final int border_width = 1;
        gc.fillRect(PLUGBOARD_BOX_X-border_width, Y_OFFSET-border_width, BOX_WIDTH+2*border_width, BOX_HEIGHT+2*border_width);
        gc.fillRect(RIGHT_ROTOR_BOX_X-border_width, Y_OFFSET-border_width, BOX_WIDTH+2*border_width, BOX_HEIGHT+2*border_width);
        gc.fillRect(MIDDLE_ROTOR_BOX_X-border_width, Y_OFFSET-border_width, BOX_WIDTH+2*border_width, BOX_HEIGHT+2*border_width);
        gc.fillRect(LEFT_ROTOR_BOX_X-border_width, Y_OFFSET-border_width, BOX_WIDTH+2*border_width, BOX_HEIGHT+2*border_width);
        gc.fillRect(REFLECTOR_BOX_X-border_width-REFLECTOR_CORRECTION, Y_OFFSET-border_width, REFLECTOR_BOX_WIDTH+2*border_width, BOX_HEIGHT+2*border_width);
        // Draw boxes
        gc.setFill(Color.rgb(204, 204, 255));
        gc.fillRect(PLUGBOARD_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(RIGHT_ROTOR_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(MIDDLE_ROTOR_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(LEFT_ROTOR_BOX_X, Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        gc.fillRect(REFLECTOR_BOX_X - REFLECTOR_CORRECTION, Y_OFFSET, REFLECTOR_BOX_WIDTH, BOX_HEIGHT);
    }

    private void drawStubs() {
        gc.setLineWidth(1);;
        gc.setStroke(Color.BLACK);
        for (int i = 0 ; i < Constants.ALPHABET_LENGTH; i++) {
            int LINE_OFFSET_Y = Y_OFFSET + (i * DOT_GAP) + DOT_SIZE;
            // Draw input stubs
            gc.strokeLine(PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2 + INPUT_STUB_LENGTH, LINE_OFFSET_Y, PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2, LINE_OFFSET_Y);
            // Connect plugboard and rotor1
            gc.strokeLine(PLUGBOARD_BOX_X - DOT_SIZE/2, LINE_OFFSET_Y, RIGHT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, LINE_OFFSET_Y);
            // Connect rotor1 to rotor 2
            gc.strokeLine(RIGHT_ROTOR_BOX_X - DOT_SIZE/2, LINE_OFFSET_Y, MIDDLE_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, LINE_OFFSET_Y);
            // Connect rotor2 to rotor3
            gc.strokeLine(MIDDLE_ROTOR_BOX_X - DOT_SIZE/2, LINE_OFFSET_Y, LEFT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, LINE_OFFSET_Y);
            // Connect rotor3 to reflector
            gc.strokeLine(LEFT_ROTOR_BOX_X - DOT_SIZE/2, LINE_OFFSET_Y, REFLECTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, LINE_OFFSET_Y);
        }
    }

    private void drawOtherConnections(List<String> otherConnections) {
        for (String encryptionPath : otherConnections) {
            int[] wiringPath = getWiringPath(encryptionPath);
            gc.setStroke(new Color(0.5, 0.5, 0.5, 0.5));
            gc.setLineWidth(1);

            // Draw forward encryption
            gc.strokeLine(PLUGBOARD_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[1] * DOT_GAP) + DOT_SIZE, PLUGBOARD_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[0] * DOT_GAP) + DOT_SIZE);
            gc.strokeLine(RIGHT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[2] * DOT_GAP) + DOT_SIZE, RIGHT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[1] * DOT_GAP) + DOT_SIZE);
            gc.strokeLine(MIDDLE_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[3] * DOT_GAP) + DOT_SIZE, MIDDLE_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[2] * DOT_GAP) + DOT_SIZE);
            gc.strokeLine(LEFT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[4] * DOT_GAP) + DOT_SIZE, LEFT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[3] * DOT_GAP) + DOT_SIZE);
            
            // Draw backward encryption
            gc.strokeLine(LEFT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[5] * DOT_GAP) + DOT_SIZE, LEFT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[6] * DOT_GAP) + DOT_SIZE);
            gc.strokeLine(MIDDLE_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[6] * DOT_GAP) + DOT_SIZE, MIDDLE_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[7] * DOT_GAP) + DOT_SIZE);
            gc.strokeLine(RIGHT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[7] * DOT_GAP) + DOT_SIZE, RIGHT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[8] * DOT_GAP) + DOT_SIZE);
            gc.strokeLine(PLUGBOARD_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[8] * DOT_GAP) + DOT_SIZE, PLUGBOARD_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[9] * DOT_GAP) + DOT_SIZE);
        }
    }

    private void drawActiveWire(int visualiserIndex) {
        String encryptionPath = EnigmaLogger.getEncryptionStep(visualiserIndex);
        int[] wiringPath = getWiringPath(encryptionPath);
        gc.setLineWidth(2);
        gc.setStroke(Color.RED);
        // Draw forward encryption
        gc.strokeLine(PLUGBOARD_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[1] * DOT_GAP) + DOT_SIZE, PLUGBOARD_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[0] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(RIGHT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[2] * DOT_GAP) + DOT_SIZE, RIGHT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[1] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(MIDDLE_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[3] * DOT_GAP) + DOT_SIZE, MIDDLE_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[2] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(LEFT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[4] * DOT_GAP) + DOT_SIZE, LEFT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[3] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(REFLECTOR_BOX_X + BOX_WIDTH - railHeightMap.get(wiringPath[5]), Y_OFFSET + (wiringPath[5] * DOT_GAP) + DOT_SIZE, REFLECTOR_BOX_X + BOX_WIDTH - railHeightMap.get(wiringPath[4]), Y_OFFSET + (wiringPath[4] * DOT_GAP) + DOT_SIZE);
        // Draw backward encryption
        gc.setStroke(Color.ROYALBLUE);
        gc.strokeLine(LEFT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[5] * DOT_GAP) + DOT_SIZE, LEFT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[6] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(MIDDLE_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[6] * DOT_GAP) + DOT_SIZE, MIDDLE_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[7] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(RIGHT_ROTOR_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[7] * DOT_GAP) + DOT_SIZE, RIGHT_ROTOR_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[8] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(PLUGBOARD_BOX_X + 2*DOT_SIZE/3, Y_OFFSET + (wiringPath[8] * DOT_GAP) + DOT_SIZE, PLUGBOARD_BOX_X + BOX_WIDTH - 1*DOT_SIZE/3, Y_OFFSET + (wiringPath[9] * DOT_GAP) + DOT_SIZE);
        // Re-draw stubs
        gc.setLineWidth(2);
        gc.setStroke(Color.RED);
        gc.strokeLine(PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2 + INPUT_STUB_LENGTH, Y_OFFSET + (wiringPath[0] * DOT_GAP) + DOT_SIZE, PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (wiringPath[0] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(PLUGBOARD_BOX_X - DOT_SIZE/2, Y_OFFSET + (wiringPath[1] * DOT_GAP) + DOT_SIZE, RIGHT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (wiringPath[1] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(RIGHT_ROTOR_BOX_X - DOT_SIZE/2,  Y_OFFSET + (wiringPath[2] * DOT_GAP) + DOT_SIZE, MIDDLE_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2,  Y_OFFSET + (wiringPath[2] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(MIDDLE_ROTOR_BOX_X - DOT_SIZE/2,  Y_OFFSET + (wiringPath[3] * DOT_GAP) + DOT_SIZE, LEFT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2,  Y_OFFSET + (wiringPath[3] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(LEFT_ROTOR_BOX_X - DOT_SIZE/2, Y_OFFSET + (wiringPath[4] * DOT_GAP) + DOT_SIZE, REFLECTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (wiringPath[4] * DOT_GAP) + DOT_SIZE);
        gc.setStroke(Color.ROYALBLUE);
        gc.strokeLine(LEFT_ROTOR_BOX_X - DOT_SIZE/2, Y_OFFSET + (wiringPath[5] * DOT_GAP) + DOT_SIZE, REFLECTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (wiringPath[5] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(MIDDLE_ROTOR_BOX_X - DOT_SIZE/2,  Y_OFFSET + (wiringPath[6] * DOT_GAP) + DOT_SIZE, LEFT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2,  Y_OFFSET + (wiringPath[6] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(RIGHT_ROTOR_BOX_X - DOT_SIZE/2,  Y_OFFSET + (wiringPath[7] * DOT_GAP) + DOT_SIZE, MIDDLE_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2,  Y_OFFSET + (wiringPath[7] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(PLUGBOARD_BOX_X - DOT_SIZE/2, Y_OFFSET + (wiringPath[8] * DOT_GAP) + DOT_SIZE, RIGHT_ROTOR_BOX_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (wiringPath[8] * DOT_GAP) + DOT_SIZE);
        gc.strokeLine(PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2 + INPUT_STUB_LENGTH, Y_OFFSET + (wiringPath[9] * DOT_GAP) + DOT_SIZE, PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (wiringPath[9] * DOT_GAP) + DOT_SIZE);

        gc.setLineWidth(2);

        gc.setStroke(Color.RED);
        gc.strokeLine(REFLECTOR_BOX_X + BOX_WIDTH, Y_OFFSET + (wiringPath[4] * DOT_GAP) + DOT_SIZE, REFLECTOR_BOX_X + BOX_WIDTH - railHeightMap.get(wiringPath[4]), Y_OFFSET + (wiringPath[4] * DOT_GAP) + DOT_SIZE);
        gc.setStroke(Color.ROYALBLUE);
        gc.strokeLine(REFLECTOR_BOX_X + BOX_WIDTH, Y_OFFSET + (wiringPath[5] * DOT_GAP) + DOT_SIZE, REFLECTOR_BOX_X + BOX_WIDTH - railHeightMap.get(wiringPath[5]), Y_OFFSET + (wiringPath[5] * DOT_GAP) + DOT_SIZE);
        // Draw Texts
        gc.setFont(new Font(null, FONT_SIZE));
        gc.setFill(Color.RED);
        gc.fillText(EnigmaLogger.getPlaintext().substring(visualiserIndex, visualiserIndex + 1), PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2 + INPUT_STUB_LENGTH + 5, Y_OFFSET + (wiringPath[0] * DOT_GAP) + 2*DOT_SIZE);
        gc.setFill(Color.ROYALBLUE);
        gc.fillText(EnigmaLogger.getCyphertext().substring(visualiserIndex, visualiserIndex + 1), PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2 + INPUT_STUB_LENGTH + 5, Y_OFFSET + (wiringPath[9] * DOT_GAP) + 2*DOT_SIZE);


    }

    private void initRailHeightMap(int[] wiring) {
        railHeightMap = new HashMap<>();
        for (int character = 0; character < wiring.length; character++) {
            int height = calculateRailHeight(character);
            railHeightMap.putIfAbsent(character, height);
            railHeightMap.putIfAbsent(wiring[character], height);
        }
    }

    private int calculateRailHeight(int character) {
        final int INITIAL_SPACING = 2;
        final int RAIL_SPACING = 5;
        return ((character % 14) + INITIAL_SPACING) * RAIL_SPACING;
    }

    private void drawReflectorWires(int[] wiring) {
        gc.setLineWidth(1);

        gc.setStroke(new Color(0.5, 0.5, 0.5, 0.5));
        for (int i = 0; i < wiring.length; i++) {
            gc.strokeLine(REFLECTOR_BOX_X + BOX_WIDTH, Y_OFFSET + (i * DOT_GAP) + DOT_SIZE, REFLECTOR_BOX_X + BOX_WIDTH - railHeightMap.get(wiring[i]), Y_OFFSET + (i * DOT_GAP) + DOT_SIZE);
            gc.strokeLine(REFLECTOR_BOX_X + BOX_WIDTH - railHeightMap.get(wiring[i]), Y_OFFSET + (i * DOT_GAP) + DOT_SIZE, REFLECTOR_BOX_X + BOX_WIDTH - railHeightMap.get(wiring[i]), Y_OFFSET + (wiring[i] * DOT_GAP) + DOT_SIZE);
        }
    }

    private void drawDots() {
        gc.setFill(Color.BLACK);
        // Generate Rectangles
        for (int i = 0; i < Constants.ALPHABET_LENGTH; i++) {
            int DOT_OFFSET_Y = Y_OFFSET + (i * DOT_GAP) + DOT_SIZE/2;
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

    public void drawWiringDiagram(int visualiserIndex, List<String> otherConnections, int[] reflectorWiring) {
        clearVisualisation();
        drawRectangles();
        drawStubs();
        initRailHeightMap(reflectorWiring);
        if (otherConnections != null) {
            drawOtherConnections(otherConnections);
            drawReflectorWires(reflectorWiring);
        }
        drawActiveWire(visualiserIndex);
        drawText();
        drawDots();
    }

    private void drawText() {
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        final int LABEL_SIZE = 11;
        final double LABEL_HEIGHT = CANVAS_HEIGHT - 5;
        gc.setFont(new Font(null, LABEL_SIZE));
        gc.fillText("Plugboard", PLUGBOARD_BOX_X + BOX_WIDTH / 2, LABEL_HEIGHT);
        gc.fillText(EnigmaLogger.getRotorName(0), RIGHT_ROTOR_BOX_X + BOX_WIDTH / 2, LABEL_HEIGHT);
        gc.fillText(EnigmaLogger.getRotorName(1), MIDDLE_ROTOR_BOX_X + BOX_WIDTH / 2, LABEL_HEIGHT);
        gc.fillText(EnigmaLogger.getRotorName(2), LEFT_ROTOR_BOX_X + BOX_WIDTH / 2, LABEL_HEIGHT);
        gc.fillText(EnigmaLogger.getReflectorName(), REFLECTOR_BOX_X + 12, LABEL_HEIGHT);
    }

    private int[] getWiringPath(String encryptionPath) {
        String[] splitPath = encryptionPath.split(" -> ");
        int[] wiringPath = new int[splitPath.length];

        for (int i = 0; i < splitPath.length; i++) {
            char character = splitPath[i].charAt(0);
            wiringPath[i] = character - Constants.JAVA_A_VALUE;
        }
        return wiringPath;
    }

    public void clearVisualisation() {
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        return;
    }
}
