package gps;

import gps.api.Heuristic;
import gps.api.Rule;
import gps.api.State;

public class GPSNode {

	private State state;

	private GPSNode parent;

	private Integer cost;
	
	private Integer heuristicValue;

	private Rule generationRule;
	
	private Integer depth;
	
	public GPSNode(State state, Integer cost, Rule generationRule) {
		this.state = state;
		this.cost = cost;
		this.generationRule = generationRule;
		this.depth = 0;
	}
	
	public GPSNode(State state, GPSNode parent, Integer cost, Rule generationRule) {
		this.state = state;
		this.parent = parent;
		this.cost = cost;
		this.generationRule = generationRule;
		this.depth = parent.getDepth() + 1;
	}

	public GPSNode(State state, GPSNode parent, Integer cost, Heuristic heuristic, Rule generationRule) {
		this.state = state;
		this.parent = parent;
		this.cost = cost;
		this.heuristicValue = heuristic.getValue(state);
		this.generationRule = generationRule;
		this.depth = parent.getDepth() + 1;
	}

	public GPSNode getParent() {
		return parent;
	}

	public void setParent(GPSNode parent) {
		this.parent = parent;
	}

	public State getState() {
		return state;
	}

	public Integer getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return state.toString();
	}

	public String getSolution() {
		if (this.parent == null) {
			return this.state.toString();
		}
		return this.parent.getSolution() + this.state.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GPSNode other = (GPSNode) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	public Rule getGenerationRule() {
		return generationRule;
	}

	public void setGenerationRule(Rule generationRule) {
		this.generationRule = generationRule;
	}
	
	public Integer getDepth() {
		return depth;
	}
	
	public Integer getHeuristicValue() {
		return heuristicValue;
	}

}
