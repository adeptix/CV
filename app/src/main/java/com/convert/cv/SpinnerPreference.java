package com.convert.cv;


import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SpinnerPreference extends Preference {

    protected String[] entries = new String[0];
    protected String[] entryValues = new String[0];
    private final LayoutInflater layoutInflater;
    private int select = 0;



    public SpinnerPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpinnerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWidgetLayoutResource(R.layout.preference_spinner);
        layoutInflater = LayoutInflater.from(getContext());


        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SpinnerPreference);

        int entriesResId = ta.getResourceId(R.styleable.SpinnerPreference_entries, 0);
        if (entriesResId != 0) {
            entries = context.getResources().getStringArray(entriesResId);
        }

        int valuesResId = ta.getResourceId(R.styleable.SpinnerPreference_entryValues, 0);
        if (valuesResId != 0) {
            entryValues = context.getResources().getStringArray(valuesResId);
        }
        ta.recycle();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);

        String value = restorePersistedValue ? getPersistedString(null) : (String) defaultValue;
        for (int i = 0; i < entryValues.length; i++) {
            if (TextUtils.equals(entryValues[i], value)) {
                select = i;
                break;
            }
        }
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        final Spinner spinner = (Spinner) holder.findViewById(R.id.spinner);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

        spinner.setAdapter(new SpinnerAdapter(){

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = createDropDownView(position, parent);
                }
                bindDropDownView(position, convertView);
                return convertView;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return entries.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return getDropDownView(position, convertView, parent);
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

        });
        spinner.setSelection(select);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = position;
                persistString(entryValues[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private View createDropDownView(int position, ViewGroup parent){
        return layoutInflater.inflate(R.layout.spinner_dropdown_item, parent, false);
    }

    private void bindDropDownView(int position, View view){
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(entries[position]);
    }

    @Override
    protected boolean persistString(String value) {

        return super.persistString(value);

    }
}
