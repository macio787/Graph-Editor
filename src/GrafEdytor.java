import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GrafEdytor extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1378717157956774054L;

    private static final String AUTOR = "Autor: Maciej Borowski\n  Data: grudzień 2019";
    private static final String TYTUL = "Prosty edytor grafów";

    private static final String INSTRUKCJA =
            "                  O P I S   P R O G R A M U \n\n" +
                    "Aktywna klawisze:\n" +
                    "   strzałki ==> przesuwanie wszystkich kół\n" +
                    "   SHIFT + strzałki ==> szybkie przesuwanie wszystkich kół\n\n" +
                    "ponadto gdy kursor wskazuje koło:\n" +
                    "   DEL   ==> kasowanie koła\n" +
                    "   +, -   ==> powiększanie, pomniejszanie koła\n" +
                    "   r,g,b ==> zmiana koloru koła\n\n" +
                    "Operacje myszka:\n" +
                    "   przeciąganie ==> przesuwanie wszystkich kół\n" +
                    "   PPM ==> tworzenie nowego koła w niejscu kursora\n" +
                    "ponadto gdy kursor wskazuje koło:\n" +
                    "   przeciąganie ==> przesuwanie koła\n" +
                    "   PPM ==> zmiana koloru koła\n" +
                    "                   lub usuwanie koła\n";


    public static void main(String[] args) {
        new GrafEdytor();
    }


    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuGraf = new JMenu("Graf");
    private JMenu menuPomoc = new JMenu("Pomoc");
    private JMenuItem menuNowy = new JMenuItem("Nowy", KeyEvent.VK_N);
    private JMenuItem menuPokażPrzykład = new JMenuItem("Przykład", KeyEvent.VK_P);
    private JMenuItem menuWyjdź = new JMenuItem("Wyjdź", KeyEvent.VK_W);
    private JMenuItem menuListaWierzchołków = new JMenuItem("Lista wierzchołków", KeyEvent.VK_V);
    private JMenuItem menuListaKrawedzi = new JMenuItem("Lista krawędzi", KeyEvent.VK_K);
    private JMenuItem menuAutor = new JMenuItem("Autor", KeyEvent.VK_A);
    private JMenuItem menuInstrukcja = new JMenuItem("Instrukcja", KeyEvent.VK_I);

    private GrafPanel panel = new GrafPanel();

    public GrafEdytor() {
        super(TYTUL);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);
        setContentPane(panel);
        createMenu();
        showBuildingExample();
        setVisible(true);
        /*panel.getGraf().getListaSasiedztwa();
        Wierzcholek [] t = panel.graf.getWierzcholki();
        int i = 0;
        for (Wierzcholek w : t)
        {
            System.out.println(i + " " + t[i]);
            i++;
        }*/
    }

    private void pokazListeWierzcholkow(Graf graf) {
        Wierzcholek t[] = graf.getWierzcholki();
        int i = 0;
        StringBuilder message = new StringBuilder("Liczba węzłów: " + t.length + "\n");
        for (Wierzcholek wierzcholek : t) {
            message.append(wierzcholek + "      ");
            if (++i % 5 == 0)
                message.append("\n");
        }
        JOptionPane.showMessageDialog(this, message, TYTUL + " - Lista węzłów", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void pokazListeKrawedzi(Graf graf) {
        Krawedz t[] = graf.getKrawedzie();
        int i=0;
        StringBuilder message = new StringBuilder("Liczba krawędzi: " + t.length + "\n");
        for (Krawedz krawedz : t) {
            message.append(krawedz + "      ");
            if (++i % 5 == 0)
                message.append("\n");
        }
        JOptionPane.showMessageDialog(this, message, TYTUL + " - Lista krawędzi", JOptionPane.PLAIN_MESSAGE);
    }

    private void showBuildingExample() {
        Graf graf = new Graf();

        Wierzcholek n1 = new Wierzcholek(100, 100);
        Wierzcholek n2 = new Wierzcholek(100, 200, Color.CYAN, "O");
        Wierzcholek n3 = new Wierzcholek(200, 100);
        n3.setR(20);
        Wierzcholek n4 = new Wierzcholek(200, 250, Color.GREEN, "A");
        n4.setR(30);
        Krawedz k1 = new Krawedz(n1, n3, 12, Color.PINK);
        Krawedz k2 = new Krawedz(n1, n4, 9, Color.cyan);


        graf.dodajWierzcholek(n1);
        graf.dodajWierzcholek(n2);
        graf.dodajWierzcholek(n3);
        graf.dodajWierzcholek(n4);
        graf.dodajKrawedz(k1);
        graf.dodajKrawedz(k2);
        panel.setGraf(graf);
    }

    private void createMenu() {
        menuNowy.addActionListener(this);
        menuPokażPrzykład.addActionListener(this);
        menuWyjdź.addActionListener(this);
        menuListaWierzchołków.addActionListener(this);
        menuAutor.addActionListener(this);
        menuInstrukcja.addActionListener(this);

        menuGraf.setMnemonic(KeyEvent.VK_G);
        menuGraf.add(menuNowy);
        menuGraf.add(menuPokażPrzykład);
        menuGraf.addSeparator();
        menuGraf.add(menuListaWierzchołków);
        menuGraf.add(menuListaKrawedzi);
        menuGraf.addSeparator();
        menuGraf.add(menuWyjdź);

        menuPomoc.setMnemonic(KeyEvent.VK_H);
        menuPomoc.add(menuInstrukcja);
        menuPomoc.add(menuAutor);

        menuBar.add(menuGraf);
        menuBar.add(menuPomoc);
        setJMenuBar(menuBar);
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == menuNowy) {
            panel.setGraf(new Graf());
        }
        if (source == menuPokażPrzykład) {
            showBuildingExample();
        }
        if (source == menuListaWierzchołków) {
            pokazListeWierzcholkow(panel.getGraf());
        }
        if (source == menuListaKrawedzi) {
            pokazListeKrawedzi(panel.getGraf());
        }
        if (source == menuAutor) {
            JOptionPane.showMessageDialog(this, AUTOR, TYTUL, JOptionPane.INFORMATION_MESSAGE);
        }
        if (source == menuInstrukcja) {
            JOptionPane.showMessageDialog(this, INSTRUKCJA, TYTUL, JOptionPane.PLAIN_MESSAGE);
        }
        if (source == menuWyjdź) {
            System.exit(0);
        }
    }
}
