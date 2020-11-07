package com.example.qr_ticket.data;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.qr_ticket.R;

public class SpinnerDialog extends DialogFragment {

    ProgressDialog _dialog;

    public SpinnerDialog() {
        // use empty constructors. If something is needed use onCreate's
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        _dialog = new ProgressDialog(getActivity(), R.style.NewDialog);
        _dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _dialog.setIndeterminate(true);
        _dialog.setCancelable(false);
        return _dialog;
    }

}
