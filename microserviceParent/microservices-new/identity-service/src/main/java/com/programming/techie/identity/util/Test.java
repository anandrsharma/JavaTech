package com.programming.techie.identity.util;

import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Test {

	public static void main(String[] args) throws Exception {
		//String jsonString = "{\"name\":\"John\", \"age\":20, \"address\":{\"street\":\"Wall Street\", \"city\":\"New York\"}}";
		String jsonString = "{\"name\":\"John\", \"age\":20}";
		JSONObject jsonObject = new JSONObject(jsonString);
	    String xmlString = XML.toString(jsonObject);
	    System.out.println("Xml :"+xmlString);
	    
	    XmlMapper xmlMapper = new XmlMapper();
	    Emp emp = xmlMapper.readValue(xmlString, Emp.class);
	    System.out.println("Emp :"+emp);
	    
	    JsonNode node = xmlMapper.readTree(xmlString.getBytes());
	    ObjectMapper jsonMapper = new ObjectMapper();
	    String json = jsonMapper.writeValueAsString(node);
	    System.out.println("Json :"+json);
	}

}
