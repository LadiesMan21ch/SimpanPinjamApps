package com.arnawa.owan.koperasisimpanpinjam;

public class classpembayaran {
    private String Oanidanggota;
    private String Oanbayar;
    private String Oanangsuranke;
    private String Oantgl;
    private String Oanjenis;
    private String Oandenda;
    private String Oanketerangan;

    public classpembayaran(String idanggota, String bayar, String angsuranke, String tgl, String jenis, String denda, String keterangan)
    {
        Oanidanggota = idanggota;
        Oanbayar = bayar;
        Oanangsuranke = angsuranke;
        Oantgl = tgl;
        Oanjenis = jenis;
        Oandenda = denda;
        Oanketerangan = keterangan;
    }

    public String Getidanggota(){
        return this.Oanidanggota;
    }

    public String Getbayar()
    {
        return this.Oanbayar;
    }

    public String Getangsuranke()
    {
        return this.Oanangsuranke;
    }

    public String Gettgl()
    {
        return this.Oantgl;
    }

    public String Getjenis()
    {
        return this.Oanjenis;
    }

    public String Getdenda()
    {
        return this.Oandenda;
    }

    public String Getketerangan()
    {
        return this.Oanketerangan;
    }
}
