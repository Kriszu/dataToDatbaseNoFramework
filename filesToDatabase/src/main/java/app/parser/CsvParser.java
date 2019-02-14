package app.parser;

import app.model.Contact;
import app.model.Customer;

import java.lang.reflect.Array;
import java.util.*;

public class CsvParser implements Parser {

    private Map<Integer, ArrayList<String>> attributesMap;

    public CsvParser(String content) {
        this.attributesMap = setAttributesMap(content);

    }

    @Override
    public Map<Integer, ArrayList<String>> setAttributesMap(String content) {

        Map<Integer, ArrayList<String>> tempAttributesMap = new HashMap<>();

        StringTokenizer stringTokenizer1 = new StringTokenizer(content, "\r\n");
        int id = 0;

        int x = stringTokenizer1.countTokens();
        for(int i = 0; i<x; ++i){
            tempAttributesMap.put(id,new ArrayList<>());
            StringTokenizer stringTokenizer = new StringTokenizer(stringTokenizer1.nextToken(), ",");

            while (stringTokenizer.hasMoreElements()){
                tempAttributesMap.get(id).add(stringTokenizer.nextToken().trim());
            }
           id++;

        }

        return tempAttributesMap;
    }

}
