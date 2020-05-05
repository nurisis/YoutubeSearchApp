package com.hinuri.youtubesearchapp.util

import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide
import com.hinuri.youtubesearchapp.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception

@BindingAdapter("view_count_convert")
fun TextView.viewCountConverter(number: String?) {
    try {
        if(number == null) {
            text = ""
            return
        }

        text = "조회수 "+DecimalFormat("###,###").format(number.toLong()) +"회"
    }
    catch (e: Exception) {
        text = ""
    }
}

@BindingAdapter("html_text")
fun TextView.textFromHtml(html:String?) {
    if(html == null) text = ""

    text =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY)
        else
            Html.fromHtml(html)
}

@BindingAdapter("date_format")
fun TextView.convertDateFormat(date:String?) {
    if(date == null || date?.isEmpty()) {
        text = ""
        return
    }

    try {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        text = dateFormat.format(SimpleDateFormat("yyyy.MM.dd").parse(date.split("T")[0])) ?: date
    }catch (e:Exception) {
        Log.e("LOG>>", "date parser error : $e")
        text = date
    }
}

@BindingAdapter("date_convert")
fun TextView.convertDate(date:String?) {
    if(date == null || date?.isEmpty()) {
        text = ""
        return
    }

    val dateFormatted = SimpleDateFormat("yyyy-MM-dd").parse(date)
    val todayFormatted = Date()

    val diff = todayFormatted.time - dateFormatted.time

//    Log.d("LOG>>", "date : $date, formatted : $dateFormatted, todayFormatted : $todayFormatted, diff : $diff")

    val min = diff/(1000*60)
    if(min < 1) {
        text = "방금"
        return
    }

    val hour = min/60
    if(hour < 1){
        text = "${min}분 전"
        return
    }

    val day = hour/24
    if(day < 1) {
        text = "${hour}시간 전"
        return
    }

    val week = day/7
    if(week < 1) {
        text = "${day}일 전"
        return
    }

    val month = week/4
    if(month < 1) {
        text = "${week}주 전"
        return
    }

    val year = month/12
    if(year < 1) {
        text = "${month}개월 전"
        return
    }
    else
        text = "${year}년 전"
}

@BindingAdapter("imagePath")
fun ImageView.loadImage(@Nullable path:String?) {
    Glide.with(this)
        .load(path ?: "")
        .placeholder(R.color.whiteGrey)
        .error(R.color.whiteGrey)
        .centerCrop()
        .into(this)
}

@BindingConversion
fun convertBooleanToVisibility(visible:Boolean) :Int {
    return if(visible) View.VISIBLE else View.GONE
}