package assignment.com.raghu.androdiassignment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import assignment.com.raghu.androdiassignment.dagger.modules.components.ActivityComponent;
import assignment.com.raghu.androdiassignment.presenters.ForgotPasswordPresenterContract;

public class ForgotPassword extends BaseActivity<ForgotPasswordPresenterContract.Presenter> implements ForgotPasswordPresenterContract.View {

    private EditText phnumber;
    private ProgressBar pb;
    private ConstraintLayout rootview;
    private Button reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        phnumber = (EditText) this.findViewById(R.id.phnumber);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        rootview = (ConstraintLayout) findViewById(R.id.rootview);
        reset = (Button) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.resetPassword(phnumber.getText().toString().trim());
            }
        });


    }

    @Override
    protected void injectFrom(ActivityComponent activityComponent) {

        activityComponent.inject(this);
    }


    @Override
    public void showProgress(boolean active) {

        if (active) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSnackBar(String message) {

        Snackbar.make(rootview, message, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void phoneError(String message) {
        phnumber.setError(message);
    }


    @Override
    public void passwordReset(boolean check, String password) {

        if (check) {
            sendNotification(password);
        }

    }

    private void sendNotification(String password) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        int notifyID = 1;


        // Sets an ID for the notification, so it can be updated.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

           // The id of the channel.
            String id = "my_channel_01";
            // The user-visible name of the channel.
            CharSequence name = "notification";
           // The user-visible description of the channel.
            String description = "Retrieve password";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
           // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            // The id of the channel.
            String CHANNEL_ID = "my_channel_01";
            // Create a notification and set the notification channel.
            Notification notification = new Notification.Builder(ForgotPassword.this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(password)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId(CHANNEL_ID)
                    .build();
             // Issue the notification.
            mNotificationManager.notify(notifyID, notification);
        } else {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(password)
                    .setAutoCancel(true);


            final Notification notification = builder.build();
            mNotificationManager.notify(notifyID, notification);
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
