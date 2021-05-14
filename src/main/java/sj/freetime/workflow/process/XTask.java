package sj.freetime.workflow.process;

import sj.freetime.workflow.XProcess;
import sj.freetime.workflow.model.ErrorProcess;
import sj.freetime.workflow.model.ProcessInfo;
import sj.freetime.workflow.model.impl.ProcessInfoImpl;

public abstract class XTask implements XProcess {

    private final String name;

    public XTask(String name) {
        this.name = name;
    }

    @Override
    public ProcessInfo run(final ProcessInfo input) {
        try {
            before(input);
            final ProcessInfo processInfo = execute(input);
            after(input);
            return processInfo;
        } catch (Exception e) {
            input.setError(ErrorProcess.of(e, "Exception when run \"" + name + "\""));
            return input;
        }
    }

    @Override
    public String name() {
        return name;
    }

    public void before(ProcessInfo input) {
        // do nothing
    }

    public void after(ProcessInfo output) {
        // do nothing
    }

    protected abstract ProcessInfo execute(ProcessInfo input);
}
