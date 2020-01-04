import java.awt.*;
import java.io.*;

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

    public static void printToFile(PrintWriter writer, Krawedz k) {
        writer.println(k.getPoczatek().getX() + "#" + k.getPoczatek().getY() + "#" +
                k.getKoniec().getX() + "#" + k.getKoniec().getY() + "#" +
                k.dlugosc + "#" + k.kolor.getRGB());
    }

    public static void printToFile(String fileName, Krawedz k) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(fileName + ".krawedz")){
            printToFile(writer, k);
        }catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Nie odnaleziono pliku " + fileName);
        }
    }

    public static Krawedz readFromFile(BufferedReader reader, Wierzcholek[] listaWierzcholkow) throws IOException {
        try {
            String line = reader.readLine();
            if (line.equals("$"))return null;
            String[] txt = line.split("#");

            Wierzcholek poczatek = null, koniec = null;
            for (Wierzcholek wierzcholek : listaWierzcholkow)
            {
                if (wierzcholek.getX()==Integer.parseInt(txt[0]) && wierzcholek.getY() == Integer.parseInt(txt[1]))
                    poczatek = wierzcholek;
                if (wierzcholek.getX()==Integer.parseInt(txt[2]) && wierzcholek.getY() == Integer.parseInt(txt[3]))
                    koniec = wierzcholek;
            }
            Krawedz krawedz = new Krawedz(poczatek, koniec, Integer.parseInt(txt[4]), new Color(Integer.parseInt(txt[5])));
            return krawedz;
        }catch (IOException e)
        {
            throw new IOException("Podczas odczytu danych z twojego pliku wystąpił błąd!");
        }
    }

    public static Krawedz readFromFile(String fileName, Wierzcholek[] listaWierzcholkow) throws IOException, FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))){
            return Krawedz.readFromFile(reader, listaWierzcholkow);
        }catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Nie znalezniono pliku o nazwie: " + fileName);
        }catch (IOException e)
        {
            throw new IOException("Podczas odczytu danych z twojego pliku wystąpił błąd!");
        }

    }

}