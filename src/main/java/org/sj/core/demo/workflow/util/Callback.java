package org.sj.core.demo.workflow.util;

@FunctionalInterface
public interface Callback<T> {

    void handle(T input);
}
