package com.soundsoftware.sound_dialogs;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchListView extends LinearLayout
{
    public SearchListView(Context context, JSONArray options, AttributeSet attrs)
    {
        super(context, attrs);


		final EditText filterInput = new EditText(context);

		List<String>listItems = new ArrayList<String>();
		for(int i = 0; i < options.length(); i++){
			JSONObject currObj;
			try {
				currObj = options.getJSONObject(i);
				listItems.add(currObj.getString("value"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		final CharSequence[] choices = listItems.toArray(new CharSequence[listItems.size()]);
		//alert.setSingleChoiceItems(choices, 1, null);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        TextView tv1 = new TextView(context);
        tv1.setText("HELLO");
        addView(tv1);

        TextView tv2 = new TextView(context);
        tv2.setText("WORLD");
        addView(tv2);
    }
}