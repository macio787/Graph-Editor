import java.awt.*;
import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        for(int i=0;i<krawedzie.size();i++)
        {
            Krawedz k = krawedzie.get(i);
            if (k.getPoczatek()==wierzcholek||k.getKoniec()==wierzcholek) {
                usunKrawedz(k);
                i--;
            }
        }
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

    public Krawedz znajdzKrawedz(Wierzcholek w1, Wierzcholek w2)
    {
        Krawedz [] t = getKrawedzie();
        for (Krawedz krawedz : t)
        {
            if ((w1==krawedz.getPoczatek() && w2==krawedz.getKoniec()) || (w2==krawedz.getPoczatek() && w1==krawedz.getKoniec())) return krawedz;
        }
        return null;
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

    public List<List<Map.Entry<Integer, Integer>>> getListaSasiedztwa(){
        /*List<Map.Entry<Integer,Integer>> [] lista = new List[wierzcholki.size()];*/
        List<List<Map.Entry<Integer, Integer>>> l = new ArrayList<List<Map.Entry<Integer,Integer>>>(wierzcholki.size());
        for (int i=0;i<wierzcholki.size();i++)
            l.add(new ArrayList<Map.Entry<Integer,Integer>>());
        for (Krawedz krawedz : krawedzie)
        {
            int ip = wierzcholki.indexOf(krawedz.getPoczatek());
            int ik = wierzcholki.indexOf(krawedz.getKoniec());


            l.get(ip).add(new AbstractMap.SimpleEntry<>(ik, krawedz.getDlugosc()));
            l.get(ik).add(new AbstractMap.SimpleEntry<>(ip, krawedz.getDlugosc()));
            /*lista[ip].add(new AbstractMap.SimpleEntry<>(ik, krawedz.getDlugosc()));
            lista[ik].add(new AbstractMap.SimpleEntry<>(ip, krawedz.getDlugosc()));*/
        }
        return l;
    }

    public static void printToFile(PrintWriter writer, Graf graf){
        for (Wierzcholek w : graf.wierzcholki)
        {
            w.printToFile(writer, w);
        }
        writer.println("$");
        for (Krawedz k : graf.krawedzie)
        {
            k.printToFile(writer, k);
        }
        writer.println("$");
    }

    public static void printToFile(String fileName, Graf graf) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(fileName)){
            printToFile(writer, graf);
        }catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Nie odnaleziono pliku " + fileName);
        }
    }

    public static Graf readFromFile(BufferedReader reader) throws IOException {
        try {
            Graf graf = new Graf();
            Wierzcholek w;
            while ((w = Wierzcholek.readFromFile(reader))!=null) {
                graf.dodajWierzcholek(w);
            }
            Krawedz k;
            while ((k = Krawedz.readFromFile(reader, graf.getWierzcholki()))!=null)
            {
                graf.dodajKrawedz(k);
            }
            return graf;
        } catch (IOException e) {
            throw new IOException("Podczas odczytu danych z twojego pliku wystąpił błąd!");
        }
    }

    public static Graf readFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))){
            return Graf.readFromFile(reader);
        }catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Nie znalezniono pliku o nazwie: " + fileName);
        }catch (IOException e)
        {
            throw new IOException("Podczas odczytu danych z twojego pliku wystąpił błąd!");
        }
    }
}
