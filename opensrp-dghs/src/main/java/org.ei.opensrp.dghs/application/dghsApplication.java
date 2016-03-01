package org.ei.opensrp.dghs.application;

import android.content.Intent;

import org.ei.opensrp.dghs.LoginActivity;
import org.ei.opensrp.view.activity.DrishtiApplication;

/**
 * Created by koros on 1/22/16.
 */
public class dghsApplication extends DrishtiApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void logoutCurrentUser(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        context.userService().logoutSession();
    }

}
