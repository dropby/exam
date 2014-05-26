package com.xmlgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.window.Answer;
import com.window.QuestionFileReader;
import com.window.Task;

public class HTMLGenerator {
	
	private static List<Task> questions = new LinkedList<Task>(); 
	private static QuestionFileReader questionFileReader;
	
	private String fileName;
	public HTMLGenerator() {
		this.fileName = "questions/generated.html";
	}
	
	public void save() {
		questionFileReader = new QuestionFileReader("questions/generated.xml");
		questionFileReader.readAll(questions);
	
		try {
	          File file = new File(this.fileName);
	          BufferedWriter output = new BufferedWriter(new FileWriter(file));
	          output.write("<html><body>");
	          output.write("<style>.exercise {border: solid 3px #cdcdcd; padding:10px;} .correct {background-color: #90EE90;} .question {background-color: #E4F1FA;}</style>");
				
	          int counter = 0;
	          for (Task task : questions) {
					counter++;
					output.write("<div class='exercise'>" + counter + "<div class='question'><pre>" + StringEscapeUtils.escapeHtml4(task.getQuestion()) + "</pre></div>");
					for (Answer answer : task.getAnswers()) {
						if (answer.isCorrect()) {
							output.write("<div class='correct'>");	
						}
						else {
							output.write("<div>");
						}
						output.write(StringEscapeUtils.escapeHtml4(answer.getContent()) + "</div>");
					}
					output.write("</div>");
				}
	          output.close();
	        } catch ( IOException e ) {
	           e.printStackTrace();
	    }
	}
}
