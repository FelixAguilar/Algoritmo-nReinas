package nReinas;

import tablero.Tablero;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

/**
 * Interfaz grafica del programa. Desde aqui se llama al codigo que ejecuta el
 * algoritmo de las nReinas.
 *
 * @author Felix Aguilar Ferrer, Ionut Florin Huzum, Jaume Pardo Socias
 */
public class Ventana extends JFrame {

    // Valores iniciales del programa.
    private final int IN = 8;
    private final int IX = 0;
    private final int IY = 0;
    private int indice = 1;

    // Constantes graficas.
    private final int IMAGEN = 40;
    private final int EX = 7;
    private final int EY = 53;

    // Componente del menu para mostrar la informacion.
    private JMenu informacion;

    // Objetos para el algoritmo y para el tablero.
    private Reinas r = new Reinas(IN, IX, IY);
    private Tablero t = new Tablero(IN);

    // Constructor de la ventana.
    public Ventana() {

        // Ejecucion del algoritmo.
        r.nReinas();

        // Mostrar la solucion en pantalla.
        solucion(indice);

        // Dimension inicial y minima de la pantalla.
        Dimension d = new Dimension((IN * IMAGEN) + EX, (IN * IMAGEN) + EY);

        // Montaje de la interfaz grafica y los eventos.
        this.setTitle("Algoritmo de las n reinas");
        this.setDefaultCloseOperation(Ventana.EXIT_ON_CLOSE);
        this.setSize(d);
        this.setMinimumSize(d);
        this.initContents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    /**
     * Funcion para añadir los componentes de la interfaz grafica. Aqui se monta
     * la barra del menu y el tablero a mostrar por pantalla.
     */
    private void initContents() {

        // Barra de menus.
        JMenuBar menu = new JMenuBar();

        // Menu de cambiar el algoritmo o salir.
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

        // Menu de mostrar soluciones.
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

        // Menu de mostrar informacion del estado del programa.
        informacion = new JMenu("Informacion");
        actualizar();
        menu.add(informacion);

        // Añade la barra de menus y el tablero al panel.
        setJMenuBar(menu);
        this.add(t);
    }

    /**
     * Funcion para actualizar el menu de informacion. En este se elimina el
     * contenido del menu y se vuelve a añadir actualizado.
     */
    private void actualizar() {

        // Eliminar los componentes.
        informacion.removeAll();

        // Añade el idice de las soluciones y el numero de estas.
        JMenuItem solucion = new JMenuItem("Soluciones " + indice + " de "
                + r.getSoluciones().size());
        informacion.add(solucion);

        // Muestra el numero de casillas en una linea del tablero.
        JMenuItem tamaño = new JMenuItem("tamaño tablero: " + r.getN());
        informacion.add(tamaño);

        // Si hay definida una reina, se muestra las componentes de esta (x,y).
        if (r.getX() != -1 || r.getY() != -1) {
            JMenuItem x = new JMenuItem("Coordenada X: " + (r.getX() + 1));
            informacion.add(x);
            JMenuItem y = new JMenuItem("Coordenada Y: " + (r.getY() + 1));
            informacion.add(y);
        }
    }

    /**
     * Funcion para mostrar la siguiente solucion en el tablero. Si no hay
     * soluciones entonces no permite moverse. Si llega a la ultima solucion
     * vuelve a la primera.
     */
    private void accionSiguiente() {

        if (!r.getSoluciones().isEmpty()) {
            if (r.getSoluciones().size() != indice) {
                indice++;
            } else {
                indice = 1;
            }

            // Muestra y actualiza el tablero y la informacion del menu.
            solucion(indice);
            actualizar();
        }
    }

    /**
     * Funcion para mostrar la anterior solucion en el tablero. Si no hay
     * soluciones entonces no permite moverse. Si llega a la primera solucion
     * vuelve a la ultima.
     */
    private void accionAnterior() {
        if (!r.getSoluciones().isEmpty()) {
            if (indice != 1) {
                indice--;
            } else {
                indice = r.getSoluciones().size();
            }

            // Mustra y actualiza el tablero y la informacion del menu.
            solucion(indice);
            actualizar();
        }
    }

    /**
     * Funcion que actualiza el tablero con la solucion indicada por parametro.
     * Este limpia el tablero de la solucion anteriormente mostrada y actualiza
     * con los nuevos valores.
     *
     * @param i
     */
    private void solucion(int i) {

        // Borrar la anterior solucion.
        for (int j = 0; j < r.getN(); j++) {
            for (int k = 0; k < r.getN(); k++) {
                t.setCasilla(j, k, false);
            }
        }

        // Introducir la nueva solucion si el array de soluciones no esta vacio.
        if (!r.getSoluciones().isEmpty()) {
            int[] a = (int[]) r.getSoluciones().get(i - 1);
            for (int j = 0; j < a.length; j++) {
                t.setCasilla(j, a[j], true);
            }
        }
    }

    /**
     * Funcion para cambiar el tamaño del tablero utilizado en el programa. En
     * este si no se introduce un tamaño valido provoca un mensaje de error. Si
     * es valido una vez cambiado el tamaño ejecuta el algoritmo.
     */
    private void accionTamaño() {

        try {

            // Introducción del nuevo tamaño del tablero.
            int n = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Introduce nuevo tamaño", "0"));

            // Comprueba que sea un valor entero positivo.
            if (n < 0) {
                throw new NumberFormatException();
            }

            /* Cambia el tamaño de la ventana, si es menor que el minimo 
               entonces se ignora este. */
            Dimension d = new Dimension((n * IMAGEN) + EX, (n * IMAGEN) + EY);
            this.setSize(d);

            // Elimina el tablero del frame y añade el nuevo tablero a utilizar.
            this.remove(t);
            t = new Tablero(n);
            this.add(t);

            // Actualiza el frame del programa.
            SwingUtilities.updateComponentTreeUI(this);

            // Sobrescribe el algoritmo y lo ejecuta.
            r = new Reinas(n, IX, IY);
            r.nReinas();

            // Muestra la ultima solucion y actualiza el menu informacion.
            indice = r.getSoluciones().size();
            solucion(indice);
            actualizar();

        } catch (NumberFormatException e) {
        }
    }

    /**
     * Funcion que permite añadir una reina fija en el tablero. Si no se
     * introduce valores salta a error pero no da ningun mensaje.
     */
    private void accionReina() {

        try {

            // Genera la lista de posibles valores a elegir en el selector.
            String[] lista = new String[r.getN() + 1];
            for (int i = 0; i < lista.length; i++) {
                lista[i] = Integer.toString(i);
            }

            // Obtiene los valores a utilizar en el algoritmo.
            int n = r.getN();
            int x = Integer.parseInt(introducir(lista, "Establecer reina",
                    "Coordenada X"));
            int y = Integer.parseInt(introducir(lista, "Establecer reina",
                    "Coordenada Y"));

            // Genera el nuevo algoritmo y lo ejecuta.
            r = new Reinas(n, x, y);
            r.nReinas();

            // Muestra la ultima solucion y acutaliza el menu de informacion.
            indice = r.getSoluciones().size();
            solucion(indice);
            actualizar();

        } catch (NumberFormatException e) {
        }
    }

    /**
     * Funcion que genera una ventana emergente. En esta se muestra un selector
     * con los valores introduccidos por parametro. Tambien el titulo y el texto
     * del panel se pasan por parametro.
     *
     * @param datos
     * @param titulo
     * @param texto
     * @return
     */
    private String introducir(String[] datos, String titulo, String texto) {
        String s = (String) JOptionPane.showInputDialog(
                null, texto, titulo,
                JOptionPane.PLAIN_MESSAGE, null, datos, datos[0]);
        return s;
    }

    /**
     * Funcion principal del programa. Este inicia la ventana y la muestra.
     *
     * @param args
     */
    public static void main(String[] args) {
        Ventana v = new Ventana();
        v.setVisible(true);
    }
}
