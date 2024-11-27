package com.VEMS.vems.other.timeFormatConfig;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeFormatter {
    public String currentTime() {
        LocalTime currentLocalTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return currentLocalTime.format(dateTimeFormatter);
    }
}
