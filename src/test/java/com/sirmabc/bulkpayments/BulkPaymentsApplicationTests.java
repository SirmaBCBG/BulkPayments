package com.sirmabc.bulkpayments;

import com.sirmabc.bulkpayments.message.WrappedMessage;
import com.sirmabc.bulkpayments.message.WrappedMessageBuilder;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.enums.InOut;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import montranMessage.montran.message.Message;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BulkPaymentsApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BulkPaymentsApplicationTests.class);

    @Autowired
    private Properties properties;

    @Autowired
    private WrappedMessageBuilder wrappedMessageBuilder;

    @Test
    void testObjectToXMLFile() {
        //region
        String xmlString = """
                <hdr:Message xmlns:hdr="urn:montran:message.01">
                <hdr:AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.01">
                <Fr>
                <FIId>
                <FinInstnId>
                <BICFI>BGUSBGS0</BICFI>
                </FinInstnId>
                </FIId>
                </Fr>
                <To>
                <FIId>
                <FinInstnId>
                <BICFI>BSBGBGS0</BICFI>
                </FinInstnId>
                </FIId>
                </To>
                <BizMsgIdr>BankSoft Ltd</BizMsgIdr>
                <MsgDefIdr>pacs.008.001.02</MsgDefIdr>
                <BizSvc>RTP</BizSvc>
                <CreDt>2023-05-12T16:06:27Z</CreDt>
                <Sgntr>
                <Signature xmlns="http://www.w3.org/2000/09/xmldsig#">
                <SignedInfo>
                <CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments"/>
                <SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
                <Reference URI="">
                <Transforms>
                <Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
                <Transform Algorithm="http://www.w3.org/2006/12/xml-c14n11"/>
                </Transforms>
                <DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
                <DigestValue>/Nq2I2xH4iKr2dn/6e3q9XqLy3/2hXdLp28X1t2qlX0=</DigestValue>
                </Reference>
                </SignedInfo>
                <SignatureValue>cfIxi07bqUSZIIAztKl2aaXH92+CGQq4McyCvd4Wj7/26qbGF5IWsRPns1Xw71fkP5wxq6SC3Kax EMJSLQDDmGni45ZmAqmi6gdfDN4ORAL8SW3IvCr9PCfED7TE6GafLsQBfZ3Ae+Qpui3d2KsWwK2B QEbtWbpSshHpu3W/8yEa3Nt2Hp1nE8V0RrWp5MO9LKS5Lr3xzcLYeRMwGAGyRZYIhVMuZyEcf52f ZbkQYqmWF/iItDOxJaSOCx2tfeJBz1bQXtjOpgmPOvQ6cG06LiayldsC/1r1mnl65T83YBmgE4z4 SHwCJAn+i9KRn4sNjSIhX3ztwx6ReYzAV9cWAg==</SignatureValue>
                <KeyInfo>
                <X509Data>
                <X509SubjectName>C=BG, L=Sofia, OID.2.5.4.97=NTRBG-121246419, O=Bulgarian-American Credit Bank AD, OU=BG Inst, CN=SSL_DS_BACB, EMAILADDRESS=bginst@bacb.bg</X509SubjectName>
                <X509IssuerSerial>
                <X509IssuerName>CN=B-Trust TEST Operational Advanced CA, OU=B-Trust, O=BORICA - BANKSERVICE AD, OID.2.5.4.97=NTRBG-201230426, C=BG</X509IssuerName>
                <X509SerialNumber>7147544073253608033</X509SerialNumber>
                </X509IssuerSerial>
                <X509Certificate>MIIGkzCCBHugAwIBAgIIYzEtdx662mEwDQYJKoZIhvcNAQELBQAwgYoxCzAJBgNVBAYTAkJHMRgw FgYDVQRhDA9OVFJCRy0yMDEyMzA0MjYxIDAeBgNVBAoMF0JPUklDQSAtIEJBTktTRVJWSUNFIEFE MRAwDgYDVQQLDAdCLVRydXN0MS0wKwYDVQQDDCRCLVRydXN0IFRFU1QgT3BlcmF0aW9uYWwgQWR2 YW5jZWQgQ0EwHhcNMjIwODI0MTIwMTA1WhcNMjMwODI0MTIwMTA1WjCBqjEdMBsGCSqGSIb3DQEJ ARYOYmdpbnN0QGJhY2IuYmcxFDASBgNVBAMMC1NTTF9EU19CQUNCMRAwDgYDVQQLDAdCRyBJbnN0 MSowKAYDVQQKDCFCdWxnYXJpYW4tQW1lcmljYW4gQ3JlZGl0IEJhbmsgQUQxGDAWBgNVBGEMD05U UkJHLTEyMTI0NjQxOTEOMAwGA1UEBwwFU29maWExCzAJBgNVBAYTAkJHMIIBIjANBgkqhkiG9w0B AQEFAAOCAQ8AMIIBCgKCAQEAnUSHIbtJuvziKRPGkTfjkIC4KgCpqgGUFxzcGmMgaYYI3oa5aHtJ pDdfFXIgnvKgH3/X10zW6IzFTcMAgyKPGxU3cVtkyU4rH7aytRsBhgyrPLc3/PTt4EirEZ4vSd9e C/HZXc26oXnYIFVGbr9zk6tnBP5LQjulbOPSaGh5nOUvAi5SpSY66ASLtfHXvKhBRxI57p+U8hnX F0NObK+rlNm8/Vp2EY415hTuojijoFU/IWIuEIW8nkzn3Qq/0b5jS8bl8cWcCDKylGKU00gIlVaL JwzDuWg0Fdv4tcOAeuYV5T15rVCZYz0TkI+gbqqMxvQC6e9ZQPxP23aZkLQ/2QIDAQABo4IB2TCC AdUwHQYDVR0OBBYEFH7qQ9MS+/M9otIKNYzOJp9WBkM/MB8GA1UdIwQYMBaAFPUnzPfbmyiUK5tm 8ClyDoo9YtyYMCEGA1UdEgQaMBiGFmh0dHA6Ly93d3cuYi10cnVzdC5vcmcwCQYDVR0TBAIwADBM BgNVHSAERTBDMEEGCysGAQQB+3YBBwEEMDIwMAYIKwYBBQUHAgEWJGh0dHA6Ly93d3cuYi10cnVz dC5vcmcvZG9jdW1lbnRzL2NwczAOBgNVHQ8BAf8EBAMCBeAwJwYDVR0lBCAwHgYIKwYBBQUHAwEG CCsGAQUFBwMCBggrBgEFBQcDBDBUBgNVHR8ETTBLMEmgR6BFhkNodHRwOi8vY3JsdGVzdC5iLXRy dXN0Lm9yZy9yZXBvc2l0b3J5L0ItVHJ1c3RUZXN0T3BlcmF0aW9uYWxBQ0EuY3JsMIGHBggrBgEF BQcBAQR7MHkwJwYIKwYBBQUHMAGGG2h0dHA6Ly9vY3NwdGVzdC5iLXRydXN0Lm9yZzBOBggrBgEF BQcwAoZCaHR0cDovL2NhdGVzdC5iLXRydXN0Lm9yZy9yZXBvc2l0b3J5L0ItVHJ1c3RUZXN0T3Bl cmF0aW9uYWxBQ0EuY2VyMA0GCSqGSIb3DQEBCwUAA4ICAQAjl0UaYEpMlKBV0P/rx8JvBEKORVIS wu3btVZcaxMec0jaJS3eymRH2CaKNyP0h7TlDOCMNWdKDEbILIxXdMdGHQuiE+zfHLJjGE3MgtsL LmmHO3X1MuNnVnWbsGtn2P5VZkFg3ESdGQPHPUFjJzWUCbn0sNOJl8hM4d4Mizw8cQxqkH/WblwZ JG2UJq+PoDhR2bxTFXgjKHtzHgFCNoKY/AjoDBzedeZN/scKFlsmau+wZko3OH9jgcO2OdHn45V6 tUvKMypoyjo4xBFhde9LfToeWab40WV4eRUQFG9CJ3UKHoNpMW+2gASGSRtA2IlEQ3OwmiDsluR6 yEWKERYYiJ1wTm6DJ9Mw+d0ptcPrqCL8ODyvueRmCHMdL3VLTAUyVgFZ1F7UapnSjJd1z96Wpm9j SGqHXajpxKiqTS7qnbvovICXC+N50rLFIptyuGIsN0tMeYvasCQesRkTf7NSqHJQ/q53CsRNjN3j PfHtvQnU8yTziBqlkhI88qgiGuFZtJ4Y4eGaULfCAdi2tSvwQeP8e1zm+WbB9EgXrvUHd9B+Khm9 DQu/7S/tJM7YvJDZLtNf6sCFE5xOv4oeT9MlMSWMHDIRTSrqJqkYiFUeSj1hbnYOF1OCyZtPIWha dVS9ViXa+drGEc7rP3I62uD5fWszLZGaJCup02Epkw2CUQ==</X509Certificate>
                </X509Data>
                </KeyInfo>
                </Signature>
                </Sgntr>
                </hdr:AppHdr>
                <hdr:FIToFICstmrCdtTrf xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02">
                <GrpHdr>
                <MsgId>26269</MsgId>
                <CreDtTm>2023-05-12T16:06:27Z</CreDtTm>
                <NbOfTxs>15</NbOfTxs>
                <TtlIntrBkSttlmAmt Ccy="BGN">506.83</TtlIntrBkSttlmAmt>
                <IntrBkSttlmDt>2023-05-12</IntrBkSttlmDt>
                <SttlmInf>
                <SttlmMtd>CLRG</SttlmMtd>
                <ClrSys>
                <Prtry>BGINST</Prtry>
                </ClrSys>
                </SttlmInf>
                <InstgAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </InstgAgt>
                </GrpHdr>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256152681</InstrId>
                <EndToEndId>2023051256152681</EndToEndId>
                <TxId>2023051256152681</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">1</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG61BGUS91601405782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256152682</InstrId>
                <EndToEndId>2023051256152682</EndToEndId>
                <TxId>2023051256152682</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">1</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG61BGUS91601405782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256152685</InstrId>
                <EndToEndId>2023051256152685</EndToEndId>
                <TxId>2023051256152685</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">10</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG79BGUS91601005782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256152686</InstrId>
                <EndToEndId>2023051256152686</EndToEndId>
                <TxId>2023051256152686</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">3</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG61BGUS91601405782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256155360</InstrId>
                <EndToEndId>2023051256155360</EndToEndId>
                <TxId>2023051256155360</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">1.23</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р•Р’Р“Р•РќР\u0098Р™ РЇРљР\u0098РњРћР’ Р\u0098Р’РђРќРћР’ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG33BGUS91607000800801</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>FINVBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ SOME NAME ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG22FINV915010BGN00J28</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ RECURRING ACC OWN ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256160846</InstrId>
                <EndToEndId>2023051256160846</EndToEndId>
                <TxId>2023051256160846</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">4.4</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG79BGUS91601005782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256160938</InstrId>
                <EndToEndId>2023051256160938</EndToEndId>
                <TxId>2023051256160938</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">51.45</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ РќР\u0098РќРђ Р’Р•Р›Р\u0098Р§РљРћР’Рђ Р§РђРџРљРЄРќРћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG84BGUS91601005138800</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>BPBIBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ РњР•РўР›РђР™Р¤ Р®Р РЄРџ Р”РђРљ ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG29BPBI79401043401502</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ Р—РђРЇР’Р›Р•РќР\u0098Р• 232964 ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256160950</InstrId>
                <EndToEndId>2023051256160950</EndToEndId>
                <TxId>2023051256160950</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">51.01</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ РњРђР“Р”РђР›Р•РќРђ Р\u0098Р›Р§Р•Р’Рђ РђРќР”РћРќРћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG55BGUS91601010316600</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>BPBIBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ Р‘РЈР›РЎРўР РђР” Р–Р\u0098Р’РћРў Р’Р\u0098Р•РќРђ Р\u0098РќРЁРЈР РЄРќРЎ Р“Р РЈРџ ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG14BPBI79421071090906</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ Р—РђРЇР’Р›Р•РќР\u0098Р• 8705964 ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256160960</InstrId>
                <EndToEndId>2023051256160960</EndToEndId>
                <TxId>2023051256160960</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">3</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG79BGUS91601005782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256161413</InstrId>
                <EndToEndId>2023051256161413</EndToEndId>
                <TxId>2023051256161413</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">7.36</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG79BGUS91601005782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256168889</InstrId>
                <EndToEndId>2023051256168889</EndToEndId>
                <TxId>2023051256168889</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">120</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р—Р”Р РђР’РљРћ РџР•РќР§Р•Р’ Р‘РђР™Р§Р•Р’ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG03BGUS91601009736700</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>STSABGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ РњР•РўР›РђР™Р¤ Р®Р РЄРџ Р”. Рђ. Рљ - Р‘РЄР›Р“РђР Р\u0098РЇ ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG11STSA93001521037395</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ 241755 ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256168891</InstrId>
                <EndToEndId>2023051256168891</EndToEndId>
                <TxId>2023051256168891</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">120</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р—Р”Р РђР’РљРћ РџР•РќР§Р•Р’ Р‘РђР™Р§Р•Р’ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG03BGUS91601009736700</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>STSABGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ РњР•РўР›РђР™Р¤ Р®Р РЄРџ Р”. Рђ. Рљ - Р‘РЄР›Р“РђР Р\u0098РЇ ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG11STSA93001521037395</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ 241755 ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256174230</InstrId>
                <EndToEndId>2023051256174230</EndToEndId>
                <TxId>2023051256174230</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">4.5</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”РђРќР\u0098Р•Р› РЎРўР•Р¤РђРќРћР’ РЎРўР•Р¤РђРќРћР’ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG16BGUS91601007158400</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>FINVBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ РЎРўР•Р¤РђРќ Р”РђРќР\u0098Р•Р›РћР’ РЎРўР•Р¤РђРќРћР’ ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG89FINV91501016221352</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ 09,10/2021 + BILETI ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256178125</InstrId>
                <EndToEndId>2023051256178125</EndToEndId>
                <TxId>2023051256178125</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">8.88</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р”Р•РЎР\u0098РЎР›РђР’Рђ Р’РђР›Р•Р Р\u0098Р•Р’Рђ Р“Р Р\u0098Р“РћР РћР’Рђ ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG79BGUS91601005782000</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>UBBSBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ DESISLAVA ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG09UBBS88881000109908</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РўР•РЎРў ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                <CdtTrfTxInf>
                <PmtId>
                <InstrId>2023051256178495</InstrId>
                <EndToEndId>2023051256178495</EndToEndId>
                <TxId>2023051256178495</TxId>
                </PmtId>
                <PmtTpInf>
                <SvcLvl>
                <Cd>SEPA</Cd>
                </SvcLvl>
                <LclInstrm>
                <Cd>INST</Cd>
                </LclInstrm>
                </PmtTpInf>
                <IntrBkSttlmAmt Ccy="BGN">120</IntrBkSttlmAmt>
                <ChrgBr>SLEV</ChrgBr>
                <Dbtr>
                <Nm>
                <![CDATA[ Р\u0098РџРџРњРџР”Рњ-Р‘Р\u0098Р›Р\u0098Р”Р•РќРў Р•РћРћР” ]]>
                </Nm>
                </Dbtr>
                <DbtrAcct>
                <Id>
                <IBAN>BG36BGUS91601006215200</IBAN>
                </Id>
                </DbtrAcct>
                <DbtrAgt>
                <FinInstnId>
                <BIC>BGUSBGS0</BIC>
                </FinInstnId>
                </DbtrAgt>
                <CdtrAgt>
                <FinInstnId>
                <BIC>IABGBGS0</BIC>
                </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                <Nm>
                <![CDATA[ Р›РћРЇР›Р\u0098РўР\u0098 РљРћРќРЎРЈР›Рў РћРћР” ]]>
                </Nm>
                </Cdtr>
                <CdtrAcct>
                <Id>
                <IBAN>BG45IABG74971000098801</IBAN>
                </Id>
                </CdtrAcct>
                <RmtInf>
                <Ustrd>
                <![CDATA[ РЎР§Р•РўРћР’РћР”РќР\u0098 РЈРЎР›РЈР“Р\u0098 ]]>
                </Ustrd>
                </RmtInf>
                </CdtTrfTxInf>
                </hdr:FIToFICstmrCdtTrf>
                </hdr:Message>""";
        //endregion

        try {
            WrappedMessage wrappedMessage = wrappedMessageBuilder.build(XMLHelper.deserializeXml(xmlString, Message.class), InOut.IN, null);
            wrappedMessage.saveToXmlFile();
        } catch (Exception e) {
            logger.error("testObjectToXMLFile() error: " + e.getMessage(), e);
        }
    }

    @Test
    void testFileMoving() {
        // !!! Before starting this test comment out the code inside the sendMessage() method inside the scheduler class !!!

        try {
            File[] files = FileHelper.getFilesFromPath(properties.getOutgngBulkMsgsPath(), ".xml");
            for (File file : files) FileHelper.moveFile(file, properties.getOutgngBulkMsgsInProgressPath());

            TimeUnit.SECONDS.sleep(5);

            File[] inProgressDir = FileHelper.getFilesFromPath(properties.getOutgngBulkMsgsInProgressPath(), ".xml");
            for (File file : inProgressDir) FileHelper.moveFile(file, properties.getOutgngBulkMsgsProcessedPath());
        } catch (Exception e) {
            logger.error("testFileMoving() error: " + e.getMessage(), e);
        }
    }
}
