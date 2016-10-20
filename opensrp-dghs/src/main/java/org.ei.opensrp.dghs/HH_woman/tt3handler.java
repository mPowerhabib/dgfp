package org.ei.opensrp.dghs.HH_woman;

import org.ei.opensrp.Context;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

import java.util.List;

public class tt3handler implements FormSubmissionHandler {


    public tt3handler() {

    }

    @Override
    public void handle(FormSubmission submission) {
        String entityID = submission.entityId();
        List<Alert> alertlist_for_client = Context.getInstance().alertService().findByEntityIdAndAlertNames(entityID, "Woman_TT3");
        if(alertlist_for_client.size() == 0){

        }else{
            for(int i = 0;i<alertlist_for_client.size();i++){
                Context.getInstance().alertService().changeAlertStatusToComplete(entityID, "Woman_TT3");
            }
        }

    }
}
