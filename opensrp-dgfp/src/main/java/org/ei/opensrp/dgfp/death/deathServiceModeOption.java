package org.ei.opensrp.dgfp.death;

import android.view.View;

import org.ei.opensrp.Context;
import org.ei.opensrp.dgfp.R;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.contract.ANCSmartRegisterClient;
import org.ei.opensrp.view.contract.ChildSmartRegisterClient;
import org.ei.opensrp.view.contract.FPSmartRegisterClient;
import org.ei.opensrp.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.opensrp.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.opensrp.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.opensrp.view.viewHolder.NativePNCSmartRegisterViewHolder;

import static org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class deathServiceModeOption extends ServiceModeOption {

    public deathServiceModeOption(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
       return Context.getInstance().getStringResource(R.string.home_death_label);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return new ClientsHeaderProvider() {
            @Override
            public int count() {
                return 5;
            }

            @Override
            public int weightSum() {
                return 20;
            }

            @Override
            public int[] weights() {
                return new int[]{6,4,3,4,3};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.hh_profile, R.string.elco_unique_id, R.string.date_of_death,
                        R.string.cause_of_death,R.string.record_death
                        };
            }
        };
    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(ANCSmartRegisterClient client, NativeANCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }


}
