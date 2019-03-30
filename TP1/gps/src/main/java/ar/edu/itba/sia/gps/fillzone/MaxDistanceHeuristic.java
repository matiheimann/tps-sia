package ar.edu.itba.sia.gps.fillzone;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;
import ar.edu.itba.sia.gps.fillzone.utils.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class MaxDistanceHeuristic implements Heuristic {

    @Override
    public Integer getValue(State state) {
        Color[][] board = ((FillZoneState) state).getBoard();
        Graph g = getGraph(board);
        return g.getMaxDistance();
    }

    public Graph getGraph(Color[][] board) {
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
                    getIsland(valueNode, board, i, j, numberNode);
                    numberNode++;
                }
            }
        }

        for(int i = 0; i < numberNode; i++) {
            g.addNode(i);
        }

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

    public void getIsland(int[][] valueNode, Color[][] board, int y, int x, int numberNode) {
        Queue<Cell> queue = new LinkedList<>();
        valueNode[y][x] = numberNode;
        queue.offer(new Cell(x, y));

        while(!queue.isEmpty()) {
            Cell cell = queue.poll();

            if(cell.y != 0 && valueNode[cell.y - 1][cell.x] == -1 && board[y][x] == board[cell.y - 1][cell.x]) {
            	valueNode[cell.y - 1][cell.x] = numberNode;
                queue.offer(new Cell(x, y - 1));
            }

            if(cell.y != board.length - 1 && valueNode[cell.y + 1][cell.x] == -1 && board[y][x] == board[cell.y + 1][cell.x]) {
            	valueNode[cell.y + 1][cell.x] = numberNode;
            	queue.offer(new Cell(x, y + 1));
            }

            if(cell.x != 0 && valueNode[cell.y][cell.x - 1] == -1 && board[y][x] == board[cell.y][cell.x - 1]) {
            	valueNode[cell.y][cell.x - 1] = numberNode;
                queue.offer(new Cell(x - 1, y));
            }

            if(cell.x != board[0].length - 1 && valueNode[cell.y][cell.x + 1] == -1 && board[y][x] == board[cell.y][cell.x + 1]) {
            	valueNode[cell.y][cell.x + 1] = numberNode;
                queue.offer(new Cell(x + 1, y));
            }
        }
    }

}
