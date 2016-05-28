package com.mmrath.sapp.web.dto;

/**
 * DTO for storing a user's activity.
 */
public class ActivityDto {

    private String sessionId;

    private String username;

    private String ipAddress;

    private String page;

    private String time;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ActivityData{" +
                "sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", page='" + page + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
