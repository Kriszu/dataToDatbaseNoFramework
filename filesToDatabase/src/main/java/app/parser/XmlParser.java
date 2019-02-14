package app.parser;

import app.model.Contact;
import app.model.Customer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class XmlParser implements Parser {

    private Map<Integer, ArrayList<String>> attributesMap;

    public Map<Integer, ArrayList<String>> getAttributesMap() {
        return attributesMap;
    }

    public Map<Integer, ArrayList<String>> setAttributesMap(String content) throws ParserConfigurationException, IOException, SAXException {

        Map<Integer, ArrayList<String>> tempAttributesMap = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(content));
        Document doc = builder.parse(is);
        Element root = doc.getDocumentElement();

        for(int i = 0; i<root.getElementsByTagName("person").getLength(); ++i){
            tempAttributesMap.put(i,new ArrayList<>());
            ArrayList<String> arrayList = new ArrayList<>();
            NodeList nodeList = doc.getElementsByTagName("person").item(0).getChildNodes();
            tempAttributesMap.get(i).add(doc.getElementsByTagName("person").item(i).getTextContent());


        }
        for (Map.Entry entry: tempAttributesMap.entrySet()
             ) {
             String s = tempAttributesMap.get(entry.getKey()).get(0);
            StringTokenizer stringTokenizer = new StringTokenizer(s, "\r\n");
            ArrayList<String> temp = new ArrayList<>();
            while(stringTokenizer.hasMoreElements()){
                temp.add(stringTokenizer.nextToken().trim());
            }
            entry.setValue(temp);
        }


        return tempAttributesMap;

    }

    public XmlParser(String content) {
        try {
            this.attributesMap = setAttributesMap(content);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
