package com.example.shay_z.ht_fragmenttest01062016;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by shay_z on 6/24/2016.
 */
public class University {
    String mails, universityName, guide;
    static String[] universitysNames = MainActivity.universitysNames;
    String[] universityMails;
    int logo;




    public  University add(int universityPosition,Context context) {

        switch (universityPosition) {
            case 0:
                universityName = universitysNames[universityPosition];
                universityMails = context.getResources().getStringArray(R.array.telavivemails);
                logo = R.drawable.tau_logo;
                guide = (context.getResources().getStringArray(R.array.guides)[universityPosition]);
                break;
            case 1:
                universityName = universitysNames[universityPosition];
                universityMails = context.getResources().getStringArray(R.array.technionemails);
                logo = R.drawable.technion_logo;
                guide = (context.getResources().getStringArray(R.array.guides)[universityPosition]);
                break;
            case 2:
                universityName = universitysNames[universityPosition];
                universityMails = context.getResources().getStringArray(R.array.herzliamails);
                logo = R.drawable.idc;
                guide = (context.getResources().getStringArray(R.array.guides)[universityPosition]);

        }
    return this;
    }

}


