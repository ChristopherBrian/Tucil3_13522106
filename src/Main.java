// Import library
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

// Kelas main
public class Main {
    public static void main(String[] args) {
        Set<String> kamus = new HashSet<>();
        String lokasiFile = "kamus.txt";
        // Olah file kamus.txt menjadi kamus
        try (BufferedReader pembaca = new BufferedReader(new FileReader(lokasiFile))) {
            String kataKamus;
            while ((kataKamus = pembaca.readLine()) != null) {
                kamus.add(kataKamus.trim().toLowerCase());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Input kata asal, kata tujuan, dan pilihan algoritma
        Penyelesai penyelesai = new Penyelesai(kamus);
        Scanner pemindai = new Scanner(System.in);
        System.out.println("Masukkan kata asal: ");
        String kataAsal = pemindai.nextLine().toLowerCase();
        // Validasi kata asal
        while (!kamus.contains(kataAsal)) {
            System.out.println("Kata asal tidak ditemukan, masukkan kata yang valid!");
            System.out.println("Masukkan kata asal: ");
            kataAsal = pemindai.nextLine().toLowerCase();
        }
        System.out.println("Masukkan kata tujuan: ");
        String kataTujuan = pemindai.nextLine().toLowerCase();
        // Validasi kata tujuan
        while (!kamus.contains(kataTujuan) || kataAsal.length() != kataTujuan.length()) {
            if (!kamus.contains(kataTujuan)) {
                System.out.println("Kata tujuan tidak ditemukan, masukkan kata yang valid!");
            }
            else {
                System.out.println("Panjang kata tujuan berbeda dengan panjang kata asal!");
            }
            System.out.println("Masukkan kata tujuan: ");
            kataTujuan = pemindai.nextLine().toLowerCase();
        }
        System.out.println("Pilih algoritma pencari rute (1/2/3):");
        System.out.println("1. Uniform Cost Search (UCS)");
        System.out.println("2. Greedy Best First Search (GBFS)");
        System.out.println("3. A Star (A*)");
        String algoritma = pemindai.nextLine();
        // Validasi pilihan algoritma
        while (!algoritma.equals("1") && !algoritma.equals("2") && !algoritma.equals("3")) {
            System.out.println("Pilihan algoritma tidak valid!");
            System.out.println("Pilih algoritma pencari rute (1/2/3):");
            algoritma = pemindai.nextLine();
        }
        pemindai.close();
        // Cari rute dari kata asal ke kata tujuan berdasarkan pilihan algoritma
        // Jika memilih algoritma UCS
        if (algoritma.equals("1")) {
            long waktuMulai = System.currentTimeMillis();
            List<String> listKata = penyelesai.cariRuteUCS(kataAsal, kataTujuan);
            // Jika rute ditemukan
            if (listKata != null) {
                System.out.println("Rute dari kata asal " + kataAsal + " ke kata tujuan " + kataTujuan + ":");
                for (String kata : listKata) {
                    System.out.println(kata);
                }
            }
            // Jika rute tidak ditemukan
            else {
                System.out.println("Rute tidak ditemukan");
            }
            long waktuSelesai = System.currentTimeMillis();
            long waktuEksekusi = waktuSelesai - waktuMulai;
            System.out.println("Waktu eksekusi: " + waktuEksekusi + " ms");
        }
        // Jika memilih algoritma GBFS
        else if (algoritma.equals("2")) {
            long waktuMulai = System.currentTimeMillis();
            List<String> listKata = penyelesai.cariRuteGBFS(kataAsal, kataTujuan);
            // Jika rute ditemukan
            if (listKata != null) {
                System.out.println("Rute dari kata asal " + kataAsal + " ke kata tujuan " + kataTujuan + ":");
                for (String kata : listKata) {
                    System.out.println(kata);
                }
            }
            // Jika rute tidak ditemukan
            else {
                System.out.println("Rute tidak ditemukan");
            }
            long waktuSelesai = System.currentTimeMillis();
            long waktuEksekusi = waktuSelesai - waktuMulai;
            System.out.println("Waktu eksekusi: " + waktuEksekusi + " ms");
        }
        // Jika memilih algoritma A*
        else if (algoritma.equals("3")) {
            long waktuMulai = System.currentTimeMillis();
            List<String> listKata = penyelesai.cariRuteAStar(kataAsal, kataTujuan);
            // Jika rute ditemukan
            if (listKata != null) {
                System.out.println("Rute dari kata asal " + kataAsal + " ke kata tujuan " + kataTujuan + ":");
                for (String kata : listKata) {
                    System.out.println(kata);
                }
            }
            // Jika rute tidak ditemukan
            else {
                System.out.println("Rute tidak ditemukan");
            }
            long waktuSelesai = System.currentTimeMillis();
            long waktuEksekusi = waktuSelesai - waktuMulai;
            System.out.println("Waktu eksekusi: " + waktuEksekusi + " ms");
        }
    }
}
