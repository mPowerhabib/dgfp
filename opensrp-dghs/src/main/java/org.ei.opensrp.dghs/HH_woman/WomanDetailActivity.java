package org.ei.opensrp.dghs.HH_woman;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.dghs.hh_member.HouseHoldDetailActivity;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.dghs.R;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

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
        TextView name = (TextView) findViewById(R.id.womandetail_name);
        TextView brid = (TextView) findViewById(R.id.womandetail_womabrid);
        TextView nid = (TextView) findViewById(R.id.womandetail_womanid);
        TextView hid = (TextView) findViewById(R.id.womandetail_womahid);
        TextView husbandname = (TextView) findViewById(R.id.womandetail_husbandname);
        TextView pregnancystatus = (TextView) findViewById(R.id.womandetail_pregnancystatus);
        TextView fathersname = (TextView) findViewById(R.id.womandetail_fathers_name);
        TextView epicardno = (TextView) findViewById(R.id.womandetail_epicardno);
        TextView womandob = (TextView) findViewById(R.id.womandetail_womandob);
        TextView address = (TextView) findViewById(R.id.womandetail_address);
        TextView maritalstatus = (TextView) findViewById(R.id.womandetail_marital_status);
        TextView contactno = (TextView) findViewById(R.id.womandetail_phone_number);
        TextView today = (TextView)findViewById(R.id.woman_detail_today);
        TextView vaccination =(TextView) findViewById(R.id.womandetail_vaccinationstatus);

        //VACCINES INFORMATION/////////////////////////////////////////////////
        TextView tt1TextView =(TextView) findViewById(R.id.womandetail_tt1);
        TextView tt2TextView =(TextView) findViewById(R.id.womandetail_tt2);
        TextView tt3TextView =(TextView) findViewById(R.id.womandetail_tt3);
        TextView tt4TextView =(TextView) findViewById(R.id.womandetail_tt4);
        TextView tt5TextView =(TextView) findViewById(R.id.womandetail_tt5);
        ///////////////////////////////////////////////////////////////////////

        //PREGNANCY INFORMATION/////////////////////////////////////////////////
//        TextView pregnant =(TextView) findViewById(R.id.pregnanctdetail);
        TextView lmp =(TextView) findViewById(R.id.womandetail_lmp);
        TextView edd =(TextView) findViewById(R.id.womandetail_edd);
        TextView ga =(TextView) findViewById(R.id.womandetail_ga);
        ///////////////////////////////////////////////////////////////////////


        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name.setText(humanize((womanclient.getColumnmaps().get("Member_Fname") != null ? womanclient.getColumnmaps().get("Member_Fname") : "").replace("+", "_")));

        brid.setText(humanize((womanclient.getDetails().get("Member_BRID") != null ? womanclient.getDetails().get("Member_BRID") : "").replace("+", "_")));

        nid.setText(humanize((womanclient.getDetails().get("Member_NID") != null ? womanclient.getDetails().get("Member_NID") : "").replace("+", "_")));

        hid.setText(humanize((womanclient.getDetails().get("Member_HID") != null ? womanclient.getDetails().get("Member_HID") : "").replace("+", "_")));

        husbandname.setText((womanclient.getDetails().get("Husband_name") != null ? womanclient.getDetails().get("Husband_name") : ""));

        fathersname.setText((womanclient.getDetails().get("Father_name") != null ? womanclient.getDetails().get("Father_name") : ""));

        epicardno.setText((womanclient.getDetails().get("epi_card_number") != null ? womanclient.getDetails().get("epi_card_number") : ""));

        womandob.setText((womanclient.getDetails().get("calc_dob_confirm") != null ? womanclient.getDetails().get("calc_dob_confirm") : ""));
        SpannableString content = new SpannableString((womanclient.getDetails().get("contact_phone_number") != null ? womanclient.getDetails().get("contact_phone_number") : ""));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        contactno.setText(content);
        contactno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ((womanclient.getDetails().get("contact_phone_number") != null ? womanclient.getDetails().get("contact_phone_number") : ""))));
                startActivity(intent);
            }
        });

        address.setText((womanclient.getDetails().get("HH_Address") != null ? womanclient.getDetails().get("HH_Address") : ""));

        if(((womanclient.getColumnmaps().get("Marital_status")!=null?womanclient.getColumnmaps().get("Marital_status"):"")).equalsIgnoreCase("1")){
            maritalstatus.setText("Unmarried");
        }
        if((womanclient.getColumnmaps().get("Marital_status")!=null?womanclient.getColumnmaps().get("Marital_status"):"").equalsIgnoreCase("2")){
            maritalstatus.setText("Married");
        }
        if ((womanclient.getColumnmaps().get("Marital_status")!=null?womanclient.getColumnmaps().get("Marital_status"):"").equalsIgnoreCase("3")){
            maritalstatus.setText("Divorced/Widow/Widower");
        }
//        number_of_child.setText((womanclient.getDetails().get("calc_dob_confirm") != null ? womanclient.getDetails().get("calc_dob_confirm") : ""));

        pregnancystatus.setText((womanclient.getDetails().get("pregnant") != null ? womanclient.getDetails().get("pregnant") : ""));
//       godhhid.setText(getString(R.string.hhid_gob_elco_label) + (womanclient.getColumnmaps().get("Member_GOB_HHID") != null ? womanclient.getColumnmaps().get("Member_GOB_HHID") : ""));
//        psf_due_date.setText(Elcoclient.getDetails().get("FWPSRDATE") != null ? Elcoclient.getDetails().get("FWPSRDATE") : "");


//        village.setText(humanize(womanclient.getDetails().get("Member_WARD") != null ? womanclient.getDetails().get("Member_WARD") : ""));



        final ImageView householdview = (ImageView) findViewById(R.id.womandetailprofileview);

        if (womanclient.getDetails().get("profilepic") != null) {
            HouseHoldDetailActivity.setImagetoHolder(this, womanclient.getDetails().get("profilepic"), householdview, R.mipmap.woman_placeholder);
        }
//        TextView lmp  = (TextView)findViewById(R.id.lmp_date);
        lmp.setText(womanclient.getDetails().get("final_lmp")!=null?womanclient.getDetails().get("final_lmp"):"not available");
        edd.setText(womanclient.getDetails().get("final_edd")!=null?womanclient.getDetails().get("final_edd"):"not available");
        ga.setText(womanclient.getDetails().get("final_ga")!=null?womanclient.getDetails().get("final_ga")+" weeks":"not available");



        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        today.setText("Today: " +formattedDate+" ");
        vaccination.setText(immunizationStatus(womanclient));


//        LinearLayout measleslayout = (LinearLayout)findViewById(R.id.measles_layout);
//        LinearLayout tt1layout = (LinearLayout)findViewById(R.id.tt1_layout);
//        LinearLayout tt2layout = (LinearLayout)findViewById(R.id.tt2_layout);
//        LinearLayout tt3layout = (LinearLayout)findViewById(R.id.tt3layout);
//        LinearLayout tt4layout = (LinearLayout)findViewById(R.id.tt4layout);
//        LinearLayout tt5layout = (LinearLayout)findViewById(R.id.tt5layout);
//
//        if(!(womanclient.getDetails().get("TT1_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
//            TextView tt1_text = (TextView)findViewById(R.id.tt1date);
        //"██"+
        ttcheck(womanclient,tt1TextView,(View)findViewById(R.id.womandetail_tt1_block),"tt1_final","Woman_TT1");
        ttcheck(womanclient,tt2TextView,(View)findViewById(R.id.womandetail_tt2_block),"tt2_final","Woman_TT2");
        ttcheck(womanclient,tt3TextView,(View)findViewById(R.id.womandetail_tt3_block),"tt3_final","Woman_TT3");
        ttcheck(womanclient,tt4TextView,(View)findViewById(R.id.womandetail_tt4_block),"tt4_final","Woman_TT4");
        ttcheck(womanclient,tt5TextView,(View)findViewById(R.id.womandetail_tt5_block),"tt5_final","Woman_TT5");
//        tt1TextView.setText((womanclient.getDetails().get("tt1_final")!=null?womanclient.getDetails().get("tt1_final"):"N/A"));
//        tt2TextView.setText((womanclient.getDetails().get("tt2_final")!=null?womanclient.getDetails().get("tt2_final"):"N/A"));
//        tt3TextView.setText((womanclient.getDetails().get("tt3_final")!=null?womanclient.getDetails().get("tt3_final"):"N/A"));
//        tt4TextView.setText((womanclient.getDetails().get("tt4_final")!=null?womanclient.getDetails().get("tt4_final"):"N/A"));
//        tt5TextView.setText((womanclient.getDetails().get("tt5_final")!=null?womanclient.getDetails().get("tt5_final"):"N/A"));
////        }else{
//            tt1layout.setVisibility(View.GONE);
//        }
//        if(!(womanclient.getDetails().get("TT2_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT2_Date_of_Vaccination"):"").equalsIgnoreCase("")){
//            TextView tt2_text = (TextView)findViewById(R.id.tt2date);
//            tt2_text.setText((womanclient.getDetails().get("TT2_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT2_Date_of_Vaccination"):""));
//        }else{
//            tt2layout.setVisibility(View.GONE);
//        }
//        if(!(womanclient.getDetails().get("TT3_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT3_Date_of_Vaccination"):"").equalsIgnoreCase("")){
//            TextView tt3_text = (TextView)findViewById(R.id.tt3);
//            tt3_text.setText((womanclient.getDetails().get("TT3_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT3_Date_of_Vaccination"):""));
//        }else{
//            tt3layout.setVisibility(View.GONE);
//        }
//        if(!(womanclient.getDetails().get("TT4_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT4_Date_of_Vaccination"):"").equalsIgnoreCase("")){
//            TextView tt4_text = (TextView)findViewById(R.id.tt4);
//            tt4_text.setText((womanclient.getDetails().get("TT4_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT4_Date_of_Vaccination"):""));
//        }else{
//            tt4layout.setVisibility(View.GONE);
//        }
//        if(!(womanclient.getDetails().get("TT5_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT5_Date_of_Vaccination"):"").equalsIgnoreCase("")){
//            TextView tt5_text = (TextView)findViewById(R.id.tt5);
//            tt5_text.setText((womanclient.getDetails().get("TT5_Date_of_Vaccination")!=null?womanclient.getDetails().get("TT5_Date_of_Vaccination"):""));
//        }else{
//            tt5layout.setVisibility(View.GONE);
//        }
//        if(!(womanclient.getDetails().get("measles_Date_of_Vaccination")!=null?womanclient.getDetails().get("measles_Date_of_Vaccination"):"").equalsIgnoreCase("")){
//            TextView measles = (TextView)findViewById(R.id.measles_date);
//            measles.setText((womanclient.getDetails().get("measles_Date_of_Vaccination")!=null?womanclient.getDetails().get("measles_Date_of_Vaccination"):""));
//        }else{
//            measleslayout.setVisibility(View.GONE);
//        }



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

