package com.sirmabc.bulkpayments.util;

public enum CodesPacs002 {

    OK01("OK01"), // Successful
    MS03("MS03"), //Internal processing error / Unsupported message / Generic error / Generic validation error.
    AB05("AB05"), //Timeout error. The code reported at sender is AB05 and the coder reported at receiver is TM01.
    // A payment with the same reference has been detected within processing.
    // A payment with the same reference has been detected as already processed.
    // Invalid time – the value of field Acceptance DataTime is older than the current time with the timeout parameter value configured in the payment schema.
    // Requested execution date is in the past.
    TM01("TM01"), //Timeout error. The code reported at sender is AB05 and the coder reported at receiver is TM01.
    MS02("MS02"), //Payment rejected by receiver Participant.
    AB08("AB08"), //Receiver Participant is disconnected / Creditor Participant is disconnected.

    AM05("AM05"), //Duplicate message
    FF01("FF01"), // The currency is invalid / The amount is invalid (less than or equal to zero)
    // Settlement date is invalid (IntrBkSttlmDt).
    // Invalid XML format.
    // The value of field Original Settlement Date is not valid in comparison to the payment schema and the current day.
    // The Participant has no ACTIVE registered DS certificate for the validation of messages.
    // The XML message does not entail the digital signature in the specified format
    // Invalid digital signature.
    // The certificate used for the signature is expired, or it has been revoked.

    DNOR("DNOR"), //Value of field DbtrAgt is invalid - No active Participant with this BIC was found / The value is not allowed for this InstgAgt.
    CNOR("CNOR"), //Value of field CdtrAgt is invalid: - No active Participant with this BIC was found.
    RC01("RC01"), //Value of field InstgAgt is invalid - No active Participant with this BIC was found / The value is different then the sender Participant detected at the communication channel level.
    // No valid sender – the sender Participant is not ACTIVE.
    // Invalid receiver message field - AppHdr.To.FIId.FinInstId.BICFI or the Asignee
    // Invalid value for originator field. It could be for pacs.004 TxInf.RtrRsnInf.Orgtr.Id.OrgId.BICOrBEI not matching instructing agent BIC.
    AC01("AC01"), //The debtor IBAN is invalid / The creditor IBAN is invalid.

    AG09("AG09"), // Invalid original reference – no original transaction with that reference was found.
    // Invalid status of the original transaction – during the processing of a pacs.002/pacs.028, the referred transaction has no appropriate status (message: WAIT_RECEIVER, transaction: HOLD).

    AG01("AG01"), // The payment schema is not defined for the currency mentioned in the message.

    AM02("AM02"), // The amount is too large in comparison to the parameter defined in the payment schema.

    AG10("AG10"), // The sender is BLOCKED (temporary deactivated).

    RR04("RR04"), // The Participant is not mapped in the payment schema. Both Participants mentioned in the message need to be checked.

    AM23("AM23"); // Insufficient funds for the transaction clearing

    public final String errorCode;

    private CodesPacs002(String errorCode) {
        this.errorCode = errorCode;
    }

}
