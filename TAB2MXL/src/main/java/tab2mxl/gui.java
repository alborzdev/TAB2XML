package tab2mxl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;

import config.ConfigReader;

class gui{
	public static void main(String args[]) {
		
		// --- WINDOW ---
		
			// Sizing (from config file)
			String s_width = ConfigReader.getConfig().getAttr("pref_width");
			String s_height = ConfigReader.getConfig().getAttr("pref_height");
			
			int width = Integer.parseInt(s_width);
			int height = Integer.parseInt(s_height);
			
			// Frame
			JFrame frame = new JFrame("TAB2MXL");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(width, height);

		// --- COMPONENTS ---
			
			// MenuBar w/File, Open
			JMenuBar mb = new JMenuBar();
			
			JMenu m1 = new JMenu("FILE");
			mb.add(m1);
			
			JMenuItem mi1 = new JMenuItem("Open");
			m1.add(mi1);

			// TextArea
			JTextArea ta = new JTextArea();
			
				//Instructions
				ta.setText(	"^\n"
						+ 	"|\n"
						+ 	"SELECT FILE\n"
						+ 	"or\n"
						+ 	"REMOVE this text then COPY AND PASTE your Tab here\n\n"
						+ 	"You can edit your tab directly if you would like to make any changes\n\n"
						+ 	"Once you are happy with it CLICK the CONVERT below");			
				
			// Convert Button
			JButton con = new JButton("Convert");
			frame.getContentPane().add(con);
	
			// Adding Components to the Frame.
			frame.getContentPane().add(BorderLayout.NORTH, mb);
			frame.getContentPane().add(BorderLayout.CENTER, ta);
			frame.getContentPane().add(BorderLayout.SOUTH, con);
			frame.setVisible(true);
		
		// --- LISTENERS ---
		
			// FILE->Open Button
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
					    	
					    	ta.setText( txtAnalyzing.analyze(selectedFile.toString()) );
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					    
					    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					}
					
				} 
			} );
		
			// Convert Button
			con.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//SEND TO BACK END
					System.out.print(ta.getText());
				} 
			} );
		
	}
	
}
