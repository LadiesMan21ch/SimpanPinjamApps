package com.arnawa.owan.koperasisimpanpinjam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class SimpanPinjamLaporanPembayaran extends Fragment {

    String nosir = "";
    private ProgressDialog prgDialogLogin;
    private AsyncTask<String, Void, JSONObject> mSendData;
    private ArrayList<classpembayaran> mpembayaran = null ;
    private OrderAdapter m_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View lapPembayaranView =  inflater.inflate(R.layout.fragment_simpan_pinjam_laporan_pembayaran,container,false);
        mpembayaran = new ArrayList<classpembayaran>();
        ListView lv = (ListView) lapPembayaranView.findViewById(R.id.lvLaporanPembayaran);
        m_adapter = new OrderAdapter(getActivity(),R.id.lvLaporanPembayaran, mpembayaran);

        lv.setAdapter(m_adapter);
        Bundle thebundle = getActivity().getIntent().getExtras();

        nosir=thebundle.getString("id");
        mSendData = new mSendData().execute(nosir);
        //setListAdapter(m_adapter);

        return lapPembayaranView;
    }

    private class OrderAdapter extends ArrayAdapter<classpembayaran> {
        private LayoutInflater mInflater;
        private ArrayList<classpembayaran> items;
        public OrderAdapter(Context context, int textViewResourceId, ArrayList<classpembayaran> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_laporan_pembayaran, null);
                holder = new ViewHolder();
                holder.tv_pinjaman_lppembayaran         = (TextView) convertView.findViewById(R.id.tv_pinjaman_lppembayaran);
                holder.tv_totalangsuran_lppembayaran    = (TextView) convertView.findViewById(R.id.tv_totalangsuran_lppembayaran);
                holder.tv_tanggalTransaksi              = (TextView) convertView.findViewById(R.id.tv_tanggalTransaksi);
                holder.tvKodeTransaksiLapPembayaran     = (TextView) convertView.findViewById(R.id.tvKodeTransaksiLapPembayaran);
                holder.tvDendaLap                       = (TextView) convertView.findViewById(R.id.tvDendaLap);
                holder.tvJumlahTagihanLaporanPembayaran = (TextView) convertView.findViewById(R.id.tvJumlahTagihanLaporanPembayaran);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            classpembayaran o = items.get(position);
            if (o != null) {

                if (holder.tv_pinjaman_lppembayaran != null) {
                    holder.tv_pinjaman_lppembayaran.setText(o.Getbayar());
                }
                if (holder.tv_totalangsuran_lppembayaran != null) {
                    holder.tv_totalangsuran_lppembayaran.setText(o.Getangsuranke());
                }
                if (holder.tv_tanggalTransaksi != null) {
                    holder.tv_tanggalTransaksi.setText(o.Gettgl());
                }
                if (holder.tvKodeTransaksiLapPembayaran != null) {
                    holder.tvKodeTransaksiLapPembayaran.setText(o.Getjenis());
                }
                if (holder.tvDendaLap != null) {
                    holder.tvDendaLap.setText(o.Getdenda());
                }
                if (holder.tvJumlahTagihanLaporanPembayaran != null) {
                    holder.tvJumlahTagihanLaporanPembayaran.setText(o.Getketerangan());
                }
            }
            return convertView;
        }
        private class ViewHolder{
            TextView tv_pinjaman_lppembayaran, tv_totalangsuran_lppembayaran, tv_tanggalTransaksi,
                    tvKodeTransaksiLapPembayaran, tvDendaLap, tvJumlahTagihanLaporanPembayaran;
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
                mpembayaran = new ArrayList<classpembayaran>();
                if (hasil !=null){
                    JSONArray manchunian = null;
                    JSONObject owan = null;

                    manchunian = hasil.getJSONArray("itemlprnpembayaran");
                    if (manchunian.length() > 0){
                        Bundle bndle = new Bundle();
                        bndle.putString("my_key", "My String");
                        classpembayaran datapembayaran[] = new classpembayaran[manchunian.getJSONArray(0).length()];
                        for (int oan = 0; oan < manchunian.getJSONArray(0).length(); ++oan )
                        {
                            owan = manchunian.getJSONArray(0).getJSONObject(oan);
                            datapembayaran[oan] = new classpembayaran
                                    (
                                            owan.getString("anggota_id"), owan.getString ("jumlah_bayar" ),
                                            owan.getString ("angsuran_ke"),owan.getString ("tgl_bayar"),
                                            owan.getString ("ket_bayar"),owan.getString("denda_rp"),
                                            owan.getString ("keterangan")
                                    );
                            mpembayaran.add(datapembayaran[oan]);
                        }
                        if(mpembayaran != null && mpembayaran.size() > 0){
                            m_adapter.notifyDataSetChanged();
                            for(int i=0;i<mpembayaran.size();i++)
                                m_adapter.add(mpembayaran.get(i));
                        }
                    }

                    else
                    {
                        prgDialogLogin.dismiss();
                        aldBuild.setMessage("Anda Belum Memiliki Pembayaran");
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
                return arwanaapiutils.datalaporanpembayaran(strings[0]);

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
