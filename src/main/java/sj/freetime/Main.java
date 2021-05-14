package sj.freetime;

import sj.freetime.workflow.model.ErrorProcess;
import sj.freetime.workflow.model.ProcessInfo;
import sj.freetime.workflow.process.XTask;
import sj.freetime.workflow.process.XWorkflow;
import sj.freetime.workflow.process.async.XAsyncTask;
import sj.freetime.workflow.process.async.XAsyncWorkflow;
import sj.freetime.workflow.util.Callback;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        XWorkflow wf = new XWorkflow("wf") {
            @Override
            protected void init() {
                add(new XTask("t-1") {
                    @Override
                    protected ProcessInfo execute(ProcessInfo input) {
                        System.out.println(input.processId() + " " + this.name());
                        return input;
                    }
                });
                add(new XTask("t-2") {
                    @Override
                    protected ProcessInfo execute(ProcessInfo input) {
                        System.out.println(input.processId() + " " + this.name());
                        input.setError(ErrorProcess.of(new RuntimeException()));
                        return input;
                    }
                });
                add(new XTask("t-3") {
                    @Override
                    protected ProcessInfo execute(ProcessInfo input) {
                        System.out.println(input.processId() + " " + this.name());
                        return input;
                    }
                });
            }
        };

        XAsyncWorkflow aWf = new XAsyncWorkflow("aWf") {
            @Override
            protected void init() {
                add(new XAsyncTask("at-1") {
                    @Override
                    protected void execute(ProcessInfo input, Callback<ProcessInfo> callback) {
                        System.out.println(input.processId() + " " + this.name());
                        callback.handle(input);
                    }
                });
                add(new XAsyncTask("at-2") {
                    @Override
                    protected void execute(ProcessInfo input, Callback<ProcessInfo> callback) {
                        System.out.println(input.processId() + " " + this.name() + " call fail");
                        input.setError(ErrorProcess.empty());
                        callback.handle(input);
                        System.out.println(input.processId() + " " + this.name() + " call success");
                        callback.handle(ProcessInfo.cloneData(input));
                    }
                });
                add(new XAsyncTask("at-3") {
                    @Override
                    protected void execute(ProcessInfo input, Callback<ProcessInfo> callback) {
                        System.out.println(input.processId() + " " + this.name() + " result before: " + input.success());
                        callback.handle(input);
                    }
                });
            }
        };
        CountDownLatch latch = new CountDownLatch(1);
        aWf.run(ProcessInfo.create(), output -> {
            System.out.println(aWf.name() + " done.");
            latch.countDown();
        });
        System.out.println(wf.name() + " run");
        wf.run(ProcessInfo.create());
        System.out.println(aWf.name() + " waiting...aWf");
        latch.await();
        System.out.println("the end.");
    }
}
