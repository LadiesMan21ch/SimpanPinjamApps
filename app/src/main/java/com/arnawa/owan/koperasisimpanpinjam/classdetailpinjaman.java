package com.arnawa.owan.koperasisimpanpinjam;

public class classdetailpinjaman {
    private String Oanblnke;
    private String Oantempo;
    private String Oanpokok;
    private String Oanbunga;
    private String Oanadm;
    private String Oanjmlangsuran;

    public classdetailpinjaman(String blnke, String tempo,
                               String pokok, String bunga, String adm, String jmlangsuran)
    {
        Oanblnke = blnke;
        Oantempo = tempo;
        Oanpokok = pokok;
        Oanbunga = bunga;
        Oanadm = adm;
        Oanjmlangsuran = jmlangsuran;
    }

    public String Getblnke(){
        return this.Oanblnke;
    }
    public String Gettempo(){
        return this.Oantempo;
    }
    public String Getpokok(){
        return this.Oanpokok;
    }
    public String Getbunga(){
        return this.Oanbunga;
    }
    public String Getadm(){
        return this.Oanadm;
    }
    public String Getjmlangsuran(){
        return this.Oanjmlangsuran;
    }

}
