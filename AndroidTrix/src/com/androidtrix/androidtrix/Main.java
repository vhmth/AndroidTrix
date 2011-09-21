/**
 * @author Vinay Hiremath
 * vhiremath4@gmail.com
 * Main menu Activity for the AndroidTrix Android showcase app.
 * Acts as a multiple-Activity launcher via a ListView menu.
 */

package com.androidtrix.androidtrix;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Main extends Activity {
	
	//variables that make up the main menu
	ListView mainMenu;
	ArrayAdapter<String> menuAdapter;//adapter that will bind the data to the ListView
	ArrayList<String> menuItems = new ArrayList<String>();
	String selectedMenuItem;//stores the selected menu item and fires up the appropriate Activity
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //initialize some variables that will be of importance to the main menu
        mainMenu = (ListView)findViewById(R.id.mainmenu);
        int layoutID = android.R.layout.simple_list_item_1;
        menuAdapter = new ArrayAdapter<String>(this, layoutID, menuItems);//bind the menuItems ArrayList to the adapter
        mainMenu.setAdapter(menuAdapter);//bind the adapter to the ListView
        String[] menuEntries = new String[]{
        	"TextView Index onClick",
        	"Natural TextView Wrapping of Other Views",
        	"Painless JSON Parsing"
        };//items that will populate the menu
        
        //populate the ListView
        for (int i = 0; i < menuEntries.length; i++)
        	menuItems.add(menuEntries[i]);
        menuAdapter.notifyDataSetChanged();//notify the adapter of data change - not necessary but precautionary
        
        //fire up separate Activites that show and demo code on menu item click
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View v, int index,
					long arg3) {
				//get the selected menu item
				selectedMenuItem = menuItems.get(index);
				
				//check to see which item has been selected and then fire up the necessary Activity
				if (selectedMenuItem.equals("TextView Index onClick")){
					Intent intent = new Intent(Main.this, TextViewIndexOnClick.class);
					startActivity(intent);
				} else if (selectedMenuItem.equals("Natural TextView Wrapping of Other Views")){
					Toast.makeText(v.getContext(), "got item 2", 10000).show();
				} else if (selectedMenuItem.equals("Painless JSON Parsing")){
					Toast.makeText(v.getContext(), "got item 3", 10000).show();
				}
			}
		});
    }
}