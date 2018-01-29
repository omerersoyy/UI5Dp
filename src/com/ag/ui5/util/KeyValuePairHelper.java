package com.ag.ui5.util;

import kotlin.Pair;

import java.io.*;
import java.util.stream.Collectors;
public class KeyValuePairHelper {
    public static Pair getNamespace(String key) throws IOException{
        String filePath = "/Templates/Namespaces";
        InputStreamReader inputStreamReader = new InputStreamReader(CreateFileContents.class.getResourceAsStream(filePath));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Pair keyValuePair = new Pair("", "");
        String line;
        String[] pair;
        while((line = bufferedReader.readLine()) != null){
            pair = line.split("-");
            if(pair[0].equalsIgnoreCase(key)){
                keyValuePair = new Pair(pair[0], pair[1]);
                break;
            }
        }
        return keyValuePair;
    }
}
