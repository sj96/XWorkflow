package org.sj.core.demo;

import org.sj.core.demo.workflow.XAsyncProcess;
import org.sj.core.demo.workflow.model.XData;
import org.sj.core.demo.workflow.process.XAsyncTask;
import org.sj.core.demo.workflow.process.XAsyncWorkflow;
import org.sj.core.demo.workflow.process.XTask;
import org.sj.core.demo.workflow.process.XWorkflow;
import org.sj.core.demo.workflow.util.Callback;

public class Main {
    public static void main(String[] args) {
        XWorkflow wf = new XWorkflow("wf") {
            @Override
            protected void init() {
                add(new XTask("t-1") {
                    @Override
                    protected XData execute(XData input) {
                        System.out.println(this.getProcessName());
                        return input;
                    }
                });
                add(new XTask("t-2") {
                    @Override
                    protected XData execute(XData input) {
                        System.out.println(this.getProcessName());
                        input.setResult(false);
                        return input;
                    }
                });
                add(new XTask("t-3") {
                    @Override
                    protected XData execute(XData input) {
                        System.out.println(this.getProcessName());
                        return input;
                    }
                });
            }
        };
        wf.run(new XData());

        XAsyncWorkflow aWf = new XAsyncWorkflow("aWf") {
            @Override
            protected void init() {
                add(new XAsyncTask("at-1") {
                    @Override
                    protected void execute(XData input, Callback<XData> callback) {
                        System.out.println(this.getProcessName());
                        callback.handle(input);
                    }
                });
                add(new XAsyncTask("at-2") {
                    @Override
                    protected void execute(XData input, Callback<XData> callback) {
                        System.out.println(this.getProcessName() + " call fail");
                        input.setResult(false);
                        callback.handle(input);
                        System.out.println(this.getProcessName() + " call success");
                        input.setResult(true);
                        callback.handle(input);
                    }
                });
                add(new XAsyncTask("at-3") {
                    @Override
                    protected void execute(XData input, Callback<XData> callback) {
                        System.out.println(this.getProcessName());
                        callback.handle(input);
                    }
                });
            }
        };
        aWf.run(new XData(), output -> {
            System.out.println(aWf.getProcessName() + " done");
        });
    }
}
