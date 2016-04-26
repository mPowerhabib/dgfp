package org.ei.opensrp.dghs.HH_child;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.dghs.R;
import org.ei.opensrp.domain.Alert;

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
public class ChildDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";

    private static int mImageThumbSize;
    private static int mImageThumbSpacing;

    private static ImageFetcher mImageFetcher;




    //image retrieving

    public static CommonPersonObjectClient childclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.child_detail_activity);
        TextView name = (TextView) findViewById(R.id.name);
        TextView brid = (TextView) findViewById(R.id.brid);
        TextView parentsname = (TextView) findViewById(R.id.parentsname);
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

        name.setText(humanize((childclient.getColumnmaps().get("Member_Fname") != null ? childclient.getColumnmaps().get("Member_Fname") : "").replace("+", "_")));

        brid.setText(getString(R.string.BRID) +humanize((childclient.getDetails().get("BRID") != null ? childclient.getDetails().get("BRID") : "").replace("+", "_")));

        parentsname.setText(getString(R.string.child_details_parents_name_label) +(childclient.getDetails().get("C_Guardian_Name_Father") != null ? childclient.getDetails().get("C_Guardian_Name_Father") : ""));
        age.setText(getString(R.string.elco_age_label) + (childclient.getColumnmaps().get("Age") != null ? childclient.getColumnmaps().get("Age") : ""));
       godhhid.setText(getString(R.string.hhid_gob_elco_label) + (childclient.getColumnmaps().get("Member_GOB_HHID") != null ? childclient.getColumnmaps().get("Member_GOB_HHID") : ""));
//        psf_due_date.setText(Elcoclient.getDetails().get("FWPSRDATE") != null ? Elcoclient.getDetails().get("FWPSRDATE") : "");


        village.setText(humanize(childclient.getDetails().get("Member_WARD") != null ? childclient.getDetails().get("Member_WARD") : ""));



        final ImageView householdview = (ImageView) findViewById(R.id.householdprofileview);

        if (childclient.getDetails().get("profilepic") != null) {
            setImagetoHolder(this, childclient.getDetails().get("profilepic"), householdview, R.mipmap.woman_placeholder);
        }
        TextView lmp  = (TextView)findViewById(R.id.dateofbirth);
        lmp.setText(childclient.getDetails().get("DoB")!=null?childclient.getDetails().get("DoB"):"not available");

        
        contstructLastVaccinedateBlock(childclient);



    }
    public LinearLayout makevaccinerow (String vaccinename,String vaccinedate){
        LinearLayout vaccinerow = (LinearLayout) getLayoutInflater().inflate(R.layout.vaccine_row, null);
        ((TextView)vaccinerow.findViewById(R.id.vaccinename)).setText(vaccinename);
        ((TextView)vaccinerow.findViewById(R.id.vaccine_date)).setText(vaccinedate);

        return vaccinerow;
    }
    private void contstructLastVaccinedateBlock(CommonPersonObjectClient pc) {
        LinearLayout vaccine_record_layout = (LinearLayout)findViewById(R.id.vaccine_record_layout);

        if(!(pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("Measles", (pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("MR", (pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("IPV", (pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("OPV3", (pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("OPV2", (pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("OPV1", (pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("PCV1", (pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("OPV0", (pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination") : "")));
        }
        if(!(pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            vaccine_record_layout.addView(makevaccinerow("BCG", (pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination") : "")));
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
