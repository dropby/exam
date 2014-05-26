package com.window;

public class Answer {
	private String content;
	private boolean correct;

	public Answer(String answer, String correct) {
		this.content = answer;
		this.correct = (correct.equals("true") ? true : false);
	}

	public boolean isCorrect() {
		return correct;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
}
