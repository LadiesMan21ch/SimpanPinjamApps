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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpanPinjamLaporanPinjaman extends Fragment {

    String nosir = "";
    private ProgressDialog prgDialogLogin;
    private AsyncTask<String, Void, JSONObject> mSendData;
    private ArrayList<classpinjaman> mpinjam = null ;
    private OrderAdapter m_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View lapPinjamanView =  inflater.inflate(R.layout.fragment_simpan_pinjam_laporan_pinjaman,container,false);
        mpinjam = new ArrayList<classpinjaman>();
        ListView lv = (ListView) lapPinjamanView.findViewById(R.id.lvLaporanPinjaman);
        m_adapter = new OrderAdapter(getActivity(),R.id.lvLaporanPinjaman, mpinjam);

        lv.setAdapter(m_adapter);
        Bundle thebundle = getActivity().getIntent().getExtras();

        nosir=thebundle.getString("id");
        mSendData = new mSendData().execute(nosir);
        //setListAdapter(m_adapter);

        return lapPinjamanView;
    }

    private class OrderAdapter extends ArrayAdapter<classpinjaman> {
        private LayoutInflater mInflater;
        private ArrayList<classpinjaman> items;
        public OrderAdapter(Context context, int textViewResourceId, ArrayList<classpinjaman> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_laporan_pinjaman, null);
                holder = new ViewHolder();
                holder.tv_tglpembayaran_lppinjaman  = (TextView) convertView.findViewById(R.id.tv_tglpembayaran_lppinjaman);
                holder.textView128                  = (TextView) convertView.findViewById(R.id.textView128);
                holder.textView230                  = (TextView) convertView.findViewById(R.id.textView230);
                holder.textView232                  = (TextView) convertView.findViewById(R.id.textView232);
                holder.textView234                  = (TextView) convertView.findViewById(R.id.textView234);
                holder.textView236                  = (TextView) convertView.findViewById(R.id.textView236);
                holder.textView238                  = (TextView) convertView.findViewById(R.id.textView238);
                holder.tv_keterangan_lppinjaman     = (TextView) convertView.findViewById(R.id.tv_keterangan_lppinjaman);
                holder.tv_jatuhtempo_lppinjaman     = (TextView) convertView.findViewById(R.id.tv_jatuhtempo_lppinjaman);
                holder.tv_status_lppinjaman         = (TextView) convertView.findViewById(R.id.tv_status_lppinjaman);
                holder.bt_detailPinjaman            = (Button) convertView.findViewById(R.id.bt_detailPinjaman);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final classpinjaman o = items.get(position);
            if (o != null) {
                holder.bt_detailPinjaman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent owanintent = new Intent(getActivity(), DetailPinjaman.class);
                        owanintent.putExtra("id",o.Getid());
                        startActivity(owanintent);
                    }
                });
                if (holder.textView128 != null) {
                    holder.textView128.setText(o.Getpinjaman());
                }
                if(holder.textView234 != null){
                    holder.textView234.setText(o.GetBunga());
                }
                if(holder.tv_keterangan_lppinjaman != null){
                    holder.tv_keterangan_lppinjaman.setText(o.Getket());
                }
                if(holder.textView232 != null){
                    holder.textView232.setText(o.Getlama());
                }
                if(holder.textView238 != null){
                    holder.textView238.setText(o.Gettagihan());
                }
                if(holder.textView236 != null){
                    holder.textView236.setText(o.Getadm());
                }
                if(holder.textView230 != null){
                    holder.textView230.setText(o.GetAnsuran());
                }
                if(holder.tv_tglpembayaran_lppinjaman != null){
                    holder.tv_tglpembayaran_lppinjaman.setText(o.Gettgl());
                }
                if(holder.tv_status_lppinjaman != null){
                    holder.tv_status_lppinjaman.setText(o.Getstatus());
                }
                if(holder.tv_jatuhtempo_lppinjaman != null){
                    holder.tv_jatuhtempo_lppinjaman.setText(o.Gettempo());
                }
            }
            return convertView;
        }
        private class ViewHolder{
            TextView tv_tglpembayaran_lppinjaman, textView128, textView230, textView232, textView234, textView236,
                    textView238, tv_keterangan_lppinjaman, tv_jatuhtempo_lppinjaman, tv_status_lppinjaman;

            Button bt_detailPinjaman;
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
                mpinjam = new ArrayList<classpinjaman>();
                if (hasil !=null){
                    JSONArray juve = null;
                    JSONObject merda = null;

                    juve = hasil.getJSONArray("itemlprnpinjaman");
                    if (juve.length() > 0){
                        Intent badutIntent = new Intent(getActivity(), SimpanPinjamLaporanPinjaman.class);
                        Bundle bndle = new Bundle();
                        bndle.putString("my_key", "My String");
                        // SimpanPinjamDashboard mySPBFrag = new SimpanPinjamDashboard();
                        classpinjaman datapinjaman[] = new classpinjaman[juve.getJSONArray(0).length()];
                        for (int a = 0; a < juve.getJSONArray(0).length(); ++a )
                        {
                            merda = juve.getJSONArray(0).getJSONObject(a);
                            datapinjaman[a] = new classpinjaman
                                    (
                                            merda.getString("id"), merda.getString("anggota_id"),
                                            merda.getString ("jumlah" ),
                                            merda.getString ("bunga_pinjaman"),merda.getString ("keterangan"),
                                            merda.getString ("tgl_pinjam"),merda.getString("ags_per_bulan"),
                                            merda.getString ("biaya_adm"),merda.getString ("lama_angsuran"),
                                            merda.getString ("tagihan"),merda.getString ("lunas"),
                                            merda.getString ("tempo")
                                    );
                            mpinjam.add(datapinjaman[a]);
                        }
                        if(mpinjam != null && mpinjam.size() > 0){
                            m_adapter.notifyDataSetChanged();
                            for(int i=0;i<mpinjam.size();i++)
                                m_adapter.add(mpinjam.get(i));
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
                return arwanaapiutils.datalaporanpinjaman(strings[0]);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
