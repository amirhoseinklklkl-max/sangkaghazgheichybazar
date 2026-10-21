package com.ancientpersia.rps.billing.util.communication;


import com.ancientpersia.rps.billing.util.IabResult;

public interface BillingSupportCommunication {
    void onBillingSupportResult(int response);
    void remoteExceptionHappened(IabResult result);
}
