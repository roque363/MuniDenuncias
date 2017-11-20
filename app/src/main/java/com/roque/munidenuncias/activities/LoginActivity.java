package com.roque.munidenuncias.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roque.munidenuncias.R;
import com.roque.munidenuncias.network.ApiService;
import com.roque.munidenuncias.network.ApiServiceGenerator;
import com.roque.munidenuncias.network.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    // SharedPreferences
    private SharedPreferences sharedPreferences;
    private EditText txtemail, txtpassword;
    private Button btningresar, btnregister;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtemail = (EditText) findViewById(R.id.txtname);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        btningresar = (Button) findViewById(R.id.btninicio);
        btnregister = (Button) findViewById(R.id.btnregistrarse);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // username remember
        final String email = sharedPreferences.getString("email", null);
        if(email != null){
            txtemail.setText(email);
            txtpassword.requestFocus();
        }

        // islogged remember
        if(sharedPreferences.getBoolean("islogged", false)){
            // Go to Dashboard
            goDashboard();
        }

    }

    public void iniciar(View view){
        final String email = txtemail.getText().toString();
        final String password = txtpassword.getText().toString();
        final String estado = "cliente";

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Nombre y Contraseña son campos requeridos", Toast.LENGTH_SHORT).show();
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<ResponseMessage> call = null;
        call = service.loginUsuario(email, password);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);
                    if (response.isSuccessful()) {
                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);
                        Toast.makeText(LoginActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        // Save to SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor
                                .putString("email", email)
                                .putBoolean("islogged", true)
                                .commit();

                        goDashboard();
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void registro(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        //Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
        finish();
    }

    private void goDashboard() {
        Intent intent = new Intent(this, MainActivity.class);
        //Intent intent = new Intent(this, PerfilActivity.class);
        intent.putExtra("correo",txtemail.getText().toString());
        startActivity(intent);
        finish();
    }
}
