package com.alphitardian.tr_pam.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alphitardian.tr_pam.R;
import com.alphitardian.tr_pam.adapters.MarketListAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.CryptoData;
import com.alphitardian.tr_pam.models.CryptoList;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;
    private ArrayList<CryptoData> cryptoData = new ArrayList<>();
    private EditText editTextSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.market_list);
        recyclerView.setHasFixedSize(true);

        getAllCrypto();
    }

    private void filter(String text) {
        ArrayList<CryptoData> filteredList = new ArrayList<>();

        for (CryptoData item : cryptoData) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MarketListAdapter listAdapter = new MarketListAdapter(filteredList);
        recyclerView.setAdapter(listAdapter);
    }

    private void getAllCrypto() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<CryptoList> call = apiList.getAllList();

        call.enqueue(new Callback<CryptoList>() {
            @Override
            public void onResponse(Call<CryptoList> call, Response<CryptoList> response) {
                if (response.isSuccessful()) {
                    CryptoList data = response.body();

                    Log.d("TAG", "onResponse: " + data.getData().size());

                    for (int i = 0; i < data.getData().size(); i++) {
                        CryptoData itemData = new CryptoData(
                                data.getData().get(i).getId(),
                                data.getData().get(i).getName(),
                                data.getData().get(i).getSymbol(),
                                data.getData().get(i).getLastUpdate(),
                                data.getData().get(i).getPrice());
                        cryptoData.add(itemData);
                    }


                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);

                    showRecyclerList();


                } else {
                    Toast.makeText(getContext(), "Responses failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CryptoList> call, Throwable t) {

            }
        });
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MarketListAdapter listAdapter = new MarketListAdapter(cryptoData);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

}