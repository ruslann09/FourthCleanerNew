package com.cacheclean.cleanapp.cacheappclean.UserI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cacheclean.cleanapp.cacheappclean.R;

import java.util.List;

public class DevelopersSup extends AppCompatActivity {

    //here all users can send their mind for developers feedback

    public final String DEVELOPER_EMAIL = "masterclean2018@yandex.ru";

    private LinearLayout tools, myPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers_support);

        getSupportActionBar().hide ();

//        tools = (LinearLayout) findViewById(R.id.tools);
//        myPage = (LinearLayout) findViewById(R.id.my_page);

        ((Button) findViewById(R.id.sendFeedBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final EditText nameField = (EditText) findViewById(R.id.editUserName);
                    String name = nameField.getText().toString();

                    final EditText feedbackField = (EditText) findViewById(R.id.editFeedbackMessage);
                    String feedback = feedbackField.getText().toString();

                    final Spinner feedbackSpinner = (Spinner) findViewById(R.id.feedbackTypeSpinner);
                    String feedbackType = feedbackSpinner.getSelectedItem().toString();

                    if ((name == "" || name.length() < 1) || (feedback == "" || feedback.length() < 1)
                            || (feedbackType == "" || feedbackType.length() < 1)) {
                        Toast.makeText(getApplicationContext(), "Fill in all rows", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    shareToGMail(new String[] {DEVELOPER_EMAIL}, feedbackType, name + "\n" + feedback);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        ((Button) findViewById(R.id.clearAllRows)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameField = (EditText) findViewById(R.id.editUserName);
                nameField.setText("");
                final EditText feedbackField = (EditText) findViewById(R.id.editFeedbackMessage);
                feedbackField.setText("");
            }
        });
    }

    public void shareToGMail(String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = getApplicationContext ().getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        getApplicationContext().startActivity(emailIntent);
    }
}
