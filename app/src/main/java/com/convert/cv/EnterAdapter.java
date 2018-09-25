package com.convert.cv;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ADEPT on 02.05.2018.
 */
public class EnterAdapter extends RecyclerView.Adapter<EnterAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private String[] names;
    private int[] imgs;
    private int width;
    private Context context;
    private Factory f;




    EnterAdapter(Context context, int [] imgs, int width, Factory f) {
       inflater = LayoutInflater.from(context);
       this.context = context;
       this.names = f.getTitleMas();
       this.imgs = imgs;
       this.width = width;
       this.f = f;



    }

    @Override
    public EnterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.enter_item, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EnterAdapter.MyViewHolder holder, int position) {

      holder.txt.setText(names[position]);
      holder.img.setImageResource(imgs[position]);

    }

    @Override
    public int getItemCount() {
        return names.length;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView txt;

        MyViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, (int)(width * 1.1));
            itemView.setLayoutParams(params);


            img = itemView.findViewById(R.id.img);

            txt = itemView.findViewById(R.id.name);


        }

        @Override
        public void onClick(View v) {

            f.generate(getAdapterPosition());
            context.startActivity(new Intent(context,  Area.class));

        }
    }

}
