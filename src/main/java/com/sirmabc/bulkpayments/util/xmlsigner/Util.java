package com.sirmabc.bulkpayments.util.xmlsigner;

import javax.xml.datatype.XMLGregorianCalendar;

public class Util {
    public static java.sql.Date toSQLDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return new java.sql.Date(calendar.toGregorianCalendar().getTime().getTime());
    }

    public static java.util.Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }
}
