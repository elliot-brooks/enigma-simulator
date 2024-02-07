package com.enigma_machine.parsers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.enigma_machine.enigma.Rotor;
import com.enigma_machine.enigma.RotorFactory;
import com.enigma_machine.enigma.exceptions.InvalidRotorEncodingException;
import com.enigma_machine.tools.Tools;

public class RotorBankParser {
    private static final String ROTOR_BANK_PATH_REL = "/rotor_bank.xml";
    private static final String NAME_TAG = "name";
    private static final String ENCODING_TAG = "encoding";
    private static final String TURNOVER_TAG = "turnover_position";

    public static HashMap<String, Rotor> parse(String configPath) throws SAXException, IOException, ParserConfigurationException {
        HashMap<String, Rotor> reflectorMap = new HashMap<>();
        File rotorBankFile = new File(configPath + ROTOR_BANK_PATH_REL);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(rotorBankFile);
        Element root = document.getDocumentElement();

        // Handle Rotors
        NodeList rotors = root.getChildNodes();
        for (int i = 0; i < rotors.getLength(); i++) {
            Node currentNode = rotors.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rotorElement = (Element) currentNode;
                String rotorName = rotorElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
                String rotorEncoding = rotorElement.getElementsByTagName(ENCODING_TAG).item(0).getTextContent();
                char rotorTurnover = rotorElement.getElementsByTagName(TURNOVER_TAG).item(0).getTextContent().charAt(0);
                Rotor rotor;
                try {
                    rotor = RotorFactory.buildCustomRotor(rotorName, rotorEncoding, 0, 0,
                            Tools.convertCharToIndex(rotorTurnover));
                } catch (InvalidRotorEncodingException e) {
                    continue;
                }
                reflectorMap.put(rotorName, rotor);
            }
        }
        return reflectorMap;

    }
}
