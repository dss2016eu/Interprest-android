package coop.biantik.traductor.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import coop.biantik.traductor.R;
import coop.biantik.traductor.fragments.ConferenceFragment;
import coop.biantik.traductor.fragments.PostsFragment;
import coop.biantik.traductor.model.Event;


public class MainPagerAdapter extends FragmentPagerAdapter {

	private int[] TITLES = { R.string.home, R.string.messages  };


	private Context context;
	private Event event;

	public MainPagerAdapter(FragmentManager fm, Context context, Event event) {
		super(fm);
		this.context = context;
		this.event = event;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
			case 0:
				return ConferenceFragment.newInstance();
			case 1:
				return PostsFragment.newInstance();
			default:
				return null;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {

		return context.getString(TITLES[position]);
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}