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

        text = context.getString(R.string.msg_video_view_count, DecimalFormat("###,###").format(number.toLong()))
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

@BindingAdapter("published_date_convert")
fun TextView.convertPublishedDate(date:String?) {
    if(date == null || date?.isEmpty()) {
        text = ""
        return
    }

    text = try {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        context.getString(R.string.msg_video_published_date, dateFormat.format(SimpleDateFormat("yyyy-MM-dd").parse(date.split("T")[0])) ?: "")
    }catch (e:Exception) {
        Log.e("LOG>>", "date parser error : $e")
        date
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

    val min = diff/(1000*60)
    if(min < 1) {
        text = context.getString(R.string.msg_time_convert_now)
        return
    }

    val hour = min/60
    if(hour < 1){
        text = context.getString(R.string.msg_time_convert_min, min.toString())
        return
    }

    val day = hour/24
    if(day < 1) {
        text = context.getString(R.string.msg_time_convert_hour, hour.toString())
        return
    }

    val week = day/7
    if(week < 1) {
        text = context.getString(R.string.msg_time_convert_day, day.toString())
        return
    }

    val month = week/4
    if(month < 1) {
        text = context.getString(R.string.msg_time_convert_week, week.toString())
        return
    }

    val year = month/12
    if(year < 1) {
        text = context.getString(R.string.msg_time_convert_month, month.toString())
        return
    }
    else
        text = context.getString(R.string.msg_time_convert_year, year.toString())
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