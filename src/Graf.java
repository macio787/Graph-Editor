import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graf implements Serializable {
    private static final long serialVersionUID = -8989249014859614147L;

    private List<Wierzcholek> wierzcholki;
    private List<Krawedz> krawedzie;

    public Graf() {
        this.wierzcholki = new ArrayList<Wierzcholek>();
        this.krawedzie = new ArrayList<Krawedz>();
    }

    public void dodajWierzcholek(Wierzcholek wierzcholek){
        wierzcholki.add(wierzcholek);
    }

    public void usunWierzcholek(Wierzcholek wierzcholek){
        wierzcholki.remove(wierzcholek);
    }

    public void dodajKrawedz(Krawedz krawedz) { krawedzie.add(krawedz); }

    public void usunKrawedz(Krawedz krawedz) { krawedzie.remove(krawedz); }

    public Wierzcholek[] getWierzcholki(){
        Wierzcholek [] t = new Wierzcholek[0];
        return wierzcholki.toArray(t);
    }

    public Krawedz[] getKrawedzie(){
        Krawedz [] t = new Krawedz[0];
        return krawedzie.toArray(t);
    }

    public void przesunWierzcholekNaWierzch(Wierzcholek wierzcholek){
        int i=wierzcholki.indexOf(wierzcholek);
        Wierzcholek bufor=wierzcholek;
        for(;i<wierzcholki.size()-1;i++)
        {
            wierzcholki.set(i,wierzcholki.get(i+1));
        }
        wierzcholki.set(i,bufor);
    }

    public void draw(Graphics g){
        for (Krawedz krawedz : krawedzie)
        {
            krawedz.draw(g);
        }
        for (Wierzcholek wierzcholek : wierzcholki)
        {
            wierzcholek.draw(g);
        }
    }
}