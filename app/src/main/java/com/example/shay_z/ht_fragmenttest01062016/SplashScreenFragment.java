package com.example.shay_z.ht_fragmenttest01062016;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SplashScreenInterface} interface
 * to handle interaction events.
 * Use the {@link SplashScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashScreenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String name;

    private SplashScreenInterface mListener;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.

     */
    // TODO: Rename and change types and number of parameters
    public static SplashScreenFragment newInstance(String param1, String param2) {
        SplashScreenFragment fragment = new SplashScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        Button button = (Button) view.findViewById(R.id.button);
        onButtonPressed(name); // TEMP SKIPPING SPLASHSCREEN

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                name = editText.getText().toString();
              // temp disabled  onButtonPressed(name);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String name) {
        if (mListener != null) {
            //  12:33 AM 6/24/2016mListener.SplashScreenInteraction(name);
            mListener.SplashScreenInteraction("test name");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SplashScreenInterface) {
            mListener = (SplashScreenInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SplashScreenInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SplashScreenInterface {
        // TODO: Update argument type and name
        void SplashScreenInteraction(String name);
    }
}
