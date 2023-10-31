package com.enigma_machine.launcher;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.enigma_machine.app.EnigmaSimulatorApp;
import com.enigma_machine.cli.EnigmaSimulatorCLI;
import com.enigma_machine.parsers.exceptions.MissingReflectorException;
import com.enigma_machine.parsers.exceptions.MissingRotorException;

public class Launcher {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
            MissingRotorException, MissingReflectorException {
        if (args.length != 1) {
            System.out.println("Please specify the application to run : <cli, gui>");
            return;
        }
        if (args[0].equals("cli")) {
            EnigmaSimulatorCLI.launchCLI();
        }
        if (args[0].equals("gui")) {
            EnigmaSimulatorApp.launchApp();
        }
    }
}
