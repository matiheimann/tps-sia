package ar.edu.itba.sia.gps.fillzone.utils;

import java.util.*;

public class Graph {

    private Map<Integer, Node> nodes;

    public Graph(){
        this.nodes = new HashMap<>();
    }

    private class Node {

        private int value;
        private boolean isVisited;
        Set<Node> neighbours;

        public Node(int value){
            this.value = value;
            this.isVisited = false;
            this.neighbours = new HashSet<>();
        }

    }

    private class PQNode implements Comparable<PQNode>{

        private int weight;
        private Node node;

        public PQNode(int weight, Node node){
            this.weight = weight;
            this.node = node;
        }

        @Override
        public int compareTo(PQNode pqNode) {
            return this.weight - pqNode.weight;
        }
    }

    public void addEdge(int node1, int node2){

        if(this.nodes.get(node1) == null ||
            this.nodes.get(node2) == null){
            throw new RuntimeException("One or both nodes does not exist");
        }

        this.nodes.get(node1).neighbours.add(this.nodes.get(node2));
        this.nodes.get(node2).neighbours.add(this.nodes.get(node1));

    }

    public void addNode(int node){
        this.nodes.put(node, new Node(node));
    }

    public void clearMarks(){
        this.nodes.forEach((k,v) -> v.isVisited = false);
    }

    public Integer getMaxDistance() {
        clearMarks();
        PriorityQueue<PQNode> pq = new PriorityQueue<>();
        Node n = nodes.get(0);
        n.isVisited = false;
        pq.offer(new PQNode(0, n));

        Integer maxValue = Integer.valueOf(0);

        while(!pq.isEmpty()){

            PQNode pqn = pq.poll();
            maxValue = Integer.valueOf(pqn.weight);

            pqn.node.neighbours.forEach(neighbour ->
                {if(neighbour.isVisited == false){
                    neighbour.isVisited = true;
                    pq.offer(new PQNode(pqn.weight + 1, neighbour));
                }});
        }

        return maxValue;
    }

}
