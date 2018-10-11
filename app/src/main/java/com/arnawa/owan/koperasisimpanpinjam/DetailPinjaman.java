package com.arnawa.owan.koperasisimpanpinjam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DetailPinjaman extends AppCompatActivity {

    String nosir = "";
    private ProgressDialog prgDialogLogin;
    private AsyncTask<String, Void, JSONObject> mSendData;
    private ArrayList<classdetailpinjaman> mdetail = null ;
    private DetailPinjaman.OrderAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjaman);

        Button btn_keluarDetailPinjaman = (Button) findViewById(R.id.btn_keluarDetailPinjaman);
        btn_keluarDetailPinjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        mdetail = new ArrayList<classdetailpinjaman>();
        ListView lv = (ListView) findViewById(R.id.lv_detailPinjaman);
        m_adapter = new DetailPinjaman.OrderAdapter(this,R.id.lv_detailPinjaman, mdetail);

        lv.setAdapter(m_adapter);
        Bundle thebundle = this.getIntent().getExtras();

        nosir=thebundle.getString("id");
        mSendData = new mSendData().execute(nosir);
    }

    private class OrderAdapter extends ArrayAdapter<classdetailpinjaman> {
        private LayoutInflater mInflater;
        private ArrayList<classdetailpinjaman> items;
        public OrderAdapter(Context context, int textViewResourceId, ArrayList<classdetailpinjaman> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            DetailPinjaman.OrderAdapter.ViewHolder holder;
            if(convertView == null){
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_detail_pinjaman, null);
                holder = new DetailPinjaman.OrderAdapter.ViewHolder();
                holder.tv_bulanKe              = (TextView) convertView.findViewById(R.id.tv_bulanKe);
                holder.tv_tglTempo             = (TextView) convertView.findViewById(R.id.tv_tglTempo);
                holder.tv_angsuranPokokJml     = (TextView) convertView.findViewById(R.id.tv_angsuranPokokJml);
                holder.tv_angsuranBungaJml     = (TextView) convertView.findViewById(R.id.tv_angsuranBungaJml);
                holder.tv_biayaAdministrasiJml = (TextView) convertView.findViewById(R.id.tv_biayaAdministrasiJml);
                holder.tv_jumlahAngsuranJml    = (TextView) convertView.findViewById(R.id.tv_jumlahAngsuranJml);

                convertView.setTag(holder);
            } else {
                holder = (DetailPinjaman.OrderAdapter.ViewHolder) convertView.getTag();
            }
            classdetailpinjaman o = items.get(position);
            if (o != null) {

                if (holder.tv_bulanKe != null) {
                    holder.tv_bulanKe.setText(o.Getblnke());
                }
                if (holder.tv_tglTempo != null) {
                    holder.tv_tglTempo.setText(o.Gettempo());
                }
                if (holder.tv_angsuranPokokJml != null) {
                    holder.tv_angsuranPokokJml.setText(o.Getpokok());
                }
                if (holder.tv_angsuranBungaJml != null) {
                    holder.tv_angsuranBungaJml.setText(o.Getbunga());
                }
                if (holder.tv_biayaAdministrasiJml != null) {
                    holder.tv_biayaAdministrasiJml.setText(o.Getadm());
                }
                if (holder.tv_jumlahAngsuranJml != null) {
                    holder.tv_jumlahAngsuranJml.setText(o.Getjmlangsuran());
                }
            }
            return convertView;
        }
        private class ViewHolder{
            TextView tv_bulanKe, tv_tglTempo, tv_angsuranPokokJml,
                    tv_angsuranBungaJml, tv_biayaAdministrasiJml, tv_jumlahAngsuranJml;
        }
    }

    public class mSendData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            prgDialogLogin = ProgressDialog.show(DetailPinjaman.this, "Please Wait", "Retrieving data ...", true);
        }

        @Override
        protected void onPostExecute(JSONObject hasil) {

            AlertDialog.Builder aldBuild = new AlertDialog.Builder(DetailPinjaman.this);
            prgDialogLogin.dismiss();

            try {
                mdetail = new ArrayList<classdetailpinjaman>();
                if (hasil !=null){
                    JSONArray manchunian = null;
                    JSONObject owan = null;

                    manchunian = hasil.getJSONArray("itemlprndetail");
                    if (manchunian.length() > 0){
                        Bundle bndle = new Bundle();
                        bndle.putString("my_key", "My String");
                        classdetailpinjaman datadetailpinjaman[] = new classdetailpinjaman[manchunian.getJSONArray(0).length()];
                        for (int oan = 0; oan < manchunian.getJSONArray(0).length(); ++oan )
                        {
                            owan = manchunian.getJSONArray(0).getJSONObject(oan);
                            datadetailpinjaman[oan] = new classdetailpinjaman
                                    (
                                            owan.getString ("tgl_pinjam"), owan.getString ("tgl_tempo" ),
                                            owan.getString ("angsuran_pokok"),owan.getString ("bunga_pinjaman"),
                                            owan.getString ("biaya_adm"),owan.getString("jumlah_ags")
                                    );
                            mdetail.add(datadetailpinjaman[oan]);
                        }
                        if(mdetail != null && mdetail.size() > 0){
                            m_adapter.notifyDataSetChanged();
                            for(int i=0;i<mdetail.size();i++)
                                m_adapter.add(mdetail.get(i));
                        }
                    }

                    else
                    {
                        prgDialogLogin.dismiss();
                        aldBuild.setMessage("Anda Belum Memiliki Detail Pinjaman");
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
                return arwanaapiutils.detilpinjaman(strings[0]);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
