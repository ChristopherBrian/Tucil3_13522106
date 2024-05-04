// Import library
import java.util.*;
// Kelas Penyelesai
public class Penyelesai {
    // Atribut kelas
    private Set<String> kamus;

    // Fungsi untuk membuat rute dari kata asal ke kata tujuan
    // Menerima parameter kata tujuan dan mengembalikan rute yang dibuat dalam bentuk list kata
    private List<String> buatRute (SimpulKata kataTujuan) {
        List<String> listKata = new ArrayList<>();
        SimpulKata kataSekarang = kataTujuan;
        // Buat rute ke kataTujuan dengan memanggil simpul leluhur secara rekursif sampai kata asal
        while (kataSekarang != null) {
            listKata.add(0, kataSekarang.ambilKata());
            kataSekarang = kataSekarang.getLeluhur();
        }
        return listKata;
    }

    // Fungsi untuk mencari semua simpul tetangga (kata yang berbeda satu huruf)
    // Menerima parameter kata dan mengembalikan semua simpul tetangga dalam bentuk list kata tetangga
    private List<String> cariTetangga (String kata) {
        List<String> listTetangga = new ArrayList<>();
        char[] arrayKarakterKata = kata.toCharArray();
        // Mencari semua kata lain yang berbeda satu huruf
        for (int i = 0; i < arrayKarakterKata.length; i++) {
            char karakterKata = arrayKarakterKata[i];
            // Mengganti setiap huruf pada kata dengan huruf lain dan mengecek apakah sebuah kata lain terbentuk
            for (char karakterCek = 'a'; karakterCek <= 'z'; karakterCek++) {
                if (karakterCek != karakterKata) {
                    arrayKarakterKata[i] = karakterCek;
                    String kataTetangga = new String(arrayKarakterKata);
                    // Jika sebuah kata lain terbentuk
                    if (kamus.contains(kataTetangga)) {
                        // Tambahkan kata sebagai kata tetangga
                        listTetangga.add(kataTetangga);
                    }
                }
            }
            arrayKarakterKata[i] = karakterKata;
        }
        return listTetangga;
    }

    // Fungsi untuk menghitung heuristik h(n)
    // Menerima parameter kata dan kata tujuan serta mengembalikan nilai h(n)
    private int hitunghn (String kata, String kataTujuan) {
        int hn = 0;
        for (int i = 0; i < kata.length(); i++) {
            if (kata.charAt(i) != kataTujuan.charAt(i)) {
                hn++;
            }
        }
        return hn;
    }

    // Konstruktor
    public Penyelesai (Set<String> kamus) {
        this.kamus = kamus;
    }

    // Fungsi untuk mencari rute dari kata asal ke kata tujuan menggunakan algoritma Uniform Cost Search (UCS)
    // Menerima parameter kata asal dan kata tujuan serta mengembalikan rute ke kata tujuan yang dibentuk fungsi buatRute
    public List<String> cariRuteUCS(String kataAsal, String kataTujuan) {
        // Buat priority queue berdasarkan harga tiap kata
        Queue<SimpulKata> antrian = new PriorityQueue<>(Comparator.comparingInt(SimpulKata -> SimpulKata.ambilHarga()));
        Set<String> kataDikunjungi = new HashSet<>();
        // Tambahkan kata asal ke antrian
        antrian.offer(new SimpulKata(kataAsal, 0, null));
        // Eksplorasi setiap kata pada antrian sampai antrian kosong
        while (!antrian.isEmpty()) {
            // Ambil kata dengan harga terendah dari antrian
            SimpulKata kataSekarang = antrian.poll();
            kataDikunjungi.add(kataSekarang.ambilKata());
            // Jika kata yang diambil merupakan kata tujuan
            if (kataSekarang.ambilKata().equals(kataTujuan)) {
                // Kembalikan rute dari kata asal ke kata sekarang
                return buatRute(kataSekarang);
            }
            // Cek setiap kata tetangga dari kata yang sedang diambil
            for (String kataTetangga : cariTetangga(kataSekarang.ambilKata())) {
                // Jika kata tetangga sudah dikunjungi
                if (!kataDikunjungi.contains(kataTetangga)) {
                    // Tambah satu ke harga kata tersebut dan masukkan ke antrian sebagai simpul baru
                    int harga = kataSekarang.ambilHarga() + 1;
                    antrian.offer(new SimpulKata(kataTetangga, harga, kataSekarang));
                }
            }
        }
        // Kembalikan null jika rute tidak ditemukan
        return null;
    }

