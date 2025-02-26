import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Scanner;

public class TP1test {
    
    @Before
    public void setUp() {
        // Reset variabel statis sebelum setiap test
        TP1.totalPengeluaran = 0;
        TP1.jumlahTransaksi = 0;
        TP1.totalDiskonDiterima = 0;
        TP1.pembelianTerbesar = 0;
        TP1.totalPendapatan = 0;
        TP1.pendapatanTerbesar = 0;
    }

    @Test
    public void testTambahStok() {
        assertEquals(20, TP1.TambahStok(15, 5));
    }

    @Test
    public void testTambahSaldo() {
        assertEquals(150.0, TP1.TambahSaldo(100.0, 50.0), 0.01);
    }

    @Test
    public void testUbahHarga() {
        assertEquals(200.0, TP1.ubahHarga(200.0), 0.01);
    }

    @Test
    public void testKodeVoucher() {
    String kode = TP1.KodeVoucher();
    
    // cek panjang string harus 10 karakter
    assertEquals(10, kode.length());

    // cek semua karakter adalah huruf besar (A-Z) dan persis 10 karakter
    assertTrue(kode.matches("[A-Z]{10}"));
}


    @Test
    public void testGenerateVoucher() {
        String kode = "ABCDEFGHIJ";
        String voucher = TP1.GenerateVoucher(kode);
        assertEquals(kode.length(), voucher.length());
        assertEquals("0149656941", voucher);
    }

    @Test
    public void testDiskonValid() {
        assertEquals(19, TP1.Diskon("1212332121"));
    }

    @Test
    public void testDiskonLebihDari100() {
        assertEquals(10, TP1.Diskon("9898998989"));
    }

    @Test
    public void testBeliBarang_StokTidakCukup() {
        Scanner scanner = new Scanner("5\n");
        Object[] result = TP1.beliBarang(scanner, 3, 100.0, 10.0, false, "");
        assertEquals(100.0, (double) result[0], 0.01); // Saldo tetap
        assertFalse((boolean) result[1]); // Tidak ada pembeli
        assertEquals("", result[2]); // Voucher tetap kosong
        assertEquals(3, (int) result[3]); // Stok tidak berubah
    }

    @Test
    public void testBeliBarang_JumlahBarangTidakValid() {
        Scanner scanner = new Scanner("0\n");
        Object[] result = TP1.beliBarang(scanner, 10, 100.0, 10.0, false, "");
        assertEquals(100.0, (double) result[0], 0.01);
        assertFalse((boolean) result[1]);
        assertEquals("", result[2]);
        assertEquals(10, (int) result[3]);
    }

    @Test
    public void testKirimBarang() {
        assertTrue(TP1.KirimBarang(true, false));
        assertFalse(TP1.KirimBarang(false, false));
    }

    @Test
    public void testLacakBarang() {
        assertTrue(TP1.LacakBarang(true));
        assertFalse(TP1.LacakBarang(false));
    }

    @Test
    public void testSimpanTransaksi() {
        TP1.simpanTransaksi(100.0, 10.0);
        assertEquals(100.0, TP1.totalPengeluaran, 0.01);
        assertEquals(10.0, TP1.totalDiskonDiterima, 0.01);
    }

    @Test
    public void testSimpanPenjual() {
        TP1.simpanPenjual(200.0);
        assertEquals(200.0, TP1.totalPendapatan, 0.01);
    }
}
