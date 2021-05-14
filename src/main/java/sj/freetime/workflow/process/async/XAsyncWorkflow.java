package sj.freetime.workflow.process.async;

import sj.freetime.workflow.XAsyncProcess;
import sj.freetime.workflow.XFlow;
import sj.freetime.workflow.model.ErrorProcess;
import sj.freetime.workflow.model.ProcessInfo;
import sj.freetime.workflow.util.Callback;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class XAsyncWorkflow extends XAsyncTask implements XFlow<XAsyncWorkflow, XAsyncTask> {

    private final List<XAsyncProcess> taskList;
    private final boolean stopWhenFail;

    public XAsyncWorkflow(String taskName) {
        this(taskName, true);
    }

    public XAsyncWorkflow(String taskName, boolean stopWhenFail) {
        super(taskName);
        this.stopWhenFail = stopWhenFail;
        taskList = new LinkedList<>();
        init();
    }

    @Override
    protected void execute(ProcessInfo input, Callback<ProcessInfo> callback) {
        runTask(input, callback, 0);
    }

    private void runTask(final ProcessInfo input, final Callback<ProcessInfo> callback, final int step) {
        try {
            if (step == taskList.size() || (stopWhenFail && input.failed())) {
                callback.handle(input);
            } else {
                XAsyncProcess task = taskList.get(step);
                task.run(input, output -> runTask(output, callback, step + 1));
            }
        } catch (Exception e) {
            input.setError(ErrorProcess.of(e, "Fail when run \"" + name() + "\" at step[" + (step + 1) + "]!"));
            callback.handle(input);
        }
    }

    @Override
    public XAsyncWorkflow add(XAsyncTask task) {
        if (Objects.nonNull(task)) {
            taskList.add(task);
        }
        return this;
    }

    @Override
    public XAsyncWorkflow addAll(List<XAsyncTask> tasks) {
        if (Objects.nonNull(tasks) && !tasks.isEmpty()) {
            taskList.addAll(tasks);
        }
        return this;
    }

    protected abstract void init();
}
