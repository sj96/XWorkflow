package sj.freetime.workflow;

import sj.freetime.workflow.model.ProcessInfo;

public interface XProcess {
    ProcessInfo run(ProcessInfo input);

    String name();
}
