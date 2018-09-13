package com.martel.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;
    public String peliculas;
    public String idade;
    public String hora;
    public String dataa;
    public String obs;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String peliculas, String idade, String hora, String data, String obs) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.peliculas = peliculas;
        this.idade = idade;
        this.hora = hora;
        this.dataa = data;
        this.obs = obs;

    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String gettitle() {
        return title;
    }

    public String getbody() {
        return body;
    }

    public String getPeliculas() {
        return peliculas;
    }

    public String getIdade() {
        return idade;
    }

    public String getHora() {
        return hora;
    }

    public String getDataa() {
        return dataa;
    }

    public int getStarCount() {
        return starCount;
    }

    public String getObs() {
        return obs;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("peliculas", peliculas);
        result.put("idade", idade);
        result.put("hora", hora);
        result.put("dataa", dataa);
        result.put("obs", obs);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
