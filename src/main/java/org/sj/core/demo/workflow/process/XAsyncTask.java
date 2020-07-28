package org.sj.core.demo.workflow.process;

import org.sj.core.demo.workflow.XAsyncProcess;
import org.sj.core.demo.workflow.model.XData;
import org.sj.core.demo.workflow.util.Callback;

import java.util.Objects;

public abstract class XAsyncTask implements XAsyncProcess {

    private final String taskName;
    protected final boolean stopWhenFail;

    public XAsyncTask(String taskName) {
        this(taskName, true);
    }

    public XAsyncTask(String taskName, boolean stopWhenFail) {
        this.taskName = taskName;
        this.stopWhenFail = stopWhenFail;
    }

    @Override
    public void run(XData input) {
        run(input, null);
    }

    @Override
    public void run(XData input, Callback<XData> callback) {
        Callback<XData> xCallback = output -> {
            afterRun(output);
            if (Objects.nonNull(callback)) {
                callback.handle(output);
            }
        };
        beforeRun(input);
        if (stopWhenFail && !input.isResult()) {
            xCallback.handle(input);
        } else {
            execute(input, xCallback);
        }
    }

    @Override
    public String getProcessName() {
        return taskName;
    }

    public void beforeRun(XData input) {
        // do nothing
    }

    public void afterRun(XData output) {
        // do nothing
    }

    protected abstract void execute(XData input, Callback<XData> callback);
}
