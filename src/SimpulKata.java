// Kelas Simpul
public class SimpulKata {
    // Atribut kelas
    private String kata;
    private int gn;
    private int hn;
    private SimpulKata leluhur;

    // Konstruktor
    public SimpulKata (String kata, int gn, int hn, SimpulKata leluhur) {
        this.kata = kata;
        this.gn = gn;
        this.hn = hn;
        this.leluhur = leluhur;
    }

    // Getter kata
    public String ambilKata() {
        return this.kata;
    }

    // Getter g(n)
    public int ambilgn() {
        return this.gn;
    }

    // Setter h(n)
    public int ambilhn() {
        return this.hn;
    }

    // Setter g(n)
    public void aturgn(int gnBaru) {
        this.gn = gnBaru;
    }
    
    // Setter h(n)
    public void aturhn(int hnBaru) {
        this.hn = hnBaru;
    }

    // Getter leluhur
    public SimpulKata getLeluhur() {
        return this.leluhur;
    }
}