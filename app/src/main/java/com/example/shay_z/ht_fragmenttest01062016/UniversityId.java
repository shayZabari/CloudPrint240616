package com.example.shay_z.ht_fragmenttest01062016;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by shay_z on 6/20/2016.
 */
public class UniversityId extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText mEditText;
    Spinner spUniversity;
    String studentId;
    EditText editText;
    ArrayAdapter adapter;
    private comunicator interfaceComunicator;
    int universityPosition;
    ArrayList mailsTag, mailsChoosed;
    int universityLogo;

    Button btnSave;
    String testStudentNumber;
    public void EditNameDialogFragment() {

    }
    public interface comunicator{
        public void UniversityInterface(int universityPosition, String studentIdNumber);
    }

    public static UniversityId newInstance(String title) {
        UniversityId frag = new UniversityId();
        Bundle args = new Bundle();
        args.putString("title",title);

        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.options_fragment_test, container);
        spUniversity = (Spinner) view.findViewById(R.id.spUniversity);
        spUniversity.setOnItemSelectedListener(this);
        spUniversity.setAdapter(adapter);
        editText = (EditText) view.findViewById(R.id.etStudentNumber);
        editText.setText(getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).getString("studentId","000"));
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentId = (editText.getText().toString());
                getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).edit().putString("studentId", studentId).commit();
                getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).edit().putString("universityName",(getResources().getStringArray(R.array.university)[universityPosition])).commit();
                getContext().getSharedPreferences("data",getContext().MODE_PRIVATE).edit().putInt("universityLogo",universityLogo).commit();
//        studentId = Integer.parseInt(String.valueOf((EditText) view.findViewById(R.id.etStudentNumber)));
                interfaceComunicator.UniversityInterface(universityPosition, studentId);
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        setCancelable(false);

        String title = getArguments().getString("title", "enter name");
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        universityPosition = i;
        mailsChoosed = new ArrayList();
        mailsTag = new ArrayList();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.university, android.R.layout.simple_list_item_1);
        this.interfaceComunicator = (comunicator) context;
    }
}
