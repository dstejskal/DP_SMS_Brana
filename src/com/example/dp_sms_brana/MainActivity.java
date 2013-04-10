package com.example.dp_sms_brana;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
//import android.content.Intent;

public class MainActivity extends Activity {


	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       // setContentView(R.layout.activity_main);
	    }
	    
	    /* Initiating Menu XML file (menu.xml) */
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.main, menu);
	        return true;
	    }
	    
	    /**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        
	        switch (item.getItemId())
	        {
		     case R.id.download:
		  	   
		    	 Toast.makeText(MainActivity.this, "Stáhnout data", Toast.LENGTH_SHORT).show();
		    	 Intent i = new Intent(this,SMSList.class);
		    	 startActivity(i);
		       return true;
		     case R.id.save:
		       return true;
		     case R.id.send:
		    	 Toast.makeText(MainActivity.this, "Odesílám SMS", Toast.LENGTH_SHORT).show();
		    	 
		    	 Intent j = new Intent(this,SMS.class);
		    	 startActivity(j);
		       return true;
		     case R.id.manage:
		       return true;
		     case R.id.update:
		       return true;
		     case R.id.info:
		    	 Toast.makeText(MainActivity.this, "SMS brána 1.0", Toast.LENGTH_SHORT).show();
		       return true;
		       
		      default:
		            return super.onOptionsItemSelected(item); 
	        }
	    }
	    

}
