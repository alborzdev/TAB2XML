package tab2mxl;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;  
import config.ConfigReader;;

class gui_test{
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
		frame.setSize(width, height);

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
		
		//
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt")); 
		fileChooser.setAcceptAllFileFilterUsed(true);
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}
}
