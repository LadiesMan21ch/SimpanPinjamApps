package com.arnawa.owan.koperasisimpanpinjam;

public class classsimulasipinjaman {
    private String SangsuranKe;
    private String StanggalTempo;
    private String SangsuranPokok;
    private String SbiayaBunga;
    private String SbiayaAdmin;
    private String SjmlTagihan;

    public classsimulasipinjaman(String angsuranKe, String tanggalTempo, String angsuranPokok,
                                        String biayaBunga, String biayaAdmin, String jmlTagihan)
    {
        SangsuranKe = angsuranKe;
        StanggalTempo = tanggalTempo;
        SangsuranPokok = angsuranPokok;
        SbiayaBunga = biayaBunga;
        SbiayaAdmin = biayaAdmin;
        SjmlTagihan = jmlTagihan;
    }

    public String GetangsuranKe()
    {
        return this.SangsuranKe;
    }

    public String GettanggalTempo()
    {
        return this.StanggalTempo;
    }

    public String GetangsuranPokok()
    {
        return this.SangsuranPokok;
    }

    public String GetbiayaBunga()
    {
        return this.SbiayaBunga;
    }

    public String GetbiayaAdmin()
    {
        return this.SbiayaAdmin;
    }

    public String GetjmlTagihan()
    {
        return this.SjmlTagihan;
    }
}
