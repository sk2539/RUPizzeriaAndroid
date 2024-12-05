package com.example.rupizzeriaandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

public class MenuPopup extends DialogFragment {

    /**
     * Called to create the view hierarchy for this fragment.
     * Inflates the layout and sets up the UI components.
     *
     * @param inflater  LayoutInflater object used to inflate the fragment's view.
     * @param container The parent view group into which the fragment's view will be added.
     * @param savedInstanceState If non-null, this fragment is being re-created from a previous saved state.
     * @return The created view for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menupopup, container, false);
        Button closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dismiss());
        return view;
    }
}
