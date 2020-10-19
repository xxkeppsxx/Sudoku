package net.htlgrieskirchen.pos3.sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* Please enter here an answer to task four between the tags:
 * <answerTask4>
 *    Hier sollte die Antwort auf die Aufgabe 4 stehen.
 * </answerTask4>
 */
public class SudokuSolver implements ISodukoSolver {
    int [][] arr = new int[9][9];
    public SudokuSolver() {
        //initialize if necessary
    }


    @Override
    public final int[][] readSudoku(File file) {

        try {
            arr= Files.lines(file.toPath())
                                    .map(s -> s.split(";"))
                                    .map(a -> new int[]{Integer.parseInt(a[0]),
                                    Integer.parseInt(a[1]),
                                    Integer.parseInt(a[2]),
                                    Integer.parseInt(a[3]),
                                    Integer.parseInt(a[4]),
                                    Integer.parseInt(a[5]),
                                    Integer.parseInt(a[6]),
                                    Integer.parseInt(a[7]),
                                    Integer.parseInt(a[8])})
                                    .toArray(int[][] ::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public boolean checkSudoku(int[][] rawSudoku) {
        boolean right = true;
        Set<Integer> set = new HashSet<>();
        for (int zeile = 0; zeile < rawSudoku.length; zeile++) {
            for (int spalte= 0; spalte< rawSudoku.length; spalte++) {
               if( set.add(rawSudoku[zeile][spalte]) ==false){
                   right= false;
               }

            }
            set.clear();
        }

        for (int spalte = 0; spalte <rawSudoku.length ; spalte++) {
            for (int zeile = 0; zeile < rawSudoku.length; zeile++) {
                if (set.add(rawSudoku[zeile][spalte]) == false) {
                    right= false;
                }
                set.clear();
            }
        }
        for (int BlockZeile = 0; BlockZeile <3 ; BlockZeile++){
            for (int blockSpalte = 0; blockSpalte < 3; blockSpalte++) {
                for (int zeile = blockSpalte * 3; zeile < blockSpalte * 3 + 3; zeile++) {
                    for (int spalte = BlockZeile*3; spalte < BlockZeile*3+3; spalte++) {
                        if(set.add(rawSudoku[zeile][spalte])== false){
                            right= false;
                        }
                    }
                }
                set.clear();
            }
        }
        return right;
    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }
    
    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }

    // add helper methods here if necessary
}
