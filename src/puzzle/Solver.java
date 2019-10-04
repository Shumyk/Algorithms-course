package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

public class Solver {

    private boolean isSolvable;
    private SearchNode solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Argument is null in Solver");

        solutionNode = null;
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial, 0, null));

        while (true) {
            SearchNode currentNode = minPQ.delMin();
            Board currentBoard = currentNode.getBoard();

            if (currentBoard.isGoal()) {
                isSolvable = true;
                solutionNode = currentNode;
                break;
            }
            if (currentBoard.hamming() == 2 && currentBoard.twin().isGoal()) {
                isSolvable = false;
                break;
            }

            int moves = currentNode.getMoves();
            Board prevBoard = moves > 0 ? currentNode.prev().getBoard() : null;
            for (Board nextBoard : currentBoard.neighbors()) {
                if (nextBoard.equals(prevBoard))
                    continue;
                minPQ.insert(new SearchNode(nextBoard, moves + 1, currentNode));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return isSolvable() ? solutionNode.getMoves() : -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable)
            return null;
        Deque<Board> solution = new LinkedList<>();
        SearchNode node = solutionNode;
        while (node != null) {
            solution.addFirst(node.getBoard());
            node = node.prev();
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode implements Comparable<SearchNode> {

        private final SearchNode prev;
        private final Board board;
        private final int moves;

        SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        public int priority() {
            return board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode prev() {
            return prev;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority() - that.priority();
        }
    }

}