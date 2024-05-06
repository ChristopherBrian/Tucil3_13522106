// Import library
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;

// Kelas Main
public class Main {
    // Atribut kelas
    private JFrame frame;
    private JTextField kataAsalField;
    private JTextField kataTujuanField;
    private JComboBox<String> algoritmaComboBox;
    private JTextArea infoArea;
    private Set<String> kamus;

    // Konstruktor
    public Main(Set<String> kamus) {
        this.kamus = kamus;
        frame = new JFrame("Word Ladder Solver");
        frame.setSize(400, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));
        JLabel kataAsalLabel = new JLabel("Kata Asal:");
        kataAsalField = new JTextField();
        kataAsalField.setPreferredSize(new Dimension(200, 20));
        JLabel kataTujuanLabel = new JLabel("Kata Tujuan:");
        kataTujuanField = new JTextField();
        kataTujuanField.setPreferredSize(new Dimension(200, 20));
        JLabel algoritmaLabel = new JLabel("Pilih Algoritma:");
        String[] pilihanAlgoritma = {"Uniform Cost Search (UCS)", "Greedy Best First Search (GBFS)", "A Star (A*)"};
        algoritmaComboBox = new JComboBox<>(pilihanAlgoritma);
        algoritmaComboBox.setPreferredSize(new Dimension(200, 20));
        mainPanel.add(kataAsalLabel);
        mainPanel.add(kataAsalField);
        mainPanel.add(kataTujuanLabel);
        mainPanel.add(kataTujuanField);
        mainPanel.add(algoritmaLabel);
        mainPanel.add(algoritmaComboBox);

        frame.add(mainPanel); // Tambahkan mainPanel ke frame

        JPanel infoPanel = new JPanel(); // Buat panel baru untuk infoArea
        infoPanel.setLayout(new BorderLayout()); // Gunakan BorderLayout agar infoArea dapat memenuhi seluruh ruang
        JLabel infoLabel = new JLabel("Informasi:");
        infoArea = new JTextArea();
        infoArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 650)); // Tinggi diperbesar
        infoPanel.add(infoLabel, BorderLayout.NORTH); // Tambahkan infoLabel ke bagian atas infoPanel
        infoPanel.add(scrollPane, BorderLayout.CENTER); // Tambahkan scrollPane ke bagian tengah infoPanel

        frame.add(infoPanel); // Tambahkan infoPanel ke frame

        JButton cariButton = new JButton("Cari Rute");
        cariButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Menerima input dari field
                String kataAsal = kataAsalField.getText().trim().toLowerCase();
                String kataTujuan = kataTujuanField.getText().trim().toLowerCase();
                String algoritma = (String) algoritmaComboBox.getSelectedItem();

                // Validasi kata asal dan kata tujuan
                if (!kataValid(kataAsal) || !kataValid(kataTujuan)) {
                    JOptionPane.showMessageDialog(frame, "Kata asal atau kata tujuan tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validasi panjang kata asal dan kata tujuan
                if (kataAsal.length() != kataTujuan.length()) {
                    JOptionPane.showMessageDialog(frame, "Panjang kata asal dan kata tujuan harus sama!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (kataValid(kataAsal) && kataValid(kataTujuan) && (kataAsal.length() == kataTujuan.length())) {
                    Penyelesai penyelesai = new Penyelesai(kamus);
                    // Pilihan algoritma UCS
                    if (algoritma.equals("Uniform Cost Search (UCS)")) {
                        Hasil hasil = penyelesai.cariRuteUCS(kataAsal, kataTujuan);
                        showInfo(hasil);
                    }
                    // Pilihan algoritma GBFS
                    else if (algoritma.equals("Greedy Best First Search (GBFS)")) {
                        Hasil hasil = penyelesai.cariRuteGBFS(kataAsal, kataTujuan);
                        showInfo(hasil);
                    }
                    // Pilihan algoritma A*
                    else if (algoritma.equals("A Star (A*)")) {
                        Hasil hasil = penyelesai.cariRuteAStar(kataAsal, kataTujuan);
                        showInfo(hasil);
                    }
                }
            }
        });

        mainPanel.add(cariButton);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Fungsi untuk mengeluarkan hasil di GUI
    // Menerima parameter hasil
    private void showInfo(Hasil hasil) {
        // Jika rute ditemukan
        if (hasil != null) {
            // Mengeluarkan informasi pada GUI
            java.util.List<String> rute = hasil.ambilRute();
            int jumlahKataDikunjungi = hasil.ambilJumlahKataDikunjungi();
            int panjangRute = rute.size() - 1;
            long waktuEksekusi = hasil.ambilWaktuEksekusi();

            StringBuilder sb = new StringBuilder();
            sb.append("Jumlah kata dikunjungi: ").append(jumlahKataDikunjungi).append("\n");
            sb.append("Panjang rute: ").append(panjangRute).append("\n");
            sb.append("Waktu eksekusi: ").append(waktuEksekusi).append(" ms\n");
            sb.append("Rute:\n");
            for (String kata : rute) {
                sb.append(kata).append("\n");
            }

            infoArea.setText(sb.toString());
        }
        // Jika rute tidak ditemukan
        else {
            infoArea.setText("Rute tidak ditemukan");
        }
    }

    // Fungsi untuk mengecek validitas kata
    // Menerima parameter kata dan mengembalikan boolean validitas kata
    private boolean kataValid(String kata) {
        return kamus.contains(kata);
    }

    // Fungsi main
    public static void main(String[] args) {
        Set<String> kamus = new HashSet<>();
        String lokasiFile = "kamus.txt";
        // Olah file kamus.txt menjadi kamus
        try (Scanner scanner = new Scanner(new FileReader(lokasiFile))) {
            while (scanner.hasNextLine()) {
                String kataKamus = scanner.nextLine().trim().toLowerCase();
                kamus.add(kataKamus);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Menjalankan GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main(kamus);
            }
        });
    }
}