    // Fungsi untuk mencari rute dari kata asal ke kata tujuan menggunakan algoritma Greedy Best First Search (GBFS)
    // Menerima parameter kata asal dan kata tujuan serta mengembalikan rute ke kata tujuan yang dibentuk fungsi buatRute
    public List<String> cariRuteGBFS (String kataAsal, String kataTujuan) {
        // Buat priority queue berdasarkan harga tiap kata
        Queue<SimpulKata> antrian = new PriorityQueue<>(Comparator.comparingInt(SimpulKata -> SimpulKata.ambilHarga()));
        Set<String> kataDikunjungi = new HashSet<>();
        // Tambahkan kata asal ke antrian
        antrian.offer(new SimpulKata(kataAsal, hitunghn(kataAsal, kataTujuan), null));
        // Eksplorasi setiap kata pada antrian sampai antrian kosong
        while (!antrian.isEmpty()) {
            // Ambil kata dengan harga terendah dari antrian
            SimpulKata kataSekarang = antrian.poll();
            kataDikunjungi.add(kataSekarang.ambilKata());
            // Jika kata yang diambil merupakan kata tujuan
            if (kataSekarang.ambilKata().equals(kataTujuan)) {
                // Kembalikan rute dari kata asal ke kata sekarang
                return buatRute(kataSekarang);
            }
            // Cek setiap kata tetangga dari kata yang sedang diambil
            for (String kataTetangga : cariTetangga(kataSekarang.ambilKata())) {
                // Jika kata tetangga sudah dikunjungi
                if (!kataDikunjungi.contains(kataTetangga)) {
                    // Hitung heuristik h(n) kata tersebut dan masukkan ke antrian sebagai simpul baru
                    int harga = hitunghn(kataTetangga, kataTujuan);
                    antrian.offer(new SimpulKata(kataTetangga, harga, kataSekarang));
                }
            }
        }
        // Kembalikan null jika rute tidak ditemukan
        return null;
    }

    // Fungsi untuk mencari rute dari kata asal ke kata tujuan menggunakan algoritma A star (A*)
    // Menerima parameter kata asal dan kata tujuan serta mengembalikan rute ke kata tujuan yang dibentuk fungsi buatRute
    public List<String> cariRuteAStar (String kataAsal, String kataTujuan) {
        // Buat priority queue berdasarkan harga tiap kata
        Queue<SimpulKata> antrian = new PriorityQueue<>(Comparator.comparingInt(SimpulKata -> SimpulKata.ambilHarga()));
        Set<String> kataDikunjungi = new HashSet<>();
        // Tambahkan kata asal ke antrian
        antrian.offer(new SimpulKata(kataAsal, hitunghn(kataAsal, kataTujuan), null));
        // Eksplorasi setiap kata pada antrian sampai antrian kosong
        while (!antrian.isEmpty()) {
            // Ambil kata dengan harga terendah dari antrian
            SimpulKata kataSekarang = antrian.poll();
            kataDikunjungi.add(kataSekarang.ambilKata());
            // Jika kata yang diambil merupakan kata tujuan
            if (kataSekarang.ambilKata().equals(kataTujuan)) {
                // Kembalikan rute dari kata asal ke kata sekarang
                return buatRute(kataSekarang);
            }
            // Cek setiap kata tetangga dari kata yang sedang diambil
            for (String kataTetangga : cariTetangga(kataSekarang.ambilKata())) {
                // Jika kata tetangga sudah dikunjungi
                if (!kataDikunjungi.contains(kataTetangga)) {
                    // Hitung harga kata tersebut (g(n) yang konstan bertambah 1 untuk setiap sisi ditambah h(n)) dan masukkan ke antrian sebagai simpul baru
                    int harga = hitunghn(kataTetangga, kataTujuan) + 1;
                    antrian.offer(new SimpulKata(kataTetangga, harga, kataSekarang));
                }
            }
        }
        // Kembalikan null jika rute tidak ditemukan
        return null;
    }
}
