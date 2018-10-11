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

public class SimpanPinjamDataPengajuan extends Fragment {

    String nosir = "";
    private ProgressDialog prgDialogLogin;
    private AsyncTask<String, Void, JSONObject> mSendData;
    private ArrayList<classdatapengajuan> mdatapengajuan = null;
    private OrderAdapter m_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dataPengajuanView = inflater.inflate(R.layout.fragment_simpan_pinjam_data_pengajuan, container, false);
        mdatapengajuan = new ArrayList<classdatapengajuan>();
        ListView lv = (ListView) dataPengajuanView.findViewById(R.id.lv_dataPengajuan);
        m_adapter = new OrderAdapter(getActivity(), R.id.lv_dataPengajuan, mdatapengajuan);

        lv.setAdapter(m_adapter);
        Bundle thebundle = getActivity().getIntent().getExtras();

        nosir=thebundle.getString("id");
        mSendData = new mSendData().execute(nosir);

        return dataPengajuanView;
    }

    private class OrderAdapter extends ArrayAdapter<classdatapengajuan> {
        private LayoutInflater mInflater;
        private ArrayList<classdatapengajuan> items;
        public OrderAdapter(Context context, int textViewResourceId, ArrayList<classdatapengajuan> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_data_pengajuan, null);
                holder = new ViewHolder();
                holder.tv_tglInput = (TextView) convertView.findViewById(R.id.tv_tglInput);
                holder.tv_nominal = (TextView) convertView.findViewById(R.id.tv_nominal);
                holder.tv_jenis = (TextView) convertView.findViewById(R.id.tv_jenis);
                holder.tv_keterangan = (TextView) convertView.findViewById(R.id.tv_keterangan);
                holder.tv_lamaAngsuran = (TextView) convertView.findViewById(R.id.tv_lamaAngsuran);
                holder.tv_alasan = (TextView) convertView.findViewById(R.id.tv_alasan);
                holder.tv_tglUpdate = (TextView) convertView.findViewById(R.id.tv_tglUpdate);
                holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final classdatapengajuan o = items.get(position);
            if (o != null) {
                if (holder.tv_tglInput != null) {
                    holder.tv_tglInput.setText(o.Gettgl());
                }
                if (holder.tv_nominal != null) {
                    holder.tv_nominal.setText(o.Getjmlpinjaman());
                }
                if (holder.tv_jenis != null) {
                    holder.tv_jenis.setText(o.Getjenis());
                }
                if (holder.tv_keterangan != null) {
                    holder.tv_keterangan.setText(o.Getket());
                }
                if (holder.tv_lamaAngsuran != null) {
                    holder.tv_lamaAngsuran.setText(o.Getjmlangsuran());
                }
                if (holder.tv_alasan != null) {
                    holder.tv_alasan.setText(o.Getalasan());
                }
                if (holder.tv_tglUpdate != null) {
                    holder.tv_tglUpdate.setText(o.Gettglupdate());
                }
                if (holder.tv_status != null) {
                    holder.tv_status.setText(o.Getstatus());
                }
            }
            return convertView;
        }

        private class ViewHolder {
            TextView tv_tglInput, tv_nominal, tv_jenis, tv_keterangan, tv_lamaAngsuran, tv_alasan,
                    tv_tglUpdate, tv_status;
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
                mdatapengajuan = new ArrayList<classdatapengajuan>();
                if (hasil != null) {
                    JSONArray juve = null;
                    JSONObject merda = null;

                    juve = hasil.getJSONArray("itemdatapengajuan");
                    if (juve.length() > 0) {
                        Bundle bndle = new Bundle();
                        bndle.putString("my_key", "My String");
                        classdatapengajuan datapengajuanpinjaman[] = new classdatapengajuan[juve.getJSONArray(0).length()];
                        for (int a = 0; a < juve.getJSONArray(0).length(); ++a) {
                            merda = juve.getJSONArray(0).getJSONObject(a);
                            datapengajuanpinjaman[a] = new classdatapengajuan
                                    (
                                            merda.getString("anggota_id"), merda.getString("tgl_input"),
                                            merda.getString("nominal"), merda.getString("jenis"),
                                            merda.getString("keterangan"), merda.getString("lama_ags"),
                                            merda.getString("alasan"), merda.getString("tgl_update"),
                                            merda.getString("status")
                                    );
                            mdatapengajuan.add(datapengajuanpinjaman[a]);
                        }
                        if (mdatapengajuan != null && mdatapengajuan.size() > 0) {
                            m_adapter.notifyDataSetChanged();
                            for (int i = 0; i < mdatapengajuan.size(); i++)
                                m_adapter.add(mdatapengajuan.get(i));
                        }
                    } else {
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
                return arwanaapiutils.datapengajuan(strings[0]);

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

