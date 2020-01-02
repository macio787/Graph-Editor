import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DijkstraZnajdywaczDrogi {


    List<Map.Entry<Integer, Integer>>[] listaSasiedztwa;
    Wierzcholek [] listaWierzcholkow;
    int droga[], poprzednik[];
    TreeMap<Integer,Integer> wierzcholkiDoPrzetworzenia;

    public DijkstraZnajdywaczDrogi(List<Map.Entry<Integer, Integer>>[] listaSasiedztwa, Wierzcholek [] listaWierzcholkow) {
        this.listaSasiedztwa = listaSasiedztwa;
        this.listaWierzcholkow = listaWierzcholkow;
        this.droga = new int[listaSasiedztwa.length];
        for (int i : droga)
            i = Integer.MAX_VALUE;
        this.poprzednik = new int[listaSasiedztwa.length];
        for (int i:poprzednik)
            i=-1;
    }

    public int[] getPoprzednik(){return this.poprzednik;}

    public int znajdzDroge (Wierzcholek start, Wierzcholek koniec){
        int indeksKoniec = -1, i = 0;
        for (Wierzcholek w : listaWierzcholkow)
        {
            if (w==start)
            { droga[i]=0;
            }
            if (w==koniec)indeksKoniec=i;
            wierzcholkiDoPrzetworzenia.put(droga[i],i);
            i++;
        }
        while (wierzcholkiDoPrzetworzenia.isEmpty())
        {
            int kluczNajblizszego=wierzcholkiDoPrzetworzenia.firstKey();
            int wierzcholek = wierzcholkiDoPrzetworzenia.get(kluczNajblizszego);
            wierzcholkiDoPrzetworzenia.remove(kluczNajblizszego);
            for (i=0;i<listaSasiedztwa[wierzcholek].size()-1;i++)
            {
                int sasiad = listaSasiedztwa[wierzcholek].get(i).getKey();
                int odlegloscDoSasiada = listaSasiedztwa[wierzcholek].get(i).getValue();
                if (droga[sasiad]>droga[wierzcholek]+odlegloscDoSasiada) {
                    droga[sasiad] = droga[wierzcholek]+odlegloscDoSasiada;
                    poprzednik[sasiad] = wierzcholek;
                }
            }
        }
        if (droga[indeksKoniec]!=Integer.MAX_VALUE) return droga[indeksKoniec];
        else return -1;
    }
}
