package ar.edu.itba.sia.gps.fillzone.utils;

import java.util.*;

import ar.edu.itba.sia.gps.fillzone.Color;

public class Graph {

    private Map<Integer, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }
    
    public Graph(Graph graph) {
    	this.nodes = new HashMap<>();
		for(Node n : graph.nodes.values()) {
			addNode(n.value, n.color);
		}
		for(Node n : graph.nodes.values()) {
			for (Node neighbour : n.neighbours) {
				addEdge(n.value, neighbour.value);
			}
		}
        
    }

    private class Node {

        private int value;
        private boolean isVisited;
        Set<Node> neighbours;
        private Color color;

        public Node(int value, Color color) {
            this.value = value;
            this.isVisited = false;
            this.neighbours = new HashSet<>();
            this.color = color;
        }
        
		@Override
		public int hashCode() {
			return Integer.valueOf(value).hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (value != other.value)
				return false;
			return true;
		}

		@Override
		public String toString() {
			String ret = "";
			for (Node n : neighbours) {
				ret += n.value + " ";
			}
			ret += "(" + color + ")";
			return ret;
		}
        
    }

    private class PQNode implements Comparable<PQNode> {
    	
        private int weight;
        private Node node;

        public PQNode(int weight, Node node) {
            this.weight = weight;
            this.node = node;
        }

        @Override
        public int compareTo(PQNode pqNode) {
        	return this.weight - pqNode.weight;
        }
        
    }

    public void addEdge(int node1, int node2) {
        if(this.nodes.get(node1) == null || this.nodes.get(node2) == null) {
            throw new RuntimeException("One or both nodes does not exist");
        }

        this.nodes.get(node1).neighbours.add(this.nodes.get(node2));
        this.nodes.get(node2).neighbours.add(this.nodes.get(node1));
    }

    public void addNode(int node, Color color) {
        this.nodes.put(node, new Node(node, color));
    }
    
    public void mergeIslands(Color color) {
        Node node = nodes.get(0);
        Set<Node> toAdd = new HashSet<>();
        Set<Node> toRemove = new HashSet<>();
        for (Node oldNeighbour : node.neighbours) {
        	if (oldNeighbour.color == color) {
        		oldNeighbour.neighbours.remove(node);
        		for (Node newNeighbour : oldNeighbour.neighbours) {
        			newNeighbour.neighbours.remove(oldNeighbour);
        			toAdd.add(newNeighbour);
        		}
        		toRemove.add(oldNeighbour);
        	}
        }
        for (Node n : toAdd) {
        	addEdge(node.value, n.value);
        }
        for (Node n : toRemove) {
        	node.neighbours.remove(n);
        	nodes.remove(n.value);
        }
        node.color = color;
    }


    public void clearMarks() {
        this.nodes.forEach((k,v) -> v.isVisited = false);
    }

    public Integer getMaxDistance() {
        clearMarks();
        PriorityQueue<PQNode> pq = new PriorityQueue<>();
        Node n = nodes.get(0);
        n.isVisited = true;
        pq.offer(new PQNode(0, n));

        int maxValue = 0;
        //Set<Color> maxValueColors = new HashSet<>();

        while(!pq.isEmpty()) {
            PQNode pqn = pq.poll();
            if (pqn.weight > maxValue) {
            	maxValue = pqn.weight;
            	//maxValueColors.clear();
            }
            
            //maxValueColors.add(pqn.node.color);
            
            pqn.node.neighbours.forEach(neighbour -> {
            	if(neighbour.isVisited == false) {
                    neighbour.isVisited = true;
                    pq.offer(new PQNode(pqn.weight + 1, neighbour));
                }
            });
        }
        //System.out.println(nodes);
        return maxValue;
    }
    
    @Override
    public String toString() {
    	return nodes.toString();
    }

}
