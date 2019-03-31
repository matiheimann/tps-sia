package ar.edu.itba.sia.gps.fillzone;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import ar.edu.itba.sia.gps.api.State;
import ar.edu.itba.sia.gps.fillzone.utils.Graph;

public class FillZoneState implements State {
	
	private Color[][] board;
	private Graph graph;
	
	public FillZoneState(Color[][] board) {
		this.board = board;
		this.graph = initGraph();
	}
	
	public FillZoneState(Color[][] board, Graph graph) {
		this.board = board;
		this.graph = graph;
	}
	
	@Override
	public String getRepresentation() {
		StringBuffer representation = new StringBuffer("");
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				representation.append(board[i][j].ordinal());
				representation.append(" ");
			}
			representation.append('\n');
		}
		return representation.toString();
	}

	public Color[][] getBoard() {
		return this.board;
	}
	
	public Graph getGraph() {
		return this.graph;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FillZoneState other = (FillZoneState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}
	
	private Graph initGraph() {
        Graph g = new Graph();
        int[][] valueNode = new int[board.length][board[0].length];

        for(int i = 0; i < valueNode.length; i++) {
            for (int j = 0; j < valueNode[0].length; j++) {
                valueNode[i][j] = -1;
            }
        }

        int numberNode = 0;

        for(int i = 0; i < valueNode.length; i++) {
            for(int j = 0; j < valueNode[0].length; j++) {
                if(valueNode[i][j] == -1) {
                	initIsland(valueNode, i, j, numberNode);
                	g.addNode(numberNode, board[i][j]);
                    numberNode++;
                }
            }
        }
        
        /*
        for(int i = 0; i < valueNode.length; i++) {
            for(int j = 0; j < valueNode[0].length; j++) {
            	System.out.print(valueNode[i][j] + "\t");
            }
            System.out.println();
        }
        */

        for(int i = 0; i < valueNode.length; i++) {
            for(int j = 0; j < valueNode[0].length; j++) {
                if(i != valueNode.length - 1 && valueNode[i][j] != valueNode[i+1][j]) {
                    g.addEdge(valueNode[i][j], valueNode[i+1][j]);
                }
                if(j != valueNode[0].length - 1 && valueNode[i][j] != valueNode[i][j+1]) {
                    g.addEdge(valueNode[i][j], valueNode[i][j+1]);
                }
            }
        }

        return g;
    }

    private void initIsland(int[][] valueNode, int y, int x, int numberNode) {
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(x, y));

        while(!queue.isEmpty()) {
            Cell cell = queue.poll();
            valueNode[cell.y][cell.x] = numberNode;

            if(cell.y != 0 && board[y][x] == board[cell.y - 1][cell.x]) {
            	if (valueNode[cell.y - 1][cell.x] == -1) {
                	queue.offer(new Cell(cell.x, cell.y - 1));
            	}
            }

            if(cell.y != board.length - 1 && board[y][x] == board[cell.y + 1][cell.x]) {
            	if (valueNode[cell.y + 1][cell.x] == -1) {
            		queue.offer(new Cell(cell.x, cell.y + 1));
            	}
            }

            if(cell.x != 0 && board[y][x] == board[cell.y][cell.x - 1]) {
            	if (valueNode[cell.y][cell.x - 1] == -1) {
                	queue.offer(new Cell(cell.x - 1, cell.y));
            	}
            }

            if(cell.x != board[0].length - 1 && board[y][x] == board[cell.y][cell.x + 1]) {
            	if (valueNode[cell.y][cell.x + 1] == -1) {
                	queue.offer(new Cell(cell.x + 1, cell.y));
            	}
            }
        }
    }

}
