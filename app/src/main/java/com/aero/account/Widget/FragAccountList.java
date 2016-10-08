package com.aero.account.Widget;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero.account.Adapter.DividerItemDecoration;
import com.aero.account.Adapter.RecyclerViewAdapter;
import com.aero.account.R;

public class FragAccountList extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    public String account_name;

    @Override
    public void onClick(View v) {
        mCustomDialogEventListener.customDialogEvent(account_name);Log.e("user", "yangyao1");
    }

    public interface ICustomDialogEventListener {
        void customDialogEvent(String account_name);
    }

    private ICustomDialogEventListener mCustomDialogEventListener;

    public void setResultListener(ICustomDialogEventListener listener){
        this.mCustomDialogEventListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String strSql = getArguments().getString("strSql");
        account_name = "default";
        View rootView = inflater.inflate(R.layout.fragment_account_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(recyclerViewAdapter = new RecyclerViewAdapter(this.getContext(),strSql));
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {//data就是Account.name
                mCustomDialogEventListener.customDialogEvent(data);
                account_name = data;
                getFragmentManager().popBackStack();
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        return rootView;
    }

}
