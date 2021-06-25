package fpt.provipluxurylimited.challengefocus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initComponents(savedInstanceState);
        initData();
    }


    protected void initComponents(Bundle savedInstanceState) {
        menu = findViewById(R.id.chipMenu);
        if (savedInstanceState == null) {
            menu.setItemSelected(R.id.itemExplore, true);
        }
        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.itemExplore:
                        System.out.println("it's explore");
                        break;
                    case R.id.itemChallenge:
                        System.out.println("it's challenge");
                        break;
                    case R.id.itemProfile:
                        System.out.println("it's profile");
                        break;
                }
            }
        });
    }


    protected void initData() {

    }

}