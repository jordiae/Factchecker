package factchecker.jordiae.com.factchecker;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ViewPagerAdapter adapter;

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder
                .setMessage("Are you sure you want to remove all users?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
                        dbAdapter.open();
                        long facts[] = dbAdapter.getAllFacts();
                        int nFacts = facts.length;
                        for (int i = 0; i < nFacts; ++i) {
                            dbAdapter.deleteEntry(facts[i]);
                        }
                        dbAdapter.close();

                        Fragment currentFragment = adapter.getItem(viewPager.getCurrentItem());
                        switch (viewPager.getCurrentItem()) {
                            case 0:
                                ((OneFragment) currentFragment).refresh();
                                break;
                            case 1:
                                ((TwoFragment) currentFragment).refresh();
                                break;
                            case 2:
                                ((ThreeFragment) currentFragment).refresh();
                                break;
                            case 3:
                                ((FourFragment) currentFragment).refresh();
                                break;
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private long addFact() {
        DBAdapter dbAdapter;
        dbAdapter = new DBAdapter(this);
        dbAdapter = dbAdapter.open();
        long entry = System.currentTimeMillis();
        dbAdapter.insertEntry(entry);
        dbAdapter.close();

        Fragment currentFragment = adapter.getItem(viewPager.getCurrentItem());
        switch (viewPager.getCurrentItem()) {
            case 0:
                ((OneFragment) currentFragment).refresh();
                return entry;
            case 1:
                ((TwoFragment) currentFragment).refresh();
                return entry;
            case 2:
                ((ThreeFragment) currentFragment).refresh();
                return entry;
            case 3:
                ((FourFragment) currentFragment).refresh();
                return entry;
            default:
                return -1;

        }
    }

    private void deleteFact(long entry) {
        DBAdapter dbAdapter;
        dbAdapter = new DBAdapter(this);
        dbAdapter = dbAdapter.open();
        dbAdapter.deleteEntry(entry);
        dbAdapter.close();

        Fragment currentFragment = adapter.getItem(viewPager.getCurrentItem());
        switch (viewPager.getCurrentItem()) {
            case 0:
                ((OneFragment) currentFragment).refresh();
                break;
            case 1:
                ((TwoFragment) currentFragment).refresh();
                break;
            case 2:
                ((ThreeFragment) currentFragment).refresh();
                break;
            case 3:
                ((FourFragment) currentFragment).refresh();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long entry = addFact();
                Snackbar.make(view, "Added new fact", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteFact(entry);

                            }}).show();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "LAST HOUR");
        adapter.addFragment(new TwoFragment(), "TODAY");
        adapter.addFragment(new ThreeFragment(), "LAST MONTH");
        adapter.addFragment(new FourFragment(), "History");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete:
                confirmDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


