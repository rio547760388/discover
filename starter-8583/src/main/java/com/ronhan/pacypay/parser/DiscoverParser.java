package com.ronhan.pacypay.parser;

import com.ronhan.Currency;
import com.ronhan.iso8583.Message;
import com.ronhan.iso8583.Providers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/25
 **/
@Service(Providers.DISCOVER)
public class DiscoverParser implements MessageParser {
    @Override
    public ParsedMessage parse(Message message) {
        if (!Providers.DISCOVER.equals(message.getChannel())) {
            throw new IllegalArgumentException();
        }
        TreeMap<Integer, String> map = message.getData();
        ParsedMessage parsedMessage = new ParsedMessage();
        parsedMessage.setProvider(Providers.DISCOVER);

        Set<Integer> keys = map.keySet();

        for (Integer key : keys) {
            String value = map.get(key);
            switch (key){
                case 2:
                    parsedMessage.setCardNo(value);
                    break;
                case 4:
                    parsedMessage.setAmount(Double.parseDouble(value));
                    break;
                case 6:
                    parsedMessage.setBillingAmount(Double.parseDouble(value));
                    break;
                case 7:
                    parsedMessage.setTransmissionTime(value);
                    break;
                case 11:
                    parsedMessage.setTraceNumber(value);
                    break;
                case 12:
                    parsedMessage.setLocalTransTime(value);
                    break;
                case 38:
                    parsedMessage.setApprovalCode(value);
                    break;
                case 39:
                    parsedMessage.setActionCode(value);
                    break;
                case 44:
                    if (value.length() >= 7) {
                        String cvr = value.substring(5, 7);
                        parsedMessage.setCavvValidationResult(cvr);
                    }
                    break;
                case 49:
                    parsedMessage.setCurrency(value);
                    break;
                case 51:
                    parsedMessage.setBillingCurrency(value);
                    break;
                case 123:
                    parsedMessage.setReferenceId(value);
                default:
            }
        }

        if (parsedMessage.getAmount() != null && StringUtils.isNotBlank(parsedMessage.getCurrency())) {
            parsedMessage.setAmount(Currency.parseAmount(parsedMessage.getAmount(), parsedMessage.getCurrency()));
        }
        if (parsedMessage.getBillingAmount() != null  && StringUtils.isNotBlank(parsedMessage.getBillingCurrency())) {
            parsedMessage.setBillingAmount(Currency.parseAmount(parsedMessage.getBillingAmount(), parsedMessage.getBillingCurrency()));
        }
        parseErrCode(message.getMti(), map.get(39), map.get(44), parsedMessage);
        return parsedMessage;
    }


    private void parseErrCode(String mti, String actionCode, String resData, ParsedMessage parsedMessage) {
        String status = "";
        String code = "";
        String reason = "";

        status = STATUS.ERROR.status;
        code = CODE.C999.code;
        if ("904".equals(actionCode)) {
            processResData(resData, parsedMessage);
            return;
        } else if ("905".equals(actionCode)) {
            reason = "Acquirer Not Supported By Xpress";
        } else if ("909".equals(actionCode)) {
            reason = "System Malfunction";
        } else if ("916".equals(actionCode)) {
            reason = "MAC Key Invalid";
        } else if ("923".equals(actionCode)) {
            reason = "Request In Progress";
        } else {
            if ("1110".equals(mti)) {
                if (StringUtils.isNotBlank(actionCode)) {
                    code = actionCode;
                    if (Integer.parseInt(actionCode) < 100) {
                        status = STATUS.APPROVED.status;
                        code = CODE.C100.code;
                    } else {
                        status = STATUS.DECLINED.status;
                        reason = codes.get(actionCode);
                    }
                }
            } else if ("1430".equals(mti)) {
                if ("400".equals(actionCode)) {
                    status = STATUS.APPROVED.status;
                    code = CODE.C100.code;
                } else {
                    status = STATUS.FAILED.status;
                    reason = codes.get(actionCode);
                }
            }
        }

        parsedMessage.setStatus(status);
        parsedMessage.setCode(code);
        parsedMessage.setReason(reason);
    }

    private void processResData(String resData, ParsedMessage parsedMessage) {
        if (resData.length() >= 12) {
            String err = resData.substring(7, 12);
            String de = err.substring(0, 3);
            parsedMessage.setStatus(STATUS.ERROR.status);
            parsedMessage.setCode(CODE.C999.code);
            parsedMessage.setReason("DE" + de + " " + errs.get(err.substring(3, 5)));
        }
    }

