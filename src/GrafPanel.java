import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GrafPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private static final long serialVersionUID = 5249273894754128807L;

    Graf graf;
    private RysownikKrawedzi rysownik=new RysownikKrawedzi();

    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mouseButtonLeft = false;
    private boolean mouseButtonRigth = false;
    private int mouseCursor = Cursor.DEFAULT_CURSOR;

    protected Wierzcholek wierzcholekPodKursorem = null;
    private Krawedz krawedzPodKursorem = null;


    public GrafPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    public Graf getGraf() {
        return graf;
    }

    public void setGraf(Graf graf) {
        this.graf = graf;
    }


    private Wierzcholek znajdzWierzcholek(int mx, int my){
        Wierzcholek [] wierzcholki = graf.getWierzcholki();
        for(int i=wierzcholki.length-1;i>=0;i--)
        {
            if (wierzcholki[i].isMouseOver(mx, my)) return wierzcholki[i];
        }
        return null;
    }

    private Wierzcholek znajdzWierzcholek(MouseEvent event){
        return znajdzWierzcholek(event.getX(), event.getY());
    }

    private Krawedz znajdzKrawedz(int mx, int my){
        for (Krawedz krawedz : graf.getKrawedzie())
        {
            if (krawedz.isMouseOver(mx, my))return krawedz;
        }
        return null;
    }

    private Krawedz znajdzKrawedz(MouseEvent event){
        return znajdzKrawedz(event.getX(), event.getY());
    }

    protected void setMouseCursor(MouseEvent event) {
        wierzcholekPodKursorem = znajdzWierzcholek(event);
        krawedzPodKursorem = znajdzKrawedz(event);
        if (krawedzPodKursorem!=null && wierzcholekPodKursorem==null)
            mouseCursor = Cursor.CROSSHAIR_CURSOR;
        else if (wierzcholekPodKursorem!=null)
            mouseCursor = Cursor.HAND_CURSOR;
        else if (mouseButtonLeft)
            mouseCursor = Cursor.MOVE_CURSOR;
        else if (rysownik.getWlacznik())
            mouseCursor = Cursor.HAND_CURSOR;
        else mouseCursor = Cursor.DEFAULT_CURSOR;
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
        mouseX = event.getX();
        mouseY = event.getY();
    }

    protected void setMouseCursor() {
        wierzcholekPodKursorem = znajdzWierzcholek(mouseX, mouseY);
        krawedzPodKursorem = znajdzKrawedz(mouseX, mouseY);
        if (krawedzPodKursorem!=null && wierzcholekPodKursorem==null)
            mouseCursor = Cursor.CROSSHAIR_CURSOR;
        else if (wierzcholekPodKursorem!=null)
            mouseCursor = Cursor.HAND_CURSOR;
        else if (mouseButtonLeft)
            mouseCursor = Cursor.MOVE_CURSOR;
        else if (rysownik.getWlacznik())
            mouseCursor = Cursor.HAND_CURSOR;
        else mouseCursor = Cursor.DEFAULT_CURSOR;
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
    }

    private void przesunWierzcholek(int dx, int dy, Wierzcholek wierzcholek){
        wierzcholek.setX(wierzcholek.getX()+dx);
        wierzcholek.setY(wierzcholek.getY()+dy);
    }

    private void przesunWszystkieWierzcholki(int dx, int dy) {
        for (Wierzcholek wierzcholek : graf.getWierzcholki()) {
            przesunWierzcholek(dx, dy, wierzcholek);
        }
    }

    private void przesunKrawedz(int dx, int dy, Krawedz krawedz){
        przesunWierzcholek(dx, dy, krawedz.getPoczatek());
        przesunWierzcholek(dx, dy, krawedz.getKoniec());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graf==null) return;
        graf.draw(g);
    }
    
    //MouseListener - implementacja
    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton()==1) mouseButtonLeft = true;
        if (event.getButton()==3) mouseButtonRigth = true;
        setMouseCursor(event);
        if (wierzcholekPodKursorem!=null) {
            graf.przesunWierzcholekNaWierzch(wierzcholekPodKursorem);
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == 1)
        {
            mouseButtonLeft = false;
            if (rysownik.getWlacznik() && wierzcholekPodKursorem!=null)
            {
                rysownik.rysujKrawedz();
                repaint();
                rysownik.przestawWlacznik();
            }
        }
        if (event.getButton() == 3)
            mouseButtonRigth = false;
        setMouseCursor(event);
        if (event.getButton() == 3) {
            if (wierzcholekPodKursorem != null)
                createPopupMenu(event, wierzcholekPodKursorem);
            else if (krawedzPodKursorem != null)
                createPopupMenu(event, krawedzPodKursorem);
            else
                createPopupMenu(event);

        }
    }
    
    @Override
    public void mouseDragged(MouseEvent event) {
        if (mouseButtonLeft) {
            if (wierzcholekPodKursorem != null)
                przesunWierzcholek(event.getX() - mouseX, event.getY() - mouseY, wierzcholekPodKursorem);
            else if (krawedzPodKursorem!=null)
                przesunKrawedz(event.getX() - mouseX, event.getY() - mouseY, krawedzPodKursorem);
            else {
                przesunWszystkieWierzcholki(event.getX() - mouseX, event.getY() - mouseY);
            }
        }
        mouseX = event.getX();
        mouseY = event.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        setMouseCursor(event);
    }

    //MouseWheelListener - implementacja
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        setMouseCursor();
        if (wierzcholekPodKursorem!=null)
        {
            int r=wierzcholekPodKursorem.getR();
            if (event.getWheelRotation()<0 && r<=60) wierzcholekPodKursorem.setR(r+5);
            else if (r>=15) wierzcholekPodKursorem.setR(r-5);
            repaint();
        }
    }

    //KeyListener - implementacja
    @Override
    public void keyPressed(KeyEvent event) {
        {   int dist;
            if (event.isShiftDown()) dist = 10;
            else dist = 1;
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    przesunWszystkieWierzcholki(-dist, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    przesunWszystkieWierzcholki(dist, 0);
                    break;
                case KeyEvent.VK_UP:
                    przesunWszystkieWierzcholki(0, -dist);
                    break;
                case KeyEvent.VK_DOWN:
                    przesunWszystkieWierzcholki(0, dist);
                    break;
                case KeyEvent.VK_DELETE:
                    if (wierzcholekPodKursorem != null) {
                        graf.usunWierzcholek(wierzcholekPodKursorem);
                        wierzcholekPodKursorem = null;
                    }
                    break;
            }
        }
        repaint();
        setMouseCursor();
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void keyTyped(KeyEvent event) {
        char znak=event.getKeyChar();
        if (wierzcholekPodKursorem!=null){
            switch (znak) {
                case 'r':
                    wierzcholekPodKursorem.setKolor(Color.RED);
                    break;
                case 'g':
                    wierzcholekPodKursorem.setKolor(Color.GREEN);
                    break;
                case 'b':
                    wierzcholekPodKursorem.setKolor(Color.BLUE);
                    break;
                case '+':
                    int r = wierzcholekPodKursorem.getR()+10;
                    if (r<=60) wierzcholekPodKursorem.setR(r);
                    break;
                case '-':
                    r = wierzcholekPodKursorem.getR()-10;
                    if (r>=10) wierzcholekPodKursorem.setR(r);
                    break;
            }
            repaint();
            setMouseCursor();
        }
    }

    //Menu kont. w pustym miejscu
    protected void createPopupMenu(MouseEvent event){
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();

        menuItem = new JMenuItem("Stwórz nowy wierzchołek prosty");
        menuItem.addActionListener((action) -> {
            graf.dodajWierzcholek(new Wierzcholek(event.getX(), event.getY()));
            repaint();
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Stwórz nowy wierzchołek");
        menuItem.addActionListener((action) -> {
            Color kolor = JColorChooser.showDialog(this, "Wybierz kolor wierzchołka", null);
            if (kolor!=null){
                String etykieta = JOptionPane.showInputDialog(this,"Podaj etykietę");
                if (etykieta!=null) graf.dodajWierzcholek(new Wierzcholek(event.getX(), event.getY(), kolor, etykieta));
            }
            repaint();
        });
        popup.add(menuItem);

        popup.show(event.getComponent(), event.getX(), event.getY());
    }

    
    //Menu kont. na wierzchołku
    protected void createPopupMenu(MouseEvent event, Wierzcholek wierzcholek) {
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();

        menuItem = new JMenuItem("Zmień kolor");
        menuItem.addActionListener((a) -> {
            Color nowyKolor = JColorChooser.showDialog(this, "Wybierz nowy kolor", wierzcholek.getKolor());
            if (nowyKolor!=null) wierzcholek.setKolor(nowyKolor);
            repaint();
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Zmień etykietę");
        menuItem.addActionListener((a) -> {
            String etykieta = JOptionPane.showInputDialog(this,"Podaj etykietę", wierzcholek.getEtykieta());
            if (etykieta!=null) wierzcholek.setEtykieta(etykieta);
            repaint();
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Usuń");
        menuItem.addActionListener((a) -> {
            graf.usunWierzcholek(wierzcholek);
            repaint();
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Połącz z...");
        menuItem.addActionListener((a) -> {
            rysownik = new RysownikKrawedzi(wierzcholek, this);
            rysownik.przestawWlacznik();
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Połącz znaczoną krawędzią z...");
        menuItem.addActionListener((a) -> {
            Color kolor = JColorChooser.showDialog(this, "Wybierz kolor krawędzi", null);
            if (kolor!=null)
            {
                String input = JOptionPane.showInputDialog(this, "Podaj długość krawędzi (liczbę rzeczywistą)");
                if (input!=null)
                {
                    try {
                        int dlugosc = Integer.parseInt(input);
                        if (dlugosc>=0)
                        {
                            rysownik = new RysownikKrawedzi(wierzcholek, dlugosc, kolor, this);
                            rysownik.przestawWlacznik();
                        }
                        else JOptionPane.showMessageDialog(this, "Długość nie może być ujemna!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(this, "Długość musi być liczbą rzeczywistą", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        popup.add(menuItem);

        popup.show(event.getComponent(), event.getX(), event.getY());
    }

    // Menu kont. dla krawędzi
    private void createPopupMenu(MouseEvent event, Krawedz krawedz){
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();

        menuItem = new JMenuItem("Zmień kolor");
        menuItem.addActionListener((a) -> {
            Color nowyKolor = JColorChooser.showDialog(this, "Wybierz nowy kolor", krawedz.getKolor());
            if (nowyKolor!=null) krawedz.setKolor(nowyKolor);
            repaint();
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Zmień długość");
        menuItem.addActionListener((a) -> {
            String input = JOptionPane.showInputDialog(this,"Podaj etykietę", krawedz.getDlugosc());
            if (input!=null) {
                try {
                    int dlugosc = Integer.parseInt(input);
                    if (dlugosc>=0)
                    {
                        krawedz.setDlugosc(dlugosc);
                        repaint();
                    }
                    else JOptionPane.showMessageDialog(this, "Długość nie może być ujemna!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "Długość musi być liczbą rzeczywistą", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Usuń");
        menuItem.addActionListener((a) -> {
            graf.usunKrawedz(krawedz);
            repaint();
        });
        popup.add(menuItem);

        popup.show(event.getComponent(), event.getX(), event.getY());
    }
}

class RysownikKrawedzi {

    private Wierzcholek poczatek;
    private int dlugosc;
    private Color kolor;

    private GrafPanel panel;
    private boolean wlacznik;

    protected RysownikKrawedzi(Wierzcholek poczatek, GrafPanel panel){
        this.poczatek=poczatek;
        this.dlugosc=0;
        this.kolor=Color.BLACK;
        this.panel=panel;
        this.wlacznik=false;
    }

    protected RysownikKrawedzi(Wierzcholek poczatek, int dlugosc, Color kolor, GrafPanel panel){
        this.poczatek=poczatek;
        this.dlugosc=dlugosc;
        this.kolor=kolor;
        this.panel=panel;
        this.wlacznik=false;
    }

    protected RysownikKrawedzi(){
        this.wlacznik=false;
    }

    protected boolean getWlacznik(){
        return this.wlacznik;
    }

    protected void przestawWlacznik(){
        if (wlacznik) wlacznik=false;
        else wlacznik=true;
    }

    protected void rysujKrawedz (){
        Wierzcholek wierzcholek = panel.wierzcholekPodKursorem;
        if (!isDuplikat(poczatek,wierzcholek))
            panel.graf.dodajKrawedz(new Krawedz(poczatek, wierzcholek, dlugosc, kolor));
        else JOptionPane.showMessageDialog(panel, "Krawedź łącząca te dwa wierzchołki już istnieje", "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isDuplikat(Wierzcholek w1, Wierzcholek w2)
    {
        Krawedz [] t = panel.graf.getKrawedzie();
        for (Krawedz krawedz : t)
        {
            if ((w1==krawedz.getPoczatek() && w2==krawedz.getKoniec()) || (w2==krawedz.getPoczatek() && w1==krawedz.getKoniec())) return true;
        }
        return false;
    }
}