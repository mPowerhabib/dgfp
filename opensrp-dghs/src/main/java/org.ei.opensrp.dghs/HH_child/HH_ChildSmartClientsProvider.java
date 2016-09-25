package org.ei.opensrp.dghs.HH_child;

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





//        TextView psrfdue = (TextView)itemView.findViewById(R.id.psrf_due_date);
////        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);
//
//        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
        profileinfolayout.setOnClickListener(onClickListener);
        profileinfolayout.setTag(smartRegisterClient);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;

        childname.setText(pc.getColumnmaps().get("Member_Fname")!=null?pc.getColumnmaps().get("Member_Fname"):"");
        parentname.setText(pc.getDetails().get("Guardian_Name_Father")!=null?pc.getDetails().get("C_Guardian_Name_Father"):"");
        gobhhid.setText(pc.getColumnmaps().get("Member_GOB_HHID")!=null?pc.getColumnmaps().get("Member_GOB_HHID"):"");
        village.setText(humanize((pc.getDetails().get("Member_WARD") != null ? pc.getDetails().get("Member_WARD") : "").replace("+", "_")));
//        dateofbirth.setText(mcaremotherObject.getColumnmaps().get("FWBNFDTOO")!=null?mcaremotherObject.getColumnmaps().get("FWBNFDTOO"):"");
        String dataofbirth = (pc.getDetails().get("DoB")!=null?pc.getDetails().get("DoB"):"") + "\n";
        dataofbirth = dataofbirth + "age : " + (pc.getColumnmaps().get("Age")!=null?pc.getColumnmaps().get("Age"):"");
        dateofbirth.setText(dataofbirth);

        if ((pc.getDetails().get("Gender") != null ? pc.getDetails().get("Gender") : "").equalsIgnoreCase("1")) {
            profilepic.setImageResource(R.drawable.child_boy_infant);
//                newborn_or_fp.setText("Family Planning");
        } else {
            profilepic.setImageResource(R.drawable.child_girl_infant);
//                newborn_or_fp.setVisibility(View.INVISIBLE);
        }



        constructRiskFlagView(pc, itemView);
        contstructNextVaccinedateBlock(pc, itemView);
        contstructLastVaccinedateBlock(pc,itemView);
//        constructENCCReminderDueBlock(pc, itemView);
////        constructNBNFDueBlock(pc, itemView);s
//        constructENCCVisitStatusBlock(pc,itemView);




        itemView.setLayoutParams(clientViewLayoutParams);
    }


    private void contstructNextVaccinedateBlock(CommonPersonObjectClient pc, View itemView) {
        TextView nextVaccineDate = (TextView)itemView.findViewById(R.id.next_vaccine);
        nextVaccineDate.setOnClickListener(onClickListener);
        nextVaccineDate.setTag(pc);
        if(!(pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green));
            nextVaccineDate.setText("Completed");
            nextVaccineDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else if(!(pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("Measles-"+ (pc.getDetails().get("Date_of_Measles")!=null?pc.getDetails().get("Date_of_Measles"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "child_vaccination_measles");
        }else if(!(pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("MR-"+ (pc.getDetails().get("Date_of_MR")!=null?pc.getDetails().get("Date_of_MR"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "child_vaccination_mr");
        }else if(!(pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("IPV-"+ (pc.getDetails().get("Date_of_OPV_Penta_3_IPV")!=null?pc.getDetails().get("Date_of_OPV_Penta_3_IPV"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "child_vaccination_ipv");
        }else if(!(pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("OPV3-"+ (pc.getDetails().get("Date_of_OPV_Penta_3_IPV")!=null?pc.getDetails().get("Date_of_OPV_Penta_3_IPV"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "child_vaccination_opv3");
        }else if(!(pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("OPV2-"+ (pc.getDetails().get("Date_of_OPV_Penta_PCV_2")!=null?pc.getDetails().get("Date_of_OPV_Penta_PCV_2"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "child_vaccination_opv2");
        }else if(!(pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("OPV1-"+ (pc.getDetails().get("Date_of_OPV_Penta_PCV_1")!=null?pc.getDetails().get("Date_of_OPV_Penta_PCV_1"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "child_vaccination_opv1");
        }else if(!(pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("PCV1-" + (pc.getDetails().get("Date_of_OPV_Penta_PCV_1") != null ? pc.getDetails().get("Date_of_OPV_Penta_PCV_1") : ""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname,"child_vaccination_pcv1");
        }else if(!(pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("OPV0-" + (pc.getDetails().get("Date_of_BCG_OPV_0") != null ? pc.getDetails().get("Date_of_BCG_OPV_0") : ""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname,"child_vaccination_opv0");
        }else{
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("BCG-" + (pc.getDetails().get("Date_of_BCG_OPV_0") != null ? pc.getDetails().get("Date_of_BCG_OPV_0") : ""));
            nextVaccineDate.setTag(R.id.clientobject, pc);

            nextVaccineDate.setTag(R.id.formname, "child_vaccination_bcg");
        }

    }

    private void contstructLastVaccinedateBlock(CommonPersonObjectClient pc, View itemView) {
        TextView nlastVaccineDate = (TextView)itemView.findViewById(R.id.vaccinetext);
        TextView nlastVaccintick = (TextView)itemView.findViewById(R.id.vaccinetick);

        nlastVaccintick.setVisibility(View.VISIBLE);

        if(!(pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("Measles-" + (pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_Measles_Date_of_Vaccination"):""));
        }else if(!(pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("MR-" + (pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_MR_Date_of_Vaccination") : ""));
        }else if(!(pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination"):"").equalsIgnoreCase("")){
           nlastVaccineDate.setText("IPV-" + (pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_IPV_Date_of_Vaccination") : ""));
        }else if(!(pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("OPV3-" + (pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV3_Date_of_Vaccination") : ""));
        }else if(!(pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("OPV2-" + (pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV2_Date_of_Vaccination") : ""));
       }else if(!(pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("OPV1-" + (pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV1_Date_of_Vaccination") : ""));
        }else if(!(pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("PCV1-" + (pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_PCV1_Date_of_Vaccination") : ""));
        }else if(!(pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("OPV0-" + (pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_OPV0_Date_of_Vaccination") : ""));
       }else if(!(pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination")!=null?pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nlastVaccineDate.setText("BCG-" + (pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination") != null ? pc.getDetails().get("ChildVaccination_BCG_Date_of_Vaccination") : ""));
       }else{
            nlastVaccineDate.setText("");
            nlastVaccintick.setVisibility(View.INVISIBLE);
        }

    }
//    "child_bcg";
//    "child_ipv";
//    "child_measles";
//    "child_mr";
//    "child_opv0";
//    "child_opv1";
//    "child_opv2";
//    "child_opv3";
//    "child_pcv1";
//    "child_pcv2";
//    "child_pcv3";
//    "child_penta1";
//    "child_penta2";
//    "child_penta3";
    private List<Alert> checkAlertListForVaccine(CommonPersonObjectClient pc) {
        List<Alert> alerts_to_return = new ArrayList<Alert>();
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
