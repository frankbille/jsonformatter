/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.frankbille.jsonformatter;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField sourceField;
	private JTextField destinationField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		setBounds(100, 100, 603, 255);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.GLUE_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblSourceJson = new JLabel("Source JSON");
		getContentPane().add(lblSourceJson, "2, 2, right, default");

		sourceField = new JTextField();
		getContentPane().add(sourceField, "4, 2, fill, default");
		sourceField.setColumns(10);

		JButton selectSourceButton = new JButton("...");
		selectSourceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				selectFile(sourceField);
			}
		});
		getContentPane().add(selectSourceButton, "6, 2");

		JLabel lblDestinationJson = new JLabel("Destination JSON");
		getContentPane().add(lblDestinationJson, "2, 4, right, default");

		destinationField = new JTextField();
		getContentPane().add(destinationField, "4, 4, fill, default");
		destinationField.setColumns(10);

		JButton selectDestinationButton = new JButton("...");
		selectDestinationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				selectFile(destinationField);
			}
		});
		getContentPane().add(selectDestinationButton, "6, 4");

		JButton formatButton = new JButton("Format");
		formatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File sourceFile = new File(sourceField.getText());
				File destinationFile = new File(destinationField.getText());
				try {
					JsonFormatter.format(sourceFile, destinationFile);
					JOptionPane.showMessageDialog(MainWindow.this, "The source file has been formatted and the content can be seen in the destination file", "File formatted!", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainWindow.this, e.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getContentPane().add(formatButton, "2, 6, 5, 1");

	}

	private void selectFile(JTextField field) {
		JFileChooser selectFile = new JFileChooser();
		selectFile.setMultiSelectionEnabled(false);
		selectFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
		selectFile.setFileFilter(new FileNameExtensionFilter("JSON File", "json"));
		File file = new File(field.getText());
		selectFile.setSelectedFile(file);
		if (selectFile.showDialog(MainWindow.this, "Select") == JOptionPane.OK_OPTION) {
			field.setText(selectFile.getSelectedFile().getAbsolutePath());
		}
	}

}
