package com.example.splash.models;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.StringRequest;
import com.example.splash.DoctorDetalleActivity;
import com.example.splash.MainActivity;
import com.example.splash.ReservarActivity;
import com.example.splash.helpers.QueueUtils;

public class Doctores {
    public int id;
    public String first_name;
    public String last_name;
    public String url_image;
    public Doctores(int _id,String _first_name, String _last_name, String _url_image){
        this.id=_id;
        this.first_name=_first_name;
        this.last_name=_last_name;
        this.url_image=_url_image;
    }
    public static ArrayList getCollection(){
        ArrayList<Doctores> collection=new ArrayList<>();
        collection.add(new Doctores(1,"Pepe","Perez",""));
        collection.add(new Doctores(2,"Maria","Mesa",""));
        collection.add(new Doctores(3,"Alex","Quispe",""));
        return collection;
    }

    public static void postDoctorFromLocal(final QueueUtils.QueueObject o,
                                           final Doctores doctor,
                                           final ReservarActivity _Interface){
        String url = "http://127.0.0.1:8000/api/auth/doctor";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                 new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int a = 0;
                        a++;
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                //Datos a enviar
                Map<String,String> params = new HashMap<String, String>();
                params.put("nombresDoctor",doctor.first_name);
                params.put("apellidosDoctor",doctor.last_name);
                params.put("usuario_id","2");
                params.put("dni","12345678");
                params.put("telefono","123456789");
                return params;
            }
        };

        o.addToRequestQueue(stringRequest);
    }


    public static void injectDoctorFromCloud(final QueueUtils.QueueObject o,
                                             final Doctores doctor,
                                             final DoctorDetalleActivity _interface){
        String url = "https://reqres.in/api/users/"+doctor.id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("data")) {

                            try {
                                JSONObject objeto = response.getJSONObject("data");
                                doctor.first_name=objeto.getString("first_name");
                                doctor.last_name=objeto.getString("last_name");
                                doctor.url_image=objeto.getString("avatar");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            _interface.refresh(); // Esta función debemos implementarla
                            // en nuestro activity
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int a = 0;
                        a++;
                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }

    public static void injectDoctoresFromCloud(final QueueUtils.QueueObject o,
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
                                    doctores.add(new Doctores(o.getInt("id"),o.getString("first_name"),
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
                        int a = 0;
                        a++;
                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }
}
