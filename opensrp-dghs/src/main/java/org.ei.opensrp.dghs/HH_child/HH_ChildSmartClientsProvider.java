package org.ei.opensrp.dghs.HH_child;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.dghs.R;
import org.ei.opensrp.dghs.hh_member.HouseHoldDetailActivity;
import org.ei.opensrp.domain.Alert;

import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.customControls.CustomFontTextView;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
public class HH_ChildSmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;
    private final AlertService alertService;


    public HH_ChildSmartClientsProvider(Context context,
                                        View.OnClickListener onClickListener,
                                        AlertService alertService) {
        this.onClickListener = onClickListener;
        this.alertService = alertService;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);
    }

    @Override
    public void getView(final SmartRegisterClient smartRegisterClient, View convertView) {
        View itemView = convertView;

        LinearLayout profileinfolayout = (LinearLayout)itemView.findViewById(R.id.profile_info_layout);

        ImageView profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
        TextView childname = (TextView)itemView.findViewById(R.id.child_name);
        TextView parentname = (TextView)itemView.findViewById(R.id.parent_name);
        TextView gobhhid = (TextView)itemView.findViewById(R.id.gobhhid);
        TextView village = (TextView)itemView.findViewById(R.id.village);
        TextView dateofbirth = (TextView)itemView.findViewById(R.id.dob_age);
        TextView epi_card_number = (TextView)itemView.findViewById(R.id.epi_cardnumber);






//        TextView psrfdue = (TextView)itemView.findViewById(R.id.psrf_due_date);
////        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);
//
//        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
        profileinfolayout.setOnClickListener(onClickListener);
        profileinfolayout.setTag(smartRegisterClient);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;

        childname.setText(pc.getColumnmaps().get("Member_Fname")!=null?pc.getColumnmaps().get("Member_Fname"):"");
        epi_card_number.setText(pc.getDetails().get("epi_card_number")!=null?pc.getDetails().get("epi_card_number"):"");
        parentname.setText(pc.getDetails().get("Child_mother_name")!=null?pc.getDetails().get("Child_mother_name"):"");
        gobhhid.setText(pc.getColumnmaps().get("Member_GOB_HHID")!=null?pc.getColumnmaps().get("Member_GOB_HHID"):"");
        gobhhid.setVisibility(View.INVISIBLE);
        village.setText((humanize((pc.getDetails().get("Member_WARD") != null ? pc.getDetails().get("Member_WARD") : "").replace("+", "_")))+", "+humanize((pc.getDetails().get("Member_BLOCK") != null ? pc.getDetails().get("Member_BLOCK") : "").replace("+", "_")));
//        dateofbirth.setText(mcaremotherObject.getColumnmaps().get("FWBNFDTOO")!=null?mcaremotherObject.getColumnmaps().get("FWBNFDTOO"):"");
        String dataofbirth = (pc.getDetails().get("Child_dob")!=null?pc.getDetails().get("Child_dob"):"") + "\n";
        dataofbirth = dataofbirth + "age : " + ((pc.getDetails().get("Child_age_days")!=null?pc.getDetails().get("Child_age_days"):"") + "days");
        dateofbirth.setText(dataofbirth);

        if ((pc.getDetails().get("Gender") != null ? pc.getDetails().get("Gender") : "").equalsIgnoreCase("1")) {
            profilepic.setImageResource(R.drawable.child_boy_infant);
//                newborn_or_fp.setText("Family Planning");
        } else {
            profilepic.setImageResource(R.drawable.child_girl_infant);
//                newborn_or_fp.setVisibility(View.INVISIBLE);
        }

        if (pc.getDetails().get("profilepic") != null) {
            HouseHoldDetailActivity.setImagetoHolder((Activity) context, pc.getDetails().get("profilepic"), profilepic, R.drawable.child_boy_infant);
        }

        constructRiskFlagView(pc, itemView);
        contstructNextVaccinedateBlock(pc, itemView);
        contstructLastVaccinedateBlock(pc,itemView);
//        constructENCCReminderDueBlock(pc, itemView);
////        constructNBNFDueBlock(pc, itemView);s
//        constructENCCVisitStatusBlock(pc,itemView);




        itemView.setLayoutParams(clientViewLayoutParams);
    }

  public boolean isSYnced(CommonPersonObjectClient pc){
      List<Alert> child_bcg_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_bcg","child_ipv","child_opv0","child_opv1","child_opv2","child_opv3","child_pcv1","child_pcv2","child_pcv3","child_penta1","child_penta2","child_penta3","child_measles","child_measles2");
      if(child_bcg_alertlist_for_client.size()>0){
          return true;
      }
      return false;
  }

  private ArrayList<Alert> checkAlertListForVaccine(CommonPersonObjectClient pc) {
      ArrayList<Alert> alerts_to_return = new ArrayList<Alert>();
        List<Alert> child_bcg_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_bcg");
        List<Alert> child_ipv_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_ipv");
        List<Alert> child_opv0_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_opv0");
        List<Alert> child_opv1_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_opv1");
        List<Alert> child_opv2_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_opv2");
        List<Alert> child_opv3_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_opv3");
        List<Alert> child_pcv1_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_pcv1");
        List<Alert> child_pcv2_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_pcv2");
        List<Alert> child_pcv3_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_pcv3");
        List<Alert> child_penta1_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_penta1");
        List<Alert> child_penta2_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_penta2");
        List<Alert> child_penta3_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_penta3");
        List<Alert> child_measles_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_measles");
        List<Alert> child_measles2_alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "child_measles2");

        if(child_bcg_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_bcg") != null ? pc.getDetails().get("final_bcg") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_bcg_alertlist_for_client.get(0));
            }
        }
        if(child_ipv_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_ipv") != null ? pc.getDetails().get("final_ipv") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_ipv_alertlist_for_client.get(0));
            }
        }
        if(child_opv0_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_opv0") != null ? pc.getDetails().get("final_opv0") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_opv0_alertlist_for_client.get(0));
            }
        }
        if(child_opv1_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_opv1") != null ? pc.getDetails().get("final_opv1") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_opv1_alertlist_for_client.get(0));
            }
        }
        if(child_opv2_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_opv2") != null ? pc.getDetails().get("final_opv2") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_opv2_alertlist_for_client.get(0));
            }
        }
        if(child_opv3_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_opv3") != null ? pc.getDetails().get("final_opv3") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_opv3_alertlist_for_client.get(0));
            }
        }
        if(child_pcv1_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_pcv1") != null ? pc.getDetails().get("final_pcv1") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_pcv1_alertlist_for_client.get(0));
            }
        }
        if(child_pcv2_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_pcv2") != null ? pc.getDetails().get("final_pcv2") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_pcv2_alertlist_for_client.get(0));
            }
        }
        if(child_pcv3_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_pcv3") != null ? pc.getDetails().get("final_pcv3") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_pcv3_alertlist_for_client.get(0));
            }
        }
        if(child_penta1_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_penta1") != null ? pc.getDetails().get("final_penta1") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_penta1_alertlist_for_client.get(0));
            }
        }
        if(child_penta2_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_penta2") != null ? pc.getDetails().get("final_penta2") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_penta2_alertlist_for_client.get(0));
            }
        }
        if(child_penta3_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_penta3") != null ? pc.getDetails().get("final_penta3") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_penta3_alertlist_for_client.get(0));
            }
        }
        if(child_measles_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_measles1") != null ? pc.getDetails().get("final_measles1") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_measles_alertlist_for_client.get(0));
            }
        }
        if(child_measles2_alertlist_for_client.size()>0){
            if((pc.getDetails().get("final_measles2") != null ? pc.getDetails().get("final_measles2") : "").equalsIgnoreCase("")) {
                alerts_to_return.add(child_measles2_alertlist_for_client.get(0));
            }
        }

        return alerts_to_return;

    }



    private void constructRiskFlagView(CommonPersonObjectClient pc,  View itemView) {
//        AllCommonsRepository allancRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("mcaremother");
//        CommonPersonObject ancobject = allancRepository.findByCaseID(pc.entityId());
//        AllCommonsRepository allelcorep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("elco");
//        CommonPersonObject elcoparent = allelcorep.findByCaseID(ancobject.getRelationalId());

        ImageView hrp = (ImageView)itemView.findViewById(R.id.hrp);
        ImageView hp = (ImageView)itemView.findViewById(R.id.hr);
        ImageView vg = (ImageView)itemView.findViewById(R.id.vg);
        if(pc.getDetails().get("FWVG") != null && pc.getDetails().get("FWVG").equalsIgnoreCase("1")){

        }else{
            vg.setVisibility(View.GONE);
        }
        if(pc.getDetails().get("FWHRP") != null && pc.getDetails().get("FWHRP").equalsIgnoreCase("1")){

        }else{
            hrp.setVisibility(View.GONE);
        }
        if(pc.getDetails().get("FWHR_PSR") != null && pc.getDetails().get("FWHR_PSR").equalsIgnoreCase("1")){

        }else{
            hp.setVisibility(View.GONE);
        }

//        if(pc.getDetails().get("FWWOMAGE")!=null &&)

    }


    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return null;
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
        return (ViewGroup) inflater().inflate(R.layout.smart_register_dghs_child_client, null);
    }
    private void contstructNextVaccinedateBlock(CommonPersonObjectClient pc, View itemView) {
        TextView nextVaccineDate = (TextView)itemView.findViewById(R.id.next_vaccine);
        boolean issynced = isSYnced(pc);
        if(!issynced){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.client_list_header_dark_grey));
            nextVaccineDate.setText("Not Synced");
            nextVaccineDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            nextVaccineDate.setOnClickListener(onClickListener);
            nextVaccineDate.setTag(pc);
        }else{
          ArrayList<Alert> alertlist = checkAlertListForVaccine(pc);
            Collections.sort(alertlist,new Comparator<Alert>() {

                public int compare(Alert alert1, Alert alert2) {
                    if(alert1.status().value().equalsIgnoreCase("urgent") && alert2.status().value().equalsIgnoreCase("urgent")){
                        return 0;
                    }else if(alert1.status().value().equalsIgnoreCase("normal") && alert2.status().value().equalsIgnoreCase("normal")){
                        return 0;
                    }else if(alert1.status().value().equalsIgnoreCase("upcoming") && alert2.status().value().equalsIgnoreCase("upcoming")){
                        return 0;
                    }else if(alert1.status().value().equalsIgnoreCase("expired") && alert2.status().value().equalsIgnoreCase("expired")){
                        return 0;
                    }else if(alert1.status().value().equalsIgnoreCase("urgent") && alert2.status().value().equalsIgnoreCase("urgent")){
                        return -1;
                    }else if(alert1.status().value().equalsIgnoreCase("urgent") && alert2.status().value().equalsIgnoreCase("normal")){
                        return -1;
                    }else if(alert1.status().value().equalsIgnoreCase("urgent") && alert2.status().value().equalsIgnoreCase("upcoming")){
                        return -1;
                    }else if(alert1.status().value().equalsIgnoreCase("urgent") && alert2.status().value().equalsIgnoreCase("expired")){
                        return -1;
                    }else if(alert1.status().value().equalsIgnoreCase("upcoming") && alert2.status().value().equalsIgnoreCase("normal")){
                        return -1;
                    }else if(alert1.status().value().equalsIgnoreCase("upcoming") && alert2.status().value().equalsIgnoreCase("expired")){
                        return -1;
                    }else if(alert1.status().value().equalsIgnoreCase("normal") && alert2.status().value().equalsIgnoreCase("expired")){
                        return -1;
                    }else{
                        return 1;
                    }


                }
            });
            if(alertlist.size()>0) {
                String vaccineName = getVaccineName(alertlist);
                nextVaccineDate.setText(vaccineName);
                if(alertlist.get(0).status().value().equalsIgnoreCase("upcoming")){
                    nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
                    nextVaccineDate.setOnClickListener(onClickListener);
                    nextVaccineDate.setTag(pc);
                }else  if(alertlist.get(0).status().value().equalsIgnoreCase("urgent")){
                    nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_urgent_red));
                    nextVaccineDate.setOnClickListener(onClickListener);
                    nextVaccineDate.setTag(pc);
                }else  if(alertlist.get(0).status().value().equalsIgnoreCase("normal")){
                    nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_light_blue));
                    nextVaccineDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }else if (alertlist.get(0).status().value().equalsIgnoreCase("expired")){
                    nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.client_list_header_dark_grey));
                    nextVaccineDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }


        }



    }

    private String getVaccineName(ArrayList<Alert> alertlist) {
       if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_bcg")) {
            return "BCG";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_ipv")) {
            return "IPV";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_opv0")) {
            return "OPV 0";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_opv1")) {
            return "OPV 1";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_opv2")) {
            return "OPV 2";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_opv3")) {
            return "OPV 3";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_pcv1")) {
            return "PCV 1";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_pcv2")) {
            return "PCV 2";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_pcv3")) {
            return "PCV 3";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_penta1")) {
            return "PENTA 1";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_penta2")) {
            return "PENTA 2";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_penta3")) {
            return "PENTA 3";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_measles")) {
            return "MEASLES 1";
        }
        if(alertlist.get(0).scheduleName().equalsIgnoreCase("child_measles2")) {
            return "MEASLES 2";
        }

        return "";
    }

    private void contstructLastVaccinedateBlock(CommonPersonObjectClient pc, View itemView) {
        TextView nlastVaccineDate = (TextView)itemView.findViewById(R.id.vaccinetext);
        TextView nlastVaccintick = (TextView)itemView.findViewById(R.id.vaccinetick);

        nlastVaccintick.setVisibility(View.VISIBLE);
        nlastVaccineDate.setVisibility(View.VISIBLE);

        if((pc.getDetails().get("child_vaccines_2") != null ? pc.getDetails().get("child_vaccines_2") : "").equalsIgnoreCase("")){
            nlastVaccineDate.setVisibility(View.INVISIBLE);
            nlastVaccintick.setVisibility(View.INVISIBLE);
        }else {
            String childVaccines = (pc.getDetails().get("child_vaccines_2") != null ? pc.getDetails().get("child_vaccines_2") : "");
            childVaccines = childVaccines.trim();
            childVaccines = childVaccines.replace(" ",",");
            nlastVaccineDate.setText(childVaccines);
        }

    }

    class alertTextandStatus{
        String alertText ,alertstatus;

        public alertTextandStatus(String alertText, String alertstatus) {
            this.alertText = alertText;
            this.alertstatus = alertstatus;
        }

        public String getAlertText() {
            return alertText;
        }

        public void setAlertText(String alertText) {
            this.alertText = alertText;
        }

        public String getAlertstatus() {
            return alertstatus;
        }

        public void setAlertstatus(String alertstatus) {
            this.alertstatus = alertstatus;
        }
    }
}
