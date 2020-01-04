import java.util.*;

public class DijkstraZnajdywaczDrogi {

    List<List<Map.Entry<Integer, Integer>>> listaSasiedztwa;
    Wierzcholek [] listaWierzcholkow;
    Integer droga[], poprzednik[];
    PriorityQueue<Map.Entry<Integer, Integer>> wierzcholkiDoPrzetworzenia;

    public DijkstraZnajdywaczDrogi(List<List<Map.Entry<Integer, Integer>>> listaSasiedztwa, Wierzcholek [] listaWierzcholkow) {
        this.listaSasiedztwa = listaSasiedztwa;
        this.listaWierzcholkow = listaWierzcholkow;
        this.droga = new Integer[listaSasiedztwa.size()];
        for (int i=0;i<listaSasiedztwa.size();i++)
            droga[i]=Integer.MAX_VALUE;
        this.poprzednik = new Integer[listaSasiedztwa.size()];
        for (int i=0;i<listaSasiedztwa.size();i++)
            poprzednik[i]=-1;
    }

    public Integer[] getPoprzednik(){return this.poprzednik;}

    public int znajdzDroge (Wierzcholek start, Wierzcholek koniec){
        int indeksKoniec = -1;
        Integer i = 0;
        wierzcholkiDoPrzetworzenia = new PriorityQueue<>(listaWierzcholkow.length, Map.Entry.comparingByValue());

        for (Wierzcholek w : listaWierzcholkow)
        {
            if (w==start)droga[i]=0;
            if (w==koniec)indeksKoniec=i;

            wierzcholkiDoPrzetworzenia.add(new AbstractMap.SimpleEntry<>(i,droga[i]));
            i++;
        }
        while (!wierzcholkiDoPrzetworzenia.isEmpty())
        {
            i=0;
            int d=wierzcholkiDoPrzetworzenia.peek().getValue();
            int wierzcholek = wierzcholkiDoPrzetworzenia.poll().getKey();
            for (;i<listaSasiedztwa.get(wierzcholek).size();i++)
            {
                int sasiad = listaSasiedztwa.get(wierzcholek).get(i).getKey();
                int odlegloscDoSasiada = listaSasiedztwa.get(wierzcholek).get(i).getValue();
                if (droga[sasiad]>droga[wierzcholek]+odlegloscDoSasiada) {
                    if (droga[wierzcholek]!=Integer.MAX_VALUE && odlegloscDoSasiada!=Integer.MAX_VALUE)
                    {
                        droga[sasiad] = droga[wierzcholek]+odlegloscDoSasiada;
                        wierzcholkiDoPrzetworzenia.add(new AbstractMap.SimpleEntry<>(sasiad,droga[sasiad]));
                    }
                    poprzednik[sasiad] = wierzcholek;
                }
            }
        }
        if (droga[indeksKoniec]!=Integer.MAX_VALUE) return droga[indeksKoniec];
        else return -1;
    }
}
