package org.sj.core.demo.workflow;

import org.sj.core.demo.workflow.model.XData;
import org.sj.core.demo.workflow.util.Callback;

public interface XAsyncProcess {
    void run(XData input);

    void run(XData input, Callback<XData> callback);

    String getProcessName();
}
