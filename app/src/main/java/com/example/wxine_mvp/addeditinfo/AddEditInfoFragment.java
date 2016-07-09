package com.example.wxine_mvp.addeditinfo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wxine_mvp.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddEditInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditInfoFragment extends Fragment implements AddEditInfoContract.View  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARGUMENT_EDIT_INFO_ID = "EDIT_INFO_ID";

    private AddEditInfoContract.Presenter mPresenter;

    private TextView mTitle;

    private TextView mContent;





    public static AddEditInfoFragment newInstance(){return new AddEditInfoFragment();}


    public AddEditInfoFragment() {
        // Required empty public constructor
    }

    public void onResume(){
        super.onResume();
        mPresenter.start();
    }

    public void setPresenter(@NonNull AddEditInfoContract.Presenter presenter){
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mPresenter.saveInfo(mTitle.getText().toString(),mContent.getText().toString());
            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.addinfo_frag, container, false);
        mTitle = (TextView) root.findViewById(R.id.add_task_title);
        mContent = (TextView) root.findViewById(R.id.add_task_description);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    public void showEmptyInfoError(){
        Snackbar.make(mTitle,"TO DOs cannot be empty",Snackbar.LENGTH_LONG).show();
    }

    public void showInfosList(){
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    public void setTitle(String title){mTitle.setText(title);}

    public void setContent(String content){mContent.setText(content);}

    public boolean isActive(){return isAdded();}





}
