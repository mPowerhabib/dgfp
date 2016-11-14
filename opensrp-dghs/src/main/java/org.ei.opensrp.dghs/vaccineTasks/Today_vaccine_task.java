package org.ei.opensrp.dghs.vaccineTasks;

import android.content.ContentValues;
import android.util.Log;

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
public class Today_vaccine_task extends AsyncTask {
    private AllSharedPreferences allSharedPreferences;
    private DristhiConfiguration configuration;
    public static final String todays_client = "todays-client";
    private  HTTPAgent httpAgent;
    private AllSettings allSettings;
    public Context context;

    public Today_vaccine_task(Context context, HTTPAgent httpAgent, DristhiConfiguration configuration, AllSettings allSettings, AllSharedPreferences allSharedPreferences) {
        this.httpAgent = httpAgent;
        this.configuration = configuration;
        this.allSettings = allSettings;
        this.allSharedPreferences = allSharedPreferences;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String pulledVaccineList= pullVaccineListFromServer();
        if(!pulledVaccineList.equalsIgnoreCase("")){
            processPulledVaccineList(pulledVaccineList);
        }
//        Log.v("pullv",pullv);
        return null;
    }

    private void processPulledVaccineList(String pulledVaccineList) {
        String todays_vaccine_list = context.applicationContext().getSharedPreferences("vaccine", android.content.Context.MODE_PRIVATE).getString("todays_vaccine_list","");
        try {
            JSONArray vaccineClientList = new JSONArray(pulledVaccineList);
            for(int i = 0;i<vaccineClientList.length();i++){

                JSONObject vaccineClient = vaccineClientList.getJSONObject(i);
                String entityId = vaccineClient.getString("entityId");
                String missedCount = vaccineClient.getString("missedCount");
                if(i!=0){
                    todays_vaccine_list = todays_vaccine_list+",'"+entityId+"'";
                }else{
                    todays_vaccine_list = "'"+entityId+"'";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.applicationContext().getSharedPreferences("vaccine", android.content.Context.MODE_PRIVATE).edit().putString("todays_vaccine_list",todays_vaccine_list).commit();


    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
    public String  pullVaccineListFromServer() {
        FetchStatus dataStatus = nothingFetched;
        String anmId = allSharedPreferences.fetchRegisteredANM();
        String baseURL = configuration.dristhiBaseURL();
        while (true) {
            String uri = format("{0}/{1}?anmIdentifier={2}",
                    baseURL,
                    todays_client,
                    anmId);
            Log.v("pull-uri",uri);
            Response<String> response = httpAgent.fetch(uri);
//            return response.payload();
            if (response.isFailure()) {
                logError(format("Form submissions pull failed."));
                return "";
            }else{
                return response.payload();
            }

        }
    }
}
