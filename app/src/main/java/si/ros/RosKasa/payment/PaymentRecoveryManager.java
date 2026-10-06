package si.ros.RosKasa.payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.math.BigDecimal;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.payment.models.SixTapResponse;

public class PaymentRecoveryManager {

    private static final String TAG = "PaymentRecoveryManager";

    public static final String PREFS_NAME = "SixTapRecoveryPrefs";

    public static final String KEY_SESSION_ID = "lastSessionId";
    public static final String KEY_RACUN_ID = "lastRacunId";
    public static final String KEY_ZNESEK_INT = "lastZnesekInt";

    public static final String KEY_INTENT_SESSION_ID = "lastIntentSessionId";
    public static final String KEY_INTENT_RACUN_ID = "lastIntentRacunId";
    public static final String KEY_INTENT_ZNESEK_INT = "lastIntentZnesekInt";

    public static final String KEY_RECOVER_SESSION_ID = "lastRecoverSessionId";
    public static final String KEY_RECOVER_RACUN_ID = "lastRecoverRacunId";
    public static final String KEY_RECOVER_ZNESEK_INT = "lastRecoverZnesekInt";
    public static final String KEY_RECOVER_INTENT_SESSION_ID = "lastRecoverIntentSessionId";
    public static final String KEY_RECOVER_INTENT_RACUN_ID = "lastRecoverIntentRacunId";
    public static final String KEY_RECOVER_INTENT_ZNESEK_INT = "lastRecoverIntentZnesekInt";
    public static final String KEY_RECOVER_RETRY_COUNT = "lastRecoverRetryCount";

    public static final int POS_STATE_NONE = 0;
    public static final int POS_STATE_PENDING_POS = 1;
    public static final int POS_STATE_POS_CONFIRMED = 2;
    public static final int POS_STATE_LOCAL_RECORDED = 3;
    public static final int POS_STATE_SERVER_SYNCED = 4;
    public static final int POS_STATE_COMPLETED = 5;

    public static final String KEY_POS_STATE = "lastPosState";
    public static final String KEY_POS_RACUN_ID = "lastPosRacunId";
    public static final String KEY_POS_SESSION_ID = "lastPosSessionId";
    public static final String KEY_POS_M_REF = "lastPosMRef";
    public static final String KEY_POS_AUTH_AMOUNT = "lastPosAuthAmount";
    public static final String KEY_POS_TIP_AMOUNT = "lastPosTipAmount";
    public static final String KEY_POS_BRAND_NAME = "lastPosBrandName";
    public static final String KEY_POS_CARD_NUMBER = "lastPosCardNumber";
    public static final String KEY_POS_SLIP_CLIENT = "lastPosSlipClient";
    public static final String KEY_POS_SLIP_MERCHANT = "lastPosSlipMerchant";
    public static final String KEY_POS_PLACILO_ID = "lastPosPlaciloId";
    public static final String KEY_POS_KUPEC_ID = "lastPosKupecId";
    public static final String KEY_POS_ZNESEK = "lastPosZnesek";

    public static class PendingPaymentInfo {
        public String sessionId = "";
        public String racunId = "";
        public String znesekInt = "";
        public String intentSessionId = "";
        public String intentRacunId = "";
        public String intentZnesekInt = "";

        public String getEffectiveSessionId() {
            if (intentSessionId != null && !intentSessionId.trim().isEmpty()) {
                return intentSessionId.trim();
            }
            return sessionId != null ? sessionId.trim() : "";
        }

        public String getEffectiveRacunId() {
            if (intentRacunId != null && !intentRacunId.trim().isEmpty()) {
                return intentRacunId.trim();
            }
            return racunId != null ? racunId.trim() : "";
        }

        public String getEffectiveZnesekInt() {
            if (intentZnesekInt != null && !intentZnesekInt.trim().isEmpty()) {
                return intentZnesekInt.trim();
            }
            return znesekInt != null ? znesekInt.trim() : "";
        }

        public boolean isValid() {
            return !getEffectiveSessionId().isEmpty() && !getEffectiveRacunId().isEmpty();
        }
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void savePendingPayment(Context context, String aSessionId, String aRacunId, String aZnesekInt,
                                          String iSessionId, String iRacunId, String iZnesekInt) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        if (aSessionId != null && !aSessionId.isEmpty()) editor.putString(KEY_SESSION_ID, aSessionId);
        if (aRacunId != null && !aRacunId.isEmpty()) editor.putString(KEY_RACUN_ID, aRacunId);
        if (aZnesekInt != null && !aZnesekInt.isEmpty()) editor.putString(KEY_ZNESEK_INT, aZnesekInt);

