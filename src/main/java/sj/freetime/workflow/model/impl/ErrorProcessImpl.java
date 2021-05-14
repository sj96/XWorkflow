package sj.freetime.workflow.model.impl;

import lombok.AllArgsConstructor;
import sj.freetime.workflow.model.ErrorProcess;

@AllArgsConstructor
public class ErrorProcessImpl implements ErrorProcess {

    private final String msg;
    private final Throwable cause;

    @Override
    public Throwable cause() {
        return cause;
    }

    @Override
    public String errorMsg() {
        return msg;
    }
}
