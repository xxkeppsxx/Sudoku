package net.htlgrieskirchen.pos3.sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

/* Please enter here an answer to task four between the tags:
 * <answerTask4>
 *    Hier sollte die Antwort auf die Aufgabe 4 stehen.
 * </answerTask4>
 */
public class SudokuSolver implements ISodukoSolver {
    int[][] arr = new int[9][9];

    public SudokuSolver() {
        //initialize if necessary
    }


    @Override
    public final int[][] readSudoku(File file) {

        try {
            arr = Files.lines(file.toPath())
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
                    .toArray(int[][]::new);
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
            for (int spalte = 0; spalte < rawSudoku.length; spalte++) {
                if (set.add(rawSudoku[zeile][spalte]) == false) {
                    right = false;
                }

            }
            set.clear();
        }

        for (int spalte = 0; spalte < rawSudoku.length; spalte++) {
            for (int zeile = 0; zeile < rawSudoku.length; zeile++) {
                if (set.add(rawSudoku[zeile][spalte]) == false) {
                    right = false;
                }
                set.clear();
            }
        }
        for (int BlockZeile = 0; BlockZeile < 3; BlockZeile++) {
            for (int blockSpalte = 0; blockSpalte < 3; blockSpalte++) {
                for (int zeile = blockSpalte * 3; zeile < blockSpalte * 3 + 3; zeile++) {
                    for (int spalte = BlockZeile * 3; spalte < BlockZeile * 3 + 3; spalte++) {
                        if (set.add(rawSudoku[zeile][spalte]) == false) {
                            right = false;
                        }
                    }
                }
                set.clear();
            }
        }
        return right;
    }
    public boolean checkSudokuParallel(int[][] rawSudoku) throws InterruptedException, ExecutionException {
        ExecutorService ex = Executors.newCachedThreadPool();

        List<Callable<Boolean>> list = new ArrayList<>();
        list.add(new WorkerColumn(rawSudoku));
        list.add(new WorkerRow(rawSudoku));
        list.add(new Worker3x3(rawSudoku));

        List<Future<Boolean>> future = ex.invokeAll(list);
        ex.shutdown();
        List<Boolean> booleans = new ArrayList<>();
        for (Future<Boolean> f : future) {
            booleans.add(f.get());
        }
        for (boolean b : booleans) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {
        solver(rawSudoku);
        return rawSudoku;
    }
    public boolean solver(int[][] rawSudoku) {
        for (int i = 0; i < rawSudoku.length; i++) {
            for (int j = 0; j < rawSudoku.length; j++) {
                if (rawSudoku[i][j] == 0) {
                    for (int u = 1; u< 10; u++) {
                        if (checknumbers(rawSudoku, i, j, u)) {
                            rawSudoku[i][j] = u;
                            if (solver(rawSudoku)) {
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


    @Override
    public int [][] solveSudokuParallel(int[][] rawSudoku) {
        ExecutorService ex = Executors.newCachedThreadPool();

        List<Future> list = new ArrayList<>();

        list.add(ex.submit(new ThreadSolve(rawSudoku)));
        try {
            if((boolean) list.get(0).get()){
                return rawSudoku;
            }
        } catch (InterruptedException e) {


        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rawSudoku;
    }

    // add helper methods here if necessary

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
    public long benchmark(int[][] rawSudoku) {
        long start = System.currentTimeMillis();
        for (int i = 0; i <= 10; i++) {
            readSudoku(new File("1_sudoku_level1.csv"));
            checkSudoku(rawSudoku);
            solveSudoku(rawSudoku);
        }
        long end = System.currentTimeMillis();
        return (end - start) / 10;
    }


    public long benchmarkParallel(int[][] rawSudoku) {
        long start = System.currentTimeMillis();
        for (int i = 0; i <= 10; i++) {
            readSudoku(new File("1_sudoku_level1.csv"));
            checkSudoku(rawSudoku);
            solveSudokuParallel(rawSudoku);
        }
        long end = System.currentTimeMillis();
        return (end - start) / 10;
    }
}

