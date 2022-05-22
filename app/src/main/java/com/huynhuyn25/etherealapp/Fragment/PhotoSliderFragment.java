package com.huynhuyn25.etherealapp.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huynhuyn25.etherealapp.ChangeInforActivity;
import com.huynhuyn25.etherealapp.Model.Photo;
import com.huynhuyn25.etherealapp.Model.SliderPhoto;
import com.huynhuyn25.etherealapp.R;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoSliderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoSliderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Bitmap bitmap;
    ImageView imgPhoto;
    public PhotoSliderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoSliderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoSliderFragment newInstance(String param1, String param2) {
        PhotoSliderFragment fragment = new PhotoSliderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_slider, container, false);
        Log.d("TAG", "onCreateView: ");
        Bundle bundle = getArguments();
        SliderPhoto photo = (SliderPhoto) bundle.get("object_photo");
        imgPhoto = view.findViewById(R.id.img_slider);
        LoadImage loadImage = new LoadImage(imgPhoto);
        loadImage.execute(photo.getUrl());
//        imgPhoto.setImageResource(photo.getPhoto());
        return view;

    }
    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage (ImageView imgPhoto){
            imageView=imgPhoto;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            bitmap=null;
            try {
                InputStream inputStream = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                Log.d("TAG", "onPostExecute: oce");
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgPhoto.setImageBitmap(bitmap);
            Log.d("TAG", "onPostExecute: oce");
        }
    }
}