package net.htlgrieskirchen.pos3.sudoku;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class Worker3x3 implements Callable<Boolean> {
    int[][] soduko;
    Set<Integer> set = new HashSet<>();

    public Worker3x3(int[][] rawSudoku) {
        this.soduko = rawSudoku;
    }

    @Override
    public Boolean call() throws Exception {
        boolean right = true;

        for (int BlockZeile = 0; BlockZeile < 3; BlockZeile++) {
            for (int blockSpalte = 0; blockSpalte < 3; blockSpalte++) {
                for (int zeile = blockSpalte * 3; zeile < blockSpalte * 3 + 3; zeile++) {
                    for (int spalte = BlockZeile * 3; spalte < BlockZeile * 3 + 3; spalte++) {
                        if (set.add(soduko[zeile][spalte]) == false) {
                            right = false;
                        }
                    }
                }
            }
        }
        return right;
    }
}