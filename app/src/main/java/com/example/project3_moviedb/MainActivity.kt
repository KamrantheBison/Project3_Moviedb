package com.example.project3_moviedb

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray


private const val TAG = "MainActivity"
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MainActivity : AppCompatActivity() {

    lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovies = findViewById(R.id.rvMovies)
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client.get(
            "https://api.themoviedb.org/3/movie/now_playing",
            params,
            object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    Log.d(TAG, "onSuccess")

                    val resultsJSON = json.jsonObject.get("results") as JSONArray
                    val moviesRawJSON = resultsJSON.toString()

                    val gson = Gson()
                    val movieListType = object : TypeToken<List<Movie>>() {}.type
                    val movies: List<Movie> = gson.fromJson(moviesRawJSON, movieListType)

                    rvMovies.adapter = MovieAdapter(movies)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    Log.e(TAG, "onFailure $statusCode: $errorResponse")
                }
            }
        )
    }
}