package com.convert.cv;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ADEPT on 25.04.2018.
 */
public class AreaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TYPE_ITEM = 0;

    private LayoutInflater inflater;
    private RecyclerView recycler;
    private LinearLayoutManager layoutManager;
    private String[] unitMas;
    private int[] labelMas;
    private Context ctx;

    private String[] editArray;

    private int selector = 1;
    private boolean isNull;
    private boolean gate = true;
    private boolean isHtml = false;
    private Factory f;


    AreaAdapter(Context ctx, RecyclerView recycler, Factory f) {

        inflater = LayoutInflater.from(ctx);
        this.ctx = ctx;

        this.recycler = recycler;
        layoutManager = (LinearLayoutManager) recycler.getLayoutManager();


        unitMas = f.getUnitMas();
        labelMas = f.getLabelMas();
        this.f = f;
        if(labelMas == null) isNull = true;
        if(f.getId() == 2 || f.getId() == 3) isHtml = true;

        editArray = new String[isNull? unitMas.length : unitMas.length - labelMas.length];

    }

    @Override
    public int getItemViewType(int position) {

        if (isNull) return TYPE_ITEM;

            for (int n : labelMas) {
                if (position == n) {
                    return 1;
                }
            }

       return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
          return new MyViewHolder(inflater.inflate(R.layout.rv_item, parent,false));
        }

       return new TitleHolder(inflater.inflate(R.layout.rv_title, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

       if (holder.getItemViewType() == TYPE_ITEM) {

           MyViewHolder vh = (MyViewHolder) holder;
           vh.textView.setText(isHtml ? Html.fromHtml(unitMas[position]) : unitMas[position]);

           if (isEmpty()) vh.editText.getText().clear();
           else vh.editText.setText(editArray[positor(position)]);

       }else{

          TitleHolder vh2 = (TitleHolder) holder;
          vh2.title.setText(unitMas[position]);
       }
    }

    private int positor(int pos){

        if(isNull) return pos;

        int p = pos;

        for (int n : labelMas) {
            if (pos > n) {
                p--;
            }
        }

      return p;
    }

    @Override
    public int getItemCount() {
        return  unitMas.length;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        private EditText editText;
        private TextView textView;

        MyViewHolder(View v) {
            super(v);

            editText = v.findViewById(R.id.edit);
            textView = v.findViewById(R.id.txt);


            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus){
                        editText.addTextChangedListener(watcher);

                    } else {
                        editText.removeTextChangedListener(watcher);

                        if(f.getScroll()){
                         InputMethodManager imm =  (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                         imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        }
                    }


                }
            });

        }

        private final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable ed) {

                if(gate) {

                    String s = ed.toString().replaceAll(" ", "").replace(",","." );

                    if(s.isEmpty()) {
                        editArray[0] = null;
                        update();
                        return;
                    }

                       try {
                            Double.parseDouble(s);

                        } catch (NumberFormatException e) {
                            return;
                        }


                        if (f.getId() == 8) new Temp(s, getAdapterPosition(), editArray, f);
                        else new Calcul(s, f.getId(), positor(getAdapterPosition()), editArray, f);

                        if(!s.contains("E")){
                            int last = s.length() - 1;
                            if(s.contains(".")) last = s.indexOf('.') - 1;

                            int c = 0;
                            StringBuilder sb = new StringBuilder(s);

                            int first = s.contains("-") ? 1 : 0;

                            for(int x = last; x > first; x--){
                                c++;
                                if(c == 3){
                                  c = 0;
                                  sb.insert(x, " ");
                                }
                            }

                            s = sb.toString().replace('.', f.getDivider());
                            editArray[positor(getAdapterPosition())] = s;

                            selector = editText.getSelectionStart() + (s.length() - ed.length());

                            update();
                        }

                }

                editText.setSelection(selector);

            }

        };
    }


    public void update(){
       final int first = layoutManager.findFirstVisibleItemPosition();
        final int last = layoutManager.findLastVisibleItemPosition();


        gate = false;

        boolean emp = isEmpty();

       for (int i = first; i <= last; ++i) {

            RecyclerView.ViewHolder h = recycler.findViewHolderForAdapterPosition(i);

               if (h.getItemViewType() == TYPE_ITEM) {

                   MyViewHolder holder = (MyViewHolder) h;
                   if (emp) holder.editText.getText().clear();
                   else holder.editText.setText(editArray[positor(i)]);
               }

        }

        gate = true;
    }


    private boolean isEmpty() {
        return editArray[0] == null;
    }

    class TitleHolder extends RecyclerView.ViewHolder{

        private TextView title;

        TitleHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tit);
        }


    }

}
