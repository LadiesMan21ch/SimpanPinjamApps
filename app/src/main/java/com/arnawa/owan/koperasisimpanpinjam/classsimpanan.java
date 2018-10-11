package com.arnawa.owan.koperasisimpanpinjam;

public class classsimpanan {
    private String Oanidanggota;
    private String Oantgl;
    private String Oanjenis;
    private String Oanketerangan;
    private String Oanjumlahsimpanan;

    public classsimpanan(String idanggota, String tgl, String jenis, String keterangan, String jumlahsimpanan)
    {
        Oanidanggota = idanggota;
        Oantgl = tgl;
        Oanjenis = jenis;
        Oanketerangan = keterangan;
        Oanjumlahsimpanan = jumlahsimpanan;
    }

    public String Getidanggota(){
        return this.Oanidanggota;
    }
    public String Gettgl() {
        return this.Oantgl;
    }
    public String Getjenis() {
        return this.Oanjenis;
    }
    public String Getketerangan() {
        return this.Oanketerangan;
    }
    public String Getjumlahsimpanan() {
        return this.Oanjumlahsimpanan;
    }
}
