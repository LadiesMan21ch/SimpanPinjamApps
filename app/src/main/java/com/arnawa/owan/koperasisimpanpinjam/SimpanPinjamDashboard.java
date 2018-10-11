package com.arnawa.owan.koperasisimpanpinjam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class SimpanPinjamDashboard extends Fragment {

    String nosir = "";
    private TextView
            idAnggotakup, namaAnggtKup, jnsKlAnggKup, jabAnggKup, alamatAnggKup,
            noTlpAnggKup, tglpngajuanpinjaman, nominalPenganjuanpin,
            statuspngajuanpinKup, simpsukarelaKup, simpPokokKup, simWajibKup,
            jmlhSimpKup, pokokPinjKup, tagihanDendaKup, dibayarKup, sisaTagihanKup,
            jmlhPinjamanKup, pinjamLunasKup, pembayaranKup, tgglTempoKup;
    private ProgressDialog prgDialogLogin;
    private ImageView fotoAnggKup;
    private AsyncTask<String, Void, JSONObject> mSendData;
    int pokokD=0;
    int pokokK=0;
    int wajibD=0;
    int wajibK=0;
    int sukaD=0;
    int sukaK=0;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View simpanpinjamDashView = inflater.inflate(R.layout.fragment_simpan_pinjam_dashboard,container,false);

        return simpanpinjamDashView;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        idAnggotakup         = (TextView) getView().findViewById(R.id.tv_IdAnggotaKuperasi);
        namaAnggtKup         = (TextView) getView().findViewById(R.id.tv_NamaSPBerandaAktif);
        jnsKlAnggKup         = (TextView) getView().findViewById(R.id.tv_JenisKelaminSPBerandaAktif);
        jabAnggKup           = (TextView) getView().findViewById(R.id.tv_JabatanSPBerandaAktif);
        alamatAnggKup        = (TextView) getView().findViewById(R.id.tv_AlamatSPBerandaAktif);
        noTlpAnggKup         = (TextView) getView().findViewById(R.id.tv_NoTelpSPBerandaAktif);
        tglpngajuanpinjaman  = (TextView) getView().findViewById(R.id.tv_TanggalPengajuanSPBerandaAktif);
        nominalPenganjuanpin = (TextView) getView().findViewById(R.id.tv_NominalPengajuanSPBerandaAktif);
        statuspngajuanpinKup = (TextView) getView().findViewById(R.id.tv_StatusSPBerandaAktif);
        simpsukarelaKup      = (TextView) getView().findViewById(R.id.textSimpananSukarela);
        simpPokokKup         = (TextView) getView().findViewById(R.id.textSimpananPokok);
        simWajibKup          = (TextView) getView().findViewById(R.id.textSimpananWajib);
        jmlhSimpKup          = (TextView) getView().findViewById(R.id.textJumlahSimpanan);
        pokokPinjKup         = (TextView) getView().findViewById(R.id.textPokokPinjaman);
        tagihanDendaKup      = (TextView) getView().findViewById(R.id.textTagihanDenda);
        dibayarKup           = (TextView) getView().findViewById(R.id.textDibayar);
        sisaTagihanKup       = (TextView) getView().findViewById(R.id.textSisaTagihan);
        jmlhPinjamanKup      = (TextView) getView().findViewById(R.id.ttJumlahPinjaman);
        pinjamLunasKup       = (TextView) getView().findViewById(R.id.textPinjamanLunas);
        pembayaranKup        = (TextView) getView().findViewById(R.id.textPembayaran);
        tgglTempoKup         = (TextView) getView().findViewById(R.id.textTglTempo);

        Bundle thebundle = getActivity().getIntent().getExtras();

        nosir=thebundle.getString("id");
        mSendData = new mSendData().execute(nosir);

    }
    private class mSendData extends AsyncTask<String, Void, JSONObject>{
        @Override
        protected void onPreExecute() {
            prgDialogLogin = ProgressDialog.show(getActivity(), "Please Wait", "Retrieving data ...", true);
        }

        @Override
        protected void onPostExecute(JSONObject hasil) {
            AlertDialog.Builder aldBuild = new AlertDialog.Builder(getActivity());
            prgDialogLogin.dismiss();

            try {
                if (hasil !=null){
                    JSONArray juve = null;
                    JSONObject merda = null;

                    juve = hasil.getJSONArray("itemdashboardsp");
                    if (juve.length() > 0){
                        Intent badutIntent = new Intent(getActivity(), SimpanPinjamDashboard.class);
                        Bundle bndle = new Bundle();
                        bndle.putString("my_key", "My String");
                        SimpanPinjamDashboard mySPBFrag = new SimpanPinjamDashboard();

                        for (int a = 0; a < juve.length(); ++a ){
                            if (a==0)
                            {
                                merda = juve.getJSONArray(a).getJSONObject(0);
                                idAnggotakup.setText(merda.getString("id"));
                                namaAnggtKup.setText(merda.getString("nama"));
                                jnsKlAnggKup.setText(merda.getString("jk"));
                                jabAnggKup.setText(merda.getString("jabatan_id"));
                                alamatAnggKup.setText(merda.getString("alamat") + " , "
                                        + merda.getString("kecamatan") + " , "
                                        + merda.getString("kota") + " , "
                                        + merda.getString("provinsi"));
                                noTlpAnggKup.setText(merda.getString("notelp"));
                            }
                            else if (a==1)
                            {
                                int apa = juve.getJSONArray(a).length();
                                if (apa>0) {

                                    for (int b = 0; b < apa; ++b) {
                                        merda = juve.getJSONArray(a).getJSONObject(b);


                                        if (merda.getString("dk").contentEquals("K")) {
                                            if (merda.getString("jenis_id").contentEquals("32")) {
                                                sukaK = merda.getInt("jml_total");
                                            } else if (merda.getString("jenis_id").contentEquals("40")) {
                                                pokokK = merda.getInt("jml_total");
                                            } else if (merda.getString("jenis_id").contentEquals("41")) {
                                                wajibK = merda.getInt("jml_total");
                                            }

                                        } else if (merda.getString("dk").contentEquals("D")) {
                                            if (merda.getString("jenis_id").contentEquals("32")) {
                                                sukaD = merda.getInt("jml_total");
                                            } else if (merda.getString("jenis_id").contentEquals("40")) {
                                                pokokD = merda.getInt("jml_total");
                                            } else if (merda.getString("jenis_id").contentEquals("41")) {
                                                wajibD = merda.getInt("jml_total");
                                            }

                                        }
                                    }
                                    int totalsuka = sukaD - sukaK;
                                    simpsukarelaKup.setText(Integer.toString(totalsuka));
                                    int totalwajib = wajibD - wajibK;
                                    simWajibKup.setText(Integer.toString(totalwajib));
                                    int totalpokok = pokokD - pokokK;
                                    simpPokokKup.setText(Integer.toString(totalpokok));
                                    int totalsemua = (sukaD - sukaK) + (pokokD - pokokK) + (wajibD - wajibK);
                                    jmlhSimpKup.setText(Integer.toString(totalsemua));
                                }
                                else
                                {
                                    simpsukarelaKup.setText("0");
                                    simWajibKup.setText("0");
                                    simpPokokKup.setText("0");
                                    jmlhSimpKup.setText("0");
                                }
                            }

                            else if (a==2) {
                                merda = juve.getJSONArray(a).getJSONObject(0);
                                String stts = null;
                                int apa = juve.getJSONArray(a).length();
                                if (apa>0) {
//                                    0 = menunggu konfirmasi
//                                    1 = disetujui-tgl cair,ngambil dari tanggal
//                                    2 = ditolak
//                                    3 = terlaksana
//                                    4 = batal
                                    if (merda.getString("status").contentEquals("0")) {
                                        stts = "Menunggu Konfirmasi";
                                        statuspngajuanpinKup.setTextColor(getResources().getColor(R.color.backgroundKu));
                                    } else if (merda.getString("status").contentEquals("1")) {
                                        stts = "Disetujui-Tanggal Cair, ngambil dari tanggal";
                                        statuspngajuanpinKup.setTextColor(getResources().getColor(R.color.abuabuMuda));
                                    } else if (merda.getString("status").contentEquals("2")) {
                                        stts = "Ditolak";
                                        statuspngajuanpinKup.setTextColor(getResources().getColor(R.color.merah));
                                    } else if (merda.getString("status").contentEquals("3")) {
                                        stts = "Terlaksana";
                                        statuspngajuanpinKup.setTextColor(getResources().getColor(R.color.backgroundKu));
                                    } else {
                                        stts = "Batal";
                                    }
                                    statuspngajuanpinKup.setText(stts);
                                    tglpngajuanpinjaman.setText(merda.getString("tgl_update"));
                                    nominalPenganjuanpin.setText(merda.getString("nominal"));
                                }
                                else
                                {
                                    statuspngajuanpinKup.setText("Blm Melakukan proses");
                                    tglpngajuanpinjaman.setText("");
                                    nominalPenganjuanpin.setText("");
                                }
                            }

                            else if (a==3){

                            }
                        }
                    }

                    else
                    {
                        prgDialogLogin.dismiss();
                        aldBuild.setMessage("User atau Password Salah !!!");
                        aldBuild.show();
                    }
                }
            } catch (JSONException e) {
                prgDialogLogin.setMessage(e.toString());
                prgDialogLogin.show();
            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            try {
                return arwanaapiutils.datadaasbord(strings[0]);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
