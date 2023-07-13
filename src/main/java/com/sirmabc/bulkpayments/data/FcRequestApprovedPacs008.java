package com.sirmabc.bulkpayments.data;

import java.math.BigDecimal;

public record FcRequestApprovedPacs008(String creationDate,
                                       String msgId,
                                       BigDecimal amount,
                                       String currency,
                                       String debtorIBAN,
                                       String debtorName,
                                       String creditorIBAN,
                                       String creditorName,
                                       String xmlMessage) {
}
