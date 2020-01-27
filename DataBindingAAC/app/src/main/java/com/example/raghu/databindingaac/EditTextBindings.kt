package com.example.raghu.databindingaac

import android.text.TextUtils
import android.widget.EditText
import androidx.core.util.Pair
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.lifecycle.MutableLiveData

object EditTextBindings {
 /*   @JvmStatic
    @BindingConversion
    fun convertBindableToString(
            bindableString: BindableString): String {
        return bindableString.get()
    }*/

    @JvmStatic
    @BindingAdapter("binding")
    fun bindEditText(view: EditText,
                     bindableString: MutableLiveData<String>) {
        var pair: Pair<MutableLiveData<String>, TextWatcherAdapter>? = null
        if(view.getTag(R.id.binded)!=null){
             pair= view.getTag(R.id.binded) as Pair<MutableLiveData<String>, TextWatcherAdapter>
        }

        if (pair == null || pair.first != bindableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second)
            }
            val watcher: TextWatcherAdapter = object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence,
                                           start: Int, before: Int, count: Int) {
                    bindableString.value=(s.toString())
                }
            }
            view.setTag(R.id.binded,
                    Pair(bindableString, watcher))
            view.addTextChangedListener(watcher)
        }
        val newValue = bindableString.value
        if (view.text.toString() != newValue) {
            view.setText(newValue)
        }
    }
}