package srt;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;
	
	public Display(String title){
		createDisplay(title);
	}
	
	private void createDisplay(String title){
		frame = new JFrame(title);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(800, 600));
		canvas.setMaximumSize(new Dimension(800, 600));
		canvas.setMinimumSize(new Dimension(800, 600));
		
		frame.add(canvas);
		frame.pack();
	}

	public Canvas getCanvas(){
		return canvas;
	}
	
	
	
	
	
	
}
