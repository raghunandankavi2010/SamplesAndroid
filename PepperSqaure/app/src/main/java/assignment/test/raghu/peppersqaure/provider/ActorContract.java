package assignment.test.raghu.peppersqaure.provider;

/**
 * Created by Raghunandan on 22-11-2015.
 */
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for interacting with {@link ActorsProvider}.
 */
public final class ActorContract {

    public static final String QUERY_PARAMETER_DISTINCT = "distinct";



   // private String name,description,dob,country,height,spouse,children,image;
    public interface ActorColumns {
        String ACTOR_ID = "actordid";
        String ACTOR_NAME = "name";
        String ACTOR_DESCRIPTION = "description";
        String ACTOR_DOB = "dob";
        String ACTOR_COUNTRY = "country";
        String ACTOR_HEIGHT = "height";
        String ACTOR_SPOUSE = "spouse";
        String ACTOR_CHILDREN= "children";
        String ACTOR_IMAGE = "image";
        String ACTOR_FAV = "fav";


    }

    public static final String CONTENT_AUTHORITY = "assignment.test.raghu.peppersquare.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ACTORS = "actors";



    public static class Actors implements ActorColumns, BaseColumns {

        public static final String TABLE_MOVIES="HOLLYWOODACTORS";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTORS).build();


        /**
         * MIME type for Actors.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.actors";

        /** Default "ORDER BY" clause. */
        public static final String DEFAULT_SORT = BaseColumns._ID + " DESC";


        /** Build {@link Uri} for requested {@link #ACTOR_ID}. */
        public static Uri buildActorsUri(String actorId) {
            return CONTENT_URI.buildUpon().appendPath(actorId).build();
        }

        /** Read {@link #ACTOR_ID} from {@link Actors} {@link Uri}. */
        public static String getActorID(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    private ActorContract() {
        throw new AssertionError("No instances.");
    }
}