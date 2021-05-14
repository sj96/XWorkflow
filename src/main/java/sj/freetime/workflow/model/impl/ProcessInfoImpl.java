package sj.freetime.workflow.model.impl;

import lombok.Getter;
import lombok.Setter;
import sj.freetime.workflow.model.ErrorProcess;
import sj.freetime.workflow.model.ProcessData;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ProcessInfoImpl implements sj.freetime.workflow.model.ProcessInfo {

    private final String id;

    @Getter
    @Setter
    private ErrorProcess error;

    @Getter
    private final ProcessData data = ProcessData.create();

    public ProcessInfoImpl() {
        this(UUID.randomUUID().toString());
    }

    public ProcessInfoImpl(String id) {
        this.id = id;
    }

    @Override
    public String processId() {
        return id;
    }

    @Override
    public boolean success() {
        return Objects.isNull(error);
    }

    @Override
    public boolean failed() {
        return !success();
    }

}
