package com.example.techbuzzproject;

import okhttp3.*;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MpesaApi {

    // M-Pesa API credentials
    private static final String CONSUMER_KEY = "S7oeT2Ur1Jb6eJ0HPAbXLCTdNbKcc6aiv6euCG5wI5yziL0t"; // Replace with your Consumer Key
    private static final String CONSUMER_SECRET = "yDw9UtL6W6bc1p9zvWZeUwahnlaIqXMQwsaYUB6ahPVw0eWMNhDKJv87yjVfDO1x"; // Replace with your Consumer Secret
    private static final String SHORT_CODE = "N/A"; // Replace with your ShortCode
    private static final String PASSKEY = "YOUR_PASSKEY"; // Replace with your M-Pesa Passkey

    // M-Pesa API URLs
    private static final String TOKEN_URL = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
    private static final String STK_PUSH_URL = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";

    // Generate current timestamp in required format (YYYYMMDDHHMMSS)
    private static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    // Generate password required for the STK Push request
    private static String getPassword() {
        String timestamp = getTimestamp();
        String plainPassword = SHORT_CODE + PASSKEY + timestamp;
        return Base64.encodeToString(plainPassword.getBytes(), Base64.NO_WRAP);
    }

    // Method to generate OAuth access token
    public static void generateAccessToken(Callback callback) {
        OkHttpClient client = new OkHttpClient();

        // Encode Consumer Key and Consumer Secret for Basic Authorization
        String credentials = CONSUMER_KEY + ":" + CONSUMER_SECRET;
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        // Build request
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .header("Authorization", "Basic " + encodedCredentials)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(callback);
    }

    // Method to initiate Lipa na M-Pesa Online (STK Push)
    public static void initiateStkPush(String accessToken, String phoneNumber, String amount, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        // Build JSON request body
        String jsonRequest = "{" +
                "\"BusinessShortCode\":\"" + SHORT_CODE + "\"," +
                "\"Password\":\"" + getPassword() + "\"," +
                "\"Timestamp\":\"" + getTimestamp() + "\"," +
                "\"TransactionType\":\"CustomerPayBillOnline\"," +
                "\"Amount\":\"" + amount + "\"," +
                "\"PartyA\":\"" + phoneNumber + "\"," +
                "\"PartyB\":\"" + SHORT_CODE + "\"," +
                "\"PhoneNumber\":\"" + phoneNumber + "\"," +
                "\"CallBackURL\":\"https://yourdomain.com/mpesa/callback\"," +
                "\"AccountReference\":\"Ticket Purchase\"," +
                "\"TransactionDesc\":\"Buying Ticket\"" +
                "}";

        // Build request
        RequestBody body = RequestBody.create(jsonRequest, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(STK_PUSH_URL)
                .header("Authorization", "Bearer " + accessToken)
                .post(body)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(callback);
    }

    // Utility method to parse access token from JSON response
    public static String parseAccessToken(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
