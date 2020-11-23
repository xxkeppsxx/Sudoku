package net.htlgrieskirchen.pos3.sudoku;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class WorkerColumn implements Callable<Boolean> {
    int[][] soduko;

    public WorkerColumn(int[][] rawSudoku) {
        this.soduko = rawSudoku;
    }

    @Override
    public Boolean call() throws Exception {
        Set<Integer> set = new HashSet<>();
        boolean right = true;
        for (int spalte = 0; spalte < soduko.length; spalte++) {
            for (int zeile = 0; zeile < soduko.length; zeile++) {
                if (set.add(soduko[zeile][spalte]) == false) {
                    right = false;
                }

            }
        }
        return right;
    }
}
