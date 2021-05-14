package sj.freetime.workflow.process;

import sj.freetime.workflow.XFlow;
import sj.freetime.workflow.XProcess;
import sj.freetime.workflow.model.ProcessInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class XWorkflow extends XTask implements XFlow<XWorkflow, XTask> {

    private final List<XTask> processList;
    private final boolean stopWhenFail;

    public XWorkflow(String taskName) {
        this(taskName, true);
    }

    public XWorkflow(String taskName, boolean stopWhenFail) {
        super(taskName);
        processList = new LinkedList<>();
        this.stopWhenFail = stopWhenFail;
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
    protected ProcessInfo execute(ProcessInfo input) {
        return runTask(input, 0);
    }

    ProcessInfo runTask(ProcessInfo input, int step) {
        if (step == processList.size() || (stopWhenFail && input.failed())) {
            return input;
        } else {
            XProcess task = processList.get(step);
            return runTask(task.run(input), step + 1);
        }
    }

    protected abstract void init();
}
