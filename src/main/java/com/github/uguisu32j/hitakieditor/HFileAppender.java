package com.github.uguisu32j.hitakieditor;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.TriggeringPolicy;

public class HFileAppender extends FileAppender<TriggeringPolicy<ILoggingEvent>> {
    public HFileAppender() {
        setFile(HitakiEditor.APP_DATA_DIR + File.separator + "logs" + File.separator
                + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now()) + ".log");
    }
}
