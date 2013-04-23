package com.example.dp_sms_brana;


import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dp_sms_brana.database.DatabaseHandler;

import content.Message;

public class MainActivity extends Activity implements Observer {
	
	SmsSender sender;
	boolean running=false;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main); 
	        
	        /*Test SQLite datab�ze*/
	       
	       DatabaseHandler databaseHandler =new DatabaseHandler(getApplicationContext()); 
	       //Toast.makeText(MainActivity.this, "V�pis z SQLite "+databaseHandler.getMessage(5).getMid(), Toast.LENGTH_SHORT).show();
	       Toast.makeText(MainActivity.this, "Po�et odeslan�ch SMS: "+ Integer.toString(databaseHandler.getMessagesCount()), Toast.LENGTH_SHORT).show(); 
	    }
	    public void connectDatabase (View toogleDatabase){
	    	
	    	sender=new SmsSender(getApplicationContext()); //AsynncTask lze spustit pouze jednou, mus�m vytvo�it nov� instance

	    	if(!running){
	    	 sender.execute("");
	    	 Toast.makeText(MainActivity.this, "P�ipojuji se k datab�zi", Toast.LENGTH_SHORT).show(); 
	    	 running=true;
	    	 }else{
	    		 sender.cancel(true); 
	
	    		 Toast.makeText(MainActivity.this, "Odpojuji datab�zi", Toast.LENGTH_SHORT).show();
	    		 running=false;
	    		 

//	    	 	 Toast.makeText(MainActivity.this, "Datab�ze byla odpojena", Toast.LENGTH_SHORT).show(); 

	    	 }
	    	
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
		     case R.id.queue:
		    	 Toast.makeText(MainActivity.this, "St�hnout data", Toast.LENGTH_SHORT).show();
		    	 Intent i = new Intent(this,SMSList.class);
		    	 startActivity(i);
		       return true;
		     case R.id.save:
		       return true;
		     case R.id.send:
		    	 /*Odesl�n� p�es GSM br�nu*/
		    	 
                /*Toast.makeText(MainActivity.this, "Odes�l�m SMS", Toast.LENGTH_SHORT).show();		    	 
		    	 Intent j = new Intent(this,SMS.class);
		    	 startActivity(j);*/
		    	 
		    	 /*Odesl�n� zpr�vy do datab�ze*/		    	 
		    	 Intent smstodb=new Intent (this,SendSmsToDatabase.class);
		    	 startActivity(smstodb);		    	 
		       return true;
		     case R.id.manage:
		       return true;
		     case R.id.update:
		    	 //SmsSender sender=new SmsSender();
		    	 sender.execute("");
		    	 Toast.makeText(MainActivity.this, "P�ipojuji se k datab�zi", Toast.LENGTH_SHORT).show();
		    	 
		       return true;
		     case R.id.stats:
		    	 Toast.makeText(MainActivity.this, "SMS br�na 1.0", Toast.LENGTH_SHORT).show();
		    	 Intent statistics=new Intent (this,Statistics.class);
		    	 startActivity(statistics);
  	 
		       return true;
		       
		      default:
		            return super.onOptionsItemSelected(item); 
	        }
	    }

		@Override
		public void update(Observable observable, Object data) {
			// TODO Auto-generated method stub
	    	// SmsSender sender=new SmsSender();
	    	 sender.execute("");
	    	 Toast.makeText(MainActivity.this, "P�epos�l�m nov� SMS", Toast.LENGTH_SHORT).show();
			
			
		}
	    

}
