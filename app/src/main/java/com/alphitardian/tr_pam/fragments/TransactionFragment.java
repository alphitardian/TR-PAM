package com.alphitardian.tr_pam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alphitardian.tr_pam.R;
import com.alphitardian.tr_pam.adapters.TransactionListAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.TransactionHistoryList;
import com.alphitardian.tr_pam.models.TransactionOnHistory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<TransactionOnHistory> transactionOnHistories = new ArrayList<TransactionOnHistory>();

    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pref = this.getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.transaction_list);
        recyclerView.setHasFixedSize(true);

        progressBar.setVisibility(View.VISIBLE);

        getAllCrypto();
    }

    private void getAllCrypto() {
        String userId = pref.getString("userId", "");

        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<TransactionHistoryList> call = apiList.getTransactionHistory(userId);

        call.enqueue(new Callback<TransactionHistoryList>() {
            @Override
            public void onResponse(Call<TransactionHistoryList> call, Response<TransactionHistoryList> response) {
                if (response.isSuccessful()) {
                    TransactionHistoryList data = response.body();

                    Log.w("Response ", data.getStatus());

                    for (int i = 0; i < data.getData().size(); i++) {

                        TransactionOnHistory itemData = new TransactionOnHistory(
                                data.getData().get(i).getTransaction().getId(),
                                data.getData().get(i).getTransaction().getType(),
                                data.getData().get(i).getTransaction().getCoin(),
                                data.getData().get(i).getTransaction().getPrice(),
                                data.getData().get(i).getTransaction().getAmount(),
                                data.getData().get(i).getTransaction().getDate());

                        transactionOnHistories.add(itemData);
                    }
                    showRecyclerList();

                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getContext(), "Responses failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionHistoryList> call, Throwable t) {
                Log.w("Error failure input", t.getMessage());
            }
        });
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TransactionListAdapter listAdapter = new TransactionListAdapter(transactionOnHistories);
        recyclerView.setAdapter(listAdapter);
    }
}