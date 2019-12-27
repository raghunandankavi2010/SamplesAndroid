package com.example.raghu.databindingaac

import android.widget.EditText
import androidx.core.util.Pair
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion

object EditTextBindings {
    @JvmStatic
    @BindingConversion
    fun convertBindableToString(
            bindableString: BindableString): String {
        return bindableString.get()
    }

    @JvmStatic
    @BindingAdapter("binding")
    fun bindEditText(view: EditText,
                     bindableString: BindableString) {
        var pair: Pair<BindableString, TextWatcherAdapter>? = null
        if(view.getTag(R.id.binded)!=null){
             pair= view.getTag(R.id.binded) as Pair<BindableString, TextWatcherAdapter>
        }

        if (pair == null || pair.first != bindableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second)
            }
            val watcher: TextWatcherAdapter = object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence,
                                           start: Int, before: Int, count: Int) {
                    bindableString.set(s.toString())
                }
            }
            view.setTag(R.id.binded,
                    Pair(bindableString, watcher))
            view.addTextChangedListener(watcher)
        }
        val newValue = bindableString.get()
        if (view.text.toString() != newValue) {
            view.setText(newValue)
        }
    }
}