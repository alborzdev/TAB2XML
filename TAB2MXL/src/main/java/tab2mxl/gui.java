package tab2mxl;
import javax.swing.*;
import java.awt.*;
class gui_test{
	public static void main(String args[]) {

		// Frame
		JFrame frame = new JFrame("My First GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

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

	}
}
