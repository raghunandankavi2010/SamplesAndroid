package com.india.innovates.pucho;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.india.innovates.pucho.events.LoginErrorEvent;
import com.india.innovates.pucho.events.LoginResponseEvent;
import com.india.innovates.pucho.events.LoginViaServerResponseEvent;
import com.india.innovates.pucho.models.SignupResponse;
import com.india.innovates.pucho.presenters.LoginPresenter;
import com.india.innovates.pucho.screen_contracts.LoginScreen;
import com.india.innovates.pucho.utils.CheckNetwork;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;




/**
 * Created by Raghunandan on 17-05-2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, LoginScreen {


    @Inject
    LoginPresenter loginPresenter;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private ProgressDialog pd;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /* End of google plus singin variable declaration */

    @Inject
    SharedPreferences sharedPreferences;

    private SignInButton btnSignIn;
    private Button facebookLogInButton;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ((PuchoApplication) getApplication()).component().inject(this);
        EventBus.getDefault().register(this);

        loginPresenter.setLoginScreen(this);

        pd = new ProgressDialog(this);
        pd.setTitle("PleaseWait...");
        pd.setMessage("Signing In...");
        pd.setCancelable(false);
        facebookLogInButton = (Button) findViewById(R.id.facebookLoginBtn);
        facebookLogInButton.setOnClickListener(this);

       // sharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        // Large sign-in
        btnSignIn = ((SignInButton) findViewById(R.id.sign_in_button));
        btnSignIn.setSize(SignInButton.SIZE_WIDE);
        btnSignIn.setOnClickListener(this);

        // Start with sign-in button disabled until sign-in either succeeds or fails
        btnSignIn.setEnabled(true);

        // Set up view instances

        if(checkPlayServices()) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .requestIdToken(getString(R.string.server_client_id))
                    .build();
            btnSignIn.setScopes(gso.getScopeArray());
            // [START create_google_api_client]
            // Build GoogleApiClient with access to basic profile
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }




    }

    @Subscribe
    public void onEvent(LoginErrorEvent event) {
        /* Do something */
        event.getErrorEvent().printStackTrace();
             if(pd!=null && pd.isShowing())
             {
                 pd.dismiss();
             }
    }

    @Subscribe
    public void onEvent(LoginResponseEvent event) {
        /* Do something */
        if(pd!=null && pd.isShowing())
        {
            pd.dismiss();
        }
        if(event.getLoginResponse().getSuccess()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id",event.getLoginResponse().getSuccessResponse().getSuccess().getId() );
            Log.d(TAG,"User email: "+event.getLoginResponse().getSuccessResponse().getSuccess().getUsername());
            Log.i(TAG,"User Name: "+event.getLoginResponse().getSuccessResponse().getSuccess().getFullName());
            editor.putString("user_email",event.getLoginResponse().getSuccessResponse().getSuccess().getUsername());
            editor.putString("user_name",event.getLoginResponse().getSuccessResponse().getSuccess().getFullName());
            editor.apply();
            /*Toast.makeText(this.getApplicationContext(),
                    "" + event.getLoginResponse().getSuccessResponse().getSuccess().getId()
                    , Toast.LENGTH_SHORT).show();*/
            Intent intent = new Intent(this, QuestionFeed.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this.getApplicationContext(),"Login Failed. Try again Later!",Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.sign_in_button:

                loginPresenter.OnGooglePlusSignButtonClicked();

                break;



        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    // [START on_start_on_stop]
    @Override
    protected void onStart() {
        super.onStart();

        /*if(mGoogleApiClient!=null) {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d(TAG, "Got cached sign-in");
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                //showProgressDialog();
                //loginPresenter.OnShowProgressDialog();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        //hideProgressDialog();
                        //loginPresenter.OnDismissProgressDialog();
                        handleSignInResult(googleSignInResult);
                    }

                });
            }
        }*/
    }



    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient!=null)
        mGoogleApiClient.disconnect();
    }


    // [START on_activity_result]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further errors.
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
        }
    }
    // [END on_activity_result]

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.getSignInAccount() + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI
            btnSignIn.setVisibility(View.INVISIBLE);
            GoogleSignInAccount account = result.getSignInAccount();
            assert account != null;
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            String idToken = account.getIdToken();

            Uri personPhoto = account.getPhotoUrl();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("photo_uri",personPhoto.toString());
            editor.apply();

            // Log.i(TAG,"Person Email"+personEmail);
            Log.d(TAG, "Person Name" + personName);
            Log.d(TAG, "Person id" + personId);
            Log.d(TAG, "Token Id " + idToken);
            Log.d(TAG,"Photo Uri" + personPhoto.toString());

            loginPresenter.setPersonName(personName);
            loginPresenter.setPersonId(personId);
            loginPresenter.setIdToken(idToken);
            loginPresenter.sendDetailsToserver();
            loginPresenter.OnShowProgressDialog();
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }


    // [START on_connection_failed]
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        } else {
            // Show the signed-out UI
            updateUI(false);
        }
    }
    // [END on_connection_failed]

    private void showErrorDialog(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
      /*  int errorCode = connectionResult.getErrorCode();

        if (GooglePlayServicesUtil.i(errorCode)) {
            // Show the default Google Play services error dialog which may still start an intent
            // on our behalf if the user can resolve the issue.
            GooglePlayServicesUtil.getErrorDialog(errorCode, this, RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mShouldResolve = false;
                            updateUI(false);
                        }
                    }).show();
        } else {
            // No default Google Play Services error, display a message to the user.
            // String errorString = getString(R.string.play_services_error_fmt, errorCode);
            Toast.makeText(this, "" + errorCode, Toast.LENGTH_SHORT).show();

            mShouldResolve = false;
            updateUI(false);
        }*/
    }

    private void updateUI(boolean isSignedIn) {

        if (isSignedIn) {

        } else {

        }

    }


    @Override
    public void launchActivity() {

        Intent intent = new Intent(this, QuestionFeed.class);
        startActivity(intent);
    }

    @Override
    public void showProgressDialog() {
        pd.show();
    }

    @Override
    public void dismissProgressDialog() {
        pd.dismiss();
    }


    @Override
    public void googlesignin() {

        if (CheckNetwork.isInternetAvailable(this)) {
            loginPresenter.CheckPermission_Email();
        } else {
            Toast.makeText(this.getApplicationContext(), "No Internet Connection!. Try again later", Toast.LENGTH_SHORT).show();
        }

    }


    /* Android Marshmallow has runtime permission .
         Those permissions marked dangerous are granted access by user at runtime
         GET_ACCOUNTS is one of them to fetch email address and other details you require the same*/

    public void checkPermissionForEmail() {
        int hasEmailPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (hasEmailPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)) {
                loginPresenter.OnShowRationale();
                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        mGoogleApiClient.connect();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void showRationale() {
        showMessageOKCancel("You need to allow access to Contacts",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS},
                                REQUEST_CODE_ASK_PERMISSIONS);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if(mGoogleApiClient!=null) {
                        mGoogleApiClient.connect();
                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(LoginActivity.this, "Email Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // sign up button clicked
    @Override
    public void signupClicked() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showEmailError() {
    }

    @Override
    public void showPasswordError() {
    }

    @Override
    public void onSuccess() {
        loginPresenter.postLoginDetails();
        //Toast.makeText(getApplicationContext(),"Login Crendentials Validated",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroyActivity();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(LoginViaServerResponseEvent event)
    {
        SignupResponse signupResponse = event.getLoginViaServerResponse();
        if(signupResponse.isSuccess())
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id",signupResponse.getSuccess_response().getId() );
            editor.putString("user_email",signupResponse.getSuccess_response().getEmail());
            editor.putString("user_name",signupResponse.getSuccess_response().getFullName());
            editor.apply();
            Log.d(TAG,"User_email :"+signupResponse.getSuccess_response().getEmail());
            Log.d(TAG,"User_Name :"+signupResponse.getSuccess_response().getFullName());
            Log.d(TAG,"User_id :"+signupResponse.getSuccess_response().getId());
            Intent intent = new Intent(this, QuestionFeed.class);
            startActivity(intent);
            finish();
        }

    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.d(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}

