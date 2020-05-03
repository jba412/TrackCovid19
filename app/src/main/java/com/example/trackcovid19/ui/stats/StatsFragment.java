package com.example.trackcovid19.ui.stats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trackcovid19.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;

public class StatsFragment extends Fragment {

    private TextView tvTodayCases, tvTotalTests, tvAffectedCountries;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stats, container, false);


        tvTodayCases = root.findViewById(R.id.tvTodayCases);
        tvTotalTests = root.findViewById(R.id.tvTotalDeaths);
        tvAffectedCountries = root.findViewById(R.id.tvAffectedCountries);
        progressBar = root.findViewById(R.id.progress_circular_stats);

        getStatsData();

        return root;
    }

    private void getStatsData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://corona.lmao.ninja/v2/all";

        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTotalTests.setText(jsonObject.getString("tests"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error Response", error.toString());
            }
        });

        queue.add(stringRequest);
    }
}