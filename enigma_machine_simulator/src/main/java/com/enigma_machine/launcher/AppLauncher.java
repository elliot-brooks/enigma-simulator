package com.enigma_machine.launcher;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.enigma_machine.app.EnigmaMachineSimulatorApp;
import com.enigma_machine.cli.EnigmaSimulator;
import com.enigma_machine.parsers.exceptions.MissingReflectorException;
import com.enigma_machine.parsers.exceptions.MissingRotorException;

public class AppLauncher {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
            MissingRotorException, MissingReflectorException {
        if (args.length != 1) {
            System.out.println("Please specify the application to run : <cli, gui>");
            return;
        }
        if (args[0].equals("cli")) {
            EnigmaSimulator.main(args);
        }
        if (args[0].equals("gui")) {
            EnigmaMachineSimulatorApp.main(args);
        }
    }
}
