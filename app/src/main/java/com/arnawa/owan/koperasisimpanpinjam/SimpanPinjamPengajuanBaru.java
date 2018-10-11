package com.arnawa.owan.koperasisimpanpinjam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SimpanPinjamPengajuanBaru extends Fragment {
    private EditText etketerangan, etJumlahPinjaman;
    private Spinner spin_tipeAngsuran, spin_lamaAngsuran, spin_jenisPinjaman;
    String nosir = "";
    private ProgressDialog prgDialogLogin;
    private AsyncTask<String, Void, JSONObject> mSendData;
    Fragment fragment = null;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        final View pengajuanBaruView =  inflater.inflate(R.layout.fragment_simpan_pinjam_pengajuan_baru,container,false);

        etketerangan = (EditText) pengajuanBaruView.findViewById(R.id.editText8);
        etJumlahPinjaman = (EditText) pengajuanBaruView.findViewById(R.id.etJumlahPinjaman);

        final TextView tglInputPinjaman = (TextView) pengajuanBaruView.findViewById(R.id.tv_tglInputPinjamanBaru);
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date_str = df.format(cal.getTime());
        tglInputPinjaman.setText(date_str);
        spin_tipeAngsuran  = (Spinner) pengajuanBaruView.findViewById(R.id.spin_tipeAngsuran);
        spin_lamaAngsuran  = (Spinner) pengajuanBaruView.findViewById(R.id.spin_lamaAngsuran);

        spin_jenisPinjaman = (Spinner) pengajuanBaruView.findViewById(R.id.spin_jenisPinjaman);
        spin_jenisPinjaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ArrayAdapter Spinerarray = ArrayAdapter.createFromResource(view.getContext(), R.array.lamaAngsuran1, android.R.layout.simple_spinner_item);
                    spin_lamaAngsuran.setAdapter(Spinerarray);
                }
                else if (position == 0) {
                    ArrayAdapter Spinerarray = ArrayAdapter.createFromResource(view.getContext(), R.array.lamaAngsuran12, android.R.layout.simple_spinner_item);
                    spin_lamaAngsuran.setAdapter(Spinerarray);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        Bundle thebundle = getActivity().getIntent().getExtras();

        nosir=thebundle.getString("id");

        Button btnKirimPengajuanPinjaman = (Button) pengajuanBaruView.findViewById(R.id.btnKirimPengajuanPinjaman);
        btnKirimPengajuanPinjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSendData = new mSendData().execute("","",nosir,tglInputPinjaman.getText().toString(),
                        spin_jenisPinjaman.getSelectedItem().toString(),etJumlahPinjaman.getText().toString(),
                        spin_lamaAngsuran.getSelectedItem().toString(),spin_tipeAngsuran.getSelectedItem().toString().toUpperCase(),
                        etketerangan.getText().toString(),"0","");

                fragment = new SimpanPinjamDashboard();
                replaceFragment(fragment);
            }
        });

        return pengajuanBaruView;
    }
    private class mSendData extends AsyncTask<String, Void, JSONObject> {
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

                    juve = hasil.getJSONArray("item");
                    if (juve.length() > 0){

//                        Intent badutIntent = new Intent(getActivity(), SimpanPinjam.class);
//                        Bundle bndle = new Bundle();
//                        bndle.putString("my_key", "My String");
//                        SimpanPinjamDashboard mySPBFrag = new SimpanPinjamDashboard();
//
//                        for (int a = 0; a < juve.length(); ++a ){
//                            merda = juve.getJSONObject(a);
//                        }
//
//                        badutIntent.putExtra("id", merda.getString("id"));
//                        badutIntent.putExtra("nor_sir", merda.getString("nor_sir"));
//                        badutIntent.putExtra("nama", merda.getString("nama") );
//                        badutIntent.putExtra("jk", merda.getString("jk"));
//                        badutIntent.putExtra("jabatan_id", merda.getString("jabatan_id"));
//                        badutIntent.putExtra("alamat", merda.getString("alamat"));
//                        badutIntent.putExtra("notelp", merda.getString("notelp"));
//
//                        mySPBFrag.setArguments(bndle);
//
//                        startActivity(badutIntent);

                    }

                    else
                    {
                        prgDialogLogin.dismiss();
                        aldBuild.setMessage("User atau Password Salah !!!");
                        aldBuild.show();
                    }
                }
            } catch (JSONException e) {
                prgDialogLogin.setMessage("User atau Password Salah !!!");
                prgDialogLogin.show();
            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            try {
                return arwanaapiutils.datapinjamanbaru(strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8],strings[9],strings[10]);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
