package com.example.mehndi;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import com.example.mehndi.model.mehndiimg;
import com.example.prernapachpande.Mehndi.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageFragment extends Fragment {
    private String TAG = ImageFragment.class.getSimpleName();

    ArrayList<mehndiimg> fullImage;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int selectedPosition = 0;
    Context context;
    String setImgLink = "";

    public ImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        fullImage = (ArrayList<mehndiimg>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        getArguments().remove("images");

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);
        BottomNavigationView navigation = (BottomNavigationView) v.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);
        return v;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_share:
                    shareItem(setImgLink);
                    return true;
                case R.id.navigation_download:
                    Picasso.with(context).load(setImgLink).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            String value = setImgLink.substring(setImgLink.lastIndexOf("/") + 1, setImgLink.length());


                            File folder = new File(Environment.getExternalStorageDirectory(), "Punch");
                            if (!folder.exists()) {
                                folder.mkdir();
                            }

                            String fileName = value + ".jpg";
                            File root = Environment.getExternalStorageDirectory();
                            File file = new File(root.getAbsolutePath() + "DCIM");
                            file.mkdir();
                            File f = new File(file, "Mehandi");
                            f.mkdir();

                            File cachePath = new File(f.getAbsolutePath(), "Img_" + System.currentTimeMillis() + ".jpg");
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(setImgLink));

                            try {
                                cachePath.createNewFile();
                                FileOutputStream ostream = new FileOutputStream(cachePath);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                                ostream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {

                                request.setTitle("Downloading");
                                request.setDescription("File is being downloaded.....");
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                                String nameOfFile = URLUtil.guessFileName(setImgLink, null,
                                        MimeTypeMap.getFileExtensionFromUrl(setImgLink));

                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);
                                DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                                manager.enqueue(request);

                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                    return true;
            }
            return false;
        }
    };
    public void shareItem(String url){
        Picasso.with(context).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(bitmap));
                startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
    public Uri getLocalBitmapUri(Bitmap bmp){
        Uri bmpUri = null;
        try {
            File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);

    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    private void displayMetaInfo(int position) {

        mehndiimg candidphotos1 = fullImage.get(position);
        setImgLink = candidphotos1.getImages();
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.fullimage, container, false);

            final ImageView imageView = (ImageView) view.findViewById(R.id.full);

            mehndiimg photos = fullImage.get(position);

            Picasso.with(context).load(photos.getImages()).into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return fullImage.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
