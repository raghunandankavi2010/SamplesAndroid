package innovates.com.pucho;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import innovates.com.pucho.models.User;
import innovates.com.pucho.utils.CheckNetwork;
import innovates.com.pucho.utils.UrlStrings;
import innovates.com.pucho.utils.Utility;
import innovates.com.pucho.widgets.MyProgressDialog;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.POST;

//import innovates.org.pucho.models.User;

/**
 * Created by Raghunandan on 17-05-2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private static final String TAG ="LoginActivity";
    private EditText user_name, pwd;
    private Button signup, login;
    private MyProgressDialog pd;

    /* Google plus sign in variable declarations */
    /* RequestCode for resolutions involving sign-in */
    private static final int RC_SIGN_IN = 9001;

    /* Keys for persisting instance variables in savedInstanceState */
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";

    /* Client for accessing Google APIs */
    private GoogleApiClient mGoogleApiClient;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    /* End of google plus singin variable declaration */



    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "User_ID";
    private SignInButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        checkPermission();
        /* Android Marshmallow has runtime permission .
                Those permissions marked dangerous are granted access by user at runtime */


        pd = new MyProgressDialog(this);
        pd.setTitle("Authenticating...");
        pd.setMessage("Please Wait");
        pd.setCancelable(false);

        user_name = (EditText) this.findViewById(R.id.userName);
        pwd = (EditText) this.findViewById(R.id.userPassword);

        login =(Button) this.findViewById(R.id.button1);
        login.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE);


        // Restore from saved instance state
        // [START restore_saved_instance_state]
        if (savedInstanceState != null) {
            mIsResolving = savedInstanceState.getBoolean(KEY_IS_RESOLVING);
            mShouldResolve = savedInstanceState.getBoolean(KEY_SHOULD_RESOLVE);
        }
        // [END restore_saved_instance_state]

        // Set up button click listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Large sign-in
        button = ((SignInButton) findViewById(R.id.sign_in_button));
        button.setSize(SignInButton.SIZE_WIDE);

        // Start with sign-in button disabled until sign-in either succeeds or fails
        findViewById(R.id.sign_in_button).setEnabled(true);

        // Set up view instances




    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

            case R.id.button1 :

                String uname,pass;
                uname = user_name.getText().toString();
                pass = pwd.getText().toString();
                if(Utility.isNotNull(uname) && Utility.isNotNull(pass))
                {
                    if(CheckNetwork.isInternetAvailable(LoginActivity.this))
                    new AuthenticateTask().execute(uname,pass);
                    else
                    Toast.makeText(getApplicationContext(),"Please check your Network Connection",Toast.LENGTH_SHORT).show();
                }

            break;

            case R.id.sign_in_button:

                // User clicked the sign-in button, so begin the sign-in process and automatically
                // attempt to resolve any errors that occur.
                // [START sign_in_clicked]
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

               /* mShouldResolve = true;
                mGoogleApiClient.connect();*/
                // [END sign_in_clicked]


                break;

        }
    }

    private void checkPermission() {
    int hasGetUserAccountsPermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.GET_ACCOUNTS);
    if (hasGetUserAccountsPermission != PackageManager.PERMISSION_GRANTED) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.GET_ACCOUNTS)) {
            showMessageOKCancel("You need to allow access to get user accounnts",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[] {Manifest.permission.GET_ACCOUNTS},
                                    REQUEST_CODE_ASK_PERMISSIONS);
                        }
                    });
            return;
        }
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[] {Manifest.permission.GET_ACCOUNTS},
                REQUEST_CODE_ASK_PERMISSIONS);
        return;
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

    private class AuthenticateTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params)
        {


            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            String response_json ="",id =null;
            int statusCode =0;


            //can catch a variety of wonderful things
            try {
                //constants
                URL url = new URL("http://192.168.1.4:18080/login");

                JSONObject jsonLogin = new JSONObject();
                //jsonLogin.put("fullName","Dinesh");
                jsonLogin.put("userName",params[0]);
                //jsonLogin.put("profession","Test");
                jsonLogin.put("password", params[1]);

                String message =  jsonLogin.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(20000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();
                statusCode = conn.getResponseCode();
                Log.i("status code", "..." + statusCode);

                if(statusCode == HttpURLConnection.HTTP_OK)
                {
                    response_json = Utility.getStringFromInputStream(is);
//                    JSONArray jarray = new JSONArray(response_json);
//                    JSONObject jsonObject = jarray.optJSONObject(0);
//                    id = jsonObject.optString("id");
                    //String fullName = jsonObject.optString("fullName");
                    //String userName = jsonObject.optString("username");

                    /* User user = new User();
                    user.setUserId(Integer.parseInt(id));
                    user.setUserName(userName);
                    user.setFullname(fullName);*/

                    Log.i("Response in json is ", "Can be null "+ response_json);
                    return id;
                }
                else
                    return id;


            }catch (Exception e)
            {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    if(os!=null && is!=null && conn!=null) {
                        os.close();
                        is.close();
                        conn.disconnect();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        public void onPreExecute() {
            pd.show();
        }

        @Override
        public void onPostExecute(String id) {

           // Toast.makeText(getApplicationContext(), "Successfull Login " + id, Toast.LENGTH_SHORT).show();
           if (pd != null && pd.isShowing() ) {
                pd.dismiss();
               if(!TextUtils.isEmpty(id)) {

                   editor = sharedPreferences.edit();
                   editor.putString("user_id", id);
                   editor.commit();
                   //Toast.makeText(getApplicationContext(), "Successfull Login " + id, Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(LoginActivity.this, QuestionFeed.class));
                   finish();

               }
            }
        }
    }

    // [START on_start_on_stop]
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    // [START on_save_instance_state]
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_RESOLVING, mIsResolving);
        outState.putBoolean(KEY_SHOULD_RESOLVE, mIsResolving);
    }
    // [END on_save_instance_state]

    // [START on_activity_result]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further errors.
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        }
    }
    // [END on_activity_result]

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            button.setVisibility(View.GONE);
            GoogleSignInAccount account = result.getSignInAccount();
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            String idToken = acct.getIdToken();

            Uri personPhoto = account.getPhotoUrl();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d(TAG, "onConnected:" + bundle);

/*

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            String personPhoto = currentPerson.getImage().getUrl();
            String personId = currentPerson.getId();

            String emailAddr = Plus.AccountApi.getAccountName(mGoogleApiClient);
            editor = sharedPreferences.edit();
            //editor.putString("user_id", personId);
            editor.putString("user_name", personName);
            editor.putString("user_email", emailAddr);
            editor.putString("user_profile_url", personPhoto);
            if(currentPerson.hasCover()) {
                Person.Cover.CoverPhoto cover = currentPerson.getCover().getCoverPhoto();
                String coverphoto = cover.getUrl();
                editor.putString("cover_photo", coverphoto);
            }
            editor.commit();

            User user = new User();
            user.setFullname(personName);
            user.setEmail(emailAddr);
            user.setProfileurl(personPhoto);
            user.setUserId(personId);
            Toast.makeText(getApplicationContext(),"Email Address "+emailAddr,Toast.LENGTH_SHORT).show();


            if(CheckNetwork.isInternetAvailable(LoginActivity.this)) {

               *//* Intent intent = new Intent(LoginActivity.this,SendToServer.class);
                intent.putExtra("user",user);
                startService(intent);
                startActivity(new Intent(LoginActivity.this, QuestionFeed.class));
                finish();*//*
                new SendDetails().execute(user);
                // new AuthenticateTask().execute(uname,pass);
            }
            else
                Toast.makeText(getApplicationContext(),"Please check your Network Connection",Toast.LENGTH_SHORT).show();
            // store person id in shared preferences and use the same to communicate with server
            // asking question and getting answer
            // you can get other details also if required
            // personPhoto will get the google plus photo
            // then use picasso to load the image.

         *//*   {
                "fullName": "Dinesh",
                    "profession": "Test",
                    "username": "Dinu",
                    "password":"xyz"
            }
*//*


            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        }


        // Show the signed-in UI
        updateUI(true);*/

    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost. The GoogleApiClient will automatically
        // attempt to re-connect. Any UI elements that depend on connection to Google APIs should
        // be hidden or disabled until onConnected is called again.
        Log.w(TAG, "onConnectionSuspended:" + i);
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
        int errorCode = connectionResult.getErrorCode();

        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
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
            Toast.makeText(this, ""+errorCode, Toast.LENGTH_SHORT).show();

            mShouldResolve = false;
            updateUI(false);
        }
    }

    private void updateUI(boolean isSignedIn) {

        if (isSignedIn) {

            // Show signed-in user's name
            //String name = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getDisplayName();
            //mStatus.setText(getString(R.string.signed_in_as, ""+name));

            // Set button visibility

            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        }
        else {
            //finish();
            // Show signed-out message


            // Set button visibility
         /*   findViewById(R.id.sign_in_button).setEnabled(true);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);*/
        }

    }

    private class SendDetails extends AsyncTask<User,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(User... voids) {
            User user =  voids[0];
            String sendString = null;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("userName", user.getFullname());
                jsonObject.put("email", user.getEmail());
                jsonObject.put("personalUrl", user.getProfileurl());
                jsonObject.put("externalUserId", user.getUserId());
                sendString = jsonObject.toString();
                OkHttpClient okHttpClient = new OkHttpClient();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UrlStrings.BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PostServer post_service = retrofit.create(PostServer.class);
                if (!TextUtils.isEmpty(sendString)) {
                    Log.i(",...........", "............");
                    String responseString = post_service.post(sendString).execute().body();
                    Log.i("LoginActivtiy", ""+responseString);
                /*Call<List<User>> responseString = post_service.post(sendString);
                //Log.i("Server Response ",responseString.get(0).getEmail());
                responseString.enqueue(new Callback<List<User>>() {


                    @Override
                    public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            // request successful (status code 200, 201)
                            List<User> result = response.body();
                            Log.i("dssfd",result.get(0).getEmail());
                        } else {
                            //request not successful (like 400,401,403 etc)
                            //Handle errors
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });*/

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface PostServer {
        @POST("/users")
        Call<String> post(@Body String string);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .requestIdToken(getString(R.string.cliendid))
                            .build();
                    button.setScopes(gso.getScopeArray());
                    // Permission Granted
                    // [START create_google_api_client]
                    // Build GoogleApiClient with access to basic profile
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                            .addScope(new Scope(Scopes.PLUS_LOGIN))
                            .build();
                    // [END create_google_api_client]
                    mGoogleApiClient.connect();
                } else {
                    // Permission Denied
                    Toast.makeText(LoginActivity.this, "User get accounts Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
