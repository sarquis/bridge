package br.com.sqs.bridge.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BridgeFormatter {

    public static String format(LocalDateTime dateTime) {
	return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateTime);
    }
}
