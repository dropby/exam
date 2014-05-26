package com.window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {
//	private enum typeEnum {CHECK_BOX, RADIO_BUTTON};
//	private typeEnum type = typeEnum.CHECK_BOX;
	private String question;
	private List<Answer> answers = new ArrayList<Answer>();
	private boolean radio;
	
	public Task() {
		this.radio = false;
	}
	public Task(String radio) {
		this.radio = (radio.equals("true") ? true : false);
	}
	
	public boolean isRadio() {
		return this.radio;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public List<Answer> getAnswers() {
		Collections.shuffle(answers);
		return this.answers;
	}

	public void setQuestion(String question) {
		this.question = question;
		
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}
}
