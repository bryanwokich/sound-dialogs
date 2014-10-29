package com.soundsoftware.plugins;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SearchListView extends TableLayout
{

	String _filterString;
	TableRow _listRow; // This is defined on the class because it will be modified outside of the constructor.
	TableRow.LayoutParams _rowParams;
	TableRow.LayoutParams _itemParams;
	Context _context;
	List<String> _activeListItems;
	JSONArray _options;
	String _selectedValue;

	public String getValue(){
		return _selectedValue;
	}

	// Based upon this: http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view
    class FilteredListAdapter extends BaseAdapter{
    	//private ArrayList<String> mData;
    	private List<String> mData;
        private List<RadioButton> _rbs;

        public FilteredListAdapter(Context context, List<String> listItems){
        	mData = listItems;
        	_context = context;
        	_rbs = new ArrayList<RadioButton>();
        }

		@Override
		public int getCount() {
        	return mData.size();

		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		private OnClickListener RadioCallback = new OnClickListener(){

			@Override
			public void onClick(View v) {
				RadioButton selected = (RadioButton)v;
				for(int i = 0; i < _rbs.size(); i++){
					_rbs.get(i).setChecked(false);
				}

				selected.setChecked(true);
				_selectedValue = (String) selected.getText();

			}
		};

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String current = (String) getItem(position);
			RadioButton rb = new RadioButton(_context);
			if(_selectedValue.equals(current)){
				rb.setChecked(true);
			}
			
			rb.setTextColor(Color.DKGRAY);
			rb.setText(current);
			rb.setOnClickListener(RadioCallback);
			_rbs.add(rb);
			return rb;
		}


    }

	private void setFilter(String string){
		_filterString = string;
	}

    public SearchListView(Context context, JSONArray options, AttributeSet attrs)
    {
        super(context, attrs);
        _context = context;
        _options = options;
        _filterString = "";
        _selectedValue = "";

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        _rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 0, 1f);
        _itemParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f);
        this.setLayoutParams(tableParams);
		final EditText filterInput = new EditText(context);
		filterInput.setLayoutParams(_itemParams);



		TextWatcher watcher = new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
					setFilter(s.toString());

			}


			@Override
			public void afterTextChanged(Editable s) {

				UpdateList();
			}
		};

		filterInput.addTextChangedListener(watcher);

        TableRow headerRow = new TableRow(_context);
        headerRow.addView(filterInput);
        headerRow.setLayoutParams(_itemParams);
        headerRow.setPadding(25,25,25,25);
        this.addView(headerRow);

        filterInput.setHint("Type to filter");



        _activeListItems = collectActiveList();




        _listRow = new TableRow(context);
        _listRow.setLayoutParams(_rowParams);
        addView(_listRow);


        ListView list = new ListView(context);
        FilteredListAdapter listAdapter = new FilteredListAdapter(context, _activeListItems);

        list.setAdapter(listAdapter);
        _listRow.addView(list);

        list.setLayoutParams(_itemParams);
        setOrientation(TableLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    }

    /// This function filters the active list based upon the filter string
    private List<String> collectActiveList() {
		List<String> Result = new ArrayList<String>();
    	for(int i = 0; i < _options.length(); i++){
    		try {
	    		String value = _options.getString(i);
				if(value.toLowerCase().contains(_filterString.toLowerCase()) || _filterString.equals("")){
					Log.w("Sound Dialog", value);
					Result.add(value);
				} else {
					Log.w("SoundDialog", "value.toLowerCase() ( " + value.toLowerCase() + " doesn't contain _filterString " + _filterString + " " + Boolean.toString(!_filterString.equals("")));
				}

    		} catch (JSONException e) {
				e.printStackTrace();
			}
		}
    	return Result;
    }

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private void UpdateList(){
		TableRow newRow = new TableRow(_context);
		newRow.setLayoutParams(_rowParams);
		_activeListItems = collectActiveList();
    	if(_activeListItems.size() == 0){
    		TextView noneFound = new TextView(_context);
    		noneFound.setText("No matches found");
    		noneFound.setTextColor(Color.DKGRAY);
    		noneFound.setTextAlignment(TEXT_ALIGNMENT_CENTER);
    		noneFound.setLayoutParams(_itemParams);
    		noneFound.setPadding(25, 25, 25, 25);
    		noneFound.setTextSize(14);
    		newRow.addView(noneFound);
    	} else {
	    	ListView newList = new ListView(_context);
	    	newList.setLayoutParams(_itemParams);
	    	FilteredListAdapter listAdapter = new FilteredListAdapter(_context, _activeListItems);
	    	newList.setAdapter(listAdapter);
	    	newRow.addView(newList);
    	}
    	removeViewAt(1);
    	addView(newRow);
    }
}