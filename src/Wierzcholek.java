import java.awt.*;

public class Wierzcholek {

    private int x;
    private int y;

    private int r;

    private Color kolor;
    private String etykieta;

    public Wierzcholek(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 5;
        this.kolor = Color.WHITE;
        this.etykieta = " ";
    }

    public Wierzcholek(int x, int y, Color kolor, String etykieta) {
        this.x = x;
        this.y = y;
        this.r = 10;
        this.kolor = kolor;
        this.etykieta = etykieta;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Color getKolor() {
        return kolor;
    }

    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }

    public void setEtykieta(String etykieta) { this.etykieta = etykieta; }

    public String getEtykieta() { return etykieta; }

    public boolean isMouseOver(int mx, int my){
        return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
    }

    void draw(Graphics g) {
        g.setColor(kolor);
        g.fillOval(x-r, y-r, 2*r, 2*r);
        g.setColor(Color.BLACK);
        g.drawOval(x-r, y-r, 2*r, 2*r);

        FontMetrics fm = g.getFontMetrics();
        int ex = x - fm.stringWidth(etykieta)/2;
        int ey = y + (fm.getAscent()-fm.getDescent())/2;
        g.drawString(etykieta, ex, ey);
    }

    @Override
    public String toString(){
        return ("(" + x +", " + y + ", " + r + ")");
    }
}