        if (iSessionId != null && !iSessionId.isEmpty()) editor.putString(KEY_INTENT_SESSION_ID, iSessionId);
        if (iRacunId != null && !iRacunId.isEmpty()) editor.putString(KEY_INTENT_RACUN_ID, iRacunId);
        if (iZnesekInt != null && !iZnesekInt.isEmpty()) editor.putString(KEY_INTENT_ZNESEK_INT, iZnesekInt);

        editor.apply();
    }

    public static PendingPaymentInfo tryGetPendingPayment(Context context) {
        SharedPreferences sp = getPrefs(context);
        PendingPaymentInfo info = new PendingPaymentInfo();
        info.sessionId = sp.getString(KEY_SESSION_ID, "");
        info.racunId = sp.getString(KEY_RACUN_ID, "");
        info.znesekInt = sp.getString(KEY_ZNESEK_INT, "");
        info.intentSessionId = sp.getString(KEY_INTENT_SESSION_ID, "");
        info.intentRacunId = sp.getString(KEY_INTENT_RACUN_ID, "");
        info.intentZnesekInt = sp.getString(KEY_INTENT_ZNESEK_INT, "");
        return info;
    }

    public static void clearPendingPayment(Context context) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove(KEY_SESSION_ID);
        editor.remove(KEY_RACUN_ID);
        editor.remove(KEY_ZNESEK_INT);
        editor.remove(KEY_INTENT_SESSION_ID);
        editor.remove(KEY_INTENT_RACUN_ID);
        editor.remove(KEY_INTENT_ZNESEK_INT);
        editor.apply();
    }

    public static void saveRecoveryPayment(Context context, String aSessionId, String aRacunId, String aZnesekInt,
                                           String iSessionId, String iRacunId, String iZnesekInt) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        if (aSessionId != null && !aSessionId.isEmpty()) editor.putString(KEY_RECOVER_SESSION_ID, aSessionId);
        if (aRacunId != null && !aRacunId.isEmpty()) editor.putString(KEY_RECOVER_RACUN_ID, aRacunId);
        if (aZnesekInt != null && !aZnesekInt.isEmpty()) editor.putString(KEY_RECOVER_ZNESEK_INT, aZnesekInt);

        if (iSessionId != null && !iSessionId.isEmpty()) editor.putString(KEY_RECOVER_INTENT_SESSION_ID, iSessionId);
        if (iRacunId != null && !iRacunId.isEmpty()) editor.putString(KEY_RECOVER_INTENT_RACUN_ID, iRacunId);
        if (iZnesekInt != null && !iZnesekInt.isEmpty()) editor.putString(KEY_RECOVER_INTENT_ZNESEK_INT, iZnesekInt);

        editor.apply();
    }

    public static PendingPaymentInfo tryGetRecoveryPayment(Context context) {
        SharedPreferences sp = getPrefs(context);
        PendingPaymentInfo info = new PendingPaymentInfo();
        info.sessionId = sp.getString(KEY_RECOVER_SESSION_ID, "");
        info.racunId = sp.getString(KEY_RECOVER_RACUN_ID, "");
        info.znesekInt = sp.getString(KEY_RECOVER_ZNESEK_INT, "");
        info.intentSessionId = sp.getString(KEY_RECOVER_INTENT_SESSION_ID, "");
        info.intentRacunId = sp.getString(KEY_RECOVER_INTENT_RACUN_ID, "");
        info.intentZnesekInt = sp.getString(KEY_RECOVER_INTENT_ZNESEK_INT, "");
        return info;
    }

    public static void clearRecoveryPayment(Context context) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove(KEY_RECOVER_SESSION_ID);
        editor.remove(KEY_RECOVER_RACUN_ID);
        editor.remove(KEY_RECOVER_ZNESEK_INT);
        editor.remove(KEY_RECOVER_INTENT_SESSION_ID);
        editor.remove(KEY_RECOVER_INTENT_RACUN_ID);
        editor.remove(KEY_RECOVER_INTENT_ZNESEK_INT);
        editor.remove(KEY_RECOVER_RETRY_COUNT);
        editor.apply();
    }

    public static int getRecoveryRetryCount(Context context) {
        return getPrefs(context).getInt(KEY_RECOVER_RETRY_COUNT, 0);
    }

    public static int incrementRecoveryRetryCount(Context context) {
        SharedPreferences sp = getPrefs(context);
        int current = sp.getInt(KEY_RECOVER_RETRY_COUNT, 0) + 1;
        sp.edit().putInt(KEY_RECOVER_RETRY_COUNT, current).apply();
        return current;
    }

    public static void resetRecoveryRetryCount(Context context) {
        getPrefs(context).edit().putInt(KEY_RECOVER_RETRY_COUNT, 0).apply();
    }

    public static void saveConfirmedPOSTransaction(Context context, SixTapResponse resp, int state) {
        if (resp == null) return;
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(KEY_POS_STATE, state);
        editor.putInt(KEY_POS_RACUN_ID, resp.getpRacunId());
        editor.putString(KEY_POS_SESSION_ID, resp.getpWpiSessionId());
        editor.putString(KEY_POS_M_REF, resp.getPaymentSolutionReference());
        editor.putString(KEY_POS_AUTH_AMOUNT, resp.getAuthorizedAmount());
        editor.putString(KEY_POS_TIP_AMOUNT, resp.getTipAmount());
        editor.putString(KEY_POS_BRAND_NAME, resp.getBrandName());
        editor.putString(KEY_POS_CARD_NUMBER, resp.getCardnumber());
        editor.putString(KEY_POS_SLIP_CLIENT, resp.getClient());
        editor.putString(KEY_POS_SLIP_MERCHANT, resp.getMerchant());
        editor.putInt(KEY_POS_PLACILO_ID, resp.getpPlaciloId());
        editor.putInt(KEY_POS_KUPEC_ID, resp.getpKupecId());
        editor.putString(KEY_POS_ZNESEK, resp.getpZnesek() != null ? resp.getpZnesek().toPlainString() : "0");
        editor.apply();
    }

    public static boolean tryGetConfirmedPOSTransaction(Context context, SixTapResponse outResp, int[] outState) {
        SharedPreferences sp = getPrefs(context);
        int state = sp.getInt(KEY_POS_STATE, POS_STATE_NONE);
        if (state < POS_STATE_POS_CONFIRMED) {
            return false;
        }
        if (outState != null && outState.length > 0) {
            outState[0] = state;
        }
        if (outResp != null) {
            outResp.setpRacunId(sp.getInt(KEY_POS_RACUN_ID, 0));
            outResp.setpWpiSessionId(sp.getString(KEY_POS_SESSION_ID, ""));
            outResp.setPaymentSolutionReference(sp.getString(KEY_POS_M_REF, ""));
            outResp.setAuthorizedAmount(sp.getString(KEY_POS_AUTH_AMOUNT, ""));
            outResp.setTipAmount(sp.getString(KEY_POS_TIP_AMOUNT, ""));
            outResp.setBrandName(sp.getString(KEY_POS_BRAND_NAME, ""));
            outResp.setCardnumber(sp.getString(KEY_POS_CARD_NUMBER, ""));
            outResp.setClient(sp.getString(KEY_POS_SLIP_CLIENT, ""));
            outResp.setMerchant(sp.getString(KEY_POS_SLIP_MERCHANT, ""));
            outResp.setpPlaciloId(sp.getInt(KEY_POS_PLACILO_ID, 0));
            outResp.setpKupecId(sp.getInt(KEY_POS_KUPEC_ID, 0));
            try {
                outResp.setpZnesek(new BigDecimal(sp.getString(KEY_POS_ZNESEK, "0")));
            } catch (Exception ignored) {
                outResp.setpZnesek(BigDecimal.ZERO);
            }
            outResp.setResult("WPI_RESULT_SUCCESS");
        }
        return true;
    }

    public static void setPOSTransactionState(Context context, int state) {
        getPrefs(context).edit().putInt(KEY_POS_STATE, state).apply();
    }

    public static int getPOSTransactionState(Context context) {
        return getPrefs(context).getInt(KEY_POS_STATE, POS_STATE_NONE);
    }

    public static void clearConfirmedPOSTransaction(Context context) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove(KEY_POS_STATE);
        editor.remove(KEY_POS_RACUN_ID);
        editor.remove(KEY_POS_SESSION_ID);
        editor.remove(KEY_POS_M_REF);
        editor.remove(KEY_POS_AUTH_AMOUNT);
        editor.remove(KEY_POS_TIP_AMOUNT);
        editor.remove(KEY_POS_BRAND_NAME);
        editor.remove(KEY_POS_CARD_NUMBER);
        editor.remove(KEY_POS_SLIP_CLIENT);
        editor.remove(KEY_POS_SLIP_MERCHANT);
        editor.remove(KEY_POS_PLACILO_ID);
        editor.remove(KEY_POS_KUPEC_ID);
        editor.remove(KEY_POS_ZNESEK);
        editor.apply();
    }

    /**
     * Počisti vse shranjene podatke za okrevanje plačil (uporabno ob uspešni transakciji ali ob Testu tiskanja).
     */
    public static void clearAllRecoveryData(Context context) {
        clearPendingPayment(context);
        clearRecoveryPayment(context);
        clearConfirmedPOSTransaction(context);
        Globals.getInstance().setSixtapintransaction(false);
        Globals.getInstance().setPayTenIntransaction(false);
        Globals.getInstance().setZadnjiPayTenARacunId(0);
        Log.i(TAG, "clearAllRecoveryData: Vsi začasni podatki plačilnih sej in flagi so bili uspešno ponastavljeni.");
    }
}
