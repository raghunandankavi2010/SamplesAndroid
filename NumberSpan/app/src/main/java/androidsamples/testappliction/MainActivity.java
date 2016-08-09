package androidsamples.testappliction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity  {

    private EditText tv;


    int oldLineCount = -1;
    Boolean isMachineEdit = false;
    private TextWatcher tw = new TextWatcher() {
        public void afterTextChanged (Editable s) {

        }

        public void beforeTextChanged (CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged (CharSequence s, int start, int before, int count) {

            if (!isMachineEdit) {
                isMachineEdit = true;
                Boolean stringCleaned = false;
                int lastSelection = tv.getSelectionEnd();
                if (s.toString().contains("\n\n")) {
                    s = s.toString().replace("\n\n", "\n");
                    stringCleaned = true;
                }
                String[] splitter = s.toString().split("\n");

                Log.d("Splitter Length ", "" + splitter.length);

                int currentLength = splitter.length;
                if (!stringCleaned) {
                    if (currentLength == oldLineCount) {
                        isMachineEdit = false;
                        return;
                    }
                }
                oldLineCount = currentLength;

                CharSequence result = "";
                int index = 1;
                for (int i = 0; i < splitter.length; i++) {
                    Log.d("Index : " + i, splitter[i]);
                    if (!splitter[i].equals("") && !splitter[i].equals("\n")) {
                        SpannableString s1 = new SpannableString(splitter[i] + "\n");
                        //s1.setSpan(new BulletSpan(15),0,splitter[i].length(),0);
                        s1.setSpan(new NumberSpan(index++, 20, false), 0, splitter[i].length(), 0);
                        result = TextUtils.concat(result, s1);
                    }
                }
				/*if (s.toString().length() > 1) {
					if (s.toString().substring(s.toString().length() - 1).equals("\n")) {
						SpannableString s1 = new SpannableString("");
						s1.setSpan(new NumberingSpan(0, 20,index), 0, 0, 0);
						result = TextUtils.concat(result, s1);
					}
				}*/
                if (stringCleaned)
                {
                    //.	result = result.subSequence(0 , result.length() - 2);
                }
                tv.setText(result);
                if (!stringCleaned)
                {
                    if (tv.getText().toString().length() > 1)
                        tv.setSelection(lastSelection);
                    //tv.setSelection(lastSelection);
                }
                else
                {
                    tv.setSelection(result.length());
                }
                isMachineEdit = false;
            }
        }
    };



/*    public static Spannable replaceQuoteSpans(Spannable text) {

        QuoteSpan[] spans = text.getSpans(0, text.length(), QuoteSpan.class);

        for (QuoteSpan span : spans) {
            int start = text.getSpanStart(span);
            int end = text.getSpanEnd(span);
            text.removeSpan(span);
            text.setSpan(new CustomQuoteSpan(), start, end, 0);
        }

        return text;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medium_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        this.setSupportActionBar(toolbar);

        tv = (EditText) findViewById(R.id.editText);
        tv.addTextChangedListener(tw);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}
