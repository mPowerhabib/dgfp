package org.ei.opensrp.dgfp;

import android.app.Activity;
import android.content.Intent;

import org.ei.opensrp.dgfp.adolescent.dgfp_adolescent_SmartRegisterActivity;
import org.ei.opensrp.dgfp.anc.mCareANCSmartRegisterActivity;
import org.ei.opensrp.dgfp.elco.HH_woman_member_SmartRegisterActivity;
import org.ei.opensrp.dgfp.hh_member.HH_member_SmartRegisterActivity;
import org.ei.opensrp.dgfp.injectables.dgfp_injectable_SmartRegisterActivity;
import org.ei.opensrp.dgfp.nutrition.dgfp_nutrition_SmartRegisterActivity;
import org.ei.opensrp.dgfp.pnc.mCarePNCSmartRegisterActivity;
import org.ei.opensrp.view.controller.ANMController;

public class DGFPNavigationController extends org.ei.opensrp.view.controller.NavigationController {
    private Activity activity;
    private ANMController anmController;

    public DGFPNavigationController(Activity activity, ANMController anmController) {
        super(activity,anmController);
        this.activity = activity;
        this.anmController = anmController;
    }

    public void startHHSmartRegistry() {

        activity.startActivity(new Intent(activity, HH_member_SmartRegisterActivity.class));


    }
    @Override
    public void startECSmartRegistry() {

//        activity.startActivity(new Intent(activity, HH_member_SmartRegisterActivity.class));


    }
    @Override
    public void startFPSmartRegistry() {
//        activity.startActivity(new Intent(activity, HH_woman_member_SmartRegisterActivity.class));
    }
    @Override
    public void startChildSmartRegistry() {
//        activity.startActivity(new Intent(activity, HH_child_member_SmartRegisterActivity.class));
    }

    public void startHHElcoSmartRegistry() {
        activity.startActivity(new Intent(activity, HH_woman_member_SmartRegisterActivity.class));

    }
    @Override
    public void startANCSmartRegistry() {
        activity.startActivity(new Intent(activity, mCareANCSmartRegisterActivity.class));

    }
    @Override
    public void startPNCSmartRegistry() {
        activity.startActivity(new Intent(activity, mCarePNCSmartRegisterActivity.class));

    }
    public void startnutritionSmartRegistry() {
        activity.startActivity(new Intent(activity, dgfp_nutrition_SmartRegisterActivity.class));

    }public void startadolescentSmartRegistry() {
        activity.startActivity(new Intent(activity, dgfp_adolescent_SmartRegisterActivity.class));

    }

    public void startinjectableSmartRegistry() {
        activity.startActivity(new Intent(activity, dgfp_injectable_SmartRegisterActivity.class));

    }

}
