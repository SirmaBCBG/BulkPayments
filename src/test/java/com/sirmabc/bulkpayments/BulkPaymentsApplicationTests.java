package com.sirmabc.bulkpayments;

import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.XMLFileHelper;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BulkPaymentsApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BulkPaymentsApplicationTests.class);

    @Autowired
    private Properties properties;

    @Test
    void testObjectToXMLFile() {
        try {
            FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10 fiToFICstmrCdtTrf = XMLFileHelper.deserializeXml("""
                <hdr:FIToFICstmrCdtTrf
                xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02">
                 <GrpHdr>
                 <MsgId>MREF7d108d08389da</MsgId>
                 <CreDtTm>2020-06-18T15:29:03+03:00</CreDtTm>
                 <NbOfTxs>1</NbOfTxs>
                 <TtlIntrBkSttlmAmt Ccy="BGN">9.43</TtlIntrBkSttlmAmt>
                 <IntrBkSttlmDt>2020-06-18</IntrBkSttlmDt>
                 <SttlmInf>
                 <SttlmMtd>CLRG</SttlmMtd>
                 <ClrSys>
                 <Prtry>BGINST</Prtry>
                 </ClrSys>
                 </SttlmInf>
                 <InstgAgt>
                 <FinInstnId>
                 <BIC>TESABGSF</BIC>
                 </FinInstnId>
                 </InstgAgt>
                 </GrpHdr>
                 <CdtTrfTxInf>
                 <PmtId>
                 <EndToEndId>PREF7d108d08389da</EndToEndId>
                 <TxId>PREF7d108d08389da</TxId>
                 </PmtId>
                 <PmtTpInf>
                 <SvcLvl>
                 <Cd>SEPA</Cd>
                 </SvcLvl>
                 </PmtTpInf>
                 <IntrBkSttlmAmt Ccy="BGN">9.43</IntrBkSttlmAmt>
                 <ChrgBr>SLEV</ChrgBr>
                 <Dbtr>
                 <Nm>zmtwjkn</Nm>
                 </Dbtr>
                 <DbtrAcct>
                 <Id>
                 <IBAN>BG20TESA10000000000001</IBAN>
                 </Id>
                 </DbtrAcct>
                 <DbtrAgt>
                 <FinInstnId>
                 <BIC>TESABGSF</BIC>
                 </FinInstnId>
                 </DbtrAgt>
                <CdtrAgt>
                 <FinInstnId>
                 <BIC>TESCBGSF</BIC>
                 </FinInstnId>
                 </CdtrAgt>
                 <Cdtr>
                 <Nm>ifgvcwoj</Nm>
                 </Cdtr>
                 <CdtrAcct>
                 <Id>
                 <IBAN>BG14TESC10000000000003</IBAN>
                 </Id>
                 </CdtrAcct>
                 <RmtInf>
                 <Ustrd>ccmsgboapvfuwmcxrgeqchamadntbsr</Ustrd>
                 </RmtInf>
                 </CdtTrfTxInf>
                 </hdr:FIToFICstmrCdtTrf>""", FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10.class);

            XMLFileHelper.objectToXmlFile(XMLFileHelper.serializeXml(fiToFICstmrCdtTrf), properties.getBulkMsgsDirPath());
        } catch (Exception e) {
            logger.error("testObjectToXMLFile error: " + e.getMessage());
        }
    }
}
