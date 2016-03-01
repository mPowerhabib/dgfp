package org.ei.opensrp.dghs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;


import org.ei.opensrp.view.activity.NativeANCSmartRegisterActivity;
import org.ei.opensrp.view.controller.ANMController;


import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class McareNavigationController extends org.ei.opensrp.view.controller.NavigationController {
    private Activity activity;
    private ANMController anmController;

    public McareNavigationController(Activity activity, ANMController anmController) {
        super(activity,anmController);
        this.activity = activity;
        this.anmController = anmController;
    }
    @Override
    public void startECSmartRegistry() {

//        activity.startActivity(new Intent(activity, HouseHoldSmartRegisterActivity.class));


    }
    @Override
    public void startFPSmartRegistry() {
//        activity.startActivity(new Intent(activity, ElcoSmartRegisterActivity.class));
    }
    @Override
    public void startANCSmartRegistry() {
//        activity.startActivity(new Intent(activity, mCareANCSmartRegisterActivity.class));
    }

}