//    private void eddlay(CommonPersonObjectClient ancclient) {
//        TextView edd = (TextView)findViewById(R.id.lmp_date);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date edd_date = format.parse(ancclient.getColumnmaps().get("FWPSRLMP")!=null?ancclient.getColumnmaps().get("FWPSRLMP"):"");
//            GregorianCalendar calendar = new GregorianCalendar();
//            calendar.setTime(edd_date);
//            calendar.add(Calendar.DATE, 259);
//            edd_date.setTime(calendar.getTime().getTime());
//            edd.setText(format.format(edd_date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }
//
    public void ttcheck(CommonPersonObjectClient womanclient, TextView tt1TextView,View block,String ttfinalKey,String ttschedulename){
        if(!(womanclient.getDetails().get(ttfinalKey)!=null?womanclient.getDetails().get(ttfinalKey):"").equalsIgnoreCase("")){
            block.setBackgroundColor(getResources().getColor(R.color.alert_complete_green));
            tt1TextView.setText((womanclient.getDetails().get(ttfinalKey)!=null?womanclient.getDetails().get(ttfinalKey):""));
        }else{

            List<Alert> Woman_TT1alertlist_for_client = Context.getInstance().alertService().findByEntityIdAndAlertNames(womanclient.entityId(), ttschedulename);
            if(Woman_TT1alertlist_for_client.size()>0) {
                for (int i = 0; i < Woman_TT1alertlist_for_client.size(); i++) {
                    if (Woman_TT1alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")) {
                        block.setBackgroundColor(getResources().getColor(R.color.alert_upcoming_yellow));
                        String text = (Woman_TT1alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(text);
                    } else if (Woman_TT1alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")) {
                        block.setBackgroundColor(getResources().getColor(R.color.alert_urgent_red));
                        String text =  (Woman_TT1alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(text);
                    } else if (Woman_TT1alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")) {
                        block.setBackgroundColor(getResources().getColor(R.color.client_list_header_dark_grey));
                        String text =  (Woman_TT1alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(text);
                    } else if (Woman_TT1alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")) {
                        block.setBackgroundColor(getResources().getColor(R.color.alert_upcoming_light_blue));
                        String text =  (Woman_TT1alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(text);
                    }

                }
            }else{
                block.setBackgroundColor(getResources().getColor(R.color.status_bar_text_almost_white));
                String text = "";
                tt1TextView.setText(text);

            }

            }

    }


    public String immunizationStatus(CommonPersonObjectClient womanclient){
        boolean tt1 = ((womanclient.getDetails().get("tt1_final")!=null?womanclient.getDetails().get("tt1_final"):"")).equalsIgnoreCase("");
        boolean tt2 = ((womanclient.getDetails().get("tt2_final")!=null?womanclient.getDetails().get("tt2_final"):"")).equalsIgnoreCase("");
        boolean tt3 = ((womanclient.getDetails().get("tt3_final")!=null?womanclient.getDetails().get("tt3_final"):"")).equalsIgnoreCase("");
        boolean tt4 = ((womanclient.getDetails().get("tt4_final")!=null?womanclient.getDetails().get("tt4_final"):"")).equalsIgnoreCase("");
        boolean tt5 = ((womanclient.getDetails().get("tt5_final")!=null?womanclient.getDetails().get("tt5_final"):"")).equalsIgnoreCase("");
        String immunization_status = "";
        if(tt1||tt2||tt3||tt4||tt5){
            immunization_status = "Partially Immunized";
        }else{
            immunization_status = "Not Immunized";
        }
        if(tt1&&tt2&&tt3&&tt4&&tt5){
            immunization_status = "Fully Immunized";
        }
        return immunization_status;
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
