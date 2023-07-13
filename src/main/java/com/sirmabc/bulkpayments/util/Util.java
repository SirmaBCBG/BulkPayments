package com.sirmabc.bulkpayments.util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {

    private static  Integer i = 0;
    private static  Integer maxSize = 9999;

    public static synchronized String generateMsgId() {

        StringBuilder stringBuilder = new StringBuilder();

        Instant now = Instant.now();
        String nowS = now.toString();
        nowS = nowS.replaceAll("[-:.T]", "");
        stringBuilder.append(nowS);

        if(i >= maxSize) {
            i = 0;
        }

        stringBuilder.append(i);

        i++;

        String msgId = stringBuilder.toString();

        return msgId;
    }


    public static boolean sameDay (Date messageDate, Date now) {

        Calendar cal1 = GregorianCalendar.getInstance();
        Calendar cal2 = GregorianCalendar.getInstance();
        cal1.setTime(messageDate);
        cal2.setTime(now);

        boolean sameDay =  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        // if it is not the same date transaction can be done if in grace period - until 00:00:20 (20 sec after midnight.)
        LocalDateTime localDateTimeNowMinusOne = convertToLocalDateViaInstant(now).minusDays(1);
        LocalDateTime localDateTimeMessageDate = convertToLocalDateViaInstant(messageDate);
        if (!sameDay && localDateTimeNowMinusOne.getDayOfMonth() == localDateTimeMessageDate.getDayOfMonth()) {
            if(localDateTimeNowMinusOne.getHour() == 0 &&
                    localDateTimeNowMinusOne.getMinute() == 0 &&
                    localDateTimeNowMinusOne.getSecond() < 20) {
                sameDay = true;

            }
        }

        return sameDay;

    }

    public static LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static String gregCalendarToString(GregorianCalendar calendar) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = calendar.getTime();

        String dateS = fmt.format(date);

        return dateS;

    }

    public static String objectToJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(o);

        return json;
    }

    public static boolean validateIBAN(String iban) {
        // Remove any white spaces from the input
//        iban = iban.replaceAll("\\s", "");

        // Check that the IBAN contains only digits and uppercase letters
        if (!iban.matches("^[0-9A-Z]+$")) {
            return false;
        }

        if(checkIfBudgetIBAN(iban)) {
            return false;
        }

        // Move the four initial characters to the end
        iban = iban.substring(4) + iban.substring(0, 4);

        // Convert letters to their corresponding numeric values
        StringBuilder numericIBAN = new StringBuilder();
        for (int i = 0; i < iban.length(); i++) {
            char ch = iban.charAt(i);
            if (Character.isLetter(ch)) {
                numericIBAN.append(Character.getNumericValue(ch));
            } else {
                numericIBAN.append(ch);
            }
        }

        // Calculate the remainder when dividing the numeric IBAN by 97
        BigInteger ibanNumber = new BigInteger(numericIBAN.toString());
        BigInteger remainder = ibanNumber.mod(new BigInteger("97"));

        // If the remainder is not 1, the IBAN is invalid
        return remainder.equals(new BigInteger("1"));
    }

    public static boolean checkIfBudgetIBAN(String iban){

        char at13 = iban.charAt(12);
        if(iban.startsWith("BG") && at13 == 56 || iban.startsWith("BG") && at13 == 51) {
            return true;
        }

        return false;
    }

}
