/*
    Corso IUM - AA 2019 - 2020
    65577 - Daniele Stochino
    Esercitazione Bonus
 */

package com.example.bonuslogin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {


    //We need some costum stuff here, such a costum Listener

    private Calendar date;
    private DatePickerFragmentListener listener;

    //We need the dialog to contain it
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        //if null set it to default values
        if ( getDate() == null){
            setDate(Calendar.getInstance());
            getDate().set(Calendar.YEAR,1992);
            getDate().set(Calendar.MONTH, Calendar.MARCH);
            getDate().set(Calendar.DAY_OF_MONTH,16);
        }

        //this getActivity gives context to the new object
        final DatePicker datePicker = new DatePicker(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Insertin the date view inside the builder
        builder.setView(datePicker);

        //builde.set return all the possible list of action i can make
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //inside here will set the numbers, then pass them to the edit text
                getDate().set(Calendar.YEAR, datePicker.getYear());
                getDate().set(Calendar.MONTH, datePicker.getMonth());
                getDate().set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                if(listener != null){
                    listener.onDatePickerFragmentOkButton(DatePickerFragment.this, getDate());
                }

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(listener != null){
                    listener.onDatePickerFragmentCancelButton(DatePickerFragment.this);
                }
            }
        });
        return builder.create();
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setOnDatePickerFragmentChanged(DatePickerFragmentListener listener){
        // set the costum listener as the lister of this class
        this.listener = listener;
    }

    public interface DatePickerFragmentListener{
        public void onDatePickerFragmentOkButton(DialogFragment dialog, Calendar date);
        public void onDatePickerFragmentCancelButton(DialogFragment dialog);
    }

}
