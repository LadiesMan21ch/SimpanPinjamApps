package com.arnawa.owan.koperasisimpanpinjam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class SimpanPinjamBayarPinjaman extends Fragment {

    private Button btn_camera, btn_galery;
    private ImageView img_buktiTf;
    private static final String TAG = SimpanPinjamBayarPinjaman.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE1 = 1111;
    private static final int PICK_IMAGE1=100;
    Uri imageUriPinjaman;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View bayarPinjamanView =  inflater.inflate(R.layout.fragment_simpan_pinjam_bayar_pinjaman,container,false);
        btn_camera = (Button) bayarPinjamanView.findViewById(R.id.btn_camera);
        btn_galery = (Button) bayarPinjamanView.findViewById(R.id.btn_galery);
        img_buktiTf = (ImageView) bayarPinjamanView.findViewById(R.id.img_buktiTf);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentcamera, CAMERA_REQUEST_CODE1);
            }
        });

        btn_galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgBuktiPinjaman = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(imgBuktiPinjaman, PICK_IMAGE1);
            }
        });
        return bayarPinjamanView;
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
                    img_buktiTf.setImageBitmap(bitmap);
                    img_buktiTf.setAdjustViewBounds(true);
                    img_buktiTf.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
        }

        switch (requestCode) {
            case(PICK_IMAGE1) :
                if(resultCode == Activity.RESULT_OK)
                {
                    imageUriPinjaman = data.getData();
                    img_buktiTf.setImageURI(imageUriPinjaman);
                    img_buktiTf.setAdjustViewBounds(true);
                    img_buktiTf.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
        }
    }
}
