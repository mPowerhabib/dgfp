package org.ei.opensrp.dghs.HH_child;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.dghs.R;
import org.ei.opensrp.dghs.hh_member.HouseHoldDetailActivity;
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
import java.util.concurrent.ExecutionException;

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
        TextView name = (TextView) findViewById(R.id.child_detail_name_field);
        TextView brid = (TextView) findViewById(R.id.child_detail_brid_nid_field);
        TextView fathername = (TextView) findViewById(R.id.child_detail_fathername_field);
        TextView mothername = (TextView) findViewById(R.id.child_detail_mothername_field);
        TextView epicarno = (TextView) findViewById(R.id.child_detail_epicard_field);
        TextView birthdate = (TextView) findViewById(R.id.child_detail_birthdate_field);
        TextView contactno = (TextView) findViewById(R.id.child_detail_contactno_field);
        TextView address = (TextView) findViewById(R.id.child_detail_address_field);
        ImageView profilepic = (ImageView) findViewById(R.id.childdetailprofileview);


        TextView childdetail_bcg = (TextView) findViewById(R.id.childdetail_bcg);
        TextView childdetail_opv0 = (TextView) findViewById(R.id.childdetail_opv0);
        TextView childdetail_pcv1 = (TextView) findViewById(R.id.childdetail_pcv1);
        TextView childdetail_opv1 = (TextView) findViewById(R.id.childdetail_opv1);
        TextView childdetail_penta1 = (TextView) findViewById(R.id.childdetail_penta1);
        TextView childdetail_pcv2 = (TextView) findViewById(R.id.childdetail_pcv2);
        TextView childdetail_opv2 = (TextView) findViewById(R.id.childdetail_opv2);
        TextView childdetail_penta2 = (TextView) findViewById(R.id.childdetail_penta2);
        TextView childdetail_pcv3 = (TextView) findViewById(R.id.childdetail_pcv3);
        TextView childdetail_opv3 = (TextView) findViewById(R.id.childdetail_opv3);
        TextView childdetail_penta3 = (TextView) findViewById(R.id.childdetail_penta3);
        TextView childdetail_ipv= (TextView) findViewById(R.id.childdetail_ipv);
        TextView childdetail_measles1 = (TextView) findViewById(R.id.childdetail_measles1);
        TextView childdetail_measles2 = (TextView) findViewById(R.id.childdetail_measles2);



        if ((childclient.getDetails().get("Gender") != null ? childclient.getDetails().get("Gender") : "").equalsIgnoreCase("1")) {
            profilepic.setImageResource(R.drawable.child_boy_infant);
//                newborn_or_fp.setText("Family Planning");
        } else {
            profilepic.setImageResource(R.drawable.child_girl_infant);
//                newborn_or_fp.setVisibility(View.INVISIBLE);
        }
        try {
            if (childclient.getDetails().get("profilepic") != null) {
                HouseHoldDetailActivity.setImagetoHolder(this, childclient.getDetails().get("profilepic"), profilepic, R.drawable.child_boy_infant);
            }
        }catch (Exception e){

        }


