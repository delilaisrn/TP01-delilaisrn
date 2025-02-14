import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class TP1 {

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

        do {
            System.out.println("\nPilih menu");
            System.out.println("1. Penjual");
            System.out.println("2. Pembeli");
            System.out.println("3. Hari Selanjutnya");
            System.out.println("4. Keluar");
            System.out.print("\nPerintah: ");
            int perintah = scanner.nextInt();

            switch (perintah) {
                case 1:
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
                            int tambahstok = scanner.nextInt();
                            stok += tambahstok;
                            System.out.println("Stok berhasil ditambah! Stok saat ini: " + stok);
                        }
                        else if (subPerintah == 4) {
                            System.out.print("Masukkan harga barang yang baru: ");
                            double hargabaru = scanner.nextDouble();
                            harga = hargabaru;
                            System.out.printf("Harga barang diperbarui: %.2f\n", harga);
                        }
                        else if (subPerintah == 8) {
                            break;
                        }
                    } while (true);
                    break;
            
                case 2:
                    do {
                        System.out.println("\n===== MENU PEMBELI =====");
                        System.out.println("1. Cek Saldo");
                        System.out.println("2. Top Up Saldo");
                        System.out.println("3. Cek Harga Barang");
                        System.out.println("4. Beli Barang");
                        System.out.println("5. Generate Voucher");
                        System.out.println("6. Lacak Barang");
                        System.out.println("7. Lihat Laporan Penngeluaran");
                        System.out.println("8. Kembali ke menu utama");
                        System.out.print("\nPerintah: ");
                        int subPerintah = scanner.nextInt();
                        
                        if (subPerintah == 1) {
                            System.out.printf("Saldo saat ini: %.2f\n", saldo);
                        }
                        else if (subPerintah == 2) {
                            System.out.print("Masukkan jumlah saldo yang ingin ditambah: ");
                            double tambahsaldo = scanner.nextDouble();
                            saldo += tambahsaldo;
                            System.out.printf("Saldo berhasil ditambah! Saldo saat ini: %.2f\n", saldo);
                        }
                        else if (subPerintah == 8) {
                            break;
                        }
                    } while (true);                    
                    break;
                
                case 4:
                    System.out.println("Terima kasih telah menggunakan Burhanpedia!");
                    scanner.close();
                    return;
            }
        } while (true);

    }
}