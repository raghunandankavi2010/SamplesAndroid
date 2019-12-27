package com.example.raghu.databindingaac

import androidx.databinding.BaseObservable

/**
 * Created by raghu on 3/1/18.
 */
class BindableString : BaseObservable() {
    private var value: String? = null
    fun get(): String {
        return if (value != null) value!! else ""
    }

    fun set(value: String?) {
        if (this.value != value) {
            this.value = value
            notifyChange()
        }
    }

    val isEmpty: Boolean
        get() = value == null || value!!.isEmpty()
}