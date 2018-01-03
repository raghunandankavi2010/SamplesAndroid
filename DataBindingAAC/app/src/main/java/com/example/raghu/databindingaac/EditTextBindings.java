package com.example.raghu.databindingaac;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.v4.util.Pair;
import android.widget.EditText;

public class EditTextBindings
{

    @BindingConversion
    public static String convertBindableToString(
            BindableString bindableString) {
        return bindableString.get();
    }

    @BindingAdapter({"app:binding"})
    public static void bindEditText(EditText view,
                                    final BindableString bindableString) {
        Pair<BindableString, TextWatcherAdapter> pair =
                (Pair) view.getTag(R.id.binded);
        if (pair == null || pair.first != bindableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second);
            }
            TextWatcherAdapter watcher = new TextWatcherAdapter() {
                public void onTextChanged(CharSequence s,
                                          int start, int before, int count) {
                    bindableString.set(s.toString());
                }
            };
            view.setTag(R.id.binded,
                    new Pair<>(bindableString, watcher));
            view.addTextChangedListener(watcher);
        }
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }
}