package com.example.time_serv.times;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tprov")
public class TimeProviderProperties {

    private String profile;
    private String descpiption;
    private String format;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescpiption() {
        return descpiption;
    }

    public void setDescpiption(String descpiption) {
        this.descpiption = descpiption;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
