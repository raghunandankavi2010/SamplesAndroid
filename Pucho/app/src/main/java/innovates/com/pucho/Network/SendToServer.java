package innovates.com.pucho.Network;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Raghunandan on 08-10-2015.
 */
public class SendToServer extends IntentService {

    // In case you require headers
    /*@Headers({
            "Accept: application/vnd.yourapi.v1.full+json",
            "User-Agent: Your-App-Name"
    })*/

    // In case you require dynamic header
    /*@GET("/tasks")
    List<Task> getTasks(@Header("Content-Range") String contentRange);
    Content-Range is the key. You can provide value as a string
    */

    // In case of static header
    /* @Headers("Cache-Control: max-age=640000")
    @GET("/tasks")
    List<Task> getTasks(); */

    /* You can use Observable as retrofit can return Observable.
       In the future we should by using this.
       Its time to dig deep into Rx Java
     */

    public SendToServer() {
        super("Send to Server");

    }


    /* Json Key value pairs     "fullName":"Dinesh",
            "profession":"Test",
            "username":"Dinu",
            "password":"xyz",
            "email": “”,
            "linkedin": “”,
            "personalUrl": “”
            “externalUserId”:””,
            “phoneNo”:””*/

    @Override
    protected void onHandleIntent(Intent intent) {







    }

}
