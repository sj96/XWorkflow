package org.sj.core.demo.workflow.process;

import org.sj.core.demo.workflow.XFlow;
import org.sj.core.demo.workflow.XProcess;
import org.sj.core.demo.workflow.model.XData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class XWorkflow extends XTask implements XFlow<XWorkflow, XTask> {

    private final List<XProcess> processList;

    public XWorkflow(String taskName) {
        this(taskName, true);
    }

    public XWorkflow(String taskName, boolean stopWhenFail) {
        super(taskName, stopWhenFail);
        processList = new ArrayList<>();
        init();
    }

    @Override
    public XWorkflow add(XTask process) {
        if (Objects.nonNull(process)) {
            processList.add(process);
        }
        return this;
    }

    @Override
    public XWorkflow addAll(List<XTask> processes) {
        if (Objects.nonNull(processes) && !processes.isEmpty()) {
            processList.addAll(processes);
        }
        return this;
    }

    @Override
    protected XData execute(XData input) {
        return runTask(input, 0);
    }

    XData runTask(XData input, int step) {
        if (step == processList.size() || (stopWhenFail && !input.isResult())) {
            return input;
        } else {
            XProcess task = processList.get(step);
            return runTask(task.run(input), step + 1);
        }
    }

    protected abstract void init();
}
