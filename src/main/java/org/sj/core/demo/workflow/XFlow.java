package org.sj.core.demo.workflow;

import java.util.List;

public interface XFlow<F, T> {

    F add(T process);

    F addAll(List<T> processes);
}
