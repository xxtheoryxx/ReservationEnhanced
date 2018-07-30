package sg.edu.rp.c346.reservation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    /*
    DatePicker dp;
    TimePicker tp;
    */
    Button btnConfirm;
    Button btnReset;
    EditText etDate;
    EditText etTime;
    EditText editName;
    EditText editNum;
    EditText editGroup;
    RadioButton rbSmoking;
    RadioButton rbNonSmoking;
    RadioGroup rgSmoking;

    int hour;
    int min;
    int year;
    int dayOfMonth;
    int month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnReset = findViewById(R.id.btnReset);
        editName = findViewById(R.id.editTextName);
        editNum = findViewById(R.id.editTextNum);
        editGroup = findViewById(R.id.editTextGroup);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);
        rbNonSmoking = findViewById(R.id.rbNonSmoking);
        rbSmoking = findViewById(R.id.rbSmoking);
        rgSmoking = findViewById(R.id.radioGroupSmoke);


/*
        int y = 2018;
        int m = 6;
        int doy = 1;
        dp.updateDate(y,m,doy);
        tp.setCurrentHour(20);
        tp.setCurrentMinute(30);

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker v, int hourOfDay, int minute) {
                hourOfDay = tp.getCurrentHour();
                Context context = getApplicationContext();
                if (hourOfDay < 8 || hourOfDay > 20){
                    tp.setCurrentHour(20);
                    tp.setCurrentMinute(00);
                    CharSequence text = "Reservation are only open from 8AM to 8.59PM.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
*/
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTime.getText().toString().equals("")){
                    hour = Calendar.getInstance().get(Calendar.HOUR);
                    min = Calendar.getInstance().get(Calendar.MINUTE);
                }else{
                    String[] value = etTime.getText().toString().split(" ");
                    String[] time = value[1].split(":");
                    hour = Integer.parseInt(time[0]);
                    min = Integer.parseInt(time[1]);
                }

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        etTime.setText("Time: "+hourOfDay+":"+minute);
                    }
                };
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,myTimeListener,
                        hour,min,true);
                myTimeDialog.show();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etDate.getText().toString().equals("")){
                    year = Calendar.getInstance().get(Calendar.YEAR);
                    month = Calendar.getInstance().get(Calendar.MONTH);
                    dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                }else{
                    String[] value = etDate.getText().toString().split(" ");
                    String[] date = value[1].split("/");
                    year = Integer.parseInt(date[2]);
                    month = Integer.parseInt(date[1]);
                    dayOfMonth = Integer.parseInt(date[0]);
                }


                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        etDate.setText("Date: "+dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog
                        (MainActivity.this,myDateListener,year,
                                month,dayOfMonth);
                myDateDialog.show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = getApplicationContext();

                if (editName.getText().toString().matches("") || editGroup.getText().toString().matches("")||
                 editNum.getText().toString().matches("") || etDate.getText().toString().matches("")||
                 etTime.getText().toString().matches("")){
                    CharSequence text = "Fill up all the text boxes required.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (!rbNonSmoking.isChecked() && !rbSmoking.isChecked()) {
                    CharSequence text = "Please set the table type.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    String name = editName.getText().toString();
                    String group = editGroup.getText().toString();
                    String date = etDate.getText().toString();
                    String time = etTime.getText().toString();

                    String smoke = "";
                    if (rbSmoking.isChecked()) {
                        smoke = "Yes";
                    } else if (rbNonSmoking.isChecked()) {
                        smoke = "No";
                    }

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                    myBuilder.setTitle("Confirm your Order");
                    myBuilder.setMessage("New Reservation\nName: "+name+"\nSmoking: "+smoke+"\nSize: "+group +"\n"+date+"\n"+time);
                    myBuilder.setCancelable(false);
                    myBuilder.setPositiveButton("CONFIRM", null);
                    myBuilder.setNegativeButton("CANCEL", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editGroup.setText("");
                editName.setText("");
                editNum.setText("");
                etTime.setText("");
                etDate.setText("");
                rbNonSmoking.setChecked(false);
                rbSmoking.setChecked(false);
                SharedPreferences preferences = getSharedPreferences("Date", Context.MODE_PRIVATE);
                SharedPreferences preferences2 = getSharedPreferences("Time", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                SharedPreferences.Editor editor2 = preferences2.edit();
                editor.clear().apply();
                editor2.clear().apply();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        String name = editName.getText().toString();
        String group = editGroup.getText().toString();
        String num = editNum.getText().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        int checkRad = rgSmoking.getCheckedRadioButtonId();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("Name", name);
        prefEdit.putString("Num", num);
        prefEdit.putString("Group", group);
        prefEdit.putString("Date", date);
        prefEdit.putString("Time", time);
        prefEdit.putInt("Smoking", checkRad);
        prefEdit.commit();

    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("Name", "");
        String group = prefs.getString("Group", "");
        String num = prefs.getString("Num", "");
        String date = prefs.getString("Date", "");
        String time = prefs.getString("Time", "");
        int smoking = prefs.getInt("Smoking", 0);
        editName.setText(name);
        editNum.setText(num);
        editGroup.setText(group);
        etDate.setText(date);
        etTime.setText(time);
        rgSmoking.check(smoking);
    }


}
