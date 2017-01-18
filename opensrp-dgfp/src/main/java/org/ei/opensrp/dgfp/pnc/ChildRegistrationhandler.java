package org.ei.opensrp.dgfp.pnc;

import android.content.Context;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

import java.util.HashMap;
import java.util.Map;

public class ChildRegistrationhandler implements FormSubmissionHandler {

    Context context;
    public ChildRegistrationhandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(FormSubmission submission) {
//        String entityID = submission.entityId();
//        Log.v("fieldvalue",""+submission.getFieldValue("Visit_Status"));
        String entityID = submission.getFieldValue("mother_relational_ID");
        AllCommonsRepository allmemberRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("members");
        Map<String, String> memberDetails = new HashMap<String, String>();

        memberDetails.put("is_child_register_done", submission.getFieldValue("1"));

        allmemberRepository.mergeDetails(entityID, memberDetails);
//        CommonPersonObject memberobject = allmemberRepository.findByCaseID(entityID);
//        if(submission.getFieldValue("Visit_Status")!= null){
//            if(submission.getFieldValue("Visit_Status").equalsIgnoreCase("3")){
//                ((mCareANCSmartRegisterActivity) ((Activity) context)).startFormActivity("childregistration",memberobject.getRelationalId(),null);
//
//            }
//        }



    }
}
