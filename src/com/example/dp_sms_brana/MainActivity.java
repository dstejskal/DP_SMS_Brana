package com.example.dp_sms_brana;


import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dp_sms_brana.database.DatabaseHandler;


public class MainActivity extends Activity implements Observer {
	
	SmsSender sender;
	boolean running=false;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main); 
	        
	        //obnoven� stavu po p�eklopen� displeje
	        restoreMe(savedInstanceState);
	        
	        try {
				//Ranges.getDaysFromInterval("1.4.2013", "05.04.2013");
				CalendarFunctions.getDaysFromBeginOfMonth("05.04.2013");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		    	 Toast.makeText(MainActivity.this, "Nahr�v�m data", Toast.LENGTH_SHORT).show();
		    	 Intent i = new Intent(this,SMSList.class);
		    	 startActivity(i);
		       return true;
		     case R.id.save:
		    	 Intent generator = new Intent(this,GenerateData.class);
		    	 startActivity(generator);
		    	//startGraphActivity(SimpleGraph.class);
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
		    	 Intent settings = new Intent(this,Settings.class);
		    	 startActivity(settings);
		       return true;
		     case R.id.update:
		    	 //SmsSender sender=new SmsSender();
		    	 sender.execute("");
		    	 Toast.makeText(MainActivity.this, "P�ipojuji se k datab�zi", Toast.LENGTH_SHORT).show();
		    	 
		       return true;
		     case R.id.stats:
		    	 Intent graph = new Intent(this,Statistics.class);
		    	 graph.putExtra("type", "line");
		    	 startActivity(graph);
  	 
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

//ulo�en� stavu po p�eklopen� displeje
@Override
protected void onSaveInstanceState(Bundle outState) {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(outState);
	outState.putBoolean("connected", running);
}

//obnoven� stavu po p�eklopen� displeje
private void restoreMe(Bundle state){
	if(state!=null){
	running=state.getBoolean("connected");	
	}
	
}
	
	
}
	    


