package org.sj.core.demo.workflow;

import org.sj.core.demo.workflow.model.XData;

public interface XProcess {
    XData run(XData input);

    String getProcessName();
}
