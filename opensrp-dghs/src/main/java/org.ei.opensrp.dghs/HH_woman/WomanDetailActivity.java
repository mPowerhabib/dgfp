package org.ei.opensrp.dghs.HH_woman;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.dghs.R;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ImageCache;
import util.ImageFetcher;

import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by raihan on 5/11/15.
 */
public class WomanDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";

    private static int mImageThumbSize;
    private static int mImageThumbSpacing;

    private static ImageFetcher mImageFetcher;




    //image retrieving

    public static CommonPersonObjectClient womanclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.woman_detail_activity);
        TextView name = (TextView) findViewById(R.id.name);
        TextView brid = (TextView) findViewById(R.id.brid);
        TextView husbandname = (TextView) findViewById(R.id.husbandname);
        TextView age = (TextView) findViewById(R.id.age);
        TextView godhhid = (TextView) findViewById(R.id.gobhhid);
        TextView village = (TextView) findViewById(R.id.ward);

        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name.setText(humanize((womanclient.getColumnmaps().get("Member_Fname") != null ? womanclient.getColumnmaps().get("Member_Fname") : "").replace("+", "_")));

        brid.setText(getString(R.string.BRID) +humanize((womanclient.getDetails().get("BRID") != null ? womanclient.getDetails().get("BRID") : "").replace("+", "_")));

        husbandname.setText(getString(R.string.elco_details_husband_name_label)+(womanclient.getDetails().get("Guardian_Name_Hus") != null ? womanclient.getDetails().get("Guardian_Name_Hus") : ""));
        age.setText(getString(R.string.elco_age_label) + (womanclient.getColumnmaps().get("Age") != null ? womanclient.getColumnmaps().get("Age") : ""));
       godhhid.setText(getString(R.string.hhid_gob_elco_label) + (womanclient.getColumnmaps().get("Member_GOB_HHID") != null ? womanclient.getColumnmaps().get("Member_GOB_HHID") : ""));
