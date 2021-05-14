package sj.freetime.workflow.process.async;

import sj.freetime.workflow.XAsyncProcess;
import sj.freetime.workflow.model.ErrorProcess;
import sj.freetime.workflow.model.ProcessInfo;
import sj.freetime.workflow.util.Callback;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class XAsyncTask implements XAsyncProcess {

    private final String name;

    public XAsyncTask(String name) {
        this.name = name;
    }


    @Override
    public void run(final ProcessInfo input, final Callback<ProcessInfo> callback) {
        CompletableFuture.runAsync(() -> {
            String errorMsg = "Fail when run \"" + name();
            final Callback<ProcessInfo> xCallback = output -> {
                try {
                    after(output, () -> {
                        if (Objects.nonNull(callback)) {
                            callback.handle(output);
                        }
                    });
                } catch (Exception e) {
                    input.setError(ErrorProcess.of(e, errorMsg));
                    callback.handle(input);
                }
            };
            try {
                before(
                        input,
                        () -> {
                            try {
                                execute(input, xCallback);
                            } catch (Exception e) {
                                input.setError(ErrorProcess.of(e, errorMsg));
                                callback.handle(input);
                            }
                        },
                        () -> callback.handle(input));
            } catch (Exception e) {
                input.setError(ErrorProcess.of(e, errorMsg));
                callback.handle(input);
            }
        });
    }

    @Override
    public String name() {
        return name;
    }

    public void before(final ProcessInfo input, final Runnable next, final Runnable skip) {
        next.run();
    }

    public void after(final ProcessInfo output, final Runnable next) {
        next.run();
    }

    protected abstract void execute(final ProcessInfo input, final Callback<ProcessInfo> callback);
}
