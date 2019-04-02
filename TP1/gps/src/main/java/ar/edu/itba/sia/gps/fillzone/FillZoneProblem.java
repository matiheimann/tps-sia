package ar.edu.itba.sia.gps.fillzone;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import ar.edu.itba.sia.gps.api.Problem;
import ar.edu.itba.sia.gps.api.Rule;
import ar.edu.itba.sia.gps.api.State;

public class FillZoneProblem implements Problem {
	
	private final static Color[] colors = Color.values();
	private final static List<Rule> rules = Arrays.asList(Rules.values());

	private FillZoneState initState;
	
	public FillZoneProblem(int height, int width) {
		initRandBoard(height, width);
	}
	
	public FillZoneProblem(String fileName) {
		parseFile(fileName);
	}

	@Override
	public State getInitState() {
		return initState;
	}

	@Override
	public boolean isGoal(State state) {
		FillZoneState fzs = (FillZoneState) state;
		Graph g = fzs.getGraph();
		if (g.getNodesCount() == 1)
			return true;
		
		return false;
	}

	@Override
	public List<Rule> getRules() {
		return rules;
	}
	
	private void initRandBoard(int height, int width) {
		Color[][] board = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = colors[rand(0, colors.length)];
			}
		}
		initState = new FillZoneState(board);
	}
	
	private void parseFile(String fileName) {
		File file = new File(fileName);
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		int height = input.nextInt();
		int width = input.nextInt();
		Color[][] board = new Color[height][width];
		input.nextLine();
		int x = 0;
		int y = 0;

		while(input.hasNextLine()) {
			x = 0;
			String line = input.nextLine();
			char[] currentLine = line.toCharArray();
			for(char current: currentLine ) {
				if(current != '\n') {
					board[y][x] = colors[Character.getNumericValue(current)];
					x++;
				}
			}
			y++;
		}

		input.close();
		initState = new FillZoneState(board);
	}
	
	private static int rand(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max);
	}

}
