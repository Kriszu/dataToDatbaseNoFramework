package app.parser;

import app.model.Contact;
import app.model.Customer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Parser {

     Map<Integer, ArrayList<String>> setAttributesMap(String content) throws ParserConfigurationException, IOException, SAXException;
}
