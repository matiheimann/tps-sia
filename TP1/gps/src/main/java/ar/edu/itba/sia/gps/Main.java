package ar.edu.itba.sia.gps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.fillzone.Color;
import ar.edu.itba.sia.gps.fillzone.FillZoneProblem;
import ar.edu.itba.sia.gps.fillzone.FillZoneState;
import ar.edu.itba.sia.gps.fillzone.NeighbourHeuristic;

public class Main {

	public static void main(String[] args) {
		FillZoneProblem problem = new FillZoneProblem("input.txt");
		Heuristic neighbourHeuristic = new NeighbourHeuristic();
		findSolution(problem, SearchStrategy.BFS, null);
		findSolution(problem, SearchStrategy.DFS, null);
		findSolution(problem, SearchStrategy.IDDFS, null);
		findSolution(problem, SearchStrategy.GREEDY, neighbourHeuristic);
		findSolution(problem, SearchStrategy.ASTAR, neighbourHeuristic);
	}
	
	private static void findSolution(FillZoneProblem problem, SearchStrategy strat, Heuristic heuristic) {
		GPSEngine engine = new GPSEngine(problem, strat, heuristic);
		engine.findSolution();
		if (engine.isFinished() && !engine.isFailed()) {
			solutionToXYZFile(getSolutionStart(engine.getSolutionNode()), strat.toString());
			System.out.println(strat.toString() + " ok.");
			System.out.println("Elapsed Time: " + engine.elapsedTime/1000.0 + "s");
			System.out.println("Depth: " + engine.getSolutionNode().getDepth());
			System.out.println("Exploded Nodes: " + engine.getExplosionCounter());
		} else {
			System.out.println(strat.toString() + " failed.");
		}
		System.out.println();
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
		}
		
		ps.close();
    }

}
