package cdr;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Call {
    private String callType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalTime callDuration;
    private double cost;

    public Call(String callType, LocalDateTime startTime, LocalDateTime endTime) {
        this.callType = callType;
        this.startTime = startTime;
        this.endTime = endTime;

        Duration duration = Duration.between(startTime, endTime);
        this.callDuration = LocalTime.of(duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }

    public String getCallType() {
        return callType;
    }

    public String getStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(startTime);
    }

    public String getEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(endTime);
    }

    public String getCallDuration() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return formatter.format(callDuration);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getMinutes() {
        int minutes = callDuration.getMinute();
        int seconds = callDuration.getSecond();

        return seconds != 0 ? minutes + 1 : minutes;
    }
}
