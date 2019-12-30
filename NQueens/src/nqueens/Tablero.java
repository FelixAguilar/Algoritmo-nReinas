package nqueens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Felix
 */
public class Tablero extends JPanel {

    private Casilla[][] casillas;

    public Tablero(int n) {
        this.casillas = new Casilla[n][n];
        boolean black = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.casillas[i][j] = new Casilla(black);
                black = !black;
            }
            black = !black;
        }
    }
    
    public void tamaño(int n){
        this.casillas = new Casilla[n][n];
        boolean black = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.casillas[i][j] = new Casilla(black);
                black = !black;
            }
            black = !black;
        }
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

    public void setCasillas(Casilla[][] casillas) {
        this.casillas = casillas;
    }
    
    public void setCasilla(int x, int y, boolean val){
        this.casillas[x][y].setQueen(val);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                if (casillas[i][j].isBlack()) {
                    g2d.setColor(Color.BLACK);
                }else{
                    g2d.setColor(Color.WHITE);
                }
                Rectangle2D rectangle = new Rectangle2D.Double(40 * j,
                        40 * i, 40,40);
                g2d.draw(rectangle);
                g2d.fill(rectangle);
                
                if(casillas[i][j].isQueen()){
                    try {
                        g2d.drawImage(ImageIO.read(new File("roja.png")),40*j, 40*i, null);
                    } catch (IOException ex) {
                        
                    }
                }
            }
        }
        repaint();
    }
    
}
