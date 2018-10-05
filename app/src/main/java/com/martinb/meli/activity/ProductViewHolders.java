package com.martinb.meli.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;

public class ProductViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView image;
    public TextView price;
    public TextView title;

    public static final String ID_PRODUCTO = "id";

    public ProductViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        image = (ImageView) itemView.findViewById(R.id.image);
        price = (TextView) itemView.findViewById(R.id.price);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();

        //Todo: pasarle el id del producto para hacer la request y que me manden todos los detalles.
        Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
        //Aca en vez de title deberia pasarle el id del producto.
        intent.putExtra(ID_PRODUCTO, title.getText().toString());
        view.getContext().startActivity(intent);
    }
}
