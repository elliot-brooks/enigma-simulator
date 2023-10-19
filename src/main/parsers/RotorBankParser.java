package main.parsers;

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

import main.enigma.Rotor;
import main.enigma.RotorFactory;
import main.enigma.exceptions.InvalidRotorEncodingException;
import main.tools.Tools;

public class RotorBankParser {
    private static final String ROTOR_BANK_PATH_REL = "src/main/resources/rotor_bank.xml";
    private static final String NAME_TAG = "name";
    private static final String ENCODING_TAG = "encoding";
    private static final String TURNOVER_TAG = "turnover_position";

    public static HashMap<String, Rotor> parse() throws SAXException, IOException, ParserConfigurationException {
        HashMap<String, Rotor> reflectorMap = new HashMap<>();
        File rotorBankFile = new File(ROTOR_BANK_PATH_REL);
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
                    rotor = RotorFactory.buildCustomRotor(rotorName, rotorEncoding, 0, 0, Tools.convertCharToIndex(rotorTurnover));
                } catch (InvalidRotorEncodingException e) {
                    continue;
                }
                reflectorMap.put(rotorName, rotor);
            }
        }
        return reflectorMap;

    }
}
