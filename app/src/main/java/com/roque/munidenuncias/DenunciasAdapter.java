package com.roque.munidenuncias;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roque.munidenuncias.model.Denuncia;
import com.roque.munidenuncias.network.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ROQUE on 16/11/2017.
 */

public class DenunciasAdapter extends RecyclerView.Adapter<DenunciasAdapter.ViewHolder>{

    private static final String TAG = DenunciasAdapter.class.getSimpleName();

    private List<Denuncia> denuncias;

    private Activity activity;

    public DenunciasAdapter(Activity activity){
        this.denuncias = new ArrayList<>();
        this.activity = activity;
    }

    public void setDenuncias(List<Denuncia> denuncias){
        this.denuncias = denuncias;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoImage;
        public TextView titulotxt;
        public TextView usuariotxt;
        public TextView ubicaciontxt;
        public ImageButton menuButton;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = (ImageView) itemView.findViewById(R.id.foto_image);
            titulotxt = (TextView) itemView.findViewById(R.id.tituloText);
            usuariotxt = (TextView) itemView.findViewById(R.id.usuarioText);
            ubicaciontxt = (TextView) itemView.findViewById(R.id.ubicacionText);

        }
    }

    @Override
    public DenunciasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_denuncia, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DenunciasAdapter.ViewHolder viewHolder, final int position) {

        final Denuncia denuncia = this.denuncias.get(position);

        viewHolder.titulotxt.setText(denuncia.getTitulo());
        viewHolder.usuariotxt.setText(denuncia.getUsers_id());
        viewHolder.ubicaciontxt.setText(denuncia.getUbicacion());

        String url = ApiService.API_BASE_URL + "/images/" + denuncia.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

    }

    @Override
    public int getItemCount() {
        return this.denuncias.size();
    }

}
