package ai;

import java.util.Scanner;
import java.util.Stack;

public class Solver {
    private final Stack<Board> solutionBoards ;
    private boolean isSolvable;
    private int moves;
    Solver(Board initial) {
        if(initial == null) throw new NullPointerException();
        isSolvable = false;
        solutionBoards = new Stack<>();
        MinPQ<SearchNode> searchNodes = new MinPQ<>();
        searchNodes.insert(new SearchNode(initial,null));
        searchNodes.insert(new SearchNode(initial.twin(),null));
        while(!searchNodes.min().board.isGoal()) {
            SearchNode searchNode = searchNodes.delMin();
            for(Board board: searchNode.board.neighbours())
                if(searchNode.prevNode == null || searchNode.prevNode != null && !searchNode.prevNode.board.equals(board))
                    searchNodes.insert(new SearchNode(board,searchNode));
        }

        SearchNode current = searchNodes.min();
        while (current.prevNode!=null) {
            solutionBoards.push(current.board);
            current= current.prevNode;
        }
        solutionBoards.push(current.board);
        if(current.board.equals(initial)) isSolvable = true;

    }


    public boolean isSolvable() {
        return isSolvable;
    }

    public Iterable<Board> solution() {
        if(isSolvable) return solutionBoards;
        return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        final Board board;
        private final SearchNode prevNode;
        private int moves;
        private int manhattan;

        SearchNode(Board board,SearchNode prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            manhattan = board.manhattan();
            if(prevNode != null) moves = prevNode.moves +1;
            else moves =0;
        }

        @Override
        public int compareTo(SearchNode that) {
            int priorityDiff = (this.manhattan + this.moves) - (that.manhattan+that.moves);
            return priorityDiff == 0 ? this.manhattan-that.manhattan: priorityDiff;
        }
    }

    public static  void main(String[] args) {
        Scanner input =  new Scanner(System.in);
        int n = input.nextInt();
        int[][] bInput = new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                bInput[i][j] = input.nextInt();
        Solver sol = new Solver(new Board(bInput));

        if(!sol.isSolvable){
            System.out.println("Not solvable");
        } else {
            int moves= -1;
            for(Board b: sol.solution()) {
                System.out.println(b);
                moves++;
            }
            System.out.print("minimum number of moves = " + moves);
        }

    }
}
