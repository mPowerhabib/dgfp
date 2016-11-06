package org.ei.opensrp.dghs.vaccineTasks;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ei.drishti.dto.form.FormSubmissionDTO;
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

import java.util.List;

import util.AsyncTask;

import static java.text.MessageFormat.format;
import static org.ei.opensrp.convertor.FormSubmissionConvertor.toDomain;
import static org.ei.opensrp.domain.FetchStatus.fetched;
import static org.ei.opensrp.domain.FetchStatus.fetchedFailed;
import static org.ei.opensrp.domain.FetchStatus.nothingFetched;
import static org.ei.opensrp.util.Log.logError;
import static org.ei.opensrp.util.Log.logVerbose;

/**
 * Created by raihan on 11/3/16.
 */
public class Last_vaccine_missedCount_task extends AsyncTask {
    private AllSharedPreferences allSharedPreferences;
    private DristhiConfiguration configuration;
    public static final String client_list = "client-list";
    private  HTTPAgent httpAgent;
    private AllSettings allSettings;
    public Context context;

    public Last_vaccine_missedCount_task(Context context, HTTPAgent httpAgent, DristhiConfiguration configuration, AllSettings allSettings, AllSharedPreferences allSharedPreferences) {
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
        long Lasttimestamp = context.applicationContext().getSharedPreferences("vaccine", android.content.Context.MODE_PRIVATE).getLong("lastTimeStampForVaccine",0);
        try {
            JSONArray vaccineClientList = new JSONArray(pulledVaccineList);
            for(int i = 0;i<vaccineClientList.length();i++){
                JSONObject vaccineClient = vaccineClientList.getJSONObject(i);
                String entityId = vaccineClient.getString("entityId");
                String missedCount = vaccineClient.getString("missedCount");
                String timeStamp = vaccineClient.getString("timeStamp");
                if(Long.parseLong(timeStamp)>Lasttimestamp){
                    Lasttimestamp = Long.parseLong(timeStamp);
                }
                ContentValues cv = new ContentValues();
                cv.put("missedCount",missedCount);
                context.commonrepository("members").updateColumn("members",cv,entityId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.applicationContext().getSharedPreferences("vaccine", android.content.Context.MODE_PRIVATE).edit().putLong("lastTimeStampForVaccine",Lasttimestamp).commit();


    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
    public String  pullVaccineListFromServer() {
        FetchStatus dataStatus = nothingFetched;
        String anmId = allSharedPreferences.fetchRegisteredANM();
        String baseURL = configuration.dristhiBaseURL();
        long Lasttimestamp = context.applicationContext().getSharedPreferences("vaccine", android.content.Context.MODE_PRIVATE).getLong("lastTimeStampForVaccine",0);
        Log.v("responsecontentlasttime",""+Lasttimestamp);
        while (true) {
            String uri = format("{0}/{1}?anmIdentifier={2}&timeStamp={3}",
                    baseURL,
                    client_list,
                    anmId,
                    Lasttimestamp);
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
