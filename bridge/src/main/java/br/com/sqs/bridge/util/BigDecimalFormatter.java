package br.com.sqs.bridge.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

public class BigDecimalFormatter implements Formatter<BigDecimal> {

    private static final String PATTERN = "#,##0.00";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(PATTERN);

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
	text = text.replace(".", "");
	text = text.replace(",", ".");
	return new BigDecimal(text);
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
	return DECIMAL_FORMAT.format(object);
    }

    public static String bigDecimalToString(BigDecimal object) {
	return DECIMAL_FORMAT.format(object);
    }
}
