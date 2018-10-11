package com.arnawa.owan.koperasisimpanpinjam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class SimpanPinjamTambahSimpanan extends Fragment {

    private Button btn_cameraSimpanan, btn_galerySimpanan;
    private ImageView img_buktiTfsimpanan;
    private TextView tv_namaAnggotaSimpanan;
    private ProgressDialog prgDialogLogin;
    private static final String TAG = SimpanPinjamTambahSimpanan.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE1 = 1111;
    private static final int PICK_IMAGE1=100;
    private AsyncTask<String, Void, JSONObject> mSendData;
    Uri imageUriSimpanan;
    String nosir = "";

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View tambahSimpananView = inflater.inflate(R.layout.fragment_simpan_pinjam_tambah_simpanan,container,false);

        tv_namaAnggotaSimpanan = (TextView) tambahSimpananView.findViewById(R.id.tv_namaAnggotaSimpanan);

        EditText etPilihTanggalTransaksi = (EditText) tambahSimpananView.findViewById(R.id.etPilihTanggalTransaksi);
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String date_str = df.format(cal.getTime());
        etPilihTanggalTransaksi.setText(date_str);
        btn_cameraSimpanan = (Button) tambahSimpananView.findViewById(R.id.btn_cameraSimpanan);
        btn_galerySimpanan = (Button) tambahSimpananView.findViewById(R.id.btn_galerySimpanan);
        img_buktiTfsimpanan = (ImageView) tambahSimpananView.findViewById(R.id.img_buktiTfsimpanan);

        btn_cameraSimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentcamera, CAMERA_REQUEST_CODE1);
            }
        });

        btn_galerySimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgBuktiSimpanan = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(imgBuktiSimpanan, PICK_IMAGE1);
            }
        });

        Bundle thebundle = getActivity().getIntent().getExtras();

        nosir=thebundle.getString("id");
        mSendData = new mSendData().execute(nosir);

        return tambahSimpananView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(CAMERA_REQUEST_CODE1) :
                if(resultCode == Activity.RESULT_OK)
                {
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    img_buktiTfsimpanan.setImageBitmap(bitmap);
                    img_buktiTfsimpanan.setAdjustViewBounds(true);
                    img_buktiTfsimpanan.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
        }

        switch (requestCode) {
            case(PICK_IMAGE1) :
                if(resultCode == Activity.RESULT_OK)
                {
                    imageUriSimpanan = data.getData();
                    img_buktiTfsimpanan.setImageURI(imageUriSimpanan);
                    img_buktiTfsimpanan.setAdjustViewBounds(true);
                    img_buktiTfsimpanan.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
        }
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
                if (hasil != null) {
                    JSONArray juve = null;
                    JSONObject merda = null;

                    juve = hasil.getJSONArray("itemnamanggota");
                    if (juve.length() > 0) {

                        for (int a = 0; a < juve.length(); ++a) {
                            if (a == 0) {
                                merda = juve.getJSONArray(a).getJSONObject(0);
                                tv_namaAnggotaSimpanan.setText(merda.getString("nama"));
                            }

                        }

                    } else
                        {
                        prgDialogLogin.dismiss();
                        aldBuild.setMessage("User atau Password Salah !!!");
                        aldBuild.show();
                        }

                    }

                }catch (JSONException e) {
                    prgDialogLogin.setMessage(e.toString());
                    prgDialogLogin.show();
            }

        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            try {
                return arwanaapiutils.datatambahsimpanan(strings[0]);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
