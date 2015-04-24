package fr.epf.medfile.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class Utils {
    public static SimpleDateFormat formatStore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatPrint = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat formatPrintHours = new SimpleDateFormat("HH:mm");

    public static String format(Date d) {
	return formatStore.format(d);
    }

    public static String formatSimple(Date d) {
	return formatPrint.format(d);
    }

    public static String formatFull(Date d) {
	return formatPrint.format(d) + " à " + formatPrintHours.format(d);
    }

    public static Date parse(String s) throws ParseException {
	return formatStore.parse(s);
    }
}
