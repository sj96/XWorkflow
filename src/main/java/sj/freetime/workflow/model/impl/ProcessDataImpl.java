package sj.freetime.workflow.model.impl;

import org.jetbrains.annotations.NotNull;
import sj.freetime.workflow.model.ProcessData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessDataImpl implements ProcessData {

    private final Map<String, Object> data = new ConcurrentHashMap<>();

    @Override
    public Map<String, Object> data() {
        return data;
    }

    @Override
    public Object get(@NotNull String key) {
        return data.get(key);
    }

    @Override
    public ProcessData put(@NotNull String key, Object value) {
        data.put(key, value);
        return this;
    }

    @Override
    public ProcessData putAll(Map<String, Object> map) {
        data.putAll(map);
        return this;
    }

    @Override
    public Object remove(@NotNull String key) {
        return data.remove(key);
    }

}
