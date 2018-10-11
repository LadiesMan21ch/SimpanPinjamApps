package com.arnawa.owan.koperasisimpanpinjam;

public class classpinjaman {
    private String Spinjaman;
    private String SBunga;
    private String Sket;
    private String Stgl;
    private String SAngsuran;
    private String Sadm;
    private String SLama ;
    private String Stagihan;
    private String Sidpelanggan;
    private String Sstatus;
    private String Stempo;
    private String Sid;
    public classpinjaman(String id, String idpelanggan,String pinjaman,String Bunga,String ket,String tgl,
                         String Angsuran,String adm,String Lama,String tagihan, String status,
                         String tempo)
    {
        Sid = id;
        Spinjaman = pinjaman;
        SBunga = Bunga;
        Sket = ket;
        Stgl = tgl;
        SAngsuran = Angsuran;
        Sadm = adm;
        Stagihan = tagihan;
        SLama = Lama;
        Sidpelanggan = idpelanggan;
        Sstatus = status;
        Stempo = tempo;

    }
    public String Getpinjaman()
    {
        return this.Spinjaman;
    }
    public String GetBunga()
    {
        return this.SBunga;
    }
    public String Getket()
    {
        return this.Sket;
    }
    public String Getlama()
    {
        return this.SLama;
    }
    public String Gettagihan()
    {
        return this.Stagihan;
    }
    public String Getadm()
    {
        return this.Sadm;
    }
    public String GetAnsuran()
    {
        return this.SAngsuran;
    }
    public String Gettgl()
    {
        return this.Stgl;
    }
    public String Getidpelanggan()
    {
        return this.Sidpelanggan;
    }
    public String Getid()
    {
        return this.Sid;
    }
    public String Getstatus()
    {
        return this.Sstatus;
    }
    public String Gettempo()
    {
        return this.Stempo;
    }
}
