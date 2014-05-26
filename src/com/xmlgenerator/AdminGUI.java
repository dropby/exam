package com.xmlgenerator;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

import com.window.Answer;
import com.window.Task;

public class AdminGUI extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		getContentPane().setLayout(new GridLayout(0, 2));
		
		final JEditorPane editorPane = new JEditorPane();
		getContentPane().add(editorPane);
		
		final JCheckBox checkBox = new JCheckBox("Radio");
		getContentPane().add(checkBox);
		
		final List<JEditorPane> editorPanes = new ArrayList<JEditorPane>();
		final List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		
		for (int i = 0; i < 8; i++) {
			JEditorPane editorPane_ = new JEditorPane();

			if (i % 2 == 0) {
				editorPane_.setBackground(SystemColor.control);
			}
			getContentPane().add(editorPane_);
			editorPanes.add(editorPane_);
			
			JCheckBox checkBox_ = new JCheckBox();
			getContentPane().add(checkBox_);
			checkBoxes.add(checkBox_);
		}

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Task task = new Task((checkBox.isSelected() ? "true" : "false"));
				
				for (int i = 0; i < editorPanes.size(); i++) {
					if (editorPanes.get(i).getText().length() > 0) {
						Answer answer = new Answer(editorPanes.get(i).getText(), (checkBoxes.get(i).isSelected() ? "true" : "false"));
						task.addAnswer(answer);
					}
				}
				
				task.setQuestion(editorPane.getText());
				XMLGenerator xmlGen = new XMLGenerator();
				xmlGen.save(task);
			}
		});
		getContentPane().add(btnNewButton);
		
		HTMLGenerator htmlGen = new HTMLGenerator();
		htmlGen.save();
	}

}