    static Map<String, String> codes = new HashMap<>();
    static Map<String, String> errs = new HashMap<>();

    static {
        codes.put("100", "Do Not Honor");
        codes.put("101", "Expired Card");
        codes.put("102", "Suspected Fraud (Account Not On Positive File)");
        codes.put("103", "Customer Authentication Required");
        codes.put("104", "Restricted Card");
        codes.put("106", "Allowable Pin Tries Exceeded");
        codes.put("109", "Invalid Merchant");
        codes.put("110", "Invalid Amount");
        codes.put("111", "Invalid Card Number");
        codes.put("115", "Requested Function Not Supported");
        codes.put("117", "Incorrect Pin");
        codes.put("118", "Cycle Range Suspended");
        codes.put("119", "Transaction Not Permitted To Cardholder");
        codes.put("120", "Transaction Not Permitted To Originatora");
        codes.put("122", "Card Validity Period Exceeded");
        codes.put("124", "Violation Of Law");
        codes.put("125", "Card Not Effective");
        codes.put("129", "Suspected Counterfeit Card");
        codes.put("140", "Off-line Declined—merchant Forced Acceptance");
        codes.put("141", "Unable To Go On Line, Off-line Declined—merchant Forced Acceptance");
        codes.put("163", "Security Violations");
        codes.put("181", "Decline Given By Pos Participant (Adjustment)");
        codes.put("182", "Decline Given By Issuer");
        codes.put("183", "Domain Restriction Control Failure");
        codes.put("184", "Decline Given By Xpress, No Communication With Issuer");
        codes.put("185", "Decline Given By Xpress, Card Is Local Use Only");
        codes.put("188", "Xpress unable to forward request to Issuer X");
        codes.put("192", "Restricted Merchant");
        codes.put("194", "PIN Change Or Unblock Failed (EMV only)");
        codes.put("195", "New PIN Not Accepted (EMV only)");
        codes.put("196", "Chip Information Advice (EMV only)");
        codes.put("197", "Card Verification Failed");
        codes.put("198", "TVR or CVR Validation Failed");
        codes.put("200", "Do Not Honor");
        codes.put("201", "Expired Card");
        codes.put("202", "Suspected Fraud");
        codes.put("203", "Card Acceptor Contact Acquirer");
        codes.put("204", "Restricted Card");
        codes.put("205", "Card Acceptor Call Acquirers Security Department");
        codes.put("206", "Allowable Pin Tries Exceeded");
        codes.put("207", "Special Conditions");
        codes.put("208", "Lost Card");
        codes.put("209", "Stolen Card");
        codes.put("210", "Suspected Counterfeit Card");
        codes.put("400", "Accepted");
        codes.put("480", "Function Code Not Allowed");
        codes.put("481", "Account Number Invalid");
        codes.put("482", "Account Number Does Not Mod10");
        codes.put("483", "No Matching Cycle Range Exists For This Account");
        codes.put("484", "Invalid Amount");

        errs.put("10", "non–numeric character found in a numeric data element");
        errs.put("15", "requester or owner currency conversion results in amount too large to handle");
        errs.put("20", "truncation");
        errs.put("61", "Invalid information given");
        errs.put("A1", "account number invalid");
        errs.put("A2", "account number failed modulus 10 check");
        errs.put("A3", "local only cycle range");
        errs.put("A4", "unknown cycle range");
        errs.put("E1", "editing error");
        errs.put("E4", "invalid function code");
        errs.put("E5", "invalid date/time");
        errs.put("E6", "data element contains value not allowed to requester");
        errs.put("F9", "invalid ABC");
        errs.put("FA", "original value invalid");
        errs.put("G0", "action not allowed");
        errs.put("G1", "invalid request");
        errs.put("G2", "warning bulletin contains invalid distribution");
        errs.put("G3", "function does not expect data element to contain a value—request rejected");
        errs.put("G5", "duplicate System Trace Audit reference number");
        errs.put("G6", "message does not agree with line owner");
        errs.put("G7", "mandatory data element not present");
        errs.put("L2", "invalid stand in limit");
        errs.put("L3", "invalid Acquirer IIC");
        errs.put("R1", "record not found");
        errs.put("R2", "record already exists");
        errs.put("R3", "entry not found");
        errs.put("X0", "Xpress system error");
    }
}
