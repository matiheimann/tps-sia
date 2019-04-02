package ar.edu.itba.sia.gps.fillzone;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.Heuristic;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	private JPanel panel;
	private JLabel headerLabel;
	private JButton pauseButton;
	private AtomicBoolean paused = new AtomicBoolean(false);
	Thread thread = new Thread();
	
	public GameWindow(Color[][] board) {
		setTitle("Fill Zone");
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		headerLabel = new JLabel("Search Strategy", JLabel.CENTER);
		headerLabel.setFont(new Font("Kai", Font.BOLD, 32));
		panel = new GamePanel(board);
		pauseButton = new JButton("PAUSE");
		pauseButton.setFont(new Font("Kai", Font.BOLD, 22));
		pauseButton.setPreferredSize(new Dimension(50, 50));
		pauseButton.setFocusPainted(false);
		pauseButton.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				if(!paused.get()) {
					paused.set(true);
					pauseButton.setText("RESUME");
				} else {
					paused.set(false);
					pauseButton.setText("PAUSE");
					synchronized(thread) {
						thread.notify();
					}
				}
		    }
		});

		getContentPane().add(headerLabel, BorderLayout.PAGE_START);
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(pauseButton, BorderLayout.PAGE_END);

		setVisible(true);
	}

	public void update(Color[][] board, int state, int totalStates, SearchStrategy strategy, Heuristic heuristic) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if(paused.get()) {
					synchronized(thread) {
						try {
							thread.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(heuristic != null) {
					headerLabel.setText(strategy.toString() + ": " + heuristic.toString() + " - " + state + "/" + totalStates);
				} else {
					headerLabel.setText(strategy.toString() + " - " + state + "/" + totalStates);
				}
				((GamePanel) panel).update(board);
			}
		};
		
		thread = new Thread(runnable);
		thread.start();
	}
	
	public boolean isPaused() {
		return paused.get();
	}
}
