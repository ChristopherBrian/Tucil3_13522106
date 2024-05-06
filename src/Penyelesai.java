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
    // Menerima parameter kata asal dan kata tujuan serta mengembalikan rute ke kata tujuan yang dibentuk fungsi buatRute dan jumlah kata dikunjungi
    public Hasil cariRuteUCS(String kataAsal, String kataTujuan) {
        // Buat priority queue berdasarkan harga tiap kata
        Queue<SimpulKata> antrian = new PriorityQueue<>(Comparator.comparingInt(SimpulKata -> SimpulKata.ambilgn()));
        // Buat set untuk menyimpan kata yang telah dikunjungi
        Set<String> kataDikunjungi = new HashSet<>();
        // Inisialisasi jumlah kata dikunjungi
        int jumlahKataDikunjungi = 0;
        // Mulai penghitungan waktu eksekusi
        long waktuMulai = System.currentTimeMillis();
        // Tambahkan kata asal ke antrian
        antrian.offer(new SimpulKata(kataAsal, 0, 0, null));
        // Eksplorasi setiap kata pada antrian sampai antrian kosong
        while (!antrian.isEmpty()) {
            // Ambil kata dengan harga terendah dari antrian
            SimpulKata kataSekarang = antrian.poll();
            kataDikunjungi.add(kataSekarang.ambilKata());
            // Jika kata yang diambil merupakan kata tujuan
            if (kataSekarang.ambilKata().equals(kataTujuan)) {
                // Kembalikan rute dari kata asal ke kata sekarang, jumlah kata dikunjungi, dan waktu eksekusi
                List<String> rute = buatRute(kataSekarang);
                long waktuSelesai = System.currentTimeMillis();
                long waktuEksekusi = waktuSelesai - waktuMulai;
                Hasil hasil = new Hasil(rute, jumlahKataDikunjungi, waktuEksekusi);
                return hasil;
            }
            // Cek setiap kata tetangga dari kata yang sedang diambil
            for (String kataTetangga : cariTetangga(kataSekarang.ambilKata())) {
                // Jika kata tetangga belum dikunjungi
                if (!kataDikunjungi.contains(kataTetangga)) {
                    // Atur harga kata tersebut menjadi harga kata sekarang ditambah satu dan masukkan ke antrian sebagai simpul baru
                    int gn = kataSekarang.ambilgn() + 1;
                    antrian.offer(new SimpulKata(kataTetangga, gn, 0, kataSekarang));
                    // Tambah jumlah kata yang dikunjungi
                    jumlahKataDikunjungi++;
                }
            }
        }
        // Kembalikan null jika rute tidak ditemukan
        return null;
    }

    // Fungsi untuk mencari rute dari kata asal ke kata tujuan menggunakan algoritma Greedy Best First Search (GBFS)
    // Menerima parameter kata asal dan kata tujuan serta mengembalikan rute ke kata tujuan yang dibentuk fungsi buatRute dan jumlah kata dikunjungi
    public Hasil cariRuteGBFS (String kataAsal, String kataTujuan) {
        // Buat priority queue berdasarkan harga tiap kata
        Queue<SimpulKata> antrian = new PriorityQueue<>(Comparator.comparingInt(SimpulKata -> SimpulKata.ambilhn()));
        // Buat set untuk menyimpan kata yang telah dikunjungi
        Set<String> kataDikunjungi = new HashSet<>();
        // Inisialisasi jumlah kata dikunjungi
        int jumlahKataDikunjungi = 0;
        // Mulai penghitungan waktu eksekusi
        long waktuMulai = System.currentTimeMillis();
        // Tambahkan kata asal ke antrian
        antrian.offer(new SimpulKata(kataAsal, 0, hitunghn(kataAsal, kataTujuan), null));
        // Eksplorasi setiap kata pada antrian sampai antrian kosong
        while (!antrian.isEmpty()) {
            // Ambil kata dengan harga terendah dari antrian
            SimpulKata kataSekarang = antrian.poll();
            kataDikunjungi.add(kataSekarang.ambilKata());
            // Jika kata yang diambil merupakan kata tujuan
            if (kataSekarang.ambilKata().equals(kataTujuan)) {
                // Kembalikan rute dari kata asal ke kata sekarang, jumlah kata dikunjungi, dan waktu eksekusi
                List<String> rute = buatRute(kataSekarang);
                long waktuSelesai = System.currentTimeMillis();
                long waktuEksekusi = waktuSelesai - waktuMulai;
                Hasil hasil = new Hasil(rute, jumlahKataDikunjungi, waktuEksekusi);
                return hasil;
            }
            // Cek setiap kata tetangga dari kata yang sedang diambil
            for (String kataTetangga : cariTetangga(kataSekarang.ambilKata())) {
                // Jika kata tetangga belum dikunjungi
                if (!kataDikunjungi.contains(kataTetangga)) {
                    // Hitung heuristik h(n) kata tersebut dan masukkan ke antrian sebagai simpul baru
                    int hn = hitunghn(kataTetangga, kataTujuan);
                    antrian.offer(new SimpulKata(kataTetangga, 0, hn, kataSekarang));
                    // Tambah jumlah kata yang dikunjungi
                    jumlahKataDikunjungi++;
                }
            }
        }
        // Kembalikan null jika rute tidak ditemukan
        return null;
    }

    // Fungsi untuk mencari rute dari kata asal ke kata tujuan menggunakan algoritma A star (A*)
    // Menerima parameter kata asal dan kata tujuan serta mengembalikan rute ke kata tujuan yang dibentuk fungsi buatRute dan jumlah kata dikunjungi
    public Hasil cariRuteAStar (String kataAsal, String kataTujuan) {
        // Buat priority queue berdasarkan harga tiap kata
        Queue<SimpulKata> antrian = new PriorityQueue<>(Comparator.comparingInt(SimpulKata -> (SimpulKata.ambilgn() + SimpulKata.ambilhn())));
        // Buat set untuk menyimpan kata yang telah dikunjungi
        Set<String> kataDikunjungi = new HashSet<>();
        // Inisialisasi jumlah kata dikunjungi
        int jumlahKataDikunjungi = 0;
        // Mulai penghitungan waktu eksekusi
        long waktuMulai = System.currentTimeMillis();
        // Tambahkan kata asal ke antrian
        antrian.offer(new SimpulKata(kataAsal, 0, hitunghn(kataAsal, kataTujuan), null));
        // Eksplorasi setiap kata pada antrian sampai antrian kosong
        while (!antrian.isEmpty()) {
            // Ambil kata dengan harga terendah dari antrian
            SimpulKata kataSekarang = antrian.poll();
            kataDikunjungi.add(kataSekarang.ambilKata());
            // Jika kata yang diambil merupakan kata tujuan
            if (kataSekarang.ambilKata().equals(kataTujuan)) {
                // Kembalikan rute dari kata asal ke kata sekarang, jumlah kata dikunjungi, dan waktu eksekusi
                List<String> rute = buatRute(kataSekarang);
                long waktuSelesai = System.currentTimeMillis();
                long waktuEksekusi = waktuSelesai - waktuMulai;
                Hasil hasil = new Hasil(rute, jumlahKataDikunjungi, waktuEksekusi);
                return hasil;
            }
            // Cek setiap kata tetangga dari kata yang sedang diambil
            for (String kataTetangga : cariTetangga(kataSekarang.ambilKata())) {
                // Jika kata tetangga belum dikunjungi
                if (!kataDikunjungi.contains(kataTetangga)) {
                    // Hitung harga kata tersebut (g(n) yang bernilai g(n) kata sekarang ditambah satu, ditambah h(n)) dan masukkan ke antrian sebagai simpul baru
                    int gn = kataSekarang.ambilgn() + 1;
                    int hn = hitunghn(kataTetangga, kataTujuan);
                    antrian.offer(new SimpulKata(kataTetangga, gn, hn, kataSekarang));
                    // Tambah jumlah kata yang dikunjungi
                    jumlahKataDikunjungi++;
                }
            }
        }
        // Kembalikan null jika rute tidak ditemukan
        return null;
    }
}
