package com.example.picobank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class OTP extends AppCompatActivity {
    private EditText otpDigit1;
    private EditText otpDigit2;
    private EditText otpDigit3;
    private EditText otpDigit4;
    private RequestQueue requestQueue;
    private Button submitOtpButton;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: com.example.picobank.OTP$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return OTP.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        this.otpDigit1 = (EditText) findViewById(R.id.otpDigit1);
        this.otpDigit2 = (EditText) findViewById(R.id.otpDigit2);
        this.otpDigit3 = (EditText) findViewById(R.id.otpDigit3);
        this.otpDigit4 = (EditText) findViewById(R.id.otpDigit4);
        this.submitOtpButton = (Button) findViewById(R.id.submitOtpButton);
        this.requestQueue = Volley.newRequestQueue(this);
        this.submitOtpButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.picobank.OTP.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) throws JSONException {
                String otp = OTP.this.otpDigit1.getText().toString() + OTP.this.otpDigit2.getText().toString() + OTP.this.otpDigit3.getText().toString() + OTP.this.otpDigit4.getText().toString();
                OTP.this.verifyOtp(otp);
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View v, WindowInsetsCompat insets) {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyOtp(String otp) throws JSONException {
        String endpoint = "your server url/verify-otp";
        if (getResources().getString(R.string.otp_value).equals(otp)) {
            Intent intent = new Intent(this, (Class<?>) MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid OTP", 0).show();
        }
        JSONObject postData = new JSONObject();
        try {
            postData.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(1, endpoint, postData, new Response.Listener<JSONObject>() { // from class: com.example.picobank.OTP.2
            @Override // com.android.volley.Response.Listener
            public void onResponse(JSONObject response) throws JSONException {
                try {
                    boolean success = response.getBoolean("success");
                    if (success) {
                        String flag = response.getString("flag");
                        String hint = response.getString("hint");
                        Intent intent2 = new Intent(OTP.this, (Class<?>) MainActivity.class);
                        intent2.putExtra("flag", flag);
                        intent2.putExtra("hint", hint);
                        OTP.this.startActivity(intent2);
                        OTP.this.finish();
                    } else {
                        Toast.makeText(OTP.this, "Invalid OTP", 0).show();
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.example.picobank.OTP.3
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError error) {
            }
        });
        this.requestQueue.add(jsonObjectRequest);
    }
}
