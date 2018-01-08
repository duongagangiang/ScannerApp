package com.example.admin.diemdanhsinhvien.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.admin.diemdanhsinhvien.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Admin on 12/19/2017.
 */

public class DatePickerFragment extends DialogFragment {
    private DatePicker datePicker;
    public interface DateDialogListener{
        String onFinishDialog(Date date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.datepicker,null);
        datePicker=(DatePicker)v.findViewById(R.id.datepicker);
        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Chọn ngày")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int nam=datePicker.getYear();
                                int thang=datePicker.getMonth();
                                int ngay=datePicker.getDayOfMonth();
                                Date date= new GregorianCalendar(nam,thang,ngay).getTime();
                                DateDialogListener activity=(DateDialogListener) getActivity();
                                activity.onFinishDialog(date);
                                dismiss();
                            }
                        }
                )
                .create();
    }
}
