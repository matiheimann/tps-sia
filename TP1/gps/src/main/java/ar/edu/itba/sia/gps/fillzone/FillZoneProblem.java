package ar.edu.itba.sia.gps.fillzone;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ar.edu.itba.sia.gps.api.Problem;
import ar.edu.itba.sia.gps.api.Rule;
import ar.edu.itba.sia.gps.api.State;

public class FillZoneProblem implements Problem {
	
	private final static Color[] colors = Color.values();
	private final static List<Rule> rules = Arrays.asList(Rules.values());

	FillZoneState initState;
	
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
		Color[][] b = fzs.getBoard();
		Color c = b[0][0];
		
		for(int i = 0; i < b.length; i++)
			for(int j = 0; j < b[0].length; j++)
				if(b[i][j] != c)
					return false;
		
		return true;
	}

	@Override
	public List<Rule> getRules() {
		return rules;
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

}
