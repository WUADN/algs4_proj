/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int[][] grids;
    private int numberOfOpenSites;
    private boolean isPercolation;
    private int gridWith;
    private int[] dx = { -1, 0, 0, 1 };
    private int[] dy = { 0, 1, -1, 0 };

    // creates n-by-n grid, with all sites initially blocked
    /*
     * grids: -1 block, 0 open, 1 full;
     */
    public Percolation(int n) {
        gridWith = n;
        isPercolation = false;
        uf = new WeightedQuickUnionUF(n * n + 2); // two virtual point
        numberOfOpenSites = 0;
        grids = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grids[i][j] = -1;
            }
        }
    }

    private boolean ifInGrid(int row, int col) {
        if (row <= 0 || row > gridWith || col <= 0 || col > gridWith) {
            return false;
        }
        return true;
    }

    private int calIndex(int row, int col) {
        return (row - 1) * gridWith + col;
    }

    private void connectNeighbour(int row, int col) {
        int curIndex = calIndex(row, col);
        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];
            if (ifInGrid(newRow, newCol) && isOpen(newRow, newCol)) {
                uf.union(calIndex(newRow, newCol), curIndex);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!ifInGrid(row, col)) {
            throw new IllegalArgumentException("the given site (row, col) must in the n-by-n grid");
        }
        int curIndex = calIndex(row, col);
        if (grids[row - 1][col - 1] == -1) {
            grids[row - 1][col - 1] = 0;
            if (row == 1) {
                uf.union(curIndex, 0);
            }
            if (row == gridWith) {
                uf.union(curIndex, gridWith * gridWith + 1);
            }
            connectNeighbour(row, col);
            numberOfOpenSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!ifInGrid(row, col)) {
            throw new IllegalArgumentException("the given site (row, col) must in the n-by-n grid");
        }
        return grids[row - 1][col - 1] == 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!ifInGrid(row, col)) {
            throw new IllegalArgumentException("the given site (row, col) must in the n-by-n grid");
        }
        return uf.find(0) == uf.find(calIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(gridWith * gridWith + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        perc.open(1, 1);
        // System.out.println(perc.percolates());
        System.out.println(perc.isFull(1, 1));
    }
}
