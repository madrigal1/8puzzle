package ai;

import java.util.Arrays;
import java.util.Stack;

public class Board {
    private int[][] board;
    private int n;
    Board(int[][] tiles) {
        this.board = copy(tiles);
        n=tiles.length;
    }

    private int[][] copy(int[][] arr) {
        int[][] clone  = new int[arr.length][arr.length];
        for(int i=0;i<arr.length;i++)
            for(int j=0;j<arr.length;j++)
                clone[i][j] = arr[i][j];
        return clone;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n+"\n");
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                s.append(String.format("%2d ",board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int hamming() {
        int hamming =0;
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                if(board[i][j] !=0 && j+i*n+1 !=board[i][j])
                    hamming ++;
        return hamming;
    }

    public int manhattan()  {
        int manhattan =0;
        for(int i =0;i<n;i++)
            for(int j=0;j<n;j++)
                if(board[i][j] == 0 && board[i][j] != j+i*n+1)
                    manhattan+=manhattanDistance(i,j,board[i][j]);
        return manhattan;
    }

    private int manhattanDistance(int i,int j,int x) {
        int horizontal = Math.abs((x-1)%n-i);
        int vertical = Math.abs((x-1)/n-j);
        return horizontal+vertical;
    }

    public boolean isGoal() {
        return hamming()==0;
    }

    @Override
    public boolean equals(Object y) {
        if(y == this) return true;
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.board,that.board);
    }

    private int blankPosition() {
        for(int i =0;i<n;i++)
            for(int j=0;j<n;j++)
                if(board[i][j] == 0) return j+i*n;
        return -1;
    }

    private int[][] swap(int io,int jo, int in,int jn) {
        int[][] clone = copy(board);
        int temp = clone[io][jo];
        clone[io][jo] = clone[in][jn];
        clone[in][jn] = temp;
        return clone;
    }

    public Iterable<Board> neighbours() {
        Stack<Board> neighbors = new Stack<>();
        int position = blankPosition();
        int i = position/n;
        int j = position%n;
        if(i>0)
            neighbors.push(new Board(swap(i,j,i-1,j)));
        if(i<n-1)
            neighbors.push(new Board(swap(i,j,i+1,j)));
        if(j>0)
            neighbors.push(new Board(swap(i,j,i,j-1)));
        if(j<n-1)
            neighbors.push(new Board(swap(i,j,i,j+1)));
        return neighbors;
    }

    public Board twin() {
        int[][] clone =  copy(board);
        if(clone[0][0] != 0 && clone[0][1] != 0)
            return new Board(swap(0,0,0,1));
        else
            return new Board(swap(1,0,1,1));
    }

    public static  void main(String[] args) {
        Board board = new Board(new int[][]{{1,2,3},{4,0,6},{7,8,5}});
        System.out.println(board.toString());
        for(Board b:board.neighbours()) {
            System.out.println(b);
        }
    }
}
