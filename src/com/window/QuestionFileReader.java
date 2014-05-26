package com.window;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QuestionFileReader {
	private StringBuilder textFileContent = new StringBuilder(); 
	private String fileName;
	
	public QuestionFileReader(String fileName) {
		this.fileName = fileName;
	}

	public void readNext(Task question) {
		try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
			String line = "";
			while (line != null) {
				line = br.readLine();
				textFileContent.append(line + " newline");				
			}
		}
		catch (IOException e) {
			System.err.println(e);
		}
		this.parseFileContent(question);
	}
	
	private void parseFileContent(Task question) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse( new FileInputStream(this.fileName) );
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
//				if (node instanceof Element) {
				if (node.getNodeName().toString().equals(new String("task"))) {
					NodeList childNodes = node.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node cNode = childNodes.item(j);
//						if (cNode instanceof Element) {
						if (cNode.getNodeName().toString().equals(new String("question"))) {
							question.setQuestion(cNode.getLastChild().getTextContent());
						}
						if (cNode.getNodeName().toString().equals(new String("answer"))) {
							String correct = cNode.getAttributes().getNamedItem("correct").getNodeValue();
							Answer answer = new Answer(cNode.getLastChild().getTextContent(), correct);
							question.addAnswer(answer);
						}
					}
				}
			}
		
		}
		catch (IOException|ParserConfigurationException|SAXException e) {
			System.out.println("exception : " + e);
		}
		catch (Error e) {
			System.out.println("error" + e);
		}
	}

	public void readAll(List<Task> questions) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse( new FileInputStream(this.fileName) );
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			System.out.println(nodeList.getLength());
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeName().toString().equals(new String("task"))) {
					String radio = node.getAttributes().getNamedItem("radio").getNodeValue();
					Task question = new Task(radio);
					NodeList childNodes = node.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node cNode = childNodes.item(j);
						if (cNode.getNodeName().toString().equals(new String("question"))) {
							question.setQuestion(cNode.getLastChild().getTextContent());
						}
						if (cNode.getNodeName().toString().equals(new String("answer"))) {
							String correct = cNode.getAttributes().getNamedItem("correct").getNodeValue();
							Answer answer = new Answer(cNode.getLastChild().getTextContent(), correct);
							question.addAnswer(answer);
						}
					}
					questions.add(question);
				}
			}
		
		}
		catch (IOException|ParserConfigurationException|SAXException e) {
			System.out.println("exception : " + e);
		}
		catch (Error e) {
			System.out.println("error" + e);
		}	
	}
}
