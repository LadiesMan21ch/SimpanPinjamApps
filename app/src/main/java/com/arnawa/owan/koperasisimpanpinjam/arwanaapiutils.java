package com.arnawa.owan.koperasisimpanpinjam;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAWO on 09/19/2018.
 */

public class arwanaapiutils {
    public static final String API_KEY = "GH72671";
    public static final String nts_send = "http://ksupegangsaanmobile.kuperasi.com/loginmobile/loginkuperasi";
    public static final String data_dasbord = "http://ksupegangsaanmobile.kuperasi.com/loginmobile/dashboard_sp";
    public static final String data_simpanan= "http://ksupegangsaanmobile.kuperasi.com/loginmobile/laporan_simpanan";
    public static final String data_tambah_simpanan= "http://ksupegangsaanmobile.kuperasi.com/loginmobile/tambah_simpanan";
    public static final String data_tambah_pinjaman= "http://ksupegangsaanmobile.kuperasi.com/loginmobile/tambah_pinjaman_baru";
    public static final String data_pinjaman= "http://ksupegangsaanmobile.kuperasi.com/loginmobile/laporan_pinjaman";
    public static final String detail_pinjaman= "http://ksupegangsaanmobile.kuperasi.com/loginmobile/pinjaman_detil";
    public static final String data_pembayaran= "http://ksupegangsaanmobile.kuperasi.com/loginmobile/laporan_pembayaran";
    public static final String data_pegajuan= "http://ksupegangsaanmobile.kuperasi.com/loginmobile/data_pengajuan";
    public static final String nts_get_saldo_simpanan = "http://ksupegangsaanmobile.kuperasi.com/loginmobile/loginkuperasi";
    public static final String perangkat_send = "http://arwanatrack.com/api/group_perangkat.php";

    public static final String TANGGALTRANSAKSI = "tgl_transaksi";
    public static final String JUMLAH = "jumlah";
    public static final String JENISID = "jenis_id";
    public static final String KETERANGAN = "keterangan";

    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_USER_NOT_ACTIVATED = 2;
    public static final int RESULT_USER_NOT_FOUND = 3;
    public static final int RESULT_WRONG_PIN = 4;
    public static final int RESULT_INSUFFICIENT_FUNDS = 5;
    public static final int RESULT_DEVICE_NOT_FOUND = 6;


    public static boolean haveInternet(Context c) {

        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();

        if (info == null || !info.isConnected()) {
            return false;
        }
		/*if (info.isRoaming()) {
			// here is the roaming option you can change it if you want to
			// disable internet while roaming, just return false
			return true;
		}*/
        return true;
    }

    private static JSONObject httpPostToApi(String ApiURL, List<NameValuePair> nameValuePairs) throws ClientProtocolException, IOException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ApiURL);
        JSONObject jsResult = null;

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);

        String result = EntityUtils.toString(response.getEntity());
        Log.i("HTTP", result);

        // Got a response from the server
        try {
            jsResult = new JSONObject(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsResult;
    }

    public static JSONObject send_data(String user, String password) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                2);
        nameValuePairs.add(new BasicNameValuePair("q",
                user));
        nameValuePairs.add(new BasicNameValuePair("p",
                password));
        return httpPostToApi(nts_send, nameValuePairs);
    }

    public static JSONObject datadaasbord(String id) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("q",
                id));
        return
                httpPostToApi(data_dasbord, nameValuePairs);
    }

    public static JSONObject datatambahsimpanan(String id) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("q",
                id));
        return
                httpPostToApi(data_tambah_simpanan, nameValuePairs);
    }

    public static JSONObject datapinjamanbaru(String noajuan, String ajuanid,
                                              String anggotaid, String tglinput,
                                              String jns, String nmnl,
                                              String lmags, String tpags,
                                              String ktrngn, String stts,
                                              String alsn) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                11);
        nameValuePairs.add(new BasicNameValuePair("no_ajuan",
                noajuan));
        nameValuePairs.add(new BasicNameValuePair("ajuan_id",
                ajuanid));
        nameValuePairs.add(new BasicNameValuePair("anggota_id",
                anggotaid));
        nameValuePairs.add(new BasicNameValuePair("tgl_input",
                tglinput));
        nameValuePairs.add(new BasicNameValuePair("jenis",
                jns));
        nameValuePairs.add(new BasicNameValuePair("nominal",
                nmnl));
        nameValuePairs.add(new BasicNameValuePair("lama_ags",
                lmags));
        nameValuePairs.add(new BasicNameValuePair("tipe_ags",
                tpags));
        nameValuePairs.add(new BasicNameValuePair("keterangan",
                ktrngn));
        nameValuePairs.add(new BasicNameValuePair("status",
                stts));
        nameValuePairs.add(new BasicNameValuePair("alasan",
                alsn));
        return
                httpPostToApi(data_tambah_pinjaman, nameValuePairs);
    }

    public static JSONObject datalaporanpinjaman(String id) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("q",
                id));
        return
                httpPostToApi(data_pinjaman, nameValuePairs);
    }

    public static JSONObject detilpinjaman(String id) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("q",
                id));
        return
                httpPostToApi(detail_pinjaman, nameValuePairs);
    }

    public static JSONObject datalaporansimpanan(String id) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("q",
                id));
        return
                httpPostToApi(data_simpanan, nameValuePairs);
    }

    public static JSONObject datalaporanpembayaran(String id) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("q",
                id));
        return
                httpPostToApi(data_pembayaran, nameValuePairs);
    }

    public static JSONObject datapengajuan(String id) throws ClientProtocolException, IOException {
        // Add the order data//uid=demo&pwd=demo
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("q",
                id));
        return
                httpPostToApi(data_pegajuan, nameValuePairs);
    }

    public static JSONObject send_data_perangkat(String user, String password) throws ClientProtocolException, IOException {
        // Add the order data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                1);
        nameValuePairs.add(new BasicNameValuePair("uid",
                user));
        nameValuePairs.add(new BasicNameValuePair("pwd",
                password));
        return httpPostToApi(perangkat_send, nameValuePairs);
    }

    public static JSONObject nts_saldo_simpanan (String user)throws ClientProtocolException,IOException{
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                10);

        nameValuePairs.add(new BasicNameValuePair(
                "q", user));

        return httpPostToApi(nts_get_saldo_simpanan, nameValuePairs);
    }

}
