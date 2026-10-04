package si.ros.RosKasa.payment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.payment.models.PaytenResponse;

public class PaytenPaymentService {

    private static final String TAG = "PaytenPaymentService";
    public static final int REQUEST_CODE_PAYTEN = 1;

    public interface PaytenCallback {
        void onSuccess(PaytenResponse response);
        void onError(String code, String message);
        void onCanceled();
    }

    public static Intent buildPaymentIntent(Context context, int racunId, int pozicijaId, BigDecimal amount,
                                            String operation, String authCode) {
        Globals g = Globals.getInstance();
        String pin = g.getPayTenAPin();
        String pkg = g.getPayTenARosPackage();
        if (pkg == null || pkg.isEmpty()) {
            pkg = context != null ? context.getPackageName() : "si.ros.RosKasa";
        }
        String activityMain = g.getPayTenAActivityMain();
        if (activityMain == null || activityMain.isEmpty()) {
            activityMain = "com.payten.nlb.slovenia";
        }
        String activitySplash = g.getPayTenAActivities();
        if (activitySplash == null || activitySplash.isEmpty()) {
            activitySplash = "com.payten.nlb.slovenia.activities.SplashActivity";
        }

        // Znesek v centih (npr. 15.50 -> 1550)
        int amountCents = amount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).intValue();
        String transactionId = racunId + "-" + pozicijaId;
        String opClass = (operation != null && !operation.isEmpty()) ? operation : "purchase";

