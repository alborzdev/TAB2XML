package tab2mxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;

import config.ConfigReader;

class gui{
	public static void main(String args[]) {
		
		// Frame
		JFrame frame = new JFrame("My First GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//get preferred window width from config file (value is string)
		String s_width = ConfigReader.getConfig().getAttr("pref_width");
		//parse value to integer
		int width = Integer.parseInt(s_width);
		//same for height, but all in one line
		int height = Integer.parseInt(ConfigReader.getConfig().getAttr("pref_height"));
		//set frame size based on values from config.ini
		frame.setSize(600, 500);//add width and height back

		// Button
		JButton button = new JButton("Convert");
		frame.getContentPane().add(button);

		// MenuBar
		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("FILE");
		mb.add(m1);
		JMenuItem mi1 = new JMenuItem("Open");
		m1.add(mi1);

		// Text Area at the Center
		JTextArea ta = new JTextArea();
		

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, mb);
		frame.getContentPane().add(BorderLayout.CENTER, ta);
		frame.getContentPane().add(BorderLayout.SOUTH, button);
		frame.setVisible(true);
		
		//Listeners
		button.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.print(ta.getText());
			} 
		} );
		
		mi1.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent arg0) {

				File selectedFile = null; //user selected file directory
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt")); 
				fileChooser.setAcceptAllFileFilterUsed(true);
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
				    selectedFile = fileChooser.getSelectedFile();
				    
				    try {
				    	ta.insert( txtAnalyzing.analyze(selectedFile.toString()) , 0);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				    
				    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				}
				
			} 
		} );
		
		
		
	}
	
}
