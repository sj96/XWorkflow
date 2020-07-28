package org.sj.core.demo.workflow.process;

import org.sj.core.demo.workflow.XProcess;
import org.sj.core.demo.workflow.model.XData;

public abstract class XTask implements XProcess {

    private final String taskName;
    protected final boolean stopWhenFail;

    public XTask(String taskName) {
        this(taskName, true);
    }

    public XTask(String taskName, boolean stopWhenFail) {
        this.taskName = taskName;
        this.stopWhenFail = stopWhenFail;
    }

    @Override
    public XData run(XData input) {
        before(input);
        if (stopWhenFail && !input.isResult()) {
            after(input);
            return input;
        } else {
            after(input);
            return execute(input);
        }
    }

    @Override
    public String getProcessName() {
        return taskName;
    }

    public void before(XData input) {
        // do nothing
    }

    public void after(XData output) {
        // do nothing
    }

    protected abstract XData execute(XData input);
}
