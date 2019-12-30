package nqueens;

import java.awt.Dimension;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Felix Aguilar Ferrer
 */
public class Ventana extends JFrame {

    //Constantes del menú.
    private final String TITULO = "Algoritmo de las n reinas";
    private final int IMAGEN = 40;

    private JMenu informacion;

    private Reinas r = new Reinas(8, 0, 0);
    private Tablero t = new Tablero(r.getN());
    private int index = 1;

    private final Dimension dimension = new Dimension((r.getN() * IMAGEN) + 7,
            (r.getN() * IMAGEN) + 53);

    public Ventana() {
        this.setTitle(TITULO);
        this.setDefaultCloseOperation(Ventana.EXIT_ON_CLOSE);
        this.setSize(dimension);
        //this.setMinimumSize(dimension);
        this.initContents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void initContents() {

        r.nReinas();
        solucion(index);

        JMenuBar menu = new JMenuBar();

        JMenu algoritmo = new JMenu("Algoritmo");

        JMenuItem tamaño = new JMenuItem("Establecer tamaño");
        tamaño.addActionListener((event) -> accionTamaño());

        JMenuItem reina = new JMenuItem("Establecer reina");
        reina.addActionListener((event) -> accionReina());

        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener((event) -> System.exit(0));

        algoritmo.add(tamaño);
        algoritmo.add(reina);
        algoritmo.addSeparator();
        algoritmo.add(salir);
        menu.add(algoritmo);

        JMenu mostrar = new JMenu("Mostrar");

        JMenuItem siguiente = new JMenuItem("Siguiente");
        siguiente.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));
        siguiente.addActionListener((event) -> accionSiguiente());

        JMenuItem anterior = new JMenuItem("Anterior");
        anterior.setAccelerator(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK));
        anterior.addActionListener((event) -> accionAnterior());

        mostrar.add(siguiente);
        mostrar.add(anterior);
        menu.add(mostrar);

        informacion = new JMenu("Informacion");
        actualizar();
        menu.add(informacion);

        setJMenuBar(menu);

        this.add(t);

    }

    private void actualizar() {

        informacion.removeAll();

        JMenuItem solucion = new JMenuItem("Soluciones " + index + " de " + r.getSoluciones().size());
        JMenuItem n = new JMenuItem("tamaño tablero: " + r.getN());
        informacion.add(solucion);
        informacion.add(n);

        if (r.getX() != -1 || r.getY() != -1) {
            JMenuItem x = new JMenuItem("Coordenada X: " + (r.getX() + 1));
            JMenuItem y = new JMenuItem("Coordenada Y: " + (r.getY() + 1));

            informacion.add(x);
            informacion.add(y);
        }

    }

    private void accionSiguiente() {
        if (!r.getSoluciones().isEmpty()) {
            if (r.getSoluciones().size() != index) {
                index++;
            } else {
                index = 1;
            }
            solucion(index);
            actualizar();
        }
    }

    private void accionAnterior() {
        if (!r.getSoluciones().isEmpty()) {
            if (index != 1) {
                index--;
            } else {
                index = r.getSoluciones().size();
            }
            solucion(index);
            actualizar();
        }
    }

    private void solucion(int i) {

        for (int j = 0; j < r.getN(); j++) {
            for (int k = 0; k < r.getN(); k++) {
                t.setCasilla(j, k, false);
            }
        }

        if (!r.getSoluciones().isEmpty()) {
            int[] a = (int[]) r.getSoluciones().get(i - 1);

            for (int j = 0; j < a.length; j++) {
                t.setCasilla(j, a[j], true);
            }
        }

    }

    private void accionTamaño() {

        try {
            int i = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Introduce nuevo tamaño", "0"));
            if (i != 0) {
                r = new Reinas(i, 0, 0);

                this.remove(t);
                t = new Tablero(i);
                dimension.height = i * IMAGEN + 53;
                dimension.width = i * IMAGEN + 7;
                this.setSize(dimension);
                this.add(t);
                r.nReinas();
                index = r.getSoluciones().size();
                solucion(index);
                actualizar();
            }
        } catch (NumberFormatException e) {
        }
    }

    private void accionReina() {
        try {
            String[] lista = new String[r.getN() + 1];
            for (int i = 0; i < lista.length; i++) {
                lista[i] = Integer.toString(i);
            }
            int n = r.getN();
            int x = Integer.parseInt(introducir(lista, "Establecer reina", "Coordenada X"));
            int y = Integer.parseInt(introducir(lista, "Establecer reina", "Coordenada Y"));

            r = new Reinas(n,x,y);
            r.nReinas();
            index = r.getSoluciones().size();
            solucion(index);
            actualizar();
        } catch (NumberFormatException e) {
        }
    }

    private String introducir(String[] datos, String titulo, String texto) {
        String s = (String) JOptionPane.showInputDialog(
                null, texto, titulo,
                JOptionPane.PLAIN_MESSAGE, null, datos, datos[0]);
        return s;
    }

    public static void main(String[] args) {
        Ventana v = new Ventana();
        v.setVisible(true);
    }
}
