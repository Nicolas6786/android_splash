package com.example.splash.models;

import java.util.ArrayList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.splash.MainActivity;
import com.example.splash.helpers.QueueUtils;

public class Doctores {
    public String first_name;
    public String last_name;
    public String url_image;
    public Doctores(String _first_name, String _last_name, String _url_image){
        this.first_name=_first_name;
        this.last_name=_last_name;
        this.url_image=_url_image;
    }
    public static ArrayList getCollection(){
        ArrayList<Doctores> collection=new ArrayList<>();
        collection.add(new Doctores("Pepe","Perez",""));
        collection.add(new Doctores("Maria","Mesa",""));
        collection.add(new Doctores("Alex","Quispe",""));
        return collection;
    }

    public static void injectContactsFromCloud(final QueueUtils.QueueObject o,
                                               final ArrayList<Doctores> doctores,
                                               final MainActivity _interface) {
        String url = "http://fipo.equisd.com/api/users.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("objects")) {

                            try {
                                JSONArray list = response.getJSONArray("objects");
                                for (int i=0; i < list.length(); i++) {
                                    JSONObject o = list.getJSONObject(i);
                                    doctores.add(new Doctores(o.getString("first_name"),
                                            o.getString("last_name"),o.getString("avatar")));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            _interface.refreshList(); // Esta función debemos implementarla
                            // en nuestro activity
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }
}
