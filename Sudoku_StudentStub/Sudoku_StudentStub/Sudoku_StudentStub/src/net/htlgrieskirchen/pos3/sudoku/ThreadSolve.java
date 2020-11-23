package net.htlgrieskirchen.pos3.sudoku;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class ThreadSolve implements Callable<Boolean> {
    int rawSudoku[][];

    public ThreadSolve(int rawSudoku[][]) {
        this.rawSudoku = rawSudoku;

    }

    @Override
    public Boolean call() throws Exception {
        for (int i = 0; i < rawSudoku.length; i++) {
            for (int j = 0; j < rawSudoku.length; j++) {
                if (rawSudoku[i][j] == 0) {
                    for (int k = 1; k < 10; k++) {
                        if (checknumbers(rawSudoku, i, j, k)) {
                            rawSudoku[i][j] = k;
                            if (call()) {
                                return true;
                            } else {
                                rawSudoku[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }

        }
        return true;
    }
    public boolean checknumbers( int[][] soduko, int spalte, int zeile , int number) {
        for (int q = 0; q < soduko.length; q++) {
            if (soduko[zeile][q] != number) {
                return false;
            } else if(soduko[zeile][q] == number){
                return true;
            }
            if (soduko[q][spalte] != number) {
                return false;
            } else {
                return true;
            }
        }
        for (int BlockZeile = 0; BlockZeile < 3; BlockZeile++) {
            for (int blockSpalte = 0; blockSpalte < 3; blockSpalte++) {
                for (int pzeile = blockSpalte * 3; pzeile < blockSpalte * 3 + 3; pzeile++) {
                    for (int pspalte = BlockZeile * 3; pspalte < BlockZeile * 3 + 3; pspalte++) {
                        if(soduko[pzeile][pspalte] != number){
                            return false;
                        }else{
                            return true;
                        }

                    }
                }
            }
        }
        return true;
    }
}