package org.ei.opensrp.dgfp.anc;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.dgfp.elco.HH_woman_member_SmartRegisterActivity;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

import java.util.List;

public class nbnfhandler implements FormSubmissionHandler {

    Context context;
    public nbnfhandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(FormSubmission submission) {
        String entityID = submission.entityId();
//        Log.v("fieldvalue",""+submission.getFieldValue("Visit_Status"));
//        AllCommonsRepository allmemberRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("members");
//        CommonPersonObject memberobject = allmemberRepository.findByCaseID(entityID);
//        if(submission.getFieldValue("Visit_Status")!= null){
//            if(submission.getFieldValue("Visit_Status").equalsIgnoreCase("3")){
//                ((mCareANCSmartRegisterActivity) ((Activity) context)).startFormActivity("childregistration",memberobject.getRelationalId(),null);
//
//            }
//        }



    }
}
