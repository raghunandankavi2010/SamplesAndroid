package raghu.test.transitions.dummy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import raghu.test.transitions.R;

/**
 * Created by Raghunandan on 03-02-2016.
 */
public class FragmentA extends Fragment {

    public interface OnButtonClick
    {
        public void onButtonClick(View view);
    }

    OnButtonClick onButtonClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a,container,false);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onButtonClick = (OnButtonClick)context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView iv = (ImageView) view.findViewById(R.id.imageView) ;
        Button b= (Button) view.findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick.onButtonClick(iv);
            }
        });
    }
}
