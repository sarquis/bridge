package br.com.sqs.bridge.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BridgeDateFormatter {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    public static String formatMedium(LocalDateTime dateTime) {
	return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateTime);
    }

    public static String formatShort(LocalDateTime dateTime) {
	return dateFormatter.format(dateTime);
    }
}
