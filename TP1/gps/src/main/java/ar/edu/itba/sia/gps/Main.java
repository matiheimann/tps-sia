package ar.edu.itba.sia.gps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.fillzone.Color;
import ar.edu.itba.sia.gps.fillzone.FillZoneProblem;
import ar.edu.itba.sia.gps.fillzone.FillZoneState;
import ar.edu.itba.sia.gps.fillzone.GameWindow;
import ar.edu.itba.sia.gps.fillzone.IsleCountHeuristic;
import ar.edu.itba.sia.gps.fillzone.MaxDistanceHeuristic;
import ar.edu.itba.sia.gps.fillzone.NeighbourHeuristic;

public class Main {
	
	private static GameWindow window;

	public static void main(String[] args) {
		FillZoneProblem problem = new FillZoneProblem("input.txt");
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
		System.out.println("Elapsed Time: " + engine.elapsedTime/1000.0 + "s");
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
			FillZoneState fsz = (FillZoneState) node.getState();
			window.update(fsz.getBoard(), node.getCost() + 1, totalStates, strat, heuristic);
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
