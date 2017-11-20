package com.roque.munidenuncias.activities;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    EditText txtnombre, txtusername, txtpassword;
    Button btnRegister;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtnombre = (EditText) findViewById(R.id.nombreText);
        txtusername = (EditText) findViewById(R.id.usernameText);
        txtpassword = (EditText) findViewById(R.id.passwordText);
        btnRegister = (Button) findViewById(R.id.btnregistro);

    }

    public void callRegistro(View view){
        final String nombre = txtnombre.getText().toString();
        final String email = txtusername.getText().toString();
        final String password = txtpassword.getText().toString();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Campos requeridos", Toast.LENGTH_SHORT).show();
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> call = null;
        call = service.registrarUsuario(nombre, email, password);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);
                    if (response.isSuccessful()) {
                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);
                        Toast.makeText(RegisterActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        // Go to Login
                        goLogin();
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        //Intent intent = new Intent(this, PerfilActivity.class);
        intent.putExtra("correo",txtnombre.getText().toString());
        startActivity(intent);
        finish();
    }
}