//        psf_due_date.setText(Elcoclient.getDetails().get("FWPSRDATE") != null ? Elcoclient.getDetails().get("FWPSRDATE") : "");


        village.setText(humanize(womanclient.getDetails().get("Member_WARD") != null ? womanclient.getDetails().get("Member_WARD") : ""));



        final ImageView householdview = (ImageView) findViewById(R.id.householdprofileview);

        if (womanclient.getDetails().get("profilepic") != null) {
            setImagetoHolder(this, womanclient.getDetails().get("profilepic"), householdview, R.mipmap.woman_placeholder);
        }
        TextView lmp  = (TextView)findViewById(R.id.lmp_date);
        lmp.setText(womanclient.getDetails().get("LMP")!=null?womanclient.getDetails().get("LMP"):"not available");

        LinearLayout measleslayout = (LinearLayout)findViewById(R.id.measles_layout);
        LinearLayout tt1layout = (LinearLayout)findViewById(R.id.tt1_layout);
        LinearLayout tt2layout = (LinearLayout)findViewById(R.id.tt2_layout);
        LinearLayout tt3layout = (LinearLayout)findViewById(R.id.tt3layout);
        LinearLayout tt4layout = (LinearLayout)findViewById(R.id.tt4layout);
        LinearLayout tt5layout = (LinearLayout)findViewById(R.id.tt5layout);

        if(!(womanclient.getDetails().get("TT1_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            TextView tt1_text = (TextView)findViewById(R.id.tt1date);
            tt1_text.setText((womanclient.getDetails().get("TT1_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT1_Date_of_Vaccination"):""));
        }else{
            tt1layout.setVisibility(View.GONE);
        }
        if(!(womanclient.getDetails().get("TT2_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT2_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            TextView tt2_text = (TextView)findViewById(R.id.tt2date);
            tt2_text.setText((womanclient.getDetails().get("TT2_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT2_Date_of_Vaccination"):""));
        }else{
            tt2layout.setVisibility(View.GONE);
        }
        if(!(womanclient.getDetails().get("TT3_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT3_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            TextView tt3_text = (TextView)findViewById(R.id.tt3);
            tt3_text.setText((womanclient.getDetails().get("TT3_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT3_Date_of_Vaccination"):""));
        }else{
            tt3layout.setVisibility(View.GONE);
        }
        if(!(womanclient.getDetails().get("TT4_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT4_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            TextView tt4_text = (TextView)findViewById(R.id.tt4);
            tt4_text.setText((womanclient.getDetails().get("TT4_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT4_Date_of_Vaccination"):""));
        }else{
            tt4layout.setVisibility(View.GONE);
        }
        if(!(womanclient.getDetails().get("TT5_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT5_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            TextView tt5_text = (TextView)findViewById(R.id.tt5);
            tt5_text.setText((womanclient.getDetails().get("TT5_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT5_Date_of_Vaccination"):""));
        }else{
            tt5layout.setVisibility(View.GONE);
        }
        if(!(womanclient.getDetails().get("measles_Date_of_Vaccination")!=null?womanclient.getDetails().get("measles_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            TextView measles = (TextView)findViewById(R.id.measles_date);
            measles.setText((womanclient.getDetails().get("measles_Date_of_Vaccination")!=null?womanclient.getDetails().get("measles_Date_of_Vaccination"):""));
        }else{
            measleslayout.setVisibility(View.GONE);
        }



//        householdview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bindobject = "household";
//                entityid = Elcoclient.entityId();
//                dispatchTakePictureIntent(householdview);
//
//            }
//        });


    }

    private void eddlay(CommonPersonObjectClient ancclient) {
        TextView edd = (TextView)findViewById(R.id.lmp_date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date edd_date = format.parse(ancclient.getColumnmaps().get("FWPSRLMP")!=null?ancclient.getColumnmaps().get("FWPSRLMP"):"");
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(edd_date);
            calendar.add(Calendar.DATE, 259);
            edd_date.setTime(calendar.getTime().getTime());
            edd.setText(format.format(edd_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    private void checkAnc1view(CommonPersonObjectClient ecclient) {
        LinearLayout anc1layout = (LinearLayout)findViewById(R.id.measles_layout);
        List<Alert> alertlist = Context.getInstance().alertService().findByEntityIdAndAlertNames(ecclient.entityId(), "ancrv_1");
        if(alertlist.size()!=0 && ecclient.getDetails().get("FWANC1DATE")!=null){
//            alerttextstatus = setAlertStatus("ANC1",alertlist);
            for(int i = 0;i<alertlist.size();i++){
                String status = alertlist.get(i).status().value();
                String text = ecclient.getDetails().get("FWANC1DATE")!=null?ecclient.getDetails().get("FWANC1DATE"):"";
                TextView anc1date = (TextView)findViewById(R.id.measles_date);
                if((ecclient.getDetails().get("anc1_current_formStatus")!=null?ecclient.getDetails().get("anc1_current_formStatus"):"").equalsIgnoreCase("upcoming")){
                    anc1date.setTextColor(getResources().getColor(R.color.alert_complete_green));
                }else if((ecclient.getDetails().get("anc1_current_formStatus")!=null?ecclient.getDetails().get("anc1_current_formStatus"):"").equalsIgnoreCase("urgent")){
                    anc1date.setTextColor(getResources().getColor(R.color.alert_urgent_red));
                }
                anc1date.setText(text);

            }
        }else{
            anc1layout.setVisibility(View.GONE);
        }

    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;
   static ImageView mImageView;
    static File currentfile;
    static String bindobject;
    static String entityid;
    private void dispatchTakePictureIntent(ImageView imageView) {
        mImageView = imageView;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                currentfile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            String imageBitmap = (String) extras.get(MediaStore.EXTRA_OUTPUT);
//            Toast.makeText(this,imageBitmap,Toast.LENGTH_LONG).show();
            HashMap <String,String> details = new HashMap<String,String>();
            details.put("profilepic",currentfile.getAbsolutePath());
            saveimagereference(bindobject,entityid,details);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(currentfile.getPath(), options);
            mImageView.setImageBitmap(bitmap);
        }
    }
    public void saveimagereference(String bindobject,String entityid,Map<String,String> details){
        Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityid,details);
//                Elcoclient.entityId();
//        Toast.makeText(this,entityid,Toast.LENGTH_LONG).show();
    }
    public static void setImagetoHolder(Activity activity,String file, ImageView view, int placeholder){
        mImageThumbSize = 300;
        mImageThumbSpacing = Context.getInstance().applicationContext().getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);


        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(activity, IMAGE_CACHE_DIR);
             cacheParams.setMemCacheSizePercent(0.50f); // Set memory cache to 25% of app memory
        mImageFetcher = new ImageFetcher(activity, mImageThumbSize);
        mImageFetcher.setLoadingImage(placeholder);
        mImageFetcher.addImageCache(activity.getFragmentManager(), cacheParams);
//        Toast.makeText(activity,file,Toast.LENGTH_LONG).show();
        mImageFetcher.loadImage("file:///"+file,view);

//        Uri.parse(new File("/sdcard/cats.jpg")






//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(file, options);
//        view.setImageBitmap(bitmap);
    }
}
