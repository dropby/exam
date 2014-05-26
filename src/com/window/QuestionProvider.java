package com.window;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class QuestionProvider {
	private static QuestionProvider instance = null;
	private static QuestionFileReader questionFileReader;
	private static List<Task> questions = new LinkedList<Task>(); 
	private static Iterator<Task> iterator;
	private static int currentPosition;
	
	private QuestionProvider() {
		questionFileReader = new QuestionFileReader("questions/generated.xml");
		questionFileReader.readAll(questions);
//		for (Question question : questions) {
//			System.out.println(question.getQuestion());
//		}
		//Collections.shuffle(questions);
		iterator = questions.iterator();
		currentPosition = 0;
	}
	
	public static QuestionProvider getInstance() {
		if (instance == null) {
			instance = new QuestionProvider();
		}
		return instance;
	}
	
	public Task getNext() {
		//Question question = new Question();
		if (iterator.hasNext() == true) {
			currentPosition++;
			return iterator.next();
		}
		else {
			return null;
		}
	}
	
	public int getPosition() {
		return currentPosition;
	}
}
