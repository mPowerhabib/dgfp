package org.ei.opensrp.dghs.hh_member;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.dghs.R;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
public class HouseHoldSmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;
    TextView nid ;
    TextView brid ;
    TextView bdh ;
    TextView gobhhid ;
    TextView mobileno ;
    TextView village;
    TextView headofhouseholdname;
    TextView age;
    TextView last_visit_date;
    TextView due_visit_date;
    ImageButton follow_up;
    LinearLayout profilelayout;
    ImageView profilepic;
    FrameLayout due_date_holder;
    Button warnbutton;

    AlertService alertService;
    public HouseHoldSmartClientsProvider(Context context,
                                         View.OnClickListener onClickListener,
                                         AlertService alertService) {
        this.onClickListener = onClickListener;
//        this.controller = controller;
        this.context = context;
        this.alertService = alertService;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);

    }

    @Override
    public void getView(SmartRegisterClient smartRegisterClient, View convertView) {

        
            profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);
             gobhhid = (TextView)convertView.findViewById(R.id.gobhhid);
         nid = (TextView)convertView.findViewById(R.id.nid);
         brid = (TextView)convertView.findViewById(R.id.brid);
         bdh = (TextView)convertView.findViewById(R.id.bdh);
             village = (TextView)convertView.findViewById(R.id.village);
             profilepic =(ImageView)convertView.findViewById(R.id.profilepic);
              age = (TextView)convertView.findViewById(R.id.txt_age);
         mobileno = (TextView)convertView.findViewById(R.id.mobile_no);

             headofhouseholdname = (TextView)convertView.findViewById(R.id.householdheadname);
               last_visit_date = (TextView)convertView.findViewById(R.id.last_visit_date);
             due_visit_date = (TextView)convertView.findViewById(R.id.next_visit_date);
             due_date_holder = (FrameLayout)convertView.findViewById(R.id.hh_due_date_holder);
             profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.household_profile_thumb));
            profilelayout.setOnClickListener(onClickListener);
         profilelayout.setTag(smartRegisterClient);
        CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;

        List<Alert> alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "FW CENSUS");

        if(pc.getDetails().get("profilepic")!=null){
            if((pc.getDetails().get("HoH_Gender")!=null?pc.getDetails().get("HoH_Gender"):"").equalsIgnoreCase("2")) {
                HouseHoldDetailActivity.setImagetoHolder((Activity) context, pc.getDetails().get("profilepic"),  profilepic, R.mipmap.womanimageload);
            }else if ((pc.getDetails().get("HoH_Gender")!=null?pc.getDetails().get("HoH_Gender"):"").equalsIgnoreCase("1")){
                HouseHoldDetailActivity.setImagetoHolder((Activity) context, pc.getDetails().get("profilepic"),  profilepic, R.mipmap.householdload);
            }

        }else{
            if((pc.getDetails().get("HoH_Gender")!=null?pc.getDetails().get("HoH_Gender"):"").equalsIgnoreCase("2")){
                 profilepic.setImageDrawable(context.getResources().getDrawable(R.drawable.woman_placeholder));
            }else if ((pc.getDetails().get("HoH_Gender")!=null?pc.getDetails().get("HoH_Gender"):"").equalsIgnoreCase("1")){
                 profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.household_profile_thumb));
            }
        }


//
         gobhhid.setText(pc.getDetails().get("GOB_HHID")!=null?pc.getDetails().get("GOB_HHID"):"");
         age.setText(pc.getDetails().get("HoH_Age")!=null?pc.getDetails().get("HoH_Age"):"");
         nid.setText("NID: "+ (pc.getDetails().get("HoH_NID")!=null?pc.getDetails().get("HoH_NID"):""));
         brid.setText("BRID: "+ (pc.getDetails().get("HoH_BRID")!=null?pc.getDetails().get("HoH_BRID"):""));
         mobileno.setText(pc.getDetails().get("HoH_Mobile_No")!=null?pc.getDetails().get("HoH_Mobile_No"):"");
         bdh.setText("BDH: "+(pc.getDetails().get("HoH_HID")!=null?pc.getDetails().get("HoH_HID"):""));

//         jvitahhid.setText(pc.getColumnmaps().get("FWJIVHHID")!=null?pc.getColumnmaps().get("FWJIVHHID"):"");
         village.setText((humanize((pc.getDetails().get("WARD")!=null?pc.getDetails().get("WARD"):"").replace("+","_"))));
         headofhouseholdname.setText(pc.getColumnmaps().get("HoH_FName")!=null?pc.getColumnmaps().get("HoH_FName"):"");
        Date lastdate = null;



                     last_visit_date.setText(pc.getDetails().get("Date_Of_Reg")!=null?pc.getDetails().get("Date_Of_Reg"):"");




        if(alertlist_for_client.size() == 0 ){
             due_visit_date.setText("Not Synced to Server");
             due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
             due_visit_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
//            due_visit_date.setOnClickListener(onClickListener);
//            due_visit_date.setTag(smartRegisterClient);

        }
        for(int i = 0;i<alertlist_for_client.size();i++){
             due_visit_date.setText(alertlist_for_client.get(i).expiryDate());
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")){
                 due_visit_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                 due_date_holder.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")){
                 due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
                 due_visit_date.setOnClickListener(onClickListener);
                 due_visit_date.setTag(smartRegisterClient);

            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")){
                 due_visit_date.setOnClickListener(onClickListener);
                 due_visit_date.setTag(smartRegisterClient);
                 due_date_holder.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")){
                 due_date_holder.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
                 due_visit_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            if(alertlist_for_client.get(i).isComplete()){
                 due_visit_date.setText("visited");
                 due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
            }
        }
        if(lastdate!= null){
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(lastdate);
            calendar.add(Calendar.DATE, 56);//8 weeks
            lastdate.setTime(calendar.getTime().getTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            String result = String.format(Locale.ENGLISH, format.format(lastdate) );
             due_visit_date.setText(format.format(lastdate) );
//             due_visit_date.append(format.format(lastdate));

        }


        convertView.setLayoutParams(clientViewLayoutParams);
//        return convertView;
    }
//    CommonPersonObjectController householdelcocontroller;
    private boolean getIfHouseholdHasElcoWithoutNationalID(CommonPersonObjectClient pc) {
        boolean toreturn = true;

        AllCommonsRepository allElcoRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("elco");
        ArrayList<String> list = new ArrayList<String>();
        list.add((pc.entityId()));
        List<CommonPersonObject> allchildelco = allElcoRepository.findByRelationalIDs(list);
        for (int i = 0; i < allchildelco.size(); i++) {
            if (allchildelco.get(i).getDetails().get("FWELIGIBLE").equalsIgnoreCase("1")) {
                if (allchildelco.get(i).getDetails().get("nidImage") == null) {
                    toreturn = false;
                }
            }
        }

        return toreturn;
    }

//    @Override
    public SmartRegisterClients getClients() {
        return controller.getClients();
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return getClients().applyFilter(villageFilter, serviceModeOption, searchFilter, sortOption);
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        // do nothing.
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public View inflatelayoutForCursorAdapter() {
        View View = (ViewGroup) inflater().inflate(R.layout.smart_register_hh_member_client, null);
        return View;
    }


    


}

