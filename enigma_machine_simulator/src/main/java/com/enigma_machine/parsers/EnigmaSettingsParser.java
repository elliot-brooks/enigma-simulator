package com.enigma_machine.parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.enigma_machine.enigma.ComponentCache;
import com.enigma_machine.enigma.Enigma;
import com.enigma_machine.enigma.Plugboard;
import com.enigma_machine.enigma.Reflector;
import com.enigma_machine.enigma.Rotor;
import com.enigma_machine.parsers.exceptions.MissingReflectorException;
import com.enigma_machine.parsers.exceptions.MissingRotorException;
import com.enigma_machine.tools.Tools;

public class EnigmaSettingsParser {
    private static final String ROTOR_TAG = "rotor";
    private static final String REFLECTOR_TAG = "reflector";
    private static final String PLUGBOARD_TAG = "plugboard";
    private static final String NAME_TAG = "name";
    private static final String ENCODING_TAG = "encoding";
    private static final String RING_SETTING_TAG = "ring_setting";
    private static final String START_POSITION_TAG = "start_position";
    private static final String ENIGMA_SETTINGS_PATH_REL = "../config/enigma_settings.xml";

    private Plugboard plugboard = new Plugboard();
    private List<Rotor> rotors = new ArrayList<>();
    private Reflector reflector;
    private Enigma enigmaMachine;
    ComponentCache cache;

    public void parse(ComponentCache cache) throws ParserConfigurationException, SAXException, IOException,
            MissingRotorException, MissingReflectorException {
        this.cache = cache;
        enigmaMachine = Enigma.createCustomEnigma(rotors, plugboard, reflector);
        File settingsFile = new File(ENIGMA_SETTINGS_PATH_REL);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(settingsFile);
        Element root = document.getDocumentElement();

        handlePlugboard(root);
        handleRotors(root);
        handleReflector(root);

        return;
    }

    private void handleRotors(Element root) throws MissingRotorException {
        NodeList rotors = root.getElementsByTagName(ROTOR_TAG);
        for (int i = 0; i < rotors.getLength(); i++) {
            Node rotorNode = rotors.item(i);
            if (rotorNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rotorElement = (Element) rotorNode;
                String rotorName = rotorElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
                int rotorRingSetting = Integer
                        .parseInt(rotorElement.getElementsByTagName(RING_SETTING_TAG).item(0).getTextContent());
                char rotorStartPosition = rotorElement.getElementsByTagName(START_POSITION_TAG).item(0).getTextContent()
                        .charAt(0);
                Rotor rotor;
                rotor = cache.getRotor(rotorName);
                if (rotor == null) {
                    throw new MissingRotorException(rotorName);
                }
                rotor.setRingSetting(Tools.minusOneInteger(rotorRingSetting));
                rotor.setRotationPosition(Tools.convertCharToIndex(rotorStartPosition));
                this.rotors.add(rotor);
            }
        }
        enigmaMachine.setRotors(this.rotors);
    }

    private void handleReflector(Element root) throws MissingReflectorException {
        Node reflectorNode = root.getElementsByTagName(REFLECTOR_TAG).item(0);
        if (reflectorNode.getNodeType() == Node.ELEMENT_NODE) {
            Element reflectorElement = (Element) reflectorNode;
            String reflectorName = reflectorElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
            Reflector reflector = cache.getReflector(reflectorName);
            if (reflector == null) {
                throw new MissingReflectorException(reflectorName);
            }
            enigmaMachine.setReflector(reflector);
        }
    }

    private void handlePlugboard(Element root) {
        Node plugboardNode = root.getElementsByTagName(PLUGBOARD_TAG).item(0);
        if (plugboardNode.getNodeType() == Node.ELEMENT_NODE) {
            Element plugboardElement = (Element) plugboardNode;
            String encoding = plugboardElement.getAttribute(ENCODING_TAG);
            List<String> encodings = new ArrayList<String>();
            for (String string : encoding.split(" ")) {
                encodings.add(string);
            }

            enigmaMachine.addCables(encodings);
        }
    }

    public Enigma getEnigmaMachine() {
        return enigmaMachine;
    }

}
