package innovates.com.pucho.utils;


/**
 * Created by Tamil on 4/28/2015.
 */
public class UrlStrings {

    //Base URL of Backend Server
    public static final String BASE_URL = "http://128.199.236.130:8080";

    //User related APIs
    public static final String LOGIN_SVC_PATH = "/users";

    public static final String SEARCH_SVC_PATH ="/{id}/search";

    //User related APIs
    public static final String USERNAME_EXISTS = "/users?username=";

    public static final String SIGNUP_USER = "/users/";

    public static final String USER_EDUCATION = "/users/{user_id}/user_educations/{id}";

    //General Listing APIs
    public static final String LANGUAGES = "/languages";

    public static final String GET_EDUCATIONS = "/educations";

    public static final String SUBJECT_AREAS = "/areas";

    //Question & Related APIs
    public static final String SAVE_QUESTION = "/questions/";

    public static final String ANSWER_QUESTION = "http://128.199.236.130:8080/questions/1/answers";


    /*GET
        GET     /questions
        GET     /questions/{id}
        GET     /questions/{question_id}/answers
        GET     /questions/{question_id}/answers/{id}


http://52.74.86.161:8080/questions?page=1&per_page=10&
      */
    public static final String  QUESTIONS = "/questions";
    public static final String  QUESTION_BY_ID = QUESTIONS+"/{id}";
    public static final String  ANSWERS = QUESTION_BY_ID+"/answers";
    public static final String  ANSWERS_BY_ID = ANSWERS+"/{id}";

    /*POST / PUT
        POST    /questions  -- QUESTIONS
        POST    /questions/{question_id}/answers -- ANSWERS
        PUT     /questions/{id} -- QUESTION_BY_ID
        PUT     /questions/{question_id}/answers/{id}  --ANSWERS_BY_ID
    */





}
