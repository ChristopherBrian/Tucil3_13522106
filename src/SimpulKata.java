// Kelas Simpul
public class SimpulKata {
    // Atribut kelas
    private String kata;
    private int harga;
    private SimpulKata leluhur;

    // Konstruktor
    public SimpulKata (String kata, int harga, SimpulKata leluhur) {
        this.kata = kata;
        this.harga = harga;
        this.leluhur = leluhur;
    }

    // Getter kata
    public String ambilKata() {
        return this.kata;
    }

    // Getter harga
    public int ambilHarga() {
        return this.harga;
    }

    // Setter harga
    public void aturHarga(int hargaBaru) {
        this.harga = hargaBaru;
    }
    
    // Getter leluhur
    public SimpulKata getLeluhur() {
        return this.leluhur;
    }
}