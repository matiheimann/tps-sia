package ar.edu.itba.sia.gps.fillzone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.GPSNode;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.Heuristic;

public class Main {
	
	private static final String HELP = "Usage:\n"
			+ "\t1. fillzone (runs with 5x5 random generated board)\n"
			+ "\t2. fillzone <filename> (runs with board inside a file)\n"
			+ "\t\tFile format example:\n"
			+ "\t\t\t3 5\n"
			+ "\t\t\t35201\n"
			+ "\t\t\t21043\n"
			+ "\t\t\t20013\n"
			+ "\t3. fillzone <height> <width> (runs with height * width random generated board)\n";
	
	private static GameWindow window;

	public static void main(String[] args) {
		FillZoneProblem problem = initProblem(args);
		Heuristic neighbourHeuristic = new NeighbourHeuristic();
		Heuristic isleCountHeuristic = new IsleCountHeuristic();
		Heuristic maxDistanceHeuristic = new MaxDistanceHeuristic();
		findSolution(problem, SearchStrategy.BFS, null);
		findSolution(problem, SearchStrategy.DFS, null);
		findSolution(problem, SearchStrategy.IDDFS, null);
		findSolution(problem, SearchStrategy.GREEDY, neighbourHeuristic);
		findSolution(problem, SearchStrategy.ASTAR, neighbourHeuristic);
		findSolution(problem, SearchStrategy.GREEDY, maxDistanceHeuristic);
		findSolution(problem, SearchStrategy.ASTAR, maxDistanceHeuristic);
		findSolution(problem, SearchStrategy.GREEDY, isleCountHeuristic);
		findSolution(problem, SearchStrategy.ASTAR, isleCountHeuristic);
	}
	
	private static FillZoneProblem initProblem(String[] args) {
		try {
			switch (args.length) {
				case 0:
					return new FillZoneProblem(5, 5);
				case 1:
					return new FillZoneProblem(new File(args[0]));
				case 2:
					return new FillZoneProblem(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
				default:
					System.out.println("ERROR: too many arguments.");
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: input file not found.");
		} catch (NumberFormatException e) {
			System.out.println("ERROR: invalid numbers format.");
		}
		System.out.print(HELP);
		System.exit(1);
		return null;
	}
	
	private static void findSolution(FillZoneProblem problem, SearchStrategy strat, Heuristic heuristic) {
		GPSEngine engine = new GPSEngine(problem, strat, heuristic);
		engine.findSolution();
		if (engine.isFinished() && !engine.isFailed()) {
			solutionToXYZFile(getSolutionStart(engine.getSolutionNode()), strat.toString());
			System.out.println(strat.toString() + " ok.");
		} else {
			System.out.println(strat.toString() + " failed.");
		}
		if (heuristic != null) {
			System.out.println("Heuristic: " + heuristic.toString());
		}
		System.out.println("Elapsed Time: " + engine.getElapsedTime()/1000.0 + "s");
		System.out.println("Depth: " + engine.getSolutionNode().getDepth());
		System.out.println("Cost: " + engine.getSolutionNode().getCost());
		System.out.println("Expanded Nodes: " + engine.getExplosionCounter());
		System.out.println("Analyzed States: " + engine.getAnalyzedCounter());
		System.out.println("Frontier Nodes: " + engine.getFrontierCounter());
		System.out.println();
		
		showSolution(getSolutionStart(engine.getSolutionNode()), problem, strat, heuristic);
	}
	
	private static LinkedList<GPSNode> getSolutionStart(GPSNode solution) {
		LinkedList<GPSNode> list = new LinkedList<>();
		while (solution != null) {
			list.addFirst(solution);
			solution = solution.getParent();
		}
		return list;
	}
	
	private static void solutionToXYZFile(LinkedList<GPSNode> solution, String fileName) {
    	File file = new File(fileName + ".xyz");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			return;
		}
		PrintStream ps = new PrintStream(fos);
		
		for (GPSNode node : solution) {
			FillZoneState state = (FillZoneState) node.getState();
			Color[][] board = state.getBoard();
			int height = board.length;
			int width = board[0].length;
			ps.println(height * width);
			ps.println();
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					ps.println(board[i][j].getRGB() + " " + (height - i) + " " + j);
				}
			}
			//System.out.println(state.getGraph());
			//System.out.println(state.getGraph().getMaxDistance());
		}
		
		ps.close();
    }
	
	private static void showSolution(List<GPSNode> solutionPath, FillZoneProblem problem, SearchStrategy strat, Heuristic heuristic) {
		if (window == null) {
			FillZoneState fsz = (FillZoneState) problem.getInitState();
			window = new GameWindow(fsz.getBoard());
		}
		int totalStates = solutionPath.size();
		for(GPSNode node : solutionPath) {
			while(window.isPaused()) {}
			FillZoneState fsz = (FillZoneState) node.getState();
			window.update(fsz.getBoard(), node.getCost() + 1, totalStates, strat, heuristic);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
