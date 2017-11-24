package dragonfly.exercisetracker.ui.activities;

import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import dragonfly.exercisetracker.ExerciseApplication;
import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.RealmWrapper;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.database.models.DSchedule;
import dragonfly.exercisetracker.ui.fragments.TimetableFragment;
import dragonfly.exercisetracker.ui.fragments.VariableListFragment;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.DrawerAdapter;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements BaseAdapter.OnItemSelectedListener {

    @Inject Bus bus;
    private boolean busRegistered = false;
    private Toolbar toolbar;
    private RecyclerView drawerRv;
    private DrawerLayout drawerLayout;
    private DrawerAdapter drawerAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ExerciseApplication)this.getApplication()).dependencyGraph.inject(this);
        this.bus.register(this);
        this.busRegistered = true;

        this.toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(null);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(false);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.fragmentContainer = (FrameLayout) this.findViewById(R.id.fragment_container);

        this.drawerRv = (RecyclerView) this.findViewById(R.id.drawer_recycler_view);

        DrawerAdapter.Item items[] = new DrawerAdapter.Item[7];
        items[0] = new DrawerAdapter.Item(this.getString(R.string.timetable));
        items[1] = new DrawerAdapter.Item(this.getString(R.string.schedules));
        items[2] = new DrawerAdapter.Item(this.getString(R.string.routines));
        items[3] = new DrawerAdapter.Item(this.getString(R.string.workouts));
        items[4] = new DrawerAdapter.Item(this.getString(R.string.exercises));
        items[5] = new DrawerAdapter.Item(this.getString(R.string.variables));
        items[6] = new DrawerAdapter.Item(this.getString(R.string.exit));

        this.drawerAdapter = new DrawerAdapter(items);
        this.drawerAdapter.nullSelectionAllowed = false;
        this.drawerAdapter.multiSelectionAllowed = false;
        this.drawerAdapter.addOnItemSelectedListener(this);
        //this.drawerAdapter(items[0]);
        this.drawerRv.setAdapter(drawerAdapter);
        this.drawerRv.setLayoutManager(new LinearLayoutManager(this));

        this.drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        this.drawerToggle = new ActionBarDrawerToggle(this,  this.drawerLayout, this.toolbar,
                R.string.drawer_open, R.string.drawer_close);

        this.drawerToggle.setDrawerIndicatorEnabled(true);
        this.drawerLayout.addDrawerListener(this.drawerToggle);
        this.drawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if(this.drawerToggle != null) {
            this.drawerToggle.syncState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!this.busRegistered) {
            this.bus.register(this);
            this.busRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.busRegistered) {
            this.bus.unregister(this);
            this.busRegistered = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.drawerToggle != null){
            this.drawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemSelected(BaseAdapter.BaseViewHolder viewHolder, Object item) {
        if(item instanceof DrawerAdapter.Item) {
            if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.timetable))) {
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        TimetableFragment.newInstance(), TimetableFragment.class.getName())
                        .addToBackStack(TimetableFragment.class.getName()).commit();
            } else if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.schedules))) {

            } else if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.routines))) {

            } else if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.workouts))) {

            } else if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.exercises))) {

            } else if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.variables))) {
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        VariableListFragment.newInstance(), VariableListFragment.class.getName())
                        .addToBackStack(VariableListFragment.class.getName()).commit();
            } else if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.exit))) {
                this.finish();
            }
        }
    }
}
