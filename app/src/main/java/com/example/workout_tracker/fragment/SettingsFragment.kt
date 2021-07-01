package com.example.workout_tracker.fragment


import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.workout_tracker.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*


class SettingsFragment: Fragment(R.layout.fragment_settings),View.OnClickListener{
    var calendar = Calendar.getInstance()
    lateinit var btn : MaterialButton
    lateinit var cal : CalendarView

    var curDate :Int = calendar.get(Calendar.DAY_OF_MONTH);
    var Year : Int = calendar.get(Calendar.YEAR);
    var Month : Int= calendar.get(Calendar.MONTH);
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.fragment_settings, container, false)
        btn = v.findViewById(R.id.btn)
        cal = v.findViewById(R.id.calendarView)
        btn.setOnClickListener(this)
        return v
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cal.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
                curDate = dayOfMonth
                Year = year
                Month = month


            }
        })


    }

    override fun onClick(v: View?) {
        if (InputTitle.editText!!.text.toString().isNotEmpty() && InputDescription.editText!!.text.toString().isNotEmpty()){
            val beginTime = Calendar.getInstance()
            beginTime.set(Year, Month, curDate, );


            val endTime = Calendar.getInstance()
            endTime.set(Year, Month, curDate, );

            val intent = Intent(Intent.ACTION_INSERT)
            intent.data = CalendarContract.Events.CONTENT_URI;
            intent.putExtra(CalendarContract.Events.TITLE, InputTitle.editText!!.text.toString());
            intent.putExtra(CalendarContract.Events.DESCRIPTION, InputDescription.editText!!.text.toString());
            intent.putExtra(CalendarContract.Events.ALL_DAY, true);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis);
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis);
            if(context?.let { it1 -> intent.resolveActivity(it1.packageManager) } != null){
                startActivity(intent);
            }else{
                Toast.makeText(context, "There is no app that support this action", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "",
                    Toast.LENGTH_SHORT).show();
        }
    }


}