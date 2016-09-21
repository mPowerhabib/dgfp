package org.ei.opensrp.dghs.HH_woman;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BirthOutcomeHandler implements FormSubmissionHandler {


    public BirthOutcomeHandler() {

    }

    @Override
    public void handle(FormSubmission submission) {
        String entityID = submission.entityId();
        CommonPersonObject householdobject = Context.getInstance().allCommonsRepositoryobjects("household").findByCaseID(entityID);
        AllCommonsRepository householdrep = Context.getInstance().allCommonsRepositoryobjects("household");
        Map<String, String> householdDetails = new HashMap<String, String>();
        householdDetails.put("outcome_current_formStatus","complete");
//        ElcoDetails.put("FWELIGIBLE",submission.getFieldValue("FWELIGIBLE"));
        householdrep.mergeDetails(householdobject.getCaseId(),householdDetails);
//        submission.getFieldValue("ELCO");
    }
}
