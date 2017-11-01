package com.example.shay_z.ht_fragmenttest01062016;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by shay_z on 03/06/2016.
 */
public class UniversityAndIdFragment extends android.support.v4.app.DialogFragment implements AdapterView.OnItemSelectedListener{
private comunicator universityAndIdInterface;

    public interface comunicator {
        public void UniversityAndIdInterface(int universityPosition, int studentIdNumber, ArrayList universityChoosed,  ArrayList mailTag,int universityLogo);
    }
    Spinner spinner;
    ArrayAdapter adapter;
    Button button;
    int universityPosition,studentIdNumber;
    EditText editText;
    String[] universityChoosedAndTag;
    ArrayList mailsChoosed;
    ArrayList mailsTag;
    int universityLogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_university_and_id, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        button = (Button) view.findViewById(R.id.btnEnter);
        editText = (EditText) view.findViewById(R.id.etStudentId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentIdNumber = Integer.parseInt(editText.getText().toString());
                universityAndIdInterface.UniversityAndIdInterface(universityPosition,studentIdNumber, mailsChoosed, mailsTag,universityLogo);
            }
        });
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         adapter=ArrayAdapter.createFromResource(getContext(),R.array.university,android.R.layout.simple_list_item_1);
        this.universityAndIdInterface = (comunicator) activity;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        universityPosition = i;
        mailsTag = new ArrayList();
        mailsChoosed = new ArrayList();
        switch (universityPosition) {
            case 0:
                universityChoosedAndTag = getResources().getStringArray(R.array.telavivemails);
                universityLogo = R.drawable.tau_logo;
                break;
            case 1:
                universityChoosedAndTag = getResources().getStringArray(R.array.technionemails);
                universityLogo = R.drawable.technion_logo;
                break;

        }
        for (String u : universityChoosedAndTag) {
            mailsChoosed.add(u.substring(0, u.indexOf(" ")));
            Log.d("shay","first "+u.substring(0, u.indexOf(" ")));
            mailsTag.add(u.substring(u.indexOf(" "),u.length()));
            Log.d("shay","tag "+u.substring(u.indexOf(" "),u.length()));

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
