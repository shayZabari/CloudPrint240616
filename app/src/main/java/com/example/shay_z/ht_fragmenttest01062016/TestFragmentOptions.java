package com.example.shay_z.ht_fragmenttest01062016;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * Created by shay_z on 6/13/2016.
 */ //    NOT ACTIVE !!!!
public class TestFragmentOptions extends Fragment implements AdapterView.OnItemSelectedListener {
    @Nullable
    Spinner spUniversity;
    int StudentNumber;
    EditText editText;
    ArrayAdapter adapter;
    private comunicator interfaceComunicator;
    int universityPosition;
    ArrayList mailsTag, mailsChoosed;
    int universityLogo;
    String[] universityChoosedAndTag;
    Button btnSave;
    String testStudentNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.options_fragment_test, container, false);
        spUniversity = (Spinner) view.findViewById(R.id.spUniversity);
        spUniversity.setOnItemSelectedListener(this);
        spUniversity.setAdapter(adapter);
        editText = (EditText) view.findViewById(R.id.etStudentNumber);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 StudentNumber = (Integer.parseInt(editText.getText().toString()));
                getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).edit().putInt("studentId", StudentNumber).commit();
                getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).edit().putString("universityName",(getResources().getStringArray(R.array.university)[universityPosition])).commit();
                getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).edit().putInt("universityLogo",universityLogo).commit();
//        etStudentNumber = Integer.parseInt(String.valueOf((EditText) view.findViewById(R.id.etStudentNumber)));
                interfaceComunicator.testFragmentOptionsInterface(universityPosition,StudentNumber,mailsChoosed,mailsTag,universityLogo);
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        universityPosition = i;
        getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).edit().putInt("universityPosition", universityPosition).commit();
        mailsChoosed = new ArrayList();
        mailsTag = new ArrayList();
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
            mailsTag.add(u.substring(u.indexOf(" ")+1, u.length()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface comunicator {
        public void testFragmentOptionsInterface(int universityPosition, int studentIdNumber, ArrayList mailsChoosed,  ArrayList mailsTag,int universityLogo);
    }

    public TestFragmentOptions() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.university, android.R.layout.simple_list_item_1);
        this.interfaceComunicator = (comunicator) activity;
    }
}
