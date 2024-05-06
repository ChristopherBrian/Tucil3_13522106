// Import library
import java.util.*;

// Kelas hasil
public class Hasil {
    // Atribut kelas
    private List<String> rute;
    private int jumlahKataDikunjungi;
    private long waktuEksekusi;

    // Konstruktor
    public Hasil (List<String> rute, int jumlahKataDikunjungi, long waktuEksekusi) {
        this.rute = rute;
        this.jumlahKataDikunjungi = jumlahKataDikunjungi;
        this.waktuEksekusi = waktuEksekusi;
    }

    // Getter rute
    public List<String> ambilRute() {
        return this.rute;
    }

    // Getter jumlah kata dikunjungi
    public int ambilJumlahKataDikunjungi() {
        return this.jumlahKataDikunjungi;
    }

    // Getter waktu eksekusi
    public long ambilWaktuEksekusi() {
        return this.waktuEksekusi;
    }
}