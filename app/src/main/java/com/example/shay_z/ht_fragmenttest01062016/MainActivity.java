package com.example.shay_z.ht_fragmenttest01062016;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SplashScreenFragment.SplashScreenInterface, UniversityId.comunicator, Paper.Communicator {
    ArrayList<String> arr = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView listView;
    FragmentManager fragmentManager;
    SplashScreenFragment splashScreenFragment;
    PrinterTypeFragment printerTypeFragment;
    String name;
    Toolbar toolbar;
    FloatingActionButton fab;
    Handler handler;
    MyBaseAdapter myBaseAdapter;
    ArrayList<File> files;
    File currentPath;
    Button btnDropBox;
    String searchInFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
    String[] folderChenger = {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(),
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath()
    };
    int countFolder;
    String[] printersKind = {"A4", "A3"};
    String[] fileExtention = {"xlsx", "docx", "doc", "pdf"};
    UniversityAndIdFragment universityAndIdFragment;
    int universityPosition;
    String universityName, studentId;
    //  4:15 AM 6/24/2016String[] universitysArrayList;
    static String[] universitysNames;
    ArrayList<String> mailsTag;
    int universityLogo;
    boolean guideCount = true;
    TestFragmentOptions testFragmentOptions;
    String printerChoosed;
    boolean firstTime = true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String[] universityChoosedAndTag;
    private String[] universityMails;
    private String guide;
    ProgressBar pb;

    // onStart
    @Override
    protected void onStart() {
        super.onStart();
        updatePaperProperties();
    }

    // OnCreate
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        mailsChoosed = new ArrayList<>();
        mailsTag = new ArrayList<>();
        firstTime = sharedPreferences.getBoolean("firstTime", true);
//        Toast.makeText(MainActivity.this, ""+firstTime, Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        splashScreenFragment = new SplashScreenFragment();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        files = new ArrayList<>(); // aeeayList of files
        myBaseAdapter = new MyBaseAdapter(MainActivity.this, files);
        currentPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        listView = (ListView) findViewById(R.id.fileListView);
        fab.setVisibility(View.GONE);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        //  18:03 7/6/2016  toolbar.setVisibility(View.GONE);

        universitysNames = getResources().getStringArray(R.array.university);

        // WHEN PRESS ON FLOATING ACTION BAR
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //  18:04 7/6/2016toolbar.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        openSplashScreen();

// after finish second thread it comes here
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                listView.setAdapter(myBaseAdapter);
                listView.requestLayout();
                myBaseAdapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

            }
        };
        // START SEARCHING FILES IN SECOND THREAD
        fillListFilesInBackground(searchInFolder);

        if (firstTime == false) {
            updateTextViews();
        }

        //END OF ON CREATE !!!
    }

    // updating 3 texts
    private void updateTextViews() {
        ((TextView) (findViewById(R.id.tvUniversityName))).setText("UNIVERSITY NAME = " + universityName);
        ((TextView) (findViewById(R.id.tvPrinterType))).setText("PRINTER CHOOSED = " + printerChoosed);
        ((TextView) (findViewById(R.id.tvStudentId))).setText("YOUR ID IS = " + studentId);
    }

    // GOES TO MAIL INTENT
    private void sendEmail() {

        ArrayList<Uri> uris = new ArrayList<>();
        for (int i = 0; i < myBaseAdapter.checkedList.size(); i++) {
            File item = files.get(myBaseAdapter.checkedList.get(i));
            uris.add(Uri.fromFile(item));
        }
        Log.d("shay", "uris size is " + uris.size());
        Intent mailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//        mailIntent.setData(Uri.parse("mailto:"));
        mailIntent.setType("plain/text");
//        String test = String.valueOf(studentId);
//        Toast.makeText(MainActivity.this, studentId+"", Toast.LENGTH_SHORT).show();
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, String.valueOf(studentId));
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{printerChoosed});
        mailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        startActivity(mailIntent);
    }

    // THREAD SEARCH FILES IN BACKGROUND
    private void fillListFilesInBackground(String folderPath) {
        files.clear();
        searchInFolder = folderPath;
        new Thread(new Runnable() {

            @Override
            public void run() {
                walk(searchInFolder);
                handler.sendEmptyMessage(0);
            }

            public void walk(String path) {
                File root = new File(path);

                File[] fileArr = root.listFiles();
                if (fileArr == null) {
                    return;
                }
                for (File f : fileArr) {
                    if (f.isDirectory()) {
                        walk(f.getAbsolutePath());
                    }
                    for (String extention : fileExtention) {
                        if (f.getName().endsWith(extention)) {

                            files.add(f);
                        }
                    }
                }
            }


        }).start();
    }

    //enable or disable toolbar and floating icon
    public void enableDisable(Boolean setVisable) {
        toolbar.setVisibility(View.VISIBLE); //testing after printer was null
        if (setVisable == false) {
//            findViewById(R.id.printer).setEnabled(false);
//            findViewById(R.id.guide).setEnabled(false);
            fab.setVisibility(View.INVISIBLE);
        } else if (setVisable == true) {
//            if (findViewById(R.id.printer) != null) {

//                findViewById(R.id.printer).setEnabled(true);
//                findViewById(R.id.guide).setEnabled(true);
            }
            fab.setVisibility(View.VISIBLE);
        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    //when click on one of the icons in menu bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.selectYourUniversity:
                openUniversityId();
                break;
        }
        if (id == R.id.CloudGuide) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(guide));
                startActivity(intent);

        }
//        if (id == R.id.printer) {
//            updatePaperProperties();
////            if (fragmentManager.findFragmentByTag("b") == null) {
////                findViewById(R.id.printer).setEnabled(false);
////                findViewById(R.id.guide).setEnabled(false);
////                PrinterTypeFragment printerTypeFragment = new PrinterTypeFragment();
////                printerTypeFragment.updateOptionsFragment(currentUniversityMailsArray,currentUniversityMailsTagArray);
////                fragmentManager = getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////
////                fragmentTransaction.add(R.id.placingFragmentOption, printerTypeFragment, "b").commit();
////            }
//
//        } else if (id == R.id.guide) {
////            enableDisable(false);
//            openUniversityId();
//        }
        return super.onOptionsItemSelected(item);
    }

    // OPEN SPLASH SCREEN FRAGMENT
    private void openSplashScreen() {
        splashScreenFragment = new SplashScreenFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, splashScreenFragment, "splashScreen");
        fragmentTransaction.commit();
    }

    // OPEN UNIVERSITYiD FRAGMENT
    private void openUniversityId() {
        UniversityId editNameDialogFragment = UniversityId.newInstance("shay");
        editNameDialogFragment.show(fragmentManager, "universityAndId");
    }

    // OPEN PAPER FRAGMENT
    private void updatePaperProperties() {
        retriveDataFile(); // test - retrive data.xml every time opening paper gui !
//        addRemovePaperLayoutPereferences(universityPosition); // test - add remove b4 load paper gui.
//        updateTags();
        //  17:46 7/6/2016  enableDisable(false); was null error.. removed for test
//        if (fragmentManager.findFragmentByTag("Paper") == null) { //  12:14 AM  6/24/2016 if first time
//            enableDisable(false);
            universityLogoNameAndMails(universityPosition, studentId);
            Paper paper = new Paper();
            //  5:00 AM 6/24/2016   paper.updateOptionsFragment(mailsChoosed, mailsTag, universityName);
            paper.updateProperties(universityPosition, universityMails);
//       DISABLE TEST !  »     fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.placingFragmentOption, paper, "Paper").commit();
//        }
    }

    // COME BACK AFTER SPLASH SCREEN INTERFACE !!
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void SplashScreenInteraction(String name) {
        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("splashScreen")).commit();
        if (sharedPreferences.getBoolean("firstTime", true)) {
            editor.putBoolean("firstTime", false).commit();
        }
        toolbar.setVisibility(View.VISIBLE);
        if (firstTime) {
            Toast.makeText(MainActivity.this, "first time !", Toast.LENGTH_SHORT).show();
//            testFragmentOptions = new TestFragmentOptions();//crash on first time. i disable temporary
//            fragmentManager.beginTransaction().replace(R.id.placingFragmentOption, testFragmentOptions, "testFragmentOptions").commit();//crash on first time. i disable temporary
//            enableDisable(false);
            return;
        } else {
            updateTextViews();
            retriveDataFile();
            Toast.makeText(MainActivity.this, "not first time", Toast.LENGTH_SHORT).show();
//            studentId = (sharedPreferences.getInt("studentId", -1));
            //  22:27 7/12/2016updatePaperProperties();
        }
    }

    // come back from Paper  !! radio button a3 a4 layout
    @Override
    public void PaperInterface(String printerChoosed) {
        //  16:20 7/6/2016fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("Paper")).commit();
        enableDisable(true);
//        ((MenuItem)(findViewById(R.id.printer))).setIcon(R.drawable.a4bwd);

        //  1:50 AM 6/24/2016Toast.makeText(MainActivity.this, "" + printerChoosed, Toast.LENGTH_SHORT).show();
        this.printerChoosed = printerChoosed;
        sharedPreferences.edit().putString("printerChoosed", printerChoosed).commit();
        //  1:05 AM 6/24/2016studentId = sharedPreferences.getInt("studentId", studentId);
        Toast.makeText(MainActivity.this, "" + printerChoosed, Toast.LENGTH_SHORT).show();
        updateTextViews();

    }

    //  after come backe from universityandId,make university mails ??? // mabe need to spleet this code
    //  UniversityId INTERFACE !!
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void UniversityInterface(int universityPosition, String studentId) {
        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("universityAndId")).commit();//  need change "blabla" 11:34 PM 6/23/2016
        this.universityPosition = universityPosition; //  add for test cause paper property not update 20:14  7/20/2016
        universityLogoNameAndMails(universityPosition, studentId);
//        universitysArrayList = getResources().getStringArray(R.array.university);
//        universityName = universitysArrayList[universityPosition];
        insertToDataFile();
        //  12:02 AM 6/24/2016 this.currentUniversityMailsTagArray = currentUniversityMailsTagArray;
        //  12:02 AM 6/24/2016this.currentUniversityMailsArray = currentUniversityMailsArray;
        updateTextViews(); // update 3 texts
        retriveDataFile(); // load parameters from data.xml
        updatePaperProperties();
        //  11:20 7/17/2016 addRemovePaperLayoutPereferences(universityPosition);
        ;

        //  16:25 7/6/2016  updatePaperProperties();
    }

//    // ADD REMOVE PROPERTIES OF PAPER RELATED TO UNIVERSITY
//    private void addRemovePaperLayoutPereferences(int universityPosition) {
//        switch (universityPosition) {
//            case 0:
//                findViewById(R.id.radioA3).setVisibility(View.GONE); // temp removed because a3 was missing
//                break;
//            case 1:
//                findViewById(R.id.radioA3).setVisibility(View.VISIBLE);
//                //  19:40 7/6/2016findViewById(R.id.radioA4).setVisibility(View.GONE);
//                break;
//            case 2:
//                findViewById(R.id.radioA3).setVisibility(View.GONE); // temp removed because a3 was missing
//                break;
//        }
//    }

    // Retrive University and Id from DATA.XML FILE to variables
    public void retriveDataFile() {
        universityPosition = sharedPreferences.getInt("universityPosition", 0);
        universityName = sharedPreferences.getString("universityName", universityName);
        universityLogo = sharedPreferences.getInt("universityLogo", universityLogo);
        //  2:17 AM 6/24/2016sharedPreferences.edit().putString("studentId","a").commit();
        studentId = sharedPreferences.getString("studentId", "123456789");
        printerChoosed = sharedPreferences.getString("printerChoosed", printerChoosed);


//        switch (universityPosition) {
//            case 0:
//                universityChoosedAndTag = getResources().getStringArray(R.array.telavivemails);
//                universityLogo = R.drawable.tau_logo;
//                break;
//            case 1:
//                universityChoosedAndTag = getResources().getStringArray(R.array.technionemails);
//                universityLogo = R.drawable.technion_logo;
//
//                break;
//        }
//        updateTags();
//        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("c")).commit();
        toolbar.setVisibility(View.VISIBLE);
//        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setLogo(universityLogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        updateTextViews();


// enableDisable(true);

//        Toast.makeText(MainActivity.this, "SETTINGS SAVED", Toast.LENGTH_SHORT).show();
    }

    // ADD PROPERTIES TO DATA.XML FILE
    private void insertToDataFile() {
        sharedPreferences.edit().putInt("universityPosition", universityPosition).commit();
        sharedPreferences.edit().putString("universityName", universityName).commit();
        sharedPreferences.edit().putString("studentId", studentId).commit();
        sharedPreferences.edit().putInt("universityLogo", universityLogo).commit();
    }

    // GOES TO University class and add properties of the current university choosed
    private void universityLogoNameAndMails(int universityPosition, String studentId) {
        University tempUniversity = new University().add(universityPosition, MainActivity.this);
        this.universityPosition = universityPosition;
        this.studentId = this.studentId;
        this.universityLogo = tempUniversity.logo;
        this.universityName = tempUniversity.universityName;
        this.universityMails = tempUniversity.universityMails;
        this.guide = tempUniversity.guide;
    }
}
