package com.homerianreyes.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("rUP9nqK34vF2tnolRu8p2HHJ4Vyoqp4hsfobTAlZ")
                // if defined
                .clientKey("Y9mxpCODUJLbCNZhLgGh6RBSNiyRnc5cpZfpQiQT")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
