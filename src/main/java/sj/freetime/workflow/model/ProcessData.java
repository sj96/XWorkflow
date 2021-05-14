package sj.freetime.workflow.model;

import org.jetbrains.annotations.NotNull;
import sj.freetime.workflow.model.impl.ProcessDataImpl;

import java.io.Serializable;
import java.util.Map;

public interface ProcessData extends Serializable {

    static ProcessData create() {
        return new ProcessDataImpl();
    }

    default <T> T get(@NotNull String key, Class<T> tClass) throws ClassCastException {
        return tClass.cast(get(key));
    }

    Object get(@NotNull String key);

    ProcessData put(@NotNull String key, Object value);

    ProcessData putAll(Map<String, Object> map);

    Object remove(@NotNull String key);

    Map<String, Object> data();
}
