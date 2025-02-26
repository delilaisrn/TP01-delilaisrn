import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class TP1 {
    // variable untuk menyimpan data transaksi
    public static double totalPengeluaran = 0;
    public static int jumlahTransaksi = 0;
    public static double totalDiskonDiterima = 0;
    public static double pembelianTerbesar = 0;

    public static double totalPendapatan = 0;
    public static double pendapatanTerbesar = 0;

    // fungsi untuk menambah stok barang
    public static int TambahStok(int stok_awal, int stok_tambahan) {
        return stok_awal + stok_tambahan;
    }

    // fungsi tambah saldo
    public static double TambahSaldo(double saldo_awal, double saldo_tambahan) {
        return saldo_awal + saldo_tambahan;
    }

    // fungsi ubah harga barang
    public static double ubahHarga(double hargaBaru) {
        return hargaBaru;
    }

    // fungsi untuk bikin kode voucher (huruf)
    public static String KodeVoucher() {
        Random random = new Random();
        StringBuilder kode = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            char karakter = (char) ('A' + random.nextInt(26));
            kode.append(karakter);
        }
        return kode.toString();
    }

    // fungsi konversi kode voucher ke angka
    public static String GenerateVoucher(String kode) {
        StringBuilder voucher = new StringBuilder();
    
        for (int i = 0; i < kode.length(); i++) {
            int nilai = (kode.charAt(i) - 65) + 10; // kalo di ASCII A = 65, B = 66, dst
            int angka = (nilai * i) % 10; // Menggunakan indeks mulai dari 0
            voucher.append(angka);
        }
    
        return voucher.toString();
    }
    

    // fungsi hitung diskon berdasarkan kode voucher
    public static int Diskon(String voucher) {
        int panjang = voucher.length();
        int totalDiskon = 0;

        for (int i = 0; i < panjang / 2; i++) {
            int depan = Character.getNumericValue(voucher.charAt(i));
            int belakang = Character.getNumericValue(voucher.charAt(panjang - 1 - i));
            totalDiskon += depan * belakang;
        }

        // untuk diskon yang lebih dari 100%
        if (panjang == 3) {
            totalDiskon += Character.getNumericValue(voucher.charAt(1));
        }

        // Jika diskon lebih dari 100%, ulangi proses secara rekursif
        if (totalDiskon > 100) {
            return Diskon(String.valueOf(totalDiskon));
        }

        return totalDiskon;
    }

    // fungsi untuk beli barang
    public static Object[] beliBarang(Scanner scanner, int stok, double saldo, double harga, boolean adaPembeli, String voucher) {
        System.out.print("Masukkan jumlah barang yang ingin dibeli: ");
        int jumlahBarang = scanner.nextInt();
        // validasi input
        if (stok < jumlahBarang) {
            System.out.println("Stok tidak mencukupi!");
        }
        else if (jumlahBarang <= 0) {
            System.out.println("Jumlah barang tidak valid!");
        }
        else {
            double totalHarga = jumlahBarang * harga;
            while (true) {
                System.out.println("Masukkan kode voucher");
                System.out.println("Jika tidak ada, ketik 'skip'");
                System.out.println("Jika ingin buat, ketik 'generate'");
                System.out.println("================================");
                System.out.print("Kode: ");
                String kode = scanner.next().toLowerCase();

                if (kode.equals("skip")) {
                    double hargaPajak = totalHarga + totalHarga * 0.03;
                    if (saldo >= hargaPajak) {
                        adaPembeli = true;
                        saldo -= hargaPajak; // saldo dikurang harga setelah pajak
                        stok -= jumlahBarang;
                        simpanTransaksi(hargaPajak, 0);
                        simpanPenjual(totalHarga);
                        System.out.printf("Harga tanpa diskon: %.2f\n", hargaPajak);
                        System.out.printf("Pembelian sukses! Saldo saat ini: %.2f\n", saldo);
                    }
                    else {
                        System.out.println("Saldo tidak mencukupi, silahkan Top Up saldo anda.");
                    }
                    break;
                }
                else if (kode.equals("generate")) {
                        voucher = GenerateVoucher(KodeVoucher());
                        System.out.println("Voucher berhasil dibuat: " + voucher);
                } 
                else if (kode.equals(voucher)) {
                        int diskon = Diskon(voucher);
                        double hargaDiskon = totalHarga - (totalHarga * diskon / 100);
                        double hargaDiskonPajak = hargaDiskon + (hargaDiskon * 0.03);
        
                        if (saldo >= hargaDiskonPajak) {
                            adaPembeli = true;
                            saldo -= hargaDiskonPajak;
                            stok -= jumlahBarang;
                            voucher = ""; // mereset voucher karena sudah dipakai
                            simpanTransaksi(hargaDiskonPajak, hargaDiskon);
                            simpanPenjual(hargaDiskon);
                            System.out.printf("Voucher berhasil digunakan! Harga setelah diskon: %.2f\n", hargaDiskonPajak);
                            System.out.printf("Pembelian sukses! Saldo saat ini: %.2f\n", saldo);
                        }
                        else {
                            System.out.println("Saldo tidak mencukupi!");
                        }
                        break;
                }
                else {
                    System.out.println("Kode voucher tidak valid!");
                }
            }
        } 
        return new Object[]{saldo, adaPembeli, voucher, stok};
    }

    // fungsi untuk kirim barang
    public static boolean KirimBarang(boolean adaPembeli, boolean kirimBarang) {
        if (adaPembeli) {
            kirimBarang = true;
            System.out.println("Barang sedang dalam perjalanan.");
        }
        else {
            System.out.println("Tidak ada barang yang bisa dikirim");
        }
        return kirimBarang;
    }

    // fungsi lacak barang
    public static boolean LacakBarang(boolean kirimBarang) {
        if (kirimBarang) {
            System.out.println("Status pengiriman: Sending");
        }
        else {
            System.out.println("Tidak ada barang yang sedang dikirim.");
        }
        return kirimBarang;
    }

    // fungsi hari selanjutnya
    public static boolean[] hariSelanjutnya(boolean adaPembeli, boolean kirimBarang, LocalDate currentDate, DateTimeFormatter formatter) {
        System.out.println("Tanggal: " + currentDate.plusDays(1).format(formatter));

        // jika sudah kirim barang
        if (kirimBarang) {
            System.out.println("Hari telah berganti. Barang sudah sampai!");
            adaPembeli = false; // reset
            kirimBarang = false;
        }
        else {
            System.out.println("Pok pok pok!");;
        }
        return new boolean[]{adaPembeli, kirimBarang};
    }

    // fungsi untuk menyimpan transaksi pembeli
    public static void simpanTransaksi(double jumlah, double diskon) {
        totalPengeluaran += jumlah;
        jumlahTransaksi++; //dapat digunakan penjual dan pembeli
        totalDiskonDiterima += diskon;
        if (jumlah > pembelianTerbesar) {
            pembelianTerbesar = jumlah;
        }
    }
    
    // fungsi untuk menyimpan transaksi penjual
    public static void simpanPenjual(double jumlah) {
        totalPendapatan += jumlah;
        if (jumlah > pendapatanTerbesar) {
            pendapatanTerbesar = jumlah;
        }
    }

    // fungsi laporan pengeluaran
    public static String laporanPengeluaran() {
        if (jumlahTransaksi == 0) {
            return "Belum ada transaksi.";
        }

        double rataPengeluaran = totalPengeluaran / jumlahTransaksi;

        return String.format(
            "\n===== Laporan Pengeluaran =====\n" +
            "Total Pengeluaran   : %.2f\n" +
            "Jumlah Transaksi    : %d\n" +
            "Rata-rata Pengeluaran : %.2f\n" +
            "Total Diskon Diterima: %.2f\n" +
            "Pembelian Terbesar  : %.2f\n" +
            "================================",
            totalPengeluaran, jumlahTransaksi, rataPengeluaran, totalDiskonDiterima, pembelianTerbesar
        );
    }

    // fungsi laporan pendapatan
    public static String laporanPendapatan() {
        if (jumlahTransaksi == 0) {
            return "Belum ada pendapatan.";
        }

        double rataPendapatan = totalPendapatan / jumlahTransaksi;

        return String.format(
            "\n===== Laporan Pendapatan =====\n" +
            "Total Pendapatan   : %.2f\n" +
            "Jumlah Transaksi    : %d\n" +
            "Rata-rata Pendapatan : %.2f\n" +
            "Total Diskon Diberikan: %.2f\n" +
            "Pendapatan Terbesar  : %.2f\n" +
            "================================",
            totalPendapatan, jumlahTransaksi, rataPendapatan, totalDiskonDiterima, pendapatanTerbesar
        );
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=============================================================");
        System.out.println("\n  ____             _                 _____         _ _       \r\n" +
                " |  _ \\           | |               |  __ \\       | (_)      \r\n" +
                " | |_) |_   _ _ __| |__   __ _ _ __ | |__) |__  __| |_  __ _ \r\n" +
                " |  _ <| | | | '__| '_ \\ / _` | '_ \\|  ___/ _ \\/ _` | |/ _` |\r\n" +
                " | |_) | |_| | |  | | | | (_| | | | | |  |  __/ (_| | | (_| |\r\n" +
                " |____/ \\__,_|_|  |_| |_|\\__,_|_| |_|_|   \\___|\\__,_|_|\\__,_|\r\n" +
                "                                                             \r\n" +
                "                                                             ");
        System.out.println("=============================================================");
        System.out.println("============== Selamat datang di Burhanpedia! ===============");
        System.out.println("=============================================================\n");

        System.out.print("Masukkan stok awal: ");
        int stok = scanner.nextInt();

        System.out.print("Masukkan harga barang: ");
        double harga = scanner.nextDouble();

        System.out.print("Masukkan saldo awal: ");
        double saldo = scanner.nextDouble();

        String voucher = "";

        boolean adaPembeli = false;
        boolean kirimBarang = false;

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

        // menu utama
        do {
            System.out.println("\nPilih menu");
            System.out.println("1. Penjual");
            System.out.println("2. Pembeli");
            System.out.println("3. Hari Selanjutnya");
            System.out.println("4. Keluar");
            System.out.print("\nPerintah: ");
            int perintah = scanner.nextInt();

            if (perintah == 1) {
                // menu penjual
                do {
                    System.out.println("\n===== MENU PENJUAL =====");
                    System.out.println("1. Cek Stok");
                    System.out.println("2. Cek Harga Barang");
                    System.out.println("3. Tambah Stok");
                    System.out.println("4. Ubah Harga Barang");
                    System.out.println("5. Generate Voucher");
                    System.out.println("6. Kirim Barang");
                    System.out.println("7. Lihat Laporan Pendapatan");
                    System.out.println("8. Kembali ke menu utama");
                    System.out.print("\nPerintah: ");
                    int subPerintah = scanner.nextInt();

                    if (subPerintah == 1) {
                        System.out.println("Stok saat ini: " + stok);
                    }
                    else if (subPerintah == 2) {
                        System.out.printf("Harga Barang saat ini: %.2f\n", harga);
                    }
                    else if (subPerintah == 3) {
                        System.out.print("Masukkan jumlah stok yang ingin ditambah: ");
                        int stok_tambahan = scanner.nextInt();
                        // validasi input
                        if (stok_tambahan > 0) {
                            stok = TambahStok(stok, stok_tambahan);
                            System.out.println("Stok berhasil ditambah! Stok saat ini: " + stok);
                        }
                        else {
                            System.out.println("Nominal tidak valid!");
                        }
                    }
                    else if (subPerintah == 4) {
                        System.out.print("Masukkan harga barang yang baru: ");
                        double hargabaru = scanner.nextDouble();
                        // validasi input
                        if (hargabaru > 0) {
                            harga = ubahHarga(hargabaru);
                            System.out.printf("Harga barang diperbarui: %.2f\n", harga);
                        }
                        else {
                            System.out.println("Nominal tidak valid!");
                        }
                    }
                    else if (subPerintah == 5) {
                        voucher = GenerateVoucher(KodeVoucher());
                        System.out.println("Voucher berhasil dibuat: " + voucher);
                    }
                    else if (subPerintah == 6) {
                        kirimBarang = KirimBarang(adaPembeli, kirimBarang);
                    }
                    else if (subPerintah == 7) {
                        System.out.println(laporanPendapatan());
                    }
                    else if (subPerintah == 8) {
                        break;
                    }
                    else {
                        System.out.println("Pilihan tidak valid!");
                    }
                } while (true);
            }    
            else if (perintah == 2) {
                // menu pembeli
                do {
                    System.out.println("\n===== MENU PEMBELI =====");
                    System.out.println("1. Cek Saldo");
                    System.out.println("2. Top Up Saldo");
                    System.out.println("3. Cek Harga Barang");
                    System.out.println("4. Beli Barang");
                    System.out.println("5. Generate Voucher");
                    System.out.println("6. Lacak Barang");
                    System.out.println("7. Lihat Laporan Pengeluaran");
                    System.out.println("8. Kembali ke menu utama");
                    System.out.print("\nPerintah: ");
                    int subPerintah = scanner.nextInt();
                    
                    if (subPerintah == 1) {
                        System.out.printf("Saldo saat ini: %.2f\n", saldo);
                    }
                    else if (subPerintah == 2) {
                        System.out.print("Masukkan jumlah saldo yang ingin ditambah: ");
                        double saldo_tambahan = scanner.nextDouble();
                        // validasi input
                        if (saldo_tambahan > 0) {
                            saldo = TambahSaldo(saldo, saldo_tambahan);
                            System.out.printf("Saldo berhasil ditambah! Saldo saat ini: %.2f\n", saldo);
                        }
                        else {
                            System.out.println("Nominal tidak valid!");
                        }
                    }
                    else if (subPerintah == 3) {
                        System.out.printf("Harga Barang saat ini: %.2f\n", harga);
                    }
                    else if (subPerintah == 4) {
                        Object[] hasil = beliBarang(scanner, stok, saldo, harga, adaPembeli, voucher);
                        saldo = (double) hasil[0];
                        adaPembeli = (boolean) hasil[1];
                        voucher = (String) hasil[2];
                        stok = (int) hasil[3];
                    } 
                    else if (subPerintah == 5) {
                        voucher = GenerateVoucher(KodeVoucher());
                        System.out.println("Voucher berhasil dibuat: " + voucher);
                    }
                    else if (subPerintah == 6) {
                        kirimBarang = LacakBarang(kirimBarang);
                    }
                    else if (subPerintah == 7) {
                        System.out.println(laporanPengeluaran());
                    }
                    else if (subPerintah == 8) {
                        break;
                    }
                    else {
                        System.out.println("Pilihan tidak valid!");
                    }
                } while (true);                    
                break;
            }
            else if (perintah == 3) {
                boolean[] status = hariSelanjutnya(adaPembeli, kirimBarang, currentDate, formatter);
                adaPembeli = status[0];
                kirimBarang = status[1];
                currentDate = currentDate.plusDays(1);
            }
            else if (perintah == 4) {
                System.out.println("Terima kasih telah menggunakan Burhanpedia!");
                scanner.close();
                return;
            }
            else {
                System.out.println("Pilihan tidak valid!");
            }
        } while (true);

    }
}