package com.wasem.mysteriousquestions.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.wasem.mysteriousquestions.R;
import com.wasem.mysteriousquestions.databinding.FragmentDialogBinding;


public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_MSG = "msg";
    private static final String ARG_ANS_LOGO = "logo";

    private String title;
    private String msg;
    private int logo;
    DialogInteractionListener listener;

    public DialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DialogInteractionListener)
            listener = (DialogInteractionListener) context;
    }

    public static DialogFragment newInstance(String title, String msg, int logo) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MSG, msg);
        args.putInt(ARG_ANS_LOGO, logo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            msg = getArguments().getString(ARG_MSG);
            logo = getArguments().getInt(ARG_ANS_LOGO);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FragmentDialogBinding binding = FragmentDialogBinding.inflate(getLayoutInflater());
        binding.tvDialogTitle.setText(title);
        binding.tvDialogMsg.setText(msg);
        binding.imgAnswerLogo.setImageResource(logo);

        binding.positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOkButtonClicked();
                dismiss();
            }
        });
        builder.create().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.create().getWindow().setDimAmount(0);
        builder.setView(binding.getRoot());
        return builder.create();
    }

}