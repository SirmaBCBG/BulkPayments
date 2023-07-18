package com.sirmabc.bulkpayments.util;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import java.util.List;
import java.util.Map;

import static com.sirmabc.bulkpayments.util.Header.X_MONTRAN_RTP_POSSIBLE_DUPLICATE;

public class BulkMessageValidator {

    @Autowired
    private static BulkMessagesRepository bulkMessagesRepository;

    @Autowired
    private static ParticipantsRepository participantsRepository;

    @Autowired
    private static Properties properties;

    @Autowired
    private static XMLSigner xmlSigner;

    public static CodesPacs002 validate(BusinessApplicationHeaderV01 appHdr, Map<String, List<String>> headers, String xmlMessage) throws Exception {
        CodesPacs002 pacs002Code = isValidAppHdr(appHdr, xmlMessage);
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            return pacs002Code;
        }

        pacs002Code = isDuplicate(appHdr, headers);
        if(!pacs002Code.equals(CodesPacs002.OK01)){
            return pacs002Code;
        }

        return CodesPacs002.OK01;
    }

    private static CodesPacs002 isValidAppHdr(BusinessApplicationHeaderV01 appHdr, String xmlMessage) throws Exception {
        Document document = xmlSigner.string2XML(xmlMessage);
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        }

        String senderBic = appHdr.getFr().getFIId().getFinInstnId().getBICFI();
        String receiverBic = appHdr.getTo().getFIId().getFinInstnId().getBICFI();

        int result = participantsRepository.checkParticipant(senderBic);
        String bic = properties.getRtpChannel();

        //check if sender bic is valid and check if receiver bic is the same as the institution bic.
        if(result == 0 || !receiverBic.equals(bic)) {
            return CodesPacs002.RC01;
        }

        return  CodesPacs002.OK01;
    }

    private static CodesPacs002 isDuplicate(BusinessApplicationHeaderV01 appHdr, Map<String, List<String>> headers) {
        boolean isDuplicateHeader = headers.containsKey(X_MONTRAN_RTP_POSSIBLE_DUPLICATE.header);

        // this way so there is no call to db if the header present!
        if (isDuplicateHeader) {
            BulkMessagesEntity entity = bulkMessagesRepository.findByMessageId(appHdr.getBizMsgIdr());

            if(entity != null) {
                return CodesPacs002.AM05;
            }
        }
        return CodesPacs002.OK01;
    }
}
