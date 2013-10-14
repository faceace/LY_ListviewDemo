package com.ly.listviewdemoly;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainFragment extends ListFragment implements PullToRefreshAttacher.OnRefreshListener {
	
	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	private ArrayList<String> doclist;
	private LyAdapter adapter;
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
		Log.d(getClass().getName(), "onCreateView");
		
		View v = inflater.inflate(R.layout.fragment_main, container, false);  
        
		initAllViews(v);
        
        return v;
    } 
	
	@Override
	public void onResume() {
		//pull refresh
        ListView lv = this.getListView();
		mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();
        mPullToRefreshAttacher.addRefreshableView(lv, this);
		super.onResume();
	}
	
	
	private void initAllViews(View v) {
		// buttons
		v.findViewById(R.id.bt_me).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//pop up a dialog
				ConfirmationDialogFragment.newInstance("a Button!").show(getFragmentManager(), null);
			}
		});
		
		//lists
		doclist = new ArrayList<String>();
		for (int i = 1; i<21; i++) {
			doclist.add(String.valueOf(i));
		}
		
        adapter = new LyAdapter(getActivity(), doclist);
        setListAdapter(adapter);

	}
	
	
	public static class LyAdapter extends BaseAdapter implements View.OnClickListener{

		//public Context context;
		public ArrayList<String> data;
	    private static LayoutInflater inflater = null;

	    public LyAdapter(Context context, ArrayList<String> data) {
	    	//this.context = context;
	        this.data = data;
	        inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    @Override
	    public int getCount() {
	        return data.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        return data.get(position);
	    }

	    @Override
	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi = convertView;
	        if (vi == null)
	            vi = inflater.inflate(R.layout.row, null);
	        
	        TextView text = (TextView) vi.findViewById(R.id.tv);
	        text.setText(data.get(position));

	        Button rba = (Button) vi.findViewById(R.id.radio_a);
	        Button rbb = (Button) vi.findViewById(R.id.radio_b);
	        rba.setSelected(position%3 == 0);
	        rbb.setSelected(position%2 == 0);

	        rba.setOnClickListener(this);
	        rbb.setOnClickListener(this);
	        
	        return vi;
	    }

		@Override
		public void onClick(View v) {
			Button bt = (Button)v;
			bt.setSelected(!bt.isSelected());
		}

	}
	

	@Override
	public void onDestroyView() {
		Log.d(getClass().getName(), "onDestroyView");
		
		//doclist = null;
		//adapter = null;
		
		super.onDestroyView();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//pop up a dialog
		ConfirmationDialogFragment.newInstance("Number " + (position+1)).show(getFragmentManager(), null);
    }
	
	

	@Override
	public void onRefreshStarted(View view) {
		
		new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Notify PullToRefreshAttacher that the refresh has finished
                mPullToRefreshAttacher.setRefreshComplete();
            }
        }.execute();
	}
	
	
	
	
	public static class ConfirmationDialogFragment extends DialogFragment {
		
		public String name;
		
		public static ConfirmationDialogFragment newInstance(String thename) {
			ConfirmationDialogFragment newfrg = new ConfirmationDialogFragment();
			newfrg.name = thename;
			
			return newfrg;
		}
		
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage("I am " + name)
	               .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                   }
	               });
	        return builder.create();
	    }
	}
	
}