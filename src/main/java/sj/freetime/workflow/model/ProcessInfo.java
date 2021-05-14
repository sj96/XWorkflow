package sj.freetime.workflow.model;

import sj.freetime.workflow.model.impl.ProcessInfoImpl;

import java.io.Serializable;

public interface ProcessInfo extends Serializable {

    static ProcessInfo create() {
        return new ProcessInfoImpl();
    }

    static ProcessInfo cloneData(ProcessInfo processInfo) {
        ProcessInfo clone = new ProcessInfoImpl(processInfo.processId());
        clone.getData().putAll(processInfo.getData().data());
        return clone;
    }

    String processId();

    boolean success();

    boolean failed();

    ErrorProcess getError();

    ProcessData getData();

    void setError(ErrorProcess error);
}
