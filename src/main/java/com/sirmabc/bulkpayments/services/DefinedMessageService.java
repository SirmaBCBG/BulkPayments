package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClientScheduler;
import com.sirmabc.bulkpayments.messages.CreditTransferMessage;
import com.sirmabc.bulkpayments.messages.DefinedMessage;
import com.sirmabc.bulkpayments.messages.FIToFIPaymentStatusReportMessage;
import com.sirmabc.bulkpayments.messages.messageBuilder.CreditTransferMessageBuilder;
import com.sirmabc.bulkpayments.persistance.repositories.MessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import montranMessage.montran.message.Message;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

@Service
public class DefinedMessageService {

    private static final Logger logger = LoggerFactory.getLogger(BorikaClientScheduler.class);
    private final ApplicationContext context;
    ParticipantsRepository participantsRepository;

    MessagesRepository messagesRepository;
    CreditTransferMessageBuilder creditTransferMessageBuilder;

    @Autowired
    public DefinedMessageService(ApplicationContext context, ParticipantsRepository participantsRepository, MessagesRepository messagesRepository, CreditTransferMessageBuilder creditTransferMessageBuilder) {
        this.context = context;
        this.participantsRepository = participantsRepository;
        this.messagesRepository = messagesRepository;
        this.creditTransferMessageBuilder = creditTransferMessageBuilder;

    }

    public DefinedMessage define(Map<String, List<String>> requestHeaders, Message message, String xmlMessage) {
        logger.info("defineMessage()...");

        // credit transfer pacs.008
        if (message.getFIToFICstmrCdtTrf() != null) {
            CreditTransferMessage creditTransferMessage = context.getBean(CreditTransferMessage.class);
            creditTransferMessage.setRequestHeaders(requestHeaders);
            creditTransferMessage.setAppHdr(message.getAppHdr());
            creditTransferMessage.setFiToFICstmrCdtTrf(message.getFIToFICstmrCdtTrf());
            creditTransferMessage.setXmlMessage(xmlMessage);

            logger.info("defineMessage() returns credit transfer message (pacs.008)!");
            return creditTransferMessage;
        } else if (message.getFIToFIPmtStsRpt() != null) { // pacs.002
            FIToFIPaymentStatusReportMessage fiToFIPaymentStatusReportMessage = context.getBean(FIToFIPaymentStatusReportMessage.class);
            fiToFIPaymentStatusReportMessage.setRequestHeaders(requestHeaders);
            fiToFIPaymentStatusReportMessage.setAppHdr(message.getAppHdr());
            fiToFIPaymentStatusReportMessage.setFiToFIPaymentStatusReport(message.getFIToFIPmtStsRpt());
            fiToFIPaymentStatusReportMessage.setXmlMessage(xmlMessage);

            logger.info("defineMessage() returns status message (pacs.002)!");
            return fiToFIPaymentStatusReportMessage;
        } /*else if (message.getParticipants() != null) { // get participants
            ParticipantsMessage participantsMessage = context.getBean(ParticipantsMessage.class);
            participantsMessage.setRequestHeaders(requestHeaders);
            participantsMessage.setAppHdr(message.getAppHdr());
            participantsMessage.setParticipantsType(message.getParticipants());
            participantsMessage.setXmlMessage(xmlMessage);

            logger.info("defineMessage() returns participants message!");
            return participantsMessage;
        } else if (message.getBkToCstmrStmt() != null) { // get statements and balances - camt.053
            StatementsMessageCamt053 statementsMessageCamt053 = context.getBean(StatementsMessageCamt053.class);
            statementsMessageCamt053.setRequestHeaders(requestHeaders);
            statementsMessageCamt053.setAppHdr(message.getAppHdr());
            statementsMessageCamt053.setStatementV02(message.getBkToCstmrStmt());
            statementsMessageCamt053.setXmlMessage(xmlMessage);

            logger.info("defineMessage() returns statements message (camt.053)!");
            return statementsMessageCamt053;
        }*/

        //TODO:finish for the rest of the messages.

        return null;

    }
}
