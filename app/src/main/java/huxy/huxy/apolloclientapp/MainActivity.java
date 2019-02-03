package huxy.huxy.apolloclientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPosts();
        submitPost();
    }


    public void getPosts(){
        MyApolloClient.getMyApolloClient()
                .query(AllPostQuery.builder().build())
                .enqueue(new ApolloCall.Callback<AllPostQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AllPostQuery.Data> response) {

                        for (int i = 0;  i< response.data().allPosts().size(); i++){

                            System.out.println("Title --> "
                                    + response.data().allPosts().get(i).title()
                                    +" -- Description --> " + response.data().allPosts.get(i).description());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {

                    }
                });
    }

    public void submitPost(){

        MyApolloClient.getMyApolloClient().mutate(NewPostMutation
                .builder().title("Entertainment").description("The movie is fantastic.")
                .build()).enqueue(new ApolloCall.Callback<NewPostMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<NewPostMutation.Data> response) {

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(MainActivity.this, "Succesfully submited", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
}
