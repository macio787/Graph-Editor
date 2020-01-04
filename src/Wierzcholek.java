import java.awt.*;
import java.io.*;

public class Wierzcholek {

    private int x;
    private int y;

    private int r;

    private Color kolor;

    private Color kolorObwodki;

    private String etykieta;
    public Wierzcholek(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 5;
        this.kolor = Color.WHITE;
        this.etykieta = " ";
        this.kolorObwodki = Color.BLACK;
    }

    public Wierzcholek(int x, int y, Color kolor, String etykieta) {
        this.x = x;
        this.y = y;
        this.r = 10;
        this.kolor = kolor;
        this.etykieta = etykieta;
        this.kolorObwodki = Color.BLACK;
    }

    public Wierzcholek(int x, int y, int r, Color kolor, String etykieta) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.kolor = kolor;
        this.etykieta = etykieta;
        this.kolorObwodki = Color.BLACK;
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

    public void setKolorObwodki(Color kolorObwodki) {
        this.kolorObwodki = kolorObwodki;
    }

    public boolean isMouseOver(int mx, int my){
        return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
    }

    void draw(Graphics g) {
        g.setColor(kolor);
        g.fillOval(x-r, y-r, 2*r, 2*r);
        g.setColor(kolorObwodki);
        g.drawOval(x-r, y-r, 2*r, 2*r);
        g.setColor(Color.BLACK);

        FontMetrics fm = g.getFontMetrics();
        int ex = x - fm.stringWidth(etykieta)/2;
        int ey = y + (fm.getAscent()-fm.getDescent())/2;
        g.drawString(etykieta, ex, ey);
    }

    @Override
    public String toString(){
        return ("(" + x +", " + y + ", " + r + ")");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof Wierzcholek == false) return false;
        Wierzcholek wierzcholek = (Wierzcholek) obj;
        if (this.x == wierzcholek.x && this.y == wierzcholek.y && this.r == wierzcholek.r) return true;
        else return false;
    }

    public static void printToFile(PrintWriter writer, Wierzcholek w){
        writer.println(w.x + "#" + w.y + "#" + w.r + "#" + w.kolor.getRGB() + "#" + w.etykieta);
    }

    public static void printToFile(String fileName, Wierzcholek w) throws FileNotFoundException {
        try(PrintWriter writer = new PrintWriter(fileName + ".wierzcholek")){
            printToFile(writer, w);
        }catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Nie odnaleziono pliku " + fileName);
        }
    }

    public static Wierzcholek readFromFile(BufferedReader reader) throws IOException {
        try {
            String line = reader.readLine();
            if (line.equals("$"))return null;
            String[] txt = line.split("#");
            Wierzcholek wierzcholek = new Wierzcholek(Integer.parseInt(txt[0]), Integer.parseInt(txt[1]), Integer.parseInt(txt[2]), new Color(Integer.parseInt(txt[3])), txt[4]);
            return wierzcholek;
        }catch (IOException e)
        {
            throw new IOException("Podczas odczytu danych z twojego pliku wystąpił błąd!");
        }
    }

    public static Wierzcholek readFromFile(String fileName) throws IOException, FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName + ".wierzcholek")))){
            return Wierzcholek.readFromFile(reader);
        }catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Nie znalezniono pliku o nazwie: " + fileName);
        }catch (IOException e)
        {
            throw new IOException("Podczas odczytu danych z twojego pliku wystąpił błąd!");
        }
    }
}
