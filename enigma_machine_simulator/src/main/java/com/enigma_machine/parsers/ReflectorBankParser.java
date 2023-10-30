package com.enigma_machine.parsers;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.enigma_machine.enigma.Reflector;
import com.enigma_machine.enigma.ReflectorFactory;
import com.enigma_machine.enigma.exceptions.InvalidReflectorEncodingException;

import java.io.*;
import java.util.HashMap;

public class ReflectorBankParser {
    private static final String REFLECTOR_BANK_PATH_REL = "../config/reflector_bank.xml";
    private static final String NAME_TAG = "name";
    private static final String ENCODING_TAG = "encoding";

    public static HashMap<String, Reflector> parse() throws SAXException, IOException, ParserConfigurationException {
        HashMap<String, Reflector> reflectorMap = new HashMap<>();
        File reflectorBankFile = new File(REFLECTOR_BANK_PATH_REL);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(reflectorBankFile);
        Element root = document.getDocumentElement();

        // Handle Reflectors
        NodeList reflectors = root.getChildNodes();
        for (int i = 0; i < reflectors.getLength(); i++) {
            Node currentNode = reflectors.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element reflectorElement = (Element) currentNode;
                String reflectorName = reflectorElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
                String reflectorEncoding = reflectorElement.getElementsByTagName(ENCODING_TAG).item(0).getTextContent();
                Reflector reflector;
                try {
                    reflector = ReflectorFactory.buildCustomReflector(reflectorName, reflectorEncoding);
                } catch (InvalidReflectorEncodingException e) {
                    continue;
                }
                reflectorMap.put(reflectorName, reflector);
            }
        }
        return reflectorMap;

    }
}