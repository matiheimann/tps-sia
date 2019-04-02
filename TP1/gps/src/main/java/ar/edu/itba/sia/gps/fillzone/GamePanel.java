package ar.edu.itba.sia.gps.fillzone;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	private JPanel[][] panels;
	
	public GamePanel(Color[][] board) {
		int height = board.length;
		int width = board[0].length;
		
		setLayout(new GridLayout(height, width));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		panels = new JPanel[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				panels[i][j] = new JPanel();
				panels[i][j].setVisible(true);
				panels[i][j].setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
				setPanelColor(panels[i][j], board[i][j]);
				add(panels[i][j]);
			}
		}
		
	}

	private void setPanelColor(JPanel jPanel, Color color) {
		switch(color) {
		case WHITE:
			jPanel.setBackground(java.awt.Color.WHITE);
			break;
		case RED:
			jPanel.setBackground(java.awt.Color.RED);
			break;
		case BLUE:
			jPanel.setBackground(java.awt.Color.BLUE);
			break;
		case YELLOW:
			jPanel.setBackground(java.awt.Color.YELLOW);
			break;
		case PINK:
			jPanel.setBackground(java.awt.Color.MAGENTA);
			break;
		case GREEN:
			jPanel.setBackground(java.awt.Color.GREEN);
			break;
		}
	}

	public void update(Color[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				setPanelColor(panels[i][j], board[i][j]);
			}
		}
	}
}
