package sj.freetime.workflow.util;

@FunctionalInterface
public interface Callback<T> {

    void handle(T input);
}