        String jsonRequest = buildJsonRequest(pin, String.valueOf(amountCents), pkg, "POS", opClass, authCode, transactionId);
        Log.i(TAG, "PayTen Zahteva za R:" + racunId + ": " + jsonRequest);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(activityMain, activitySplash));
        intent.setAction(activitySplash);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("REQUEST_JSON_STRING", jsonRequest);

        // Shrani začasno sejo za okrevanje
        PaymentRecoveryManager.clearPendingPayment(context);
        PaymentRecoveryManager.savePendingPayment(context, "", "", "", transactionId, String.valueOf(racunId), String.valueOf(amountCents));
        g.setPayTenIntransaction(true);
        g.setZadnjiPayTenARacunId(racunId);
        g.setZadnjaPayTenAPozicijaId(pozicijaId);

        return intent;
    }

    private static String buildJsonRequest(String pin, String amount, String packageName,
                                           String transactionType, String transactionClass,
                                           String authorizationCode, String merchantUniqueID) {
        try {
            JSONObject root = new JSONObject();
            JSONObject request = new JSONObject();
            request.put("pin", pin != null ? pin : "");
            request.put("amount", amount != null ? amount : "0");
            request.put("packageName", packageName != null ? packageName : "");
            request.put("transactionType", transactionType != null ? transactionType : "POS");
            request.put("transactionClass", transactionClass != null ? transactionClass : "purchase");
            if (authorizationCode != null && !authorizationCode.isEmpty()) {
                request.put("authorizationCode", authorizationCode);
            }
            request.put("merchantUniqueID", merchantUniqueID != null ? merchantUniqueID : "");
            root.put("request", request);
            return root.toString();
        } catch (Exception e) {
            Log.e(TAG, "Napaka pri sestavi JSON za PayTen: " + e.getMessage(), e);
            return "{}";
        }
    }

    public static PaytenResponse parseResponseIntent(Intent data) {
        PaytenResponse resp = new PaytenResponse();
        if (data == null) {
            resp.setResult("Napaka");
            resp.setpFault("Intent data is null");
            return resp;
        }

        Bundle extras = data.getExtras();
        if (extras == null) {
            resp.setResult("Napaka");
            resp.setpFault("Extras is null");
            return resp;
        }

        CharSequence seq = extras.getCharSequence("RESPONSE_JSON_STRING");
        if (seq == null) {
            resp.setResult("Napaka");
            resp.setpFault("RESPONSE_JSON_STRING ni najden");
            return resp;
        }

        String jsonString = seq.toString();
        Log.i(TAG, "PayTen Odgovor JSON: " + jsonString);

        try {
            JSONObject root = new JSONObject(jsonString);
            if (root.has("response")) {
                JSONObject responseObj = root.getJSONObject("response");
                if (responseObj.has("paymentIdentificator")) {
                    resp.setPaymentIdentificator(responseObj.optString("paymentIdentificator", ""));
                    resp.setPaymentSolutionReference(resp.getPaymentIdentificator());
                }

                if (responseObj.has("status")) {
                    JSONObject statusObj = responseObj.getJSONObject("status");
                    resp.setCode(statusObj.optString("code", ""));
                    resp.setMessage(statusObj.optString("message", ""));
                    resp.setpMessage(resp.getMessage());

                    if ("00".equals(resp.getCode())) {
                        resp.setResult("OK");
                        String receiptDataStr = statusObj.optString("receiptData", "");
                        resp.setpReceiptData(receiptDataStr);

                        if (!receiptDataStr.isEmpty()) {
                            JSONObject receiptObj = new JSONObject(receiptDataStr);
                            resp.setApplicationIdentifier(receiptObj.optString("aid", ""));
                            resp.setAuthorizedAmount(receiptObj.optString("amount", ""));
                            resp.setTipAmount(receiptObj.optString("tipAmount", ""));
                            resp.setApplicationLabel(receiptObj.optString("applicationLabel", ""));
                            resp.setpAuthorizationCode(receiptObj.optString("authorizationCode", ""));
                            resp.setBankName(receiptObj.optString("bankName", ""));
                            resp.setCardNumber(receiptObj.optString("cardNumber", ""));
                            resp.setTimestamp(receiptObj.optString("dateTime", ""));
                            resp.setMerchantId(receiptObj.optString("merchantId", ""));
                            resp.setMerchant(receiptObj.optString("merchantName", ""));
                            resp.setOperationName(receiptObj.optString("operationName", ""));
                            resp.setResponse(receiptObj.optString("response", ""));
                            resp.setRrn(receiptObj.optString("rrn", ""));
                            resp.setStatus(receiptObj.optString("status", ""));
                            resp.setTerminalId(receiptObj.optString("terminalId", ""));

                            // Parsiranje zneska v BigDecimal
                            try {
                                if (!resp.getAuthorizedAmount().isEmpty()) {
                                    resp.setZnesekPOS(new BigDecimal(resp.getAuthorizedAmount().replace(",", ".")));
                                }
                            } catch (Exception ignored) {}

                            // Parsiranje morebitne napitnine
                            try {
                                if (!resp.getTipAmount().isEmpty()) {
                                    BigDecimal tip = new BigDecimal(resp.getTipAmount().replace(",", "."));
                                    if (tip.compareTo(BigDecimal.ZERO) >= 0 && tip.compareTo(new BigDecimal(1000)) <= 0) {
                                        resp.setpNapitnina(tip);
                                    }
                                }
                            } catch (Exception ignored) {}

                            // Formatiran bančni slip
                            StringBuilder sb = new StringBuilder();
                            addFormattedLine(sb, "Prodajalec", resp.getMerchant());
                            addFormattedLine(sb, "ID", resp.getMerchantId());
                            sb.append("--------------------------------------\n");
                            addFormattedLine(sb, "Terminal", resp.getTerminalId());
                            addFormattedLine(sb, "AID", resp.getApplicationIdentifier());
                            addFormattedLine(sb, "Kartica", resp.getApplicationLabel());
                            addFormattedLine(sb, "Št.", resp.getCardNumber());
                            addFormattedLine(sb, "Avt.koda", resp.getpAuthorizationCode());
                            addFormattedLine(sb, "Banka", resp.getBankName());
                            addFormattedLine(sb, "Datum", resp.getTimestamp());
                            sb.append("--------------------------------------\n");
                            addFormattedLine(sb, "Vrsta", resp.getOperationName());
                            addFormattedLine(sb, "Znesek", resp.getAuthorizedAmount());
                            sb.append("--------------------------------------\n");

                            resp.setReceipt(sb.toString());
                            resp.setClient(sb.toString());
                        }
                    } else {
                        resp.setResult("Napaka");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Napaka pri razčlenjevanju PayTen odgovora: " + e.getMessage(), e);
            resp.setResult("Napaka");
            resp.setpFault("JSON parse napaka: " + e.getMessage());
        }

        return resp;
    }

    private static void addFormattedLine(StringBuilder sb, String label, String value) {
        if (value == null) value = "";
        String line = String.format(Locale.getDefault(), "%-10s %s", label + ":", value);
        if (line.length() > 34) {
            line = line.substring(0, 34);
        }
        sb.append(line).append("\n");
    }
}
