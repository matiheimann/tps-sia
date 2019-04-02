package ar.edu.itba.sia.gps.fillzone;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.Heuristic;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	JPanel panel;
	JLabel headerLabel;
	
	public GameWindow(Color[][] board) {
		setTitle("Fill Zone");
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		headerLabel = new JLabel("Search Strategy", JLabel.CENTER);
		headerLabel.setFont(new Font("Kai", Font.BOLD, 32));
		
		panel = new GamePanel(board);

		getContentPane().add(headerLabel, BorderLayout.PAGE_START);
		getContentPane().add(panel, BorderLayout.CENTER);

		setVisible(true);
	}

	public void update(Color[][] board, int state, int totalStates, SearchStrategy strategy, Heuristic heuristic) {
		if(heuristic != null) {
			headerLabel.setText(strategy.toString() + ": " + heuristic.toString() + " - " + state + "/" + totalStates);
		} else {
			headerLabel.setText(strategy.toString() + " - " + state + "/" + totalStates);
		}
		((GamePanel) panel).update(board);
	}
	
}