//        TextView age = (TextView) findViewById(R.id.age);
//        TextView godhhid = (TextView) findViewById(R.id.gobhhid);
//        TextView village = (TextView) findViewById(R.id.ward);
//
//        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
        name.setText(humanize((childclient.getColumnmaps().get("Member_Fname") != null ? childclient.getColumnmaps().get("Member_Fname") : "").replace("+", "_")));//
        brid.setText((childclient.getDetails().get("Member_BRID") != null ? childclient.getDetails().get("Member_BRID") : "").replace("+", "_"));//
        fathername.setText((childclient.getDetails().get("Child_father_name") != null ? childclient.getDetails().get("Child_father_name") : ""));
        mothername.setText((childclient.getDetails().get("Child_mother_name") != null ? childclient.getDetails().get("Child_mother_name") : ""));
        epicarno.setText((childclient.getDetails().get("epi_card_number") != null ? childclient.getDetails().get("epi_card_number") : ""));
        birthdate.setText((childclient.getDetails().get("Child_birth_date") != null ? childclient.getDetails().get("Child_birth_date") : ""));
        contactno.setText((childclient.getDetails().get("contact_phone_number") != null ? childclient.getDetails().get("contact_phone_number") : ""));
        address.setText((childclient.getDetails().get("HH_Address") != null ? childclient.getDetails().get("HH_Address") : ""));

        ChildVaccinecheck(childclient,childdetail_bcg,findViewById(R.id.child_block1),"final_bcg","child_bcg");
        ChildVaccinecheck(childclient,childdetail_opv0,findViewById(R.id.child_block2),"final_opv0","child_opv0");
        ChildVaccinecheck(childclient,childdetail_pcv1,findViewById(R.id.child_block3),"final_pcv1","child_pcv1");
        ChildVaccinecheck(childclient,childdetail_opv1,findViewById(R.id.child_block4),"final_opv1","child_opv1");
        ChildVaccinecheck(childclient,childdetail_penta1,findViewById(R.id.child_block5),"final_penta1","child_penta1");
        ChildVaccinecheck(childclient,childdetail_pcv2,findViewById(R.id.child_block6),"final_pcv2","child_pcv2");
        ChildVaccinecheck(childclient,childdetail_opv2,findViewById(R.id.child_block7),"final_opv2","child_opv2");
        ChildVaccinecheck(childclient,childdetail_penta2,findViewById(R.id.child_block8),"final_penta2","child_penta2");
        ChildVaccinecheck(childclient,childdetail_pcv3,findViewById(R.id.child_block9),"final_pcv3","child_pcv3");
        ChildVaccinecheck(childclient,childdetail_opv3,findViewById(R.id.child_block10),"final_opv3","child_opv3");
        ChildVaccinecheck(childclient,childdetail_penta3,findViewById(R.id.child_block11),"final_penta3","child_penta3");
        ChildVaccinecheck(childclient,childdetail_ipv,findViewById(R.id.child_block12),"final_ipv","child_ipv");
        ChildVaccinecheck(childclient,childdetail_measles1,findViewById(R.id.child_block13),"final_measles1","child_measles");
        ChildVaccinecheck(childclient,childdetail_measles2,findViewById(R.id.child_block14),"final_measles2","child_measles2");


    }
    public LinearLayout makevaccinerow (String vaccinename,String vaccinedate){
        LinearLayout vaccinerow = (LinearLayout) getLayoutInflater().inflate(R.layout.vaccine_row, null);
        ((TextView)vaccinerow.findViewById(R.id.vaccinename)).setText(vaccinename);
        ((TextView)vaccinerow.findViewById(R.id.vaccine_date)).setText(vaccinedate);

        return vaccinerow;
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
    public void ChildVaccinecheck(CommonPersonObjectClient childClient, TextView tt1TextView,View block,String ttfinalKey,String ttschedulename){
        if(!(childClient.getDetails().get(ttfinalKey)!=null?childClient.getDetails().get(ttfinalKey):"").equalsIgnoreCase("")){
            block.setBackgroundColor(getResources().getColor(R.color.alert_complete_green));
            String text = (childClient.getDetails().get(ttfinalKey)!=null?childClient.getDetails().get(ttfinalKey):"");
            tt1TextView.setText(text);
        }else{

            List<Alert> child_alertlist_for_client = Context.getInstance().alertService().findByEntityIdAndAlertNames(childClient.entityId(), ttschedulename);
            if(child_alertlist_for_client.size()>0) {
                for (int i = 0; i < child_alertlist_for_client.size(); i++) {
                    if (child_alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")) {
                        block.setBackgroundColor(getResources().getColor(R.color.alert_upcoming_yellow));
                        String text =  (child_alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(text);
                    } else if (child_alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")) {
                        block.setBackgroundColor(getResources().getColor(R.color.alert_urgent_red));
                        String text = (child_alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(text);
                    } else if (child_alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")) {
                        block.setBackgroundColor(getResources().getColor(R.color.client_list_header_dark_grey));
                        String text = (child_alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(text);
                    } else if (child_alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")) {
                        block.setBackgroundColor(getResources().getColor(R.color.alert_upcoming_light_blue));
                        String text = (child_alertlist_for_client.get(i).expiryDate());
                        tt1TextView.setText(Html.fromHtml(text));
                    }

                }
            }else{
                String text = " ";
                tt1TextView.setText(text);

            }

        }

    }


    class childVaccineDetailAdapter extends BaseAdapter {
       vaccineInfo [] vaccineInfos;
       android.content.Context context;
       public childVaccineDetailAdapter(android.content.Context context,vaccineInfo [] childvaccineInfo){
           this.vaccineInfos = childvaccineInfo;
           this.context = context;
       }

       @Override
       public int getCount() {
           return vaccineInfos.length;
       }

       @Override
       public Object getItem(int position) {
           return null;
       }

       @Override
       public long getItemId(int position) {
           return 0;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {

           LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.vaccine_row,null);
           TextView vaccinename = (TextView)convertView.findViewById(R.id.vaccinename);
           TextView vaccinedate = (TextView)convertView.findViewById(R.id.vaccine_date);
           Button vaccinestate = (Button) convertView.findViewById(R.id.vacc_state);
           vaccinename.setText(vaccineInfos[position].name);
           vaccinedate.setText(vaccineInfos[position].date);
           if(vaccineInfos[position].state.equalsIgnoreCase("upcoming")){
               vaccinestate.setBackgroundColor(getResources().getColor(R.color.alert_complete_green));
           }
           if(vaccineInfos[position].state.equalsIgnoreCase("urgent")){
               vaccinestate.setBackgroundColor(getResources().getColor(R.color.alert_urgent_red));
           }
           if(vaccineInfos[position].state.equalsIgnoreCase("expired")){
               vaccinestate.setBackgroundColor(getResources().getColor(R.color.client_list_header_dark_grey));
           }
           if(vaccineInfos[position].state.equalsIgnoreCase("not yet due")){
               vaccinestate.setBackgroundColor(getResources().getColor(R.color.alert_upcoming_yellow));
           }

           return convertView;
       }


   }
    class vaccineInfo {
        String name;
        String state;
        String date;

        public vaccineInfo(String name, String state, String date) {
            this.name = name;
            this.state = state;
            this.date = date;
        }
    }
}
