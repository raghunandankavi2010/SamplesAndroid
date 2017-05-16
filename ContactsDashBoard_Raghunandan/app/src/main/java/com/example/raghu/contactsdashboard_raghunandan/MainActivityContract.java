package com.example.raghu.contactsdashboard_raghunandan;

import java.util.List;

/**
 * Created by raghu on 12/5/17.
 */

public interface MainActivityContract {


    interface View {

        void showProgressBar(boolean show);

        void addItemAdapter(List<Contacts> contacts);

    }

    interface UserActionsListener {

        void getDetails(boolean resetCache);

        void onDestroy();
    }
}
