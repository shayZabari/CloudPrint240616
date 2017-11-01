package com.example.shay_z.ht_fragmenttest01062016;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by shay_z on 03/06/2016.
 */
public class PrinterTypeFragment extends Fragment implements View.OnClickListener{
    private OptionsFragmentInterface interfaceImplementor ;


    ArrayAdapter arrayAdapter;
    ArrayList mailsChoosed,mailsTag;

    public void updateOptionsFragment(ArrayList mailsChoosed,ArrayList mailsTag) {
        this.mailsChoosed = mailsChoosed;
        this.mailsTag = mailsTag;
//        for (int i = 0; i < universityMails.length; i++) {
//            universityMailsArr.add(i, universityMails[i]);
//        }


    }




    @Override
    public void onClick(View view) {
        String email = (String) view.getTag();
        interfaceImplementor.optionFragmentInterfaceMailChoosed(email);
    }

    public interface OptionsFragmentInterface {
        public void optionFragmentInterfaceMailChoosed(String currentMail);
    }


    public PrinterTypeFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.interfaceImplementor = (OptionsFragmentInterface) context;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_printer_type, container, false);
        ArrayList<Button> buttonarr = new ArrayList<>();
        Button b = new Button(getContext());
//            HorizontalScrollView horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.llInsideScrollView);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        for (int i = 0; i < mailsChoosed.size(); i++) {
            Button tempButton = new Button(getContext());
            tempButton.setText(mailsTag.get(i).toString());
            tempButton.setTag(mailsChoosed.get(i));
            int a = 1;
            tempButton.setId(View.generateViewId());
            tempButton.setOnClickListener((View.OnClickListener) this);
             buttonarr.add(tempButton);

            ll.addView(tempButton, lp);
        }
//        listView = (ListView) view.findViewById(R.id.lvUniversityMails);
//        arrayAdapter = new ArrayAdapter((Context) interfaceImplementor, android.R.layout.simple_list_item_1, universityMailsArr);
//        listView.setAdapter(arrayAdapter);
//        return super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }


}
