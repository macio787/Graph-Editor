import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graf implements Serializable {
    private static final long serialVersionUID = -8989249014859614147L;

    private List<Wierzcholek> wierzcholki;

    public Graf() {
        this.wierzcholki = new ArrayList<Wierzcholek>();
    }

    public void dodajWierzcholek(Wierzcholek wierzcholek){
        wierzcholki.add(wierzcholek);
    }

    public void usunWierzcholek(Wierzcholek wierzcholek){
        wierzcholki.remove(wierzcholek);
    }

    public Wierzcholek[] getWierzcholki(){
        Wierzcholek [] array = new Wierzcholek[0];
        return wierzcholki.toArray(array);
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
        for(Wierzcholek wierzcholek : wierzcholki){
            wierzcholek.draw(g);
        }
    }
}
