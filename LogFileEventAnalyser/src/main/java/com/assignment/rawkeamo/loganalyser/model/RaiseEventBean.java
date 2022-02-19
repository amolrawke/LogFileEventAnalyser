package com.assignment.rawkeamo.loganalyser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RaiseEventBean {
    @JsonProperty("id")
    private String id;

    @JsonProperty("stateMgmBean")
    private StateMgmBean stateMgmBean;

    @JsonProperty("type")
    private TypeOfEvents type;

    @JsonProperty("host")
    private String host;

    @JsonProperty("timestamp")
    private long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StateMgmBean getStateMgmBean() {
        return stateMgmBean;
    }

    public void setStateMgmBean(StateMgmBean stateMgmBean) {
        this.stateMgmBean = stateMgmBean;
    }

    public TypeOfEvents getType() {
        return type;
    }

    public void setType(TypeOfEvents type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
