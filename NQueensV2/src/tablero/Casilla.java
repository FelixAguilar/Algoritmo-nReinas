package tablero;

/**
 * Clase Casilla. Esta contiene los atributos y metodos que definen a una 
 * casilla.
 * 
 * @author Felix
 */
public class Casilla {
    
    // Atributos de la casilla.
    private boolean queen;
    private boolean black;

    // Constructor. Reguiere la variable black, se inicializa reina en false.
    public Casilla(boolean black) {
        this.queen = false;
        this.black = black;
    }

    /**
     * Metodo para obtener si contiene una reina.
     * 
     * @return 
     */
    public boolean isQueen() {
        return queen;
    }

    /**
     * Metodo para actallizar si contiene una reina.
     * 
     * @param queen 
     */
    public void setQueen(boolean queen) {
        this.queen = queen;
    }

    /**
     * Metodo para saber si la casilla ha de ser negra o no.
     * 
     * @return 
     */
    public boolean isBlack() {
        return black;
    }

    /**
     * Metodo para cambiar el valor de la variable black.
     * 
     * @param black 
     */
    public void setBlack(boolean black) {
        this.black = black;
    }
}
