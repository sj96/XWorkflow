package sj.freetime.workflow;

import sj.freetime.workflow.model.ProcessInfo;
import sj.freetime.workflow.util.Callback;

import java.util.concurrent.CompletableFuture;

public interface XAsyncProcess {

    default void run(ProcessInfo input) {
        CompletableFuture.runAsync(() -> run(input, i -> {
            // do nothing
        }));
    }

    void run(ProcessInfo input, Callback<ProcessInfo> callback);

    String name();
}
