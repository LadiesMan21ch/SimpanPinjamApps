package com.arnawa.owan.koperasisimpanpinjam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpanPinjamLaporanSimpanan extends Fragment {

    String nosir = "";
    private ProgressDialog prgDialogLogin;
    private AsyncTask<String, Void, JSONObject> mSendData;
    private ArrayList<classsimpanan> msimpan = null ;
    private OrderAdapter m_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View lapSimpananView =  inflater.inflate(R.layout.fragment_simpan_pinjam_laporan_simpanan,container,false);
        msimpan = new ArrayList<classsimpanan>();
        ListView lv = (ListView) lapSimpananView.findViewById(R.id.lvLaporanSimpanan);
        m_adapter = new OrderAdapter(getActivity(),R.id.lvLaporanSimpanan, msimpan);

        lv.setAdapter(m_adapter);
        Bundle thebundle = getActivity().getIntent().getExtras();

        nosir=thebundle.getString("id");
        mSendData = new mSendData().execute(nosir);
        //setListAdapter(m_adapter);

        return lapSimpananView;
    }

    private class OrderAdapter extends ArrayAdapter<classsimpanan> {
        private LayoutInflater mInflater;
        private ArrayList<classsimpanan> items;
        public OrderAdapter(Context context, int textViewResourceId, ArrayList<classsimpanan> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_laporan_simpanan, null);
                holder = new ViewHolder();
                holder.tv_tglpembayaran_lpsimpanan  = (TextView) convertView.findViewById(R.id.tv_tglpembayaran_lpsimpanan);
                holder.tv_jenis_lpsimpanan  = (TextView) convertView.findViewById(R.id.tv_jenis_lpsimpanan);
                holder.tv_keterangan_lpsimpanan  = (TextView) convertView.findViewById(R.id.tv_keterangan_lpsimpanan);
                holder.tv_jmlsimpanan_lpsimpanan  = (TextView) convertView.findViewById(R.id.tv_jmlsimpanan_lpsimpanan);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            classsimpanan o = items.get(position);
            if (o != null) {

                if (holder.tv_tglpembayaran_lpsimpanan != null) {
                    holder.tv_tglpembayaran_lpsimpanan.setText(o.Gettgl());
                }
                if (holder.tv_jenis_lpsimpanan != null) {
                    holder.tv_jenis_lpsimpanan.setText(o.Getjenis());
                }
                if (holder.tv_keterangan_lpsimpanan != null) {
                    holder.tv_keterangan_lpsimpanan.setText(o.Getketerangan());
                }
                if (holder.tv_jmlsimpanan_lpsimpanan != null) {
                    holder.tv_jmlsimpanan_lpsimpanan.setText(o.Getjumlahsimpanan());
                }
            }
            return convertView;
        }
        private class ViewHolder{
            TextView tv_tglpembayaran_lpsimpanan, tv_jenis_lpsimpanan, tv_keterangan_lpsimpanan, tv_jmlsimpanan_lpsimpanan;
        }
    }

    public class mSendData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            prgDialogLogin = ProgressDialog.show(getActivity(), "Please Wait", "Retrieving data ...", true);
        }

        @Override
        protected void onPostExecute(JSONObject hasil) {

            AlertDialog.Builder aldBuild = new AlertDialog.Builder(getActivity());
            prgDialogLogin.dismiss();

            try {
                msimpan = new ArrayList<classsimpanan>();
                if (hasil !=null){
                    JSONArray juve = null;
                    JSONObject merda = null;

                    juve = hasil.getJSONArray("itemlprnsimpanan");
                    if (juve.length() > 0){
                        Intent badutIntent = new Intent(getActivity(), SimpanPinjamLaporanSimpanan.class);
                        Bundle bndle = new Bundle();
                        bndle.putString("my_key", "My String");
                        // SimpanPinjamDashboard mySPBFrag = new SimpanPinjamDashboard();
                        classsimpanan datasimpanan[] = new classsimpanan[juve.getJSONArray(0).length()];
                        for (int a = 0; a < juve.getJSONArray(0).length(); ++a )
                        {
                            merda = juve.getJSONArray(0).getJSONObject(a);
                            datasimpanan[a] = new classsimpanan
                                    (
                                            merda.getString("anggota_id"), merda.getString ("tgl_transaksi" ),
                                            merda.getString ("jenis_id"),merda.getString ("keterangan"),
                                            merda.getString ("jumlah")
                                    );
                            msimpan.add(datasimpanan[a]);
                        }
                        if(msimpan != null && msimpan.size() > 0){
                            m_adapter.notifyDataSetChanged();
                            for(int i=0;i<msimpan.size();i++)
                                m_adapter.add(msimpan.get(i));
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
                return arwanaapiutils.datalaporansimpanan(strings[0]);

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