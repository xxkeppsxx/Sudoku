package net.htlgrieskirchen.pos3.sudoku;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class WorkerRow implements Callable<Boolean> {
    int [][] soduko;

    public WorkerRow(int[][] rawSudoku) {
        this.soduko = rawSudoku;
    }

    @Override
    public Boolean call() throws Exception {
        boolean right = true;
        Set<Integer> set = new HashSet<>();
        for (int zeile = 0; zeile < soduko.length; zeile++) {
            for (int spalte = 0; spalte < soduko.length; spalte++) {
                if (set.add(soduko[zeile][spalte]) == false) {
                    right = false;
                }

            }

        }
        return right;
    }
}
