package com.ly.listviewdemoly;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
	    getActionBar().setCustomView(R.layout.actionbar_view_title);
		
		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
		
		if (savedInstanceState == null)
			initFragments();
	}
	
	
	private void initFragments() {
		MainFragment newFragment = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.mainfgm, newFragment).commit();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }

}
