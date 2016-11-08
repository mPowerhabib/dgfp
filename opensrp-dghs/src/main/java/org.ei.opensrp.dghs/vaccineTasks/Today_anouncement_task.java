package org.ei.opensrp.dghs.vaccineTasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import org.ei.opensrp.Context;
import org.ei.opensrp.DristhiConfiguration;
import org.ei.opensrp.domain.FetchStatus;
import org.ei.opensrp.domain.Response;
import org.ei.opensrp.repository.AllSettings;
import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.service.HTTPAgent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.AsyncTask;

import static java.text.MessageFormat.format;
import static org.ei.opensrp.domain.FetchStatus.nothingFetched;
import static org.ei.opensrp.util.Log.logError;

/**
 * Created by raihan on 11/3/16.
 */
public class Today_anouncement_task extends AsyncTask {
    private AllSharedPreferences allSharedPreferences;
    private DristhiConfiguration configuration;
    public static final String camp_announcement = "camp-announcement";
    private  HTTPAgent httpAgent;
    private AllSettings allSettings;
    public Context context;
    ProgressDialog progress;
    String returnMessage;
    android.content.Context activitycontext;

    public Today_anouncement_task(android.content.Context activitycontext,Context context, HTTPAgent httpAgent, DristhiConfiguration configuration, AllSettings allSettings, AllSharedPreferences allSharedPreferences) {
        this.activitycontext = activitycontext;
        this.httpAgent = httpAgent;
        this.configuration = configuration;
        this.allSettings = allSettings;
        this.allSharedPreferences = allSharedPreferences;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(activitycontext,"please wait","Sending Announcement");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        returnMessage = pullVaccineListFromServer();

//        Log.v("pullv",pullv);
        return null;
    }



    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progress.dismiss();
        Toast.makeText(context.applicationContext(),returnMessage,Toast.LENGTH_LONG).show();
    }
    public String  pullVaccineListFromServer() {
        FetchStatus dataStatus = nothingFetched;
        String anmId = allSharedPreferences.fetchRegisteredANM();
        String baseURL = configuration.dristhiBaseURL();
        while (true) {
            String uri = format("{0}/{1}?anmIdentifier={2}",
                    baseURL,
                    camp_announcement,
                    anmId);
            Log.v("pull-uri",uri);
            Response<String> response = httpAgent.fetch(uri);
//            return response.payload();
            if (response.isFailure()) {
                logError(format("Form submissions pull failed."));
                return "Anouncement Not sent";
            }else{
                return "Anouncement Sent Successfully";
            }

        }
    }
}
