package example.raghunandan.databinding.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class FeedResponse {


    /*{
        "total": 7,
            "count": 7,
            "perPage": 10,
            "page": 1,
            "data": [{
        "active": true,
                "id": 2,
                "userId": 1,
                "title": "What does/did Germany do right?",
                "content": null,
                "upvote": 0,
                "downvote": 0,
                "shareCount": null,
                "audioFileUrl": null,
                "askedOn": "2015-04-30 08:28:32",
                "user": {
            "active": true,
                    "id": 1,
                    "fullName": "Harsh Mathur",
                    "profession": null,
                    "externalUserId": null,
                    "username": null,
                    "phone": "919599771751",
                    "email": null,
                    "linkedin": null,
                    "personalUrl": null,
                    "userEducations": [],
            "gcmUser": null
        },
        "answers": []
    },
}*/

    private int total, count, perPage, page;

    private List<FeedModel> data = new ArrayList<>();


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<FeedModel> getData() {
        return data;
    }

    public void setData(List<FeedModel> data) {
        this.data = data;
    }







}
