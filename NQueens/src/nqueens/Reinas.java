package nqueens;

import java.util.ArrayList;

/**
 *
 * @author Felix
 */
public class Reinas {

    private int n;
    private int x;
    private int y;
    private ArrayList<int[]> soluciones;
    private int[][] solucion;

    public Reinas(int n, int x, int y) {
        this.n = n;
        this.x = x - 1;
        this.y = y - 1;
        this.soluciones = new ArrayList();
        this.solucion = new int[n][n];
    }

    public boolean nReinas() {
        
        if (x != -1 && y != -1) {
            solucion[y][x] = 1;
            if (y == 0) {
                algoritmo(1);
            } else {
                algoritmo(0);
            }
        } else if (x == -1 && y == -1) {
            algoritmo(0);
        }
        return false;
    }

    private void algoritmo(int col) {

        for (int row = 0; row < n; row++) {

            if (esPosible(col, row)) {

                solucion[col][row] = 1;

                if (col == n - 1) {

                    int[] aux = new int[n];
                    for (int i = 0; i < aux.length; i++) {
                        int j = 0;
                        while (solucion[i][j] == 0) {
                            j++;
                        }
                        aux[i] = j;
                    }
                    soluciones.add(aux);

                } else if (col + 1 == y) {

                    if (y == n - 1) {

                        int[] aux = new int[n];
                        for (int i = 0; i < aux.length; i++) {
                            int j = 0;
                            while (solucion[i][j] == 0) {
                                j++;
                            }
                            aux[i] = j;
                        }
                        soluciones.add(aux);

                    } else {
                        algoritmo(col + 2);
                    }
                } else {
                    algoritmo(col + 1);
                }

                solucion[col][row] = 0;
            }
        }
    }

    private boolean esPosible(int col, int row) {

        for (int i = 0; i < n; i++) {
            if (solucion[i][row] == 1) {
                return false;
            }
        }
        int i = col;
        int j = row;
        while (i >= 0 && j >= 0) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i--;
            j--;
        }
        i = col;
        j = row;
        while (i < n && j < n) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i++;
            j++;
        }
        i = col;
        j = row;
        while (i >= 0 && j < n) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i--;
            j++;
        }
        i = col;
        j = row;
        while (i < n && j >= 0) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x - 1;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y - 1;
    }

    public ArrayList getSoluciones() {
        return soluciones;
    }

    public void setSoluciones(ArrayList soluciones) {
        this.soluciones = soluciones;
    }

    public int[][] getSolucion() {
        return solucion;
    }

    public void setSolucion(int[][] solucion) {
        this.solucion = solucion;
    }
}
