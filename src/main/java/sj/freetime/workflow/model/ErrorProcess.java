package sj.freetime.workflow.model;

import sj.freetime.workflow.model.impl.ErrorProcessImpl;

import java.io.Serializable;

public interface ErrorProcess extends Serializable {

    static ErrorProcess of(String msg) {
        return new ErrorProcessImpl(msg, new RuntimeException(msg));
    }

    static ErrorProcess of(Throwable t) {
        return new ErrorProcessImpl(t.getMessage(), t);
    }

    static ErrorProcess of(Throwable t, String msg) {
        return new ErrorProcessImpl(msg, t);
    }

    static ErrorProcess empty() {
        return new ErrorProcessImpl("Error!", new RuntimeException("Error!"));
    }

    Throwable cause();

    String errorMsg();
}
