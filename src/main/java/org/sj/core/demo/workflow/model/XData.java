package org.sj.core.demo.workflow.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class XData implements Serializable {

    private boolean result = true;

    private String error;

    private final Map<String, Object> data;

    public XData(){
        data = new HashMap<>();
    }

    public XData(Map<String, Object> data) {
        this.data = data;
    }

    public Object getData(String key){
        return data.get(key);
    }

    public <T> T getData(String key, Class<T> type){
        return (T) data.get(key);
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
