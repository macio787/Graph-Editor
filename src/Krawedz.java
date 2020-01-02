import java.awt.*;

public class Krawedz {

    private Wierzcholek poczatek;
    private Wierzcholek koniec;

    private int dlugosc;

    private Color kolor;

    public Krawedz(Wierzcholek poczatek, Wierzcholek koniec, int dlugosc, Color kolor) {
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.dlugosc = dlugosc;
        this.kolor = kolor;
    }

    public Wierzcholek getPoczatek() {
        return poczatek;
    }

    public void setPoczatek(Wierzcholek poczatek) {
        this.poczatek = poczatek;
    }

    public Wierzcholek getKoniec() {
        return koniec;
    }

    public void setKoniec(Wierzcholek koniec) {
        this.koniec = koniec;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
    }

    public Color getKolor() {
        return kolor;
    }

    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }

    public int getSrodekX () {
        int p=Math.min(poczatek.getX(), koniec.getX());
        int k=Math.max(poczatek.getX(), koniec.getX());
        return p+(k-p)/2;
    }

    public int getSrodekY () {
        int p=Math.min(poczatek.getY(), koniec.getY());
        int k=Math.max(poczatek.getY(), koniec.getY());
        return p+(k-p)/2;
    }

    public boolean isMouseOver (int mx, int my) {
        if (Math.abs((my-poczatek.getY())*(koniec.getX()-poczatek.getX())-(koniec.getY()-poczatek.getY())*(mx-poczatek.getX()))<=300) return true;
        else return false;
    }

    public void draw (Graphics g)
    {
        g.setColor(kolor);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2.0f));
        g.drawLine(poczatek.getX(), poczatek.getY(), koniec.getX(), koniec.getY());
        g2.setStroke(new BasicStroke(1.0f));

        g.setColor(kolor.darker());
        if (dlugosc>0) g.drawString(String.valueOf(dlugosc), this.getSrodekX(), this.getSrodekY());
    }

    @Override
    public String toString(){
        return ("([" + poczatek.getX() +", " + poczatek.getY() + "], [" + koniec.getX() + ", " + koniec.getY() + "])");
    }
}
