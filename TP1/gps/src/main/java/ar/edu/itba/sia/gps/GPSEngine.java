package ar.edu.itba.sia.gps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.Problem;
import ar.edu.itba.sia.gps.api.Rule;
import ar.edu.itba.sia.gps.api.State;

public class GPSEngine {

	Queue<GPSNode> open;
	Map<State, Integer> bestCosts;
	Problem problem;
	long explosionCounter;
	long analyzedCounter;
	long frontierCounter;
	boolean finished;
	boolean failed;
	GPSNode solutionNode;
	Optional<Heuristic> heuristic;
	
	long elapsedTime;

	// Use this variable in open set order.
	protected SearchStrategy strategy;

	public GPSEngine(Problem problem, SearchStrategy strategy, Heuristic heuristic) {
		// T̶O̶D̶O̶: open = *Su queue favorito, TENIENDO EN CUENTA EL ORDEN DE LOS NODOS*
		open = new PriorityQueue<>(strategy);
		bestCosts = new HashMap<>();
		this.problem = problem;
		this.strategy = strategy;
		this.heuristic = Optional.ofNullable(heuristic);
		explosionCounter = 0;
		analyzedCounter = 0;
		frontierCounter = 0;
		finished = false;
		failed = false;
	}

	public void findSolution() {
		long startTime = System.currentTimeMillis();
		GPSNode rootNode;
		if (heuristic.isPresent())
			rootNode = new GPSNode(problem.getInitState(), 0, heuristic.get().getValue(problem.getInitState()), null);
		else
			rootNode = new GPSNode(problem.getInitState(), 0, null);
		open.add(rootNode);
		// T̶O̶D̶O̶: ¿Lógica de IDDFS?
		if (strategy == SearchStrategy.IDDFS) {
			findSolutionIDDFS();
		} else {
			while (open.size() > 0) {
				GPSNode currentNode = open.remove();
				analyzedCounter++;
				if (problem.isGoal(currentNode.getState())) {
					finished = true;
					solutionNode = currentNode;
					elapsedTime = System.currentTimeMillis() - startTime;
					return;
				} else {
					explode(currentNode);
				}
			}
			failed = true;
			finished = true;
		}
	}
	
	private void findSolutionIDDFS() {
		int maxDepth = 5; // Poner maxDepth inicial optimo para el problema
		int currentDepth = 0;
		while (currentDepth <= maxDepth) {
			ArrayList<GPSNode> aux = new ArrayList<>();
			while(open.size() > 0) {
				GPSNode currentNode = open.remove();
				if (problem.isGoal(currentNode.getState())) {
					frontierCounter++;
					finished = true;
					solutionNode = currentNode;
					return;
				} else {
					if (currentNode.getDepth() <= maxDepth) {
						explode(currentNode);
					} else {
						aux.add(currentNode);
					}
				}
			}
			if (aux.size() > 0) {
				open.addAll(aux);
				currentDepth = maxDepth;
				maxDepth++;
			}
			currentDepth++;
		}
		failed = true;
		finished = true;
	}

	private void explode(GPSNode node) {
		Collection<GPSNode> newCandidates;
		boolean added = false;
		switch (strategy) {
			case BFS:
				if (bestCosts.containsKey(node.getState())) {
					return;
				}
				newCandidates = new ArrayList<>();
				addCandidates(node, newCandidates);
				// T̶O̶D̶O̶: ¿Cómo se agregan los nodos a open en BFS?
				for (GPSNode n : newCandidates) {
					if (!bestCosts.containsKey(n.getState())) {
						added = true;
						open.add(n);
					}
				}
				break;
			case DFS:
				if (bestCosts.containsKey(node.getState())) {
					return;
				}
				newCandidates = new ArrayList<>();
				addCandidates(node, newCandidates);
				// T̶O̶D̶O̶: ¿Cómo se agregan los nodos a open en DFS?
				for (GPSNode n : newCandidates) {
					if (!bestCosts.containsKey(n.getState())) {
						added = true;
						open.add(n);
					}
				}
				break;
			case IDDFS:
				newCandidates = new ArrayList<>();
				addCandidates(node, newCandidates);
				// T̶O̶D̶O̶: ¿Cómo se agregan los nodos a open en IDDFS?
				for (GPSNode n : newCandidates) {
					if (!bestCosts.containsKey(n.getState())) {
						added = true;
						open.add(n);
					}
				}
				break;
			case GREEDY:
				newCandidates = new ArrayList<>();
				addCandidates(node, newCandidates);
				// T̶O̶D̶O̶: ¿Cómo se agregan los nodos a open en GREEDY?
				for (GPSNode n : newCandidates) {
					if (!bestCosts.containsKey(n.getState())) {
						added = true;
						open.add(n);
					}
				}
				break;
			case ASTAR:
				if (!isBest(node.getState(), node.getCost())) {
					return;
				}
				newCandidates = new ArrayList<>();
				addCandidates(node, newCandidates);
				// T̶O̶D̶O̶: ¿Cómo se agregan los nodos a open en A*?
				for (GPSNode n : newCandidates) {
					if (isBest(n.getState(), n.getCost())) {
						added = true;
						open.add(n);
					}
				}
				break;
		}
		if (!added)
			frontierCounter++;
	}

	private void addCandidates(GPSNode node, Collection<GPSNode> candidates) {
		explosionCounter++;
		updateBest(node);
		for (Rule rule : problem.getRules()) {
			Optional<State> newState = rule.apply(node.getState());
			if (newState.isPresent()) {
				GPSNode newNode;
				if (heuristic.isPresent())
					newNode = new GPSNode(newState.get(), node, node.getCost() + rule.getCost(), heuristic.get().getValue(newState.get()), rule);
				else {
					newNode = new GPSNode(newState.get(), node, node.getCost() + rule.getCost(), rule);
				}
				candidates.add(newNode);
			}
		}
	}

	private boolean isBest(State state, Integer cost) {
		return !bestCosts.containsKey(state) || cost < bestCosts.get(state);
	}

	private void updateBest(GPSNode node) {
		bestCosts.put(node.getState(), node.getCost());
	}

	// GETTERS FOR THE PEOPLE!

	public Queue<GPSNode> getOpen() {
		return open;
	}

	public Map<State, Integer> getBestCosts() {
		return bestCosts;
	}

	public Problem getProblem() {
		return problem;
	}

	public long getExplosionCounter() {
		return explosionCounter;
	}
	
	public long getAnalyzedCounter() {
		return analyzedCounter;
	}
	
	public long getFrontierCounter() {
		return frontierCounter;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isFailed() {
		return failed;
	}

	public GPSNode getSolutionNode() {
		return solutionNode;
	}

	public SearchStrategy getStrategy() {
		return strategy;
	}

}
