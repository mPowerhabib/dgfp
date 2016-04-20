package org.ei.opensrp.dghs.HH_woman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
public class HH_woman_member_SmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;
    AlertService alertService;
    public HH_woman_member_SmartClientsProvider(Context context,
                                                View.OnClickListener onClickListener, AlertService alertService) {
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
        View itemView;
        itemView = convertView;
//        itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_mcare_anc_client, null);
        LinearLayout profileinfolayout = (LinearLayout)itemView.findViewById(R.id.profile_info_layout);

//        ImageView profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
        TextView name = (TextView)itemView.findViewById(R.id.name);
        TextView maritalstatus = (TextView)itemView.findViewById(R.id.maritalstatus);
        TextView gobhhid = (TextView)itemView.findViewById(R.id.gobhhid);
        TextView pregnancystatus = (TextView)itemView.findViewById(R.id.pregnancystatus);
        TextView village = (TextView)itemView.findViewById(R.id.village);
        TextView age = (TextView)itemView.findViewById(R.id.age);
        TextView nid = (TextView)itemView.findViewById(R.id.nid);
        TextView brid = (TextView)itemView.findViewById(R.id.brid);
        TextView hid = (TextView)itemView.findViewById(R.id.bdh);
        TextView edd = (TextView)itemView.findViewById(R.id.edd);
        TextView lmp = (TextView)itemView.findViewById(R.id.lmp);
        TextView ga = (TextView)itemView.findViewById(R.id.ga);
//        TextView psrfdue = (TextView)itemView.findViewById(R.id.psrf_due_date);
////        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);
//
//        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
        profileinfolayout.setOnClickListener(onClickListener);
        profileinfolayout.setTag(smartRegisterClient);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;


        name.setText(pc.getColumnmaps().get("Member_Fname")!=null?pc.getColumnmaps().get("Member_Fname"):"");
        gobhhid.setText(pc.getColumnmaps().get("Member_GOB_HHID")!=null?pc.getColumnmaps().get("Member_GOB_HHID"):"");
        hid.setText("BDH :" +pc.getDetails().get("HID")!=null?pc.getDetails().get("HID"):"");

        edd.setText("EDD :" +pc.getColumnmaps().get("EDD")!=null?pc.getColumnmaps().get("EDD"):"");
        lmp.setText("LMP :" +pc.getDetails().get("LMP")!=null?pc.getDetails().get("LMP"):"");
        ga.setText("GA :" +pc.getDetails().get("GA")!=null?pc.getDetails().get("GA"):"");



        if((pc.getColumnmaps().get("Marital_Status")!=null?pc.getColumnmaps().get("Marital_Status"):"").equalsIgnoreCase("1")){
            maritalstatus.setText("Unmarried");
        }
        else if((pc.getColumnmaps().get("Marital_Status")!=null?pc.getColumnmaps().get("Marital_Status"):"").equalsIgnoreCase("2")){
            maritalstatus.setText("Married");
        }else if ((pc.getColumnmaps().get("Marital_Status")!=null?pc.getColumnmaps().get("Marital_Status"):"").equalsIgnoreCase("3")){
            maritalstatus.setText("Divorced/Widow/Widower");
        }

        if((pc.getColumnmaps().get("Pregnancy_Status")!=null?pc.getColumnmaps().get("Pregnancy_Status"):"").equalsIgnoreCase("0")){
            pregnancystatus.setText("Not Pregnant");
        }
        else if((pc.getColumnmaps().get("Pregnancy_Status")!=null?pc.getColumnmaps().get("Pregnancy_Status"):"").equalsIgnoreCase("1")){
            pregnancystatus.setText("Pregnant");
        }else if ((pc.getColumnmaps().get("Pregnancy_Status")!=null?pc.getColumnmaps().get("Pregnancy_Status"):"").equalsIgnoreCase("9")){
            pregnancystatus.setText("");
        }
             village.setText(humanize((pc.getDetails().get("Member_WARD") != null ? pc.getDetails().get("Member_WARD") : "").replace("+", "_")));


        age.setText(pc.getColumnmaps().get("Age")!=null?pc.getColumnmaps().get("Age"):"");
        nid.setText("NID :" +(pc.getDetails().get("NID")!=null?pc.getDetails().get("NID"):""));
        brid.setText("BRID :" +(pc.getDetails().get("BRID")!=null?pc.getDetails().get("BRID"):""));

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date edd_date = format.parse(pc.getColumnmaps().get("FWPSRLMP")!=null?pc.getColumnmaps().get("FWPSRLMP"):"");
//            GregorianCalendar calendar = new GregorianCalendar();
//                calendar.setTime(edd_date);
//                calendar.add(Calendar.DATE, 259);
//                edd_date.setTime(calendar.getTime().getTime());
//            edd.setText("EDD :" + format.format(edd_date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        constructRiskFlagView(pc,itemView);
//        constructANCReminderDueBlock(pc.getColumnmaps().get("FWPSRLMP")!=null?pc.getColumnmaps().get("FWPSRLMP"):"",pc, itemView);
//        constructNBNFDueBlock(pc, itemView);
        constructvaccineVisitStatusBlock(pc, itemView);
        contstructNextVaccinedateBlock(pc,itemView);




        itemView.setLayoutParams(clientViewLayoutParams);
    }

    private void contstructNextVaccinedateBlock(CommonPersonObjectClient pc, View itemView) {

        TextView nextVaccineDate = (TextView)itemView.findViewById(R.id.next_vaccine_date);
        if(!(pc.getDetails().get("TT5_Date_of_Vaccination")!=null?pc.getDetails().get("TT5_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green));
            nextVaccineDate.setText("Completed");
        }else if(!(pc.getDetails().get("TT4_Date_of_Vaccination")!=null?pc.getDetails().get("TT4_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("TT5-"+ (pc.getDetails().get("Date_of_TT5")!=null?pc.getDetails().get("Date_of_TT5"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "woman_vaccination_tt5");
        }else if(!(pc.getDetails().get("TT3_Date_of_Vaccination")!=null?pc.getDetails().get("TT3_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("TT4-"+ (pc.getDetails().get("Date_of_TT4")!=null?pc.getDetails().get("Date_of_TT4"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "woman_vaccination_tt4");
        }else if(!(pc.getDetails().get("TT2_Date_of_Vaccination")!=null?pc.getDetails().get("TT2_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("TT3-"+ (pc.getDetails().get("Date_of_TT3")!=null?pc.getDetails().get("Date_of_TT3"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "woman_vaccination_tt3");
        }else if(!(pc.getDetails().get("TT1_Date_of_Vaccination")!=null?pc.getDetails().get("TT1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("TT2-"+ (pc.getDetails().get("Date_of_TT2")!=null?pc.getDetails().get("Date_of_TT2"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "woman_vaccination_tt2");
        }else if(!(pc.getDetails().get("measles_Date_of_Vaccination")!=null?pc.getDetails().get("measles_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("TT1-"+ (pc.getDetails().get("Date_of_TT1")!=null?pc.getDetails().get("Date_of_TT1"):""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname, "woman_vaccination_tt1");
        }else if(!(pc.getDetails().get("Date_of_Measles")!=null?pc.getDetails().get("Date_of_Measles"):"").equalsIgnoreCase("")){
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            nextVaccineDate.setText("Measles-" + (pc.getDetails().get("Date_of_Measles") != null ? pc.getDetails().get("Date_of_Measles") : ""));
            nextVaccineDate.setTag(R.id.clientobject, pc);
            nextVaccineDate.setTag(R.id.formname,"woman_vaccination_measles");
        }else{
            nextVaccineDate.setBackgroundColor(context.getResources().getColor(R.color.client_list_header_dark_grey));
            nextVaccineDate.setText("not applicable");
        }
        nextVaccineDate.setOnClickListener(onClickListener);
        nextVaccineDate.setTag(pc);
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption, FilterOption searchFilter, SortOption sortOption) {
        return null;
    }

    private void constructvaccineVisitStatusBlock(CommonPersonObjectClient pc, View itemview) {
        TextView tt1tick = (TextView)itemview.findViewById(R.id.tt1tick);
        TextView tt1text = (TextView)itemview.findViewById(R.id.tt1text);
        TextView tt2tick = (TextView)itemview.findViewById(R.id.tt2tick);
        TextView tt2text = (TextView)itemview.findViewById(R.id.tt2text);
        TextView tt3tick = (TextView)itemview.findViewById(R.id.tt3tick);
        TextView tt3text = (TextView)itemview.findViewById(R.id.tt3text);
        TextView tt4tick = (TextView)itemview.findViewById(R.id.tt4tick);
        TextView tt4text = (TextView)itemview.findViewById(R.id.tt4text);
        TextView tt5tick = (TextView)itemview.findViewById(R.id.tt5tick);
        TextView tt5text = (TextView)itemview.findViewById(R.id.tt5text);
        TextView measlestick = (TextView)itemview.findViewById(R.id.measlestick);
        TextView measlestext = (TextView)itemview.findViewById(R.id.measlestext);



        tt1text.setVisibility(View.INVISIBLE);
        tt1tick.setVisibility(View.INVISIBLE);

        tt2text.setVisibility(View.INVISIBLE);
        tt2tick.setVisibility(View.INVISIBLE);

        tt3text.setVisibility(View.INVISIBLE);
        tt3tick.setVisibility(View.INVISIBLE);

        tt4text.setVisibility(View.INVISIBLE);
        tt4tick.setVisibility(View.INVISIBLE);

        tt5text.setVisibility(View.INVISIBLE);
        tt5tick.setVisibility(View.INVISIBLE);

        measlestext.setVisibility(View.INVISIBLE);
        measlestick.setVisibility(View.INVISIBLE);







        if(!(pc.getDetails().get("TT5_Date_of_Vaccination")!=null?pc.getDetails().get("TT5_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            tt5tick.setVisibility(View.VISIBLE);
            tt5text.setVisibility(View.VISIBLE);
            tt5text.setText("TT5-"+ pc.getDetails().get("TT5_Date_of_Vaccination"));
        }
        if(!(pc.getDetails().get("TT4_Date_of_Vaccination")!=null?pc.getDetails().get("TT4_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            tt4tick.setVisibility(View.VISIBLE);
            tt4text.setVisibility(View.VISIBLE);
            tt4text.setText("TT4-" + pc.getDetails().get("TT4_Date_of_Vaccination"));
        }
        if(!(pc.getDetails().get("TT3_Date_of_Vaccination")!=null?pc.getDetails().get("TT3_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            tt3tick.setVisibility(View.VISIBLE);
            tt3text.setVisibility(View.VISIBLE);
            tt3text.setText("TT3-" + pc.getDetails().get("TT3_Date_of_Vaccination"));
        }
        if(!(pc.getDetails().get("TT2_Date_of_Vaccination")!=null?pc.getDetails().get("TT2_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            tt2tick.setVisibility(View.VISIBLE);
            tt2text.setVisibility(View.VISIBLE);
            tt2text.setText("TT2-" + pc.getDetails().get("TT2_Date_of_Vaccination"));
        }
        if(!(pc.getDetails().get("TT1_Date_of_Vaccination")!=null?pc.getDetails().get("TT1_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            tt1tick.setVisibility(View.VISIBLE);
            tt1text.setVisibility(View.VISIBLE);
            tt1text.setText("TT1-" + pc.getDetails().get("TT1_Date_of_Vaccination"));
        }
        if(!(pc.getDetails().get("measles_Date_of_Vaccination")!=null?pc.getDetails().get("measles_Date_of_Vaccination"):"").equalsIgnoreCase("")){
            measlestick.setVisibility(View.VISIBLE);
            measlestext.setVisibility(View.VISIBLE);
            measlestext.setText("Measles-" + pc.getDetails().get("measles_Date_of_Vaccination"));
        }


    }





    private void constructRiskFlagView(CommonPersonObjectClient pc, View itemView) {
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
    public View inflatelayoutForCursorAdapter() {
        View View = (ViewGroup) inflater().inflate(R.layout.smart_register_dghs_woman, null);
        return View;
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

    public String ancdate(String date,int day){
        String ancdate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date anc_date = format.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(anc_date);
            calendar.add(Calendar.DATE, day);
            anc_date.setTime(calendar.getTime().getTime());
            ancdate = format.format(anc_date);
        } catch (Exception e) {
            e.printStackTrace();
            ancdate = "";
        }
        return ancdate;
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
