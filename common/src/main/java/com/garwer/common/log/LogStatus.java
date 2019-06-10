package com.garwer.common.log;

/**
 * @Author: Garwer
 * @Date: 2019/6/6 10:11 PM
 * @Version 1.0
 */
public enum LogStatus {
    SUCCESS(1, "SUCCESS"),ERROR(0, "ERROR");

    private Integer flag;
    private String status;

    LogStatus(Integer flag, String status) {
        this.flag = flag;
        this.status = status;
    }

    public static LogStatus matchStatus(String status) {
        for (LogStatus logStatus : LogStatus.values()) {
            if (logStatus.status.equalsIgnoreCase(status)) {
                return logStatus;
            }
        }
        return null;
    }

}
