package brickBracker;

import javax.swing.JFrame;

public class Main_bri {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame frame = new JFrame();
		Gameplay gameplay = new Gameplay();
		frame.setBounds(10,10,700,600);
		frame.setTitle("Breakout Ball");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gameplay);
	}

}
