package nReinas;

import java.util.ArrayList;

/**
 * Clase Reinas. Contiene el algoritmo de las nReinas y metodos para obtener
 * tanto sus resultados como informacion de este.
 *
 * @author Felix
 */
public class Reinas {

    // Atributos del algoritmo.
    private int n;
    private int x;
    private int y;
    private ArrayList<int[]> soluciones;
    private int[][] solucion;

    // Constructor.
    public Reinas(int n, int x, int y) {
        this.n = n;
        this.x = x - 1;
        this.y = y - 1;
        this.soluciones = new ArrayList();
        this.solucion = new int[n][n];
    }

    /**
     * Funicon inicializadora del algoritmo. Esta revisa como hay que comenzar
     * el algoritmo para evitar errores de ejecucion. Si se define una reina de
     * forma erronea, una de las coordenadas de esta no esta definida, no se
     * ejecuta el algoritmo.
     */
    public void nReinas() {

        /* Si se ha definido una reina fija entonces se añade esta a la matriz 
           solucion y se revisa si esta en la primera columna. si lo esta 
           entonces se ejecuta el algoritmo desde la segunda columna. */
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
    }

    /**
     * Metodo recursivo del algoritmo. Este va colocando las reinas en el
     * tablero solucion. Si se ha podido colocar una reina en el tablero, este
     * llama de forma recursiva a si mismo con la siguiente columna a tratar. Si
     * no se puede añadir una reina en la columna por la disposicion de las
     * reinas, este hace backtracking y retira la reina de la columna anterior y
     * busca una nueva posicion para esta. Una vez llegada a una solucion, este
     * añade la solucion a la lista de soluciones y procede a buscar una nueva
     * solucion del algoritmo.
     *
     * Si se ha definido una reina fija, cuando toque tratar la columna con esta
     * reina, el algorimto salta una columna de mas evitando modificar la
     * columna con esta reina. Si la reina esta en la primera columna el
     * algoritmo finalizara cuando este no encuentre una posicion valida para la
     * reina en la segunda columna. Si la reina se encuentra en al ultima fila
     * el algoritmo añadira la solucion a la lista cuando se haya añadido una
     * reina en la penultima fila.
     *
     * @param col
     */
    private boolean algoritmo(int col) {
        for (int row = 0; row < n; row++) {
            if (esPosible(col, row)) {

                // Añade la reina en la posicion indicada.
                solucion[col][row] = 1;

                // Si la columna es la ultima añade la solucion a la lista.
                if (col == n - 1) {

                    // Array donde se introducira la solucion.
                    int[] aux = new int[n];

                    // Se recorre las columnas para guardar la solucion.
                    for (int i = 0; i < aux.length; i++) {

                        // Busca la posicion de la reina en la columna.
                        int j = 0;
                        while (solucion[i][j] == 0) {
                            j++;
                        }

                        // Guarda la posicion en la columna de la reina.
                        aux[i] = j;
                    }

                    // Se añade el array a la lista de soluciones.
                    soluciones.add(aux);

                    if (x != -1 && y != -1) {
                        return true;
                    }
                } /* Si la siguiente columna contiene la reina definida por el 
                   usuario. */ else if (col + 1 == y) {

                    /* Si la reina se encuentra en la ultima posicion entonces 
                       guarda la solucion en la lista. */
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

                        if (x == -1 && y == -1) {
                            return true;
                        }
                    } /* Si no procesa la siguiente columna despues de la definida
                       por el usuario. */ else {
                        if (algoritmo(col + 2)) {
                            return true;
                        }
                    }
                } // Sino, procesa la siguiente columna.
                else {
                    if (algoritmo(col + 1)) {
                        return true;
                    }
                }

                /* Backtraking, si no se ha podido colocar una reina en la 
                   siguiente columna. */
                solucion[col][row] = 0;
            }
        }
        return false;
    }

    /**
     * Metodo para la comprobacion de colision entre dos reinas. Este comprueba
     * si en la posicion dada por los parametros col y row interfiere con otra
     * reina en el tablero. Si es asi devuelve false. Si no interfiere devuelve
     * true.
     *
     * @param col
     * @param row
     * @return
     */
    private boolean esPosible(int col, int row) {

        // Revisa la fila actual.
        for (int i = 0; i < n; i++) {
            if (solucion[i][row] == 1) {
                return false;
            }
        }

        // Revisa la diagonal superior - izquierda.
        int i = col;
        int j = row;
        while (i >= 0 && j >= 0) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i--;
            j--;
        }

        // Revisa la diagonal inferior - izquierda.
        i = col;
        j = row;
        while (i < n && j >= 0) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i++;
            j--;
        }

        // Revisa la diagonal inferior - derecha.
        i = col;
        j = row;
        while (i < n && j < n) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i++;
            j++;
        }

        // Revisa la diagonal superior - derecha.
        i = col;
        j = row;
        while (i >= 0 && j < n) {
            if (solucion[i][j] == 1) {
                return false;
            }
            i--;
            j++;
        }

        // Si no se ha dado ninguna coincidencia devuevle true.
        return true;
    }

    /**
     * Metodo para obtener el tamaño del tablero.
     *
     * @return
     */
    public int getN() {
        return n;
    }

    /**
     * Metodo para actualizar el tamaño del tablero.
     *
     * @param n
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * Metodo para obtener la coordenada x de la reina fijada por el usuario.
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Metodo para actualizar la coordenada x de la reina fijada por el usuario.
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x - 1;
    }

    /**
     * Metodo para obtener la coordenada y de la reina fijada por el usuario.
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Metodo para actualizar la coordenada y de la reina fijada por el usuario.
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y - 1;
    }

    /**
     * Metodo para obtener la lista de las soluciones del algoritmo.
     *
     * @return
     */
    public ArrayList getSoluciones() {
        return soluciones;
    }

    /**
     * Metodo para actualizar la lista de las soluciones del algoritmo.
     *
     * @param soluciones
     */
    public void setSoluciones(ArrayList soluciones) {
        this.soluciones = soluciones;
    }

    /**
     * Metodo para obtener la solucion mas reciente del algoritmo.
     *
     * @return
     */
    public int[][] getSolucion() {
        return solucion;
    }

    /**
     * Metodo para actualizar la solucion mas reciente del algoritmo.
     *
     * @param solucion
     */
    public void setSolucion(int[][] solucion) {
        this.solucion = solucion;
    }
}
