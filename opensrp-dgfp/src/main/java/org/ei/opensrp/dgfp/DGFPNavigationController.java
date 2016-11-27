package org.ei.opensrp.dgfp;

import android.app.Activity;
import android.content.Intent;

import org.ei.opensrp.dgfp.hh_member.HH_member_SmartRegisterActivity;
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

}
