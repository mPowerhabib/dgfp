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
        CommonPersonObject memberobject = Context.getInstance().allCommonsRepositoryobjects("members").findByCaseID(submission.getFieldValue("current_woman_id"));
        AllCommonsRepository memberrep = Context.getInstance().allCommonsRepositoryobjects("members");
        Map<String, String> memberDetails = new HashMap<String, String>();
        memberDetails.put("outcome_active","1");
//        ElcoDetails.put("FWELIGIBLE",submission.getFieldValue("FWELIGIBLE"));
        memberrep.mergeDetails(memberobject.getCaseId(),memberDetails);
//        submission.getFieldValue("ELCO");
    }
}
