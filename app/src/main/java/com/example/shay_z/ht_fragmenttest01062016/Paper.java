package com.example.shay_z.ht_fragmenttest01062016;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shay_z on 6/14/2016.
 */
public class Paper extends Fragment implements View.OnClickListener {
    Communicator communicator;
    private int universityPosition;
    private String[] mailsChoosed;

    public void updateProperties(int universityPosition, String[] mailsChoosed) {
        this.universityPosition = universityPosition;
        this.mailsChoosed = mailsChoosed;
    }

    @Override
    public void onClick(View view) {
        if (mailsChoosed == null) {
            Toast.makeText(getContext(), "mailsChoosed = null", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().remove(this).commit();
        }
        currentPaper();
        saveCurrentMail();
    }

    public interface Communicator {
        public void PaperInterface(String printerChoosed);
    }

//    public void updateOptionsFragment(ArrayList currentUniversityMailsArray, ArrayList currentUniversityMailsTagArray, String universityName) {
//        this.currentUniversityMailsArray = currentUniversityMailsArray;
//        this.currentUniversityMailsTagArray = currentUniversityMailsTagArray;
//        this.universityName = universityName;
////        for (int i = 0; i < universityMails.length; i++) {
////            universityMailsArr.add(i, universityMails[i]);
////        }
//
//
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;

    }



    ArrayList<String> currentUniversityMailsArray, currentUniversityMailsTagArray;
    String universityName;


    Button button;
    Switch switchColor, switchDuplex;
    RadioButton radioA4, radioA3;
    String paperSize, paperColor, paperDuplex;
    RadioGroup radioGroup;
    String paperResault;
    String currentPaper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentPaper = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getString("printerChoosed", "a4bws");
        switch (getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getInt("universityPosition", 0)) {
            case 0:
                mailsChoosed = getResources().getStringArray(R.array.telavivemails);
                Toast.makeText(getContext(), "teststs on attach telaviv", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                mailsChoosed = getResources().getStringArray(R.array.technionemails);
        }
        //  10:02 AM 6/26/2016mailsChoosed = getResources().getStringArray(R.array.)
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paper, container, false);
        switchColor = (Switch) view.findViewById(R.id.switchColor);
        switchDuplex = (Switch) view.findViewById(R.id.switchDuplex);
        radioA3 = (RadioButton) view.findViewById(R.id.radioA3);
        radioA4 = (RadioButton) view.findViewById(R.id.radioA4);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        switchColor.setChecked(getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean("paperColor", false));
        switchDuplex.setChecked(getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean("paperDuplex", false));
        radioA3.setOnClickListener(this);
        radioA4.setOnClickListener(this);
        switchColor.setOnClickListener(this);
        switchDuplex.setOnClickListener(this);
        if (getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean("paperA4", true)) {

            radioA4.setChecked(true);
        } else {
            radioA3.setChecked(true);
        }
//        switch (universityPosition) {
//            case 0:
//                      (view.findViewById(R.id.radioA3)).setVisibility(View.INVISIBLE);
//        }
        currentPaper();  // testing
        saveCurrentMail(); // testing


//        button.setOnClickListener(new View.OnClickListener() {
//
//                                      @Override
//                                      public void onClick(View view) {
//                                          currentPaper();
//
//
////                int count = 0;
//
////                for (String tag : currentUniversityMailsTagArray) {
////                    if (tag.equals(paperResault)) {
////                communicator.PaperInterface(currentUniversityMailsArray.get(count));
////
////
////                    count++;
////
////                }
////            }
//
//                                      }
//                                  }

//        );
        return view;
    }
// go back to mainActivity and set the choosed mail !
    private void saveCurrentMail() {
        switch (paperResault) {
            case "a4bws":

                communicator.PaperInterface(mailsChoosed[0]);
                break;
            case "a4bwd":

                communicator.PaperInterface(mailsChoosed[1]);
                break;
            case "a4colors":

                communicator.PaperInterface(mailsChoosed[2]);
                break;
            case "a4colord":

                communicator.PaperInterface(mailsChoosed[3]);
                break;
            case "a3bws":
                communicator.PaperInterface(mailsChoosed[4]);
                break;
            case "a3bwd":
                communicator.PaperInterface(mailsChoosed[5]);
                break;
            case "a3colors":
                communicator.PaperInterface(mailsChoosed[6]);
                break;
            case "a3colord":
                try {
                communicator.PaperInterface(mailsChoosed[7]);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    private void currentPaper() {
        paperColor = switchColor.isChecked() ? "color" : "bw";
        paperDuplex = switchDuplex.isChecked() ? "d" : "s";
        int radioButton = radioGroup.getCheckedRadioButtonId();
        if (radioButton == radioA3.getId()) {
            paperSize = "a3";
        } else {
            paperSize = "a4";
        }
        // enable disable radioButtons related to universityPosition.
        switch (universityPosition) {
            case 0:
                radioA3.setVisibility(View.GONE);
                break;
            case 1:
                radioA3.setVisibility(View.VISIBLE);
                if (paperSize == "a3") {
                    switchDuplex.setChecked(false);
                    switchDuplex.setEnabled(false);
                    paperDuplex = switchDuplex.isChecked() ? "d" : "s";
                } else if (paperSize == "a4") {
                    switchDuplex.setEnabled(true);

                }
                break;
            case 2:
                radioA3.setVisibility(View.GONE);
                break;
        }

        paperResault = paperSize + paperColor + paperDuplex;
        //  6:21 PM 6/28/2016Toast.makeText(getContext(), "" + paperResault.toString(), Toast.LENGTH_SHORT).show();

        addToDataFile();
    }

    private void addToDataFile() {
        getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean("paperColor", switchColor.isChecked()).commit();
        getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean("paperDuplex", switchDuplex.isChecked()).commit();
        getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean("paperA4", false).commit();
        getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean("paperA4", true).commit();
    }
}
