package com.example.multiautocompletetest


import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.MultiAutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), TextWatcher, MyClickableSpan.OnClickItem {

    private lateinit var tv: TextView
    private lateinit var inputEditText: MultiAutoCompleteTextView
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        tv = findViewById<TextView>(R.id.textView)
        inputEditText = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView1) as MultiAutoCompleteTextView
        val button = findViewById<Button>(R.id.button) as Button
        inputEditText.setTokenizer(UsernameTokenizer())
        inputEditText.addTextChangedListener(this)
        inputEditText.movementMethod = LinkMovementMethod.getInstance()

        button.setOnClickListener {
            val spannableStringBuilder = SpannableStringBuilder(inputEditText.text)
            val spans = (inputEditText.text as SpannableStringBuilder)

            val clickableSpan = spans.getSpans(0, inputEditText.text.length, ClickableSpan::class.java)

            clickableSpan.forEach {
                val start: Int = spans.getSpanStart(it)
                val end: Int = spans.getSpanEnd(it)
                spannableStringBuilder.setSpan(ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                Log.i("Start- end", "$start $end")
            }

            tv.text = spannableStringBuilder

        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        var cursor = start
        if (cursor >= s.length) cursor = s.length - 1
        if (isValidToken(s, cursor)) {
            val token: String = getToken(s, start)
            //Log.i("MainActivity","$token")
            lifecycleScope.launch() {
                val suggest = getList(token)
                arrayAdapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_dropdown_item_1line, suggest);
                inputEditText.setAdapter(arrayAdapter)
                arrayAdapter.notifyDataSetChanged()
            }

        }
    }

    override fun afterTextChanged(s: Editable) {

        val regex = "@(\\w+)(\\s)$"
        val pattern: Pattern = Pattern.compile(regex)
        //Retrieving the matcher object
        val matcher: Matcher = pattern.matcher(s)
        while (matcher.find()) {
            val clickableSpan = MyClickableSpan()
            clickableSpan.setOnClickListener(this@MainActivity)
            val start = matcher.start()
            val end = matcher.end() - 1 // space is also considered so need to do -1
            Log.i(".....", "${matcher.group()} $start $end")
            s.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            s.setSpan(ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        }

    }


    /**
     * Checks if the current word being edited is a valid token (e.g. starts with @ and has no spaces)
     * @param text - all text being edited in input
     * @param cursor - current position of text change
     * @return is valid
     */
    private fun isValidToken(text: CharSequence, cursor: Int): Boolean {

        for (i in cursor downTo 0) {
            if (text[i] == '@') return true
            if (text[i] == ' ') return false
        }
        return false
    }

    /**
     * Fetches the current token being edited - assumes valid token (use isValidToken to confirm)
     * @param text
     * @param cursor
     * @return
     */
    private fun getToken(text: CharSequence, cursor: Int): String {
        val start = findTokenStart(text, cursor)
        val end = findTokenEnd(text, cursor)
        // Log.i("Start- End:", "$start $end")
        return text.subSequence(start, end).toString()
    }

    /**
     * In the current input text, finds the start position of the current token (iterates backwards from current position
     * until finds the token prefix "@")
     * @param text - all text being edited in input
     * @param cursor - current position of text change
     * @return position of token start
     */
    private fun findTokenStart(text: CharSequence, cursor: Int): Int {
        var i = cursor
        while (i > 0 && text[i - 1] != '@') {
            i--
        }
        return i

    }

    /**
     * In the current input text, finds the position of the end of the current token (iterates forwards from current
     * position
     * until finds the the end, e.g. a space or end of all input)
     * @param text - all text being edited in input
     * @param cursor - current position of text change
     * @return position of token end
     */
    private fun findTokenEnd(text: CharSequence, cursor: Int): Int {
        var i = cursor
        val len = text.length
        while (i < len && text[i] != ' ' && text[i] != ',' && text[i] != '.') {
            i++
        }
        return i
    }

    suspend fun getList(token: String): List<String> {
        delay(1000L)
        val list: List<String> = object : ArrayList<String>() {
            init {
                add("Abc")
                add("Bdfdf")
                add("Cfdfdf")
            }
        }
        return list.filter { it.startsWith(token) }
        // it.contains(token)
        // return list
    }

    override fun onClick(clickedItem: String) {
        Toast.makeText(this@MainActivity.applicationContext, clickedItem, Toast.LENGTH_SHORT).show()
    }


}