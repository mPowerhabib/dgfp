package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import org.ei.drishti.R;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Criterion;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.service.HTTPAgent;
import org.ei.drishti.view.AlertFilter;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.UpdateAlertsTask;
import org.ei.drishti.view.adapter.AlertAdapter;
import org.ei.drishti.view.matcher.MatchByBeneficiaryOrThaayiCard;
import org.ei.drishti.view.matcher.MatchByTime;
import org.ei.drishti.view.matcher.MatchByVisitCode;

import java.util.ArrayList;

import static org.ei.drishti.domain.AlertFilterCriterionForTime.*;
import static org.ei.drishti.domain.AlertFilterCriterionForType.*;
import static org.ei.drishti.util.Log.logVerbose;

public class DrishtiMainActivity extends Activity {
    private static DrishtiService drishtiService;
    private UpdateAlertsTask updateAlerts;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private AlertController controller;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logVerbose("Initializing ...");
        setContentView(R.layout.main);

        final AlertAdapter alertAdapter = new AlertAdapter(this, R.layout.list_item, new ArrayList<Alert>());
        controller = setupController(alertAdapter);
        updateAlerts = new UpdateAlertsTask(controller, (ProgressBar) findViewById(R.id.progressBar));

        actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("Workplan");

        AlertFilter filter = new AlertFilter(alertAdapter);
        filter.addFilter(new MatchByBeneficiaryOrThaayiCard((EditText) findViewById(R.id.searchEditText)));
        filter.addFilter(new MatchByTime(createDialog(R.drawable.icon, "Filter By Time", ShowAll, PastDue, Upcoming)));
        filter.addFilter(new MatchByVisitCode(createDialog(R.drawable.icon, "Filter By Type", All, BCG, HEP, OPV)));

        ListView alertsList = (ListView) findViewById(R.id.listView);
        alertsList.setAdapter(alertAdapter);
        alertsList.setTextFilterEnabled(true);

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                controller.changeUser();
                Toast.makeText(getApplicationContext(), "Changes saved.", Toast.LENGTH_LONG).show();
            }
        };
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAlerts.updateDisplay();
        updateAlerts.updateFromServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateMenuItem:
                updateAlerts.updateFromServer();
                return true;
            case R.id.settingsMenuItem:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public AlertController setupController(AlertAdapter alertAdapter) {
        SettingsRepository settingsRepository = new SettingsRepository();
        AlertRepository alertRepository = new AlertRepository();
        new Repository(getApplicationContext(), settingsRepository, alertRepository);
        AllSettings allSettings = new AllSettings(PreferenceManager.getDefaultSharedPreferences(this), settingsRepository);
        AllAlerts allAlerts = new AllAlerts(alertRepository);
        if (drishtiService == null) {
            drishtiService = new DrishtiService(new HTTPAgent(), "http://drishti.modilabs.org");
        }
        return new AlertController(drishtiService, allSettings, allAlerts, alertAdapter);
    }

    public static void setDrishtiService(DrishtiService value) {
        drishtiService = value;
    }

    private DialogAction createDialog(int icon, String title, Criterion... options) {
        DialogAction filterDialog = new DialogAction(this, icon, title, options);
        actionBar.addAction(filterDialog);
        return filterDialog;
    }
}
