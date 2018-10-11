package com.arnawa.owan.koperasisimpanpinjam;

public class classdatapengajuan {
    private String Oanidanggota;
    private String Oantgl;
    private String Oanjmlpinjaman;
    private String Oanjenis;
    private String Oanket;
    private String Oanjmlangsuran;
    private String Oanalasan;
    private String Oantglupdate;
    private String Oanstatus;

    public classdatapengajuan(String idanggota, String tgl, String jmlpinjaman, String jenis, String ket,
                              String jmlangsuran, String alasan, String tglupdate, String status)
    {
        Oanidanggota = idanggota;
        Oantgl = tgl;
        Oanjmlpinjaman = jmlpinjaman;
        Oanjenis = jenis;
        Oanket = ket;
        Oanjmlangsuran = jmlangsuran;
        Oanalasan = alasan;
        Oantglupdate = tglupdate;
        Oanstatus = status;
    }

    public String Getidanggota(){
        return this.Oanidanggota;
    }

    public String Gettgl(){
        return this.Oantgl;
    }

    public String Getjmlpinjaman(){
        return this.Oanjmlpinjaman;
    }

    public String Getjenis(){
        return this.Oanjenis;
    }

    public String Getket(){
        return this.Oanket;
    }

    public String Getjmlangsuran(){
        return this.Oanjmlangsuran;
    }

    public String Getalasan(){
        return this.Oanalasan;
    }

    public String Gettglupdate(){
        return this.Oantglupdate;
    }

    public String Getstatus(){
        return this.Oanstatus;
    }
}
