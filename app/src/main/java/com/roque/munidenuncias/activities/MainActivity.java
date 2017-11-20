package com.roque.munidenuncias.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roque.munidenuncias.DenunciasAdapter;
import com.roque.munidenuncias.R;
import com.roque.munidenuncias.model.Denuncia;
import com.roque.munidenuncias.network.ApiService;
import com.roque.munidenuncias.network.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView denunciasList;
    private SharedPreferences sharedPreferences;
    private static final int REGISTER_FORM_REQUEST = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String email;

    public void showRegister(View view){
        startActivityForResult(new Intent(this, StoreActivity.class), REGISTER_FORM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTER_FORM_REQUEST) {
            // refresh data
            initialize();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        denunciasList = (RecyclerView) findViewById(R.id.recyclerview);
        denunciasList.setLayoutManager(new LinearLayoutManager(this));

        denunciasList.setAdapter(new DenunciasAdapter(this));

        initialize();

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        email = sharedPreferences.getString("email", null);
        Log.d(TAG, "email: " + email);
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Denuncia>> call = service.getDenuncias();

        call.enqueue(new Callback<List<Denuncia>>() {
            @Override
            public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Denuncia> denuncias = response.body();
                        Log.d(TAG, "productos: " + denuncias);

                        DenunciasAdapter adapter = (DenunciasAdapter) denunciasList.getAdapter();
                        adapter.setDenuncias(denuncias);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Denuncia>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
