package test.raghu.testdifferentlanguage;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.append(" Raghu");

        final List<FontListParser.SystemFont> fonts = FontListParser.safelyGetSystemFonts();
        String[] items = new String[fonts.size()];
        for (int i = 0; i < fonts.size(); i++) {
            items[i] = fonts.get(i).name;
            Log.i("Font Name" + i, items[i]);
        }


      /*  if(checkLocales("kn"))
        {
            Toast.makeText(getApplicationContext(),"Device Supports Kannada",Toast.LENGTH_SHORT).show();
            tv.append(" Raghu");
        } else
        {
            Toast.makeText(getApplicationContext(),"Device Supports Kannada",Toast.LENGTH_SHORT).show();
            Typeface font = Typeface.createFromAsset(getAssets(), "NotoSansDevanagari-Bold.ttf");
            //tv.setTypeface(font);
            tv.append(" Raghu");
        }*/
    }

    public boolean checkLocales(String checklocale)
    {
        boolean hasLocale = false;
        String myLocale = checklocale;
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (locale.getLanguage().equals(myLocale)) {
                hasLocale = true;
            }
        }
         return hasLocale;
    }
}
