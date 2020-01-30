package com.company;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean [] openNodes;
    final private WeightedQuickUnionUF grid;
    final private WeightedQuickUnionUF full;
    final private int n;
    final private int top;
    final private int bottom;


    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N must be greater than zero");
        }

        grid = new WeightedQuickUnionUF(N * N + 2);
        full = new WeightedQuickUnionUF(N * N + 1);

        this.n = N;

        top = getSingleArrayIndx(N, N) + 1;
        bottom = getSingleArrayIndx(N, N) + 2;

        openNodes = new boolean[N * N];
    }

    private int getSingleArrayIndx(int i, int j) {
        outOfRangeCheck(i, j);

        return (n * (i - 1) + j) - 1;
    }

    private boolean inRange(int i, int j) {
        if (i > 0 && i <= n && j > 0 && j <=n){
            return true;
        } else {
            return false;
        }
    }

    private void outOfRangeCheck(int i, int j) {
        if (!inRange(i, j)){
            throw new IndexOutOfBoundsException("Values are out of bounds");
        }
    }

    public void open(int i, int j) {
        outOfRangeCheck(i, j);

        if (isOpen(i, j)) {
            return;
        }

        int idx = getSingleArrayIndx(i, j);
        openNodes[idx] = true;


        if (i == 1) {
            grid.union(top, idx);
            full.union(top, idx);
        }

        if (i == n) {
            grid.union(bottom, idx);
        }

        if (inRange(i - 1, j) && isOpen(i - 1, j)) {
            grid.union(getSingleArrayIndx(i - 1, j), idx);
            full.union(getSingleArrayIndx(i - 1, j), idx);
        }

        if (inRange(i + 1, j) && isOpen(i + 1, j)) {
            grid.union(getSingleArrayIndx(i + 1, j), idx);
            full.union(getSingleArrayIndx(i + 1, j), idx);

        }

        if (inRange(i, j + 1) && isOpen(i, j + 1)) {
            grid.union(getSingleArrayIndx(i, j + 1), idx);
            full.union(getSingleArrayIndx(i, j + 1), idx);
        }

        if (inRange(i, j - 1) && isOpen(i, j - 1)) {
            grid.union(getSingleArrayIndx(i, j - 1), idx);
            full.union(getSingleArrayIndx(i, j - 1), idx);
        }
    }

    public boolean isOpen(int i, int j) {
        outOfRangeCheck(i, j);

        return openNodes[getSingleArrayIndx(i, j)];
    }

    public boolean isFull(int i, int j) {
        int idx = getSingleArrayIndx(i, j);
        return full.connected(idx, top);
    }

    public boolean percolates() {
        return grid.connected(top, bottom);
    }

    public int numberOfOpenSites() {
        int open_site = 0;

        for(int row = 1; row <= n; row++){
            for(int col = 1; col <= n; col++) {
                if (isOpen(row, col)) {
                    open_site++;
                }
            }
        }
        return open_site;
    }
}
