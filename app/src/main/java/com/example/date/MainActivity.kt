package com.example.date

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.date.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class MainActivity : AppCompatActivity(),View.OnClickListener {

    private var binding: ActivityMainBinding? = null
    // Две переменные для часов и минут
    private var hour = 0
    private var minute = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Нажатие на кнопку
        binding?.selectTime?.setOnClickListener(this)
    }

    // При тапе по кнопке вызываем Материал Тайм Пикер
    override fun onClick(view: View) {

        when(view.id) {
            R.id.selectTime -> {
                startTimePicker()
            }

        }
    }

    // Здесь Прописываем Метод Материал Тайм Пикера
    @SuppressLint("SetTextI18n")
    private fun startTimePicker () {

        // вызываем время на сейчас которое (Метод ниже)
        getTime()

        // Создаем объект Пикер
        val picker =
            MaterialTimePicker.Builder()
                    // В формате 24 часа
                .setTimeFormat(TimeFormat.CLOCK_24H)
                    // задаем в Пикер Актуальное время сейчас которое
                .setHour(hour)
                .setMinute(minute)
                    // Задаем название Пикера
                .setTitleText(getString(R.string.selectTime))
                .build()

        // Запускаем сам Пикер
        picker.show(supportFragmentManager, "startTimePicker")

        // Метод кнопки Ок на пикере
        picker.addOnPositiveButtonClickListener {
            // Записываем в текст вью Час и Минуты выбранные в пикере
            binding?.selected?.text = "${picker.hour}:${picker.minute}"
            // Записываем в текствью2 ...
            binding?.condition?.text = getString(R.string.selected)

        }

        // Метод нажатие на Кансел на пикере
        picker.addOnNegativeButtonClickListener {
           binding?.condition?.text = getString(R.string.canceled)
        }
        // Если на нижней панеле тапнули ВЕРНУТЬСЯ НАЗАД (Закрывается пикер)
        picker.addOnCancelListener {
            binding?.condition?.text = getString(R.string.previous)

        }
        // Все случаи закрытия Пикера
        picker.addOnDismissListener {
            binding?.cancel?.text = getString(R.string.closed)
        }

    }

    // Создаем метод, в котором будем получать Время СЕЙЧАС (Актуальное на сейчас)
    private fun getTime() {
        val time = Calendar.getInstance()
        hour = time.get(Calendar.HOUR)
        minute = time.get(Calendar.MINUTE)
    }

}