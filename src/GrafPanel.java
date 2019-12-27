import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GrafPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private static final long serialVersionUID = 1L;

    protected Graf graf;


    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mouseButtonLeft = false;
    private boolean mouseButtonRigth = false;
    protected int mouseCursor = Cursor.DEFAULT_CURSOR;

    protected Wierzcholek wierzcholekPodKursorem = null;


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
        for(Wierzcholek wierzcholek: graf.getWierzcholki()){
            if (wierzcholek.isMouseOver(mx, my)){
                return wierzcholek;
            }
        }
        return null;
    }

    private Wierzcholek znajdzWierzcholek(MouseEvent event){
        return znajdzWierzcholek(event.getX(), event.getY());
    }

    protected void setMouseCursor(MouseEvent event) {
        wierzcholekPodKursorem = znajdzWierzcholek(event);
        if (wierzcholekPodKursorem != null) {
            mouseCursor = Cursor.HAND_CURSOR;
        } else if (mouseButtonLeft) {
            mouseCursor = Cursor.MOVE_CURSOR;
        } else {
            mouseCursor = Cursor.DEFAULT_CURSOR;
        }
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
        mouseX = event.getX();
        mouseY = event.getY();
    }

    protected void setMouseCursor() {
        wierzcholekPodKursorem = znajdzWierzcholek(mouseX, mouseY);
        if (wierzcholekPodKursorem != null) {
            mouseCursor = Cursor.HAND_CURSOR;
        } else if (mouseButtonLeft) {
            mouseCursor = Cursor.MOVE_CURSOR;
        } else {
            mouseCursor = Cursor.DEFAULT_CURSOR;
        }
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
            mouseButtonLeft = false;
        if (event.getButton() == 3)
            mouseButtonRigth = false;
        setMouseCursor(event);
        if (event.getButton() == 3) {
            if (wierzcholekPodKursorem != null) {
                createPopupMenu(event, wierzcholekPodKursorem);
            } else {
                createPopupMenu(event);
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent event) {
        if (mouseButtonLeft) {
            if (wierzcholekPodKursorem != null) {
                przesunWierzcholek(event.getX() - mouseX, event.getY() - mouseY, wierzcholekPodKursorem);
            } else {
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
        menuItem.addActionListener((action) -> {
            graf.usunWierzcholek(wierzcholek);
            repaint();
        });
        popup.add(menuItem);

        popup.show(event.getComponent(), event.getX(), event.getY());
    }
}
