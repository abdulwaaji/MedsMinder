package com.medsminder.view;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.medsminder.AlarmReceiver;
import com.medsminder.MedicViewModel;
import com.medsminder.R;
import com.medsminder.model.Medic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class AddNewItemFragment extends Fragment {
    private int  mHour, mMinute;
    Button button, saveButton, cancelButton;
    EditText et_diseaseName, et_medsName, et_medsIntakeQuantity, et_medsStock;
    String medsName, diseaseName, medsIntakeQuantitiy, medsUnit, medsColor, mschedule = "";
    int medsStock;
    TextView timeSlotsList;
    ArrayList<String> schedule;
    private MedicViewModel medicViewModel;


    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    int alarm_hour, alarm_minutes;


    public AddNewItemFragment() {
        // Required empty public constructor
    }

    public static AddNewItemFragment newInstance(String param1, String param2) {
        AddNewItemFragment fragment = new AddNewItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schedule = new ArrayList<>();
        medicViewModel = new ViewModelProvider(this).get(MedicViewModel.class);
        alarmManager = (AlarmManager) requireActivity().getSystemService(ALARM_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_item, container, false);

        timeSlotsList = view.findViewById(R.id.tv_time_slots);
        button = view.findViewById(R.id.btn_add_time_slot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setAlarm();
                showTimerPicker();


            }
        });

        et_medsName = view.findViewById(R.id.et_medicine_name);
        et_diseaseName = view.findViewById(R.id.et_disease_Name);
        et_medsIntakeQuantity = view.findViewById(R.id.et_medicine_quantitiy);
        et_medsStock = view.findViewById(R.id.et_stock_available);

        saveButton = view.findViewById(R.id.btn_save);
        cancelButton = view.findViewById(R.id.btn_discard);


        // spinner for selecting units of intake amount
        Spinner quantityspinner = view.findViewById(R.id.spinner_quantity_units_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.quantity_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityspinner.setAdapter(adapter);

        // spinner for selecting color to identify medicine
        Spinner colorSpinner = view.findViewById(R.id.spinner_color);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.colors_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medsName = et_medsName.getText().toString().trim();
                diseaseName = et_diseaseName.getText().toString().trim();
                medsIntakeQuantitiy = et_medsIntakeQuantity.getText().toString();
                if (!et_medsStock.getText().toString().isEmpty()) {
                    medsStock = Integer.parseInt(et_medsStock.getText().toString());
                } else {
                    medsStock = 0;
                }

                medsUnit = quantityspinner.getSelectedItem().toString();
                medsColor = colorSpinner.getSelectedItem().toString();

                try {
                    if (!medsName.isEmpty() || !diseaseName.isEmpty()
                            || !Objects.equals(diseaseName, "") || !Objects.equals(medsName, "")) {

                        medicViewModel.insert(new Medic(medsName, diseaseName, medsIntakeQuantitiy + medsUnit, medsColor, schedule, medsStock));

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainerView, new DashboardFragment())
                                .commit();
                    } else {
                        Toast.makeText(getContext(), "Please fill the details", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new DashboardFragment()).commit();
            }
        });

        return view;
    }


    public void showTimerPicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker alarmTimePicker, int hourOfDay,
                                          int minute) {
                        long timeL;
                        alarm_hour = alarmTimePicker.getCurrentHour();
                        alarm_minutes = alarmTimePicker.getCurrentMinute();
                        String time = alarm_hour + ":" + minute;
                        schedule.add(time);
                        mschedule = mschedule + " " + time;
                        timeSlotsList.setText(mschedule);


                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, alarm_hour);
                        calendar.set(Calendar.MINUTE, alarm_minutes);
                        timeL = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                        Intent intent = new Intent(getContext(), AlarmReceiver.class);

                        pendingIntent = PendingIntent.
                                getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                        if (System.currentTimeMillis() > timeL) {
                            if (calendar.AM_PM == 0)
                                timeL = timeL + (1000 * 60 * 60 * 12);
                            else
                                timeL = timeL + (1000 * 60 * 60 * 24);
                        }
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeL,  pendingIntent);

                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();


    }


}