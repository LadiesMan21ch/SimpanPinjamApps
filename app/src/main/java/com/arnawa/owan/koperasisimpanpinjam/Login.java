package com.arnawa.owan.koperasisimpanpinjam;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText eTHpEmail, eTPswwd;
    private Button myBtLogin;
    private ProgressDialog prgDialogLogin;
    private AsyncTask<String, Void, JSONObject> mSendData;
    private static FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eTHpEmail = (EditText) findViewById(R.id.et_no_hp_atau_email);
        eTPswwd = (EditText) findViewById(R.id.et_password);
        myBtLogin = (Button) findViewById(R.id.my_btnLogin);
        prgDialogLogin = new ProgressDialog(this);
        prgDialogLogin.setMessage("Please Wait ... !");

        myBtLogin.setOnClickListener(this);
    }

    private void loginUser(){
        mSendData = new mSendData().execute(eTHpEmail.getText().toString(), eTPswwd.getText().toString());
    }

    private class mSendData extends AsyncTask<String, Void, JSONObject>{
        @Override
        protected void onPreExecute() {
            prgDialogLogin = ProgressDialog.show(Login.this, "Please Wait", "Retrieving data ...", true);
        }

        @Override
        protected void onPostExecute(JSONObject hasil) {
            AlertDialog.Builder aldBuild = new AlertDialog.Builder(Login.this);
            prgDialogLogin.dismiss();

            try {
                if (hasil !=null){
                    JSONArray juve = null;
                    JSONObject merda = null;

                    juve = hasil.getJSONArray("item");
                    if (juve.length() > 0){
                        Intent badutIntent = new Intent(Login.this, SimpanPinjam.class);
                        Bundle bndle = new Bundle();
                        bndle.putString("my_key", "My String");
                        SimpanPinjamDashboard mySPBFrag = new SimpanPinjamDashboard();

                        for (int a = 0; a < juve.length(); ++a ){
                            merda = juve.getJSONObject(a);
                        }

                        badutIntent.putExtra("uid", eTHpEmail.getText());
                        badutIntent.putExtra("id", merda.getString("id"));
                        badutIntent.putExtra("nor_sir", merda.getString("nor_sir"));
                        badutIntent.putExtra("nama", merda.getString("nama") );
                        badutIntent.putExtra("jk", merda.getString("jk"));
                        badutIntent.putExtra("jabatan_id", merda.getString("jabatan_id"));
                        badutIntent.putExtra("alamat", merda.getString("alamat"));
                        badutIntent.putExtra("notelp", merda.getString("notelp"));

                        mySPBFrag.setArguments(bndle);

                        startActivity(badutIntent);
                        finish();
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
                return arwanaapiutils.send_data(strings[0],strings[1]);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public void onClick(View v) {
        if (v ==myBtLogin){
            loginUser();
        }
    }
}
