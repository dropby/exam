package com.window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class EAction {
	private JPanel contentPane;
	private QuestionProvider questionProvider = QuestionProvider.getInstance();
	private static JTextArea textQuestion;
	private static List<JCheckBox> checkBoxList = new LinkedList<JCheckBox>();
	private static List<JRadioButton> radioButtonList = new LinkedList<JRadioButton>();
	private static JLabel currentPosition;
	private Task task;
	

	public EAction(JPanel contentPane) {
		this.contentPane = contentPane;
	}
	
	private void getTask() {
		task = questionProvider.getNext();
		if (task == null) System.exit(1);
	}
	private void deleteLastContent() {
		if (null != currentPosition) {
			contentPane.remove(currentPosition);
		}
		if (null == textQuestion) {
		}
		else {
			contentPane.remove(textQuestion);
			for (JCheckBox checkBox : checkBoxList) {
				contentPane.remove(checkBox);
			}
			for (JRadioButton radioButton : radioButtonList) {
				contentPane.remove(radioButton);
			}
		}
	}
	private void drawQuestion() {
		textQuestion = new JTextArea(task.getQuestion());
		textQuestion.setBackground(new Color(228, 241, 250));
		contentPane.add(textQuestion, "2, 4");
	}

	public void drawTask() {
		this.getTask();
		this.deleteLastContent();
		this.drawQuestion();
		this.drawAnswers();
		

		currentPosition = new JLabel(String.valueOf(questionProvider.getPosition()));
		contentPane.add(currentPosition, "2, 1");
		
		contentPane.revalidate();
		contentPane.repaint();
	}

	private void drawAnswers() {
		int rowNum = 6;

		ButtonGroup group = new ButtonGroup();

		for (final Answer answer : task.getAnswers()) {			
			if (task.isRadio()) {
				JRadioButton radioButton = new JRadioButton(answer.getContent());
				radioButtonList.add(radioButton);
				group.add(radioButton);
				
				radioButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (answer.isCorrect() == true) {
							JRadioButton a = (JRadioButton) e.getSource();
							a.setBackground(Color.GREEN);
						}
						else {
							JRadioButton a = (JRadioButton) e.getSource();
							a.setBackground(Color.RED);
						}
		                contentPane.revalidate();
					}
				});
				
				contentPane.add(radioButton, "2, " + rowNum);
				rowNum += 2;	
			}
			else {
				JCheckBox chckbxNewCheckBox = new JCheckBox(answer.getContent());
				checkBoxList.add(chckbxNewCheckBox);
				
				chckbxNewCheckBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (answer.isCorrect() == true) {
							JCheckBox a = (JCheckBox) e.getSource();
			                Color tmp = a.isSelected() ? Color.GREEN : null;
			                a.setBackground(tmp);
						}
						else {
							JCheckBox a = (JCheckBox) e.getSource();
			                Color tmp = a.isSelected() ? Color.RED : null;
			                a.setBackground(tmp);	
						}
		                contentPane.revalidate();
					}
				});
				contentPane.add(chckbxNewCheckBox, "2, " + rowNum);
				rowNum += 2;	
			}
		}
	}
}
