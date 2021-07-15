package com.example.agecalulator

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                // show selected date
                val selectedDate = showDate(selectedDayOfMonth, selectedMonth, selectedYear)

                //format date and get date in minutes and days
                val (sdf, selectedDateInMinutes, selectedDateInDays) = retrieveSelectedDate(selectedDate)


                // format and get current date in minutes
                val (currentDateInMinutes, currentDateInDays) = retrieveCurrentDate(sdf)

                // calculate age in minutes and days and show in app
                displayCalculatedAges(
                    currentDateInMinutes,
                    selectedDateInMinutes,
                    currentDateInDays,
                    selectedDateInDays
                )

            },
            year,
            month,
            day)

        dpd.datePicker.maxDate = Date().time - 86400000 // number of milliseconds in one day
        dpd.show()
    }

    private fun displayCalculatedAges(
        currentDateInMinutes: Long,
        selectedDateInMinutes: Long,
        currentDateInDays: Long,
        selectedDateInDays: Long
    ) {
        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
        val differenceInDays = currentDateInDays - selectedDateInDays
        tvAgeInMinutes.text = differenceInMinutes.toString()
        tvAgeInDays.text = differenceInDays.toString()
    }

    private fun retrieveCurrentDate(sdf: SimpleDateFormat): Pair<Long, Long> {
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        val currentDateInMinutes = currentDate!!.time / 60000
        val currentDateInDays = currentDate.time / 86400000
        return Pair(currentDateInMinutes, currentDateInDays)
    }

    private fun retrieveSelectedDate(selectedDate: String): Triple<SimpleDateFormat, Long, Long> {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val theDate = sdf.parse(selectedDate)
        val selectedDateInMinutes = theDate!!.time / 60000
        val selectedDateInDays = theDate.time / 86400000
        return Triple(sdf, selectedDateInMinutes, selectedDateInDays)
    }

    private fun showDate(
        selectedDayOfMonth: Int,
        selectedMonth: Int,
        selectedYear: Int
    ): String {
        val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        tvSelectedDate.text = selectedDate
        return selectedDate
    }
}