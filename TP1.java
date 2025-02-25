import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class TP1 {
    public static int TambahStok(int stok_awal, int stok_tambahan) {
        return stok_awal + stok_tambahan;
    }
    public static double TambahSaldo(double saldo_awal, double saldo_tambahan) {
        return saldo_awal + saldo_tambahan;
    }
    public static double ubahHarga(double hargaAwal, double hargaBaru) {
        return hargaBaru;
    }
    public static String KodeVoucher() {
        Random random = new Random();
        StringBuilder kode = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            char karakter = (char) ('A' + random.nextInt(26));
            kode.append(karakter);
        }
        return kode.toString();
    }
    public static String GenerateVoucher(String kode) {
        StringBuilder voucher = new StringBuilder();

        for (int i = 0; i < kode.length(); i++) {
            int code93 = (int) kode.charAt(i);
            int angka = (code93 * (i + 1)) % 10;
            voucher.append(angka);
        }
        return voucher.toString();
    }
    public static int Diskon(String voucher) {
        int panjang = voucher.length();
        int totalDiskon = 0;

        for (int i = 0; i < panjang / 2; i++) {
            int depan = Character.getNumericValue(voucher.charAt(i));
            int belakang = Character.getNumericValue(voucher.charAt(panjang - 1 - i));
            totalDiskon += depan * belakang;
        }

        // untuk diskon yang lebih dari 100% dengan jumlah digit ganjil
        if (panjang % 2 == 1) {
            totalDiskon += Character.getNumericValue(voucher.charAt(panjang / 2));
        }

        // Jika diskon lebih dari 100%, ulangi proses secara rekursif
        if (totalDiskon > 100) {
            return Diskon(String.valueOf(totalDiskon));
        }

        return totalDiskon;
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
                            int stok_tambahan = scanner.nextInt();
                            stok = TambahStok(stok, stok_tambahan);
                            System.out.println("Stok berhasil ditambah! Stok saat ini: " + stok);
                        }
                        else if (subPerintah == 4) {
                            System.out.print("Masukkan harga barang yang baru: ");
                            double hargabaru = scanner.nextDouble();
                            harga = ubahHarga(harga, hargabaru);
                            System.out.printf("Harga barang diperbarui: %.2f\n", harga);
                        }
                        else if (subPerintah == 5) {
                            voucher = GenerateVoucher(KodeVoucher());
                            System.out.println("Voucher berhasil dibuat: " + voucher);
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
                            double saldo_tambahan = scanner.nextDouble();
                            saldo = TambahSaldo(saldo, saldo_tambahan);
                            System.out.printf("Saldo berhasil ditambah! Saldo saat ini: %.2f\n", saldo);
                        }
                        else if (subPerintah == 3) {
                            System.out.printf("Harga Barang saat ini: %.2f\n", harga);
                        }
                        else if (subPerintah == 4) {
                            System.out.print("Masukkan jumlah barang yang ingin dibeli: ");
                            while (true) {
                                int jumlahBarang = scanner.nextInt();
                                double totalHarga = jumlahBarang * harga;
                                System.out.println("Masukkan kode voucher");
                                System.out.println("Jika tidak ada, ketik 'skip'");
                                System.out.println("Jika ingin buat, ketik 'generate'");
                                System.out.println("================================");
                                System.out.print("Kode: ");
                                String kode = scanner.next().toLowerCase();

                                if (kode.equals("skip")) {
                                    double hargaPajak = totalHarga + totalHarga * 0.03;
                                    if (saldo >= hargaPajak) {
                                        saldo -= hargaPajak;
                                        System.out.printf("Harga tanpa diskon: %.2f\n", hargaPajak);
                                        System.out.printf("Pembelian sukses! Saldo saat ini: %.2f\n", saldo);
                                    }
                                    else {
                                        System.out.println("Saldo tidak mencukupi, silahkan Top Up saldo anda");
                                    }
                                    break;
                                }

                                else if (kode.equals("generate")) {
                                    voucher = GenerateVoucher(KodeVoucher());
                                    System.out.println("Voucher berhasil dibuat: " + voucher);
                                }
                                else if (kode.equals(voucher)) {
                                    int diskon = Diskon(voucher);
                                    double hargaDiskonPajak = (totalHarga - (totalHarga * diskon / 100)) * 0.03;
                                    if (saldo >= hargaDiskonPajak) {
                                        saldo -= hargaDiskonPajak;
                                        System.out.printf("Voucher berhasil digunakan! Harga setelah diskon: %.2f\n", hargaDiskonPajak);
                                        System.out.printf("Pembelian sukses! Saldo saat ini: %.2f\n", saldo);
                                    }
                                    else {
                                        System.out.println("Saldo tidak mencukupi, silahkan Top Up saldo anda");
                                    }
                                    break;
                                }
                                else {
                                    System.out.println("Kode tidak terdaftar! silahkan coba lagi");
                                }
                            }
                        } 
                        else if (subPerintah == 5) {
                            voucher = GenerateVoucher(KodeVoucher());
                            System.out.println("Voucher berhasil dibuat: " + voucher);
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