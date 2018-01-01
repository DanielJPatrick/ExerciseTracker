package dragonfly.exercisetracker.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.ui.fragments.ExerciseListFragment;
import dragonfly.exercisetracker.ui.fragments.VariableListFragment;
import dragonfly.exercisetracker.ui.fragments.WorkoutListFragment;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.DrawerAdapter;

public class MainActivity extends AppCompatActivity implements BaseAdapter.OnItemSelectedListener {

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

        this.toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(null);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(false);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.fragmentContainer = (FrameLayout) this.findViewById(R.id.fragment_container);

        this.drawerRv = (RecyclerView) this.findViewById(R.id.drawer_recycler_view);

        DrawerAdapter.Item items[] = new DrawerAdapter.Item[4];
        //items[0] = new DrawerAdapter.Item(this.getString(R.string.timetable));
        //items[1] = new DrawerAdapter.Item(this.getString(R.string.schedules));
        //items[2] = new DrawerAdapter.Item(this.getString(R.string.routines));
        items[0] = new DrawerAdapter.Item(this.getString(R.string.workouts));
        items[1] = new DrawerAdapter.Item(this.getString(R.string.exercises));
        items[2] = new DrawerAdapter.Item(this.getString(R.string.variables));
        items[3] = new DrawerAdapter.Item(this.getString(R.string.exit));

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
    public void onItemSelected(BaseAdapter adapter, BaseAdapter.BaseViewHolder viewHolder, Object item) {
        if(adapter instanceof DrawerAdapter) {
            if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.workouts))) {
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        WorkoutListFragment.newInstance(), WorkoutListFragment.class.getName())
                        .addToBackStack(WorkoutListFragment.class.getName()).commit();
            } else if(((DrawerAdapter.Item) item).getName().equals(this.getString(R.string.exercises))) {
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        ExerciseListFragment.newInstance(), ExerciseListFragment.class.getName())
                        .addToBackStack(ExerciseListFragment.class.getName()).commit();
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
