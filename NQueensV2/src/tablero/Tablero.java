package tablero;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Clase Tablero. Esta contiene el metodo para dibujar el tablero como para 
 * añadir reinas a este.
 *
 * @author Felix
 */
public class Tablero extends JPanel {

    // Array bidimensional de casillas.
    private final Casilla[][] casillas;

    // Constructor del tablero.
    public Tablero(int n) {

        this.casillas = new Casilla[n][n];

        // Asigna el color de cada casilla de forma perinente.
        boolean black = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.casillas[i][j] = new Casilla(black);
                black = !black;
            }

            // si el numero de fila es par se cambia de color la linea.
            if (Math.floorMod(n, 2) == 0) {
                black = !black;
            }
        }
    }

    /**
     * Funcion para añadir o eliminar la reina del tablero. Se introduce por
     * parametro las coordenadas de esta y si hay o no reina.
     *
     * @param x
     * @param y
     * @param val
     */
    public void setCasilla(int x, int y, boolean val) {
        this.casillas[x][y].setQueen(val);
    }

    /**
     * Funcion para pintar el tablero en la ventana del programa.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Recorre el array de casillas pintando cada una de estas.
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {

                // Segun si ha de ser negro o no se cambia el color a utilizar.
                if (casillas[i][j].isBlack()) {
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(Color.WHITE);
                }

                // Dibuja el rectangulo del color adecuado donde pertoca.
                Rectangle2D rectangle = new Rectangle2D.Double(40 * j,
                        40 * i, 40, 40);
                g2d.draw(rectangle);
                g2d.fill(rectangle);

                // Si ha de contener una renia, esta se añade en la casilla.
                if (casillas[i][j].isQueen()) {
                    try {
                        g2d.drawImage(ImageIO.read(new File("roja.png")),
                                40 * j, 40 * i, null);
                    } catch (IOException ex) {
                        Logger.getLogger(Tablero.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
            }
        }
        repaint();
    }
}
