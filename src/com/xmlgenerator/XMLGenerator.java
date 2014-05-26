package com.xmlgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.window.Answer;
import com.window.Task;

public class XMLGenerator {
	private String fileName;
	public XMLGenerator() {
		this.fileName = "questions/generated.xml";
		// TODO Auto-generated constructor stub
	}
	
	public void save(Task task) {
		System.out.println(task.getQuestion());
		for (Answer answer : task.getAnswers()) {
			System.out.println(answer.getContent() + " " + answer.isCorrect());
		}
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.parse( new FileInputStream(this.fileName) );
			//NodeList nodeList = doc.getDocumentElement().getChildNodes();
			
			NodeList rootList = doc.getElementsByTagName("tasks");
			Element rootElement = (Element) rootList.item(0);
			// root elements
			//Document doc = docBuilder.newDocument();
			//Element rootElement = doc.createElement("tasks");
			//doc.appendChild(rootElement);
			
			// task elements
			Element taskElement = doc.createElement("task");
			taskElement.setAttribute("radio", (task.isRadio() ? "true" : "false"));
			rootElement.appendChild(taskElement);
			
			// question element
			Element question = doc.createElement("question");
			question.appendChild(doc.createTextNode(task.getQuestion()));
			taskElement.appendChild(question);
			
			// answer elements
			for (Answer answer : task.getAnswers()) {
				Element answerElement = doc.createElement("answer");
				answerElement.appendChild(doc.createTextNode(answer.getContent()));
				answerElement.setAttribute("correct", (answer.isCorrect() ? "true" : "false"));
				taskElement.appendChild(answerElement);
			}
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("questions/generated.xml"));
 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
 
			transformer.transform(source, result);
		} catch (DOMException
				| ParserConfigurationException
				| TransformerFactoryConfigurationError | TransformerException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		System.out.println("File saved!");
	}
}
