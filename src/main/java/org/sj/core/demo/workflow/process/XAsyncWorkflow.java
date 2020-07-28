package org.sj.core.demo.workflow.process;

import org.sj.core.demo.workflow.XAsyncProcess;
import org.sj.core.demo.workflow.XFlow;
import org.sj.core.demo.workflow.model.XData;
import org.sj.core.demo.workflow.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class XAsyncWorkflow extends XAsyncTask implements XFlow<XAsyncWorkflow, XAsyncTask> {

    private final List<XAsyncProcess> taskList;

    public XAsyncWorkflow(String taskName) {
        this(taskName, true);
    }

    public XAsyncWorkflow(String taskName, boolean stopWhenFail) {
        super(taskName, stopWhenFail);
        taskList = new ArrayList<>();
        init();
    }

    @Override
    protected void execute(XData input, Callback<XData> callback) {
        runTask(input, callback, 0);
    }

    private void runTask(XData input, Callback<XData> callbackFinal, int step){
        if(step == taskList.size() || (stopWhenFail && !input.isResult())){
            callbackFinal.handle(input);
        } else {
            XAsyncProcess task = taskList.get(step);
            task.run(input, output -> runTask(output, callbackFinal, step + 1));
        }
    }

    @Override
    public XAsyncWorkflow add(XAsyncTask task) {
        if(Objects.nonNull(task)){
            taskList.add(task);
        }
        return this;
    }

    @Override
    public XAsyncWorkflow addAll(List<XAsyncTask> tasks) {
        if(Objects.nonNull(tasks) && !tasks.isEmpty()){
            taskList.addAll(tasks);
        }
        return this;
    }

    protected abstract void init();
}
