package com.example.dp_sms_brana;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dp_sms_brana.database.DatabaseHandler;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import content.Message;

public class Statistics extends Activity implements OnItemSelectedListener{
	SimpleDateFormat dateFormatter;
	ArrayList<Message> messageList;
	private Spinner spinner1;
	int iCurrentSelection;
	//v základu vykresluji data za poslední týden
	int interval=6;

	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.graphs);

	spinner1 = (Spinner) findViewById(R.id.spinner1);
	spinner1.setSelection(1);
	spinner1.setOnItemSelectedListener(this);
	iCurrentSelection = spinner1.getSelectedItemPosition();
    paintGraph(interval);
	   }

    public void paintGraph (int interval){
    	int countOfSMS=0;
    	DatabaseHandler databaseHandler =new DatabaseHandler(getApplicationContext());
    	
    	List<String> days=new ArrayList<String>();
    	try {
    		if (interval==0){ 
    			//všechny dny z uplynulého měsíce
    		days = CalendarFunctions.getDaysFromBeginOfMonth(CalendarFunctions.now());
    		}
    		else if (interval==-1){//vypsání všech SMS
    		//získání první SMS
    		String first=	databaseHandler.getMessage(1).getDate(); 
    		days=CalendarFunctions.getDaysFromDate(first);
    			
    		}else {
    			//počet dnů od dnešního dne do minulosti
    		days = CalendarFunctions.getDaysFromToday(interval);	   			
    		}
    		if (days==null){
    			Toast.makeText(Statistics.this,"Nebyly odeslány žádné zprávy.", Toast.LENGTH_LONG).show();  	
    			//pokud nejsou žádné sms, nevykreslím graf	
    			return; 
    		} 

    	} catch (ParseException e) {
    		e.printStackTrace();
    	}

    	
    	TextView countSms = (TextView) findViewById(R.id.countSms);
   
    	dateFormatter=new SimpleDateFormat("dd.MM.yyyy");
    	Date date=new Date();
    	try {
    		date=(Date)dateFormatter.parse("1.01.2013");
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}

    	 int countOfDays=days.size();
    	 GraphViewData[] gvd=new GraphViewData[countOfDays];	 
         
    	 for(int i=0;i<countOfDays;i++){
         	gvd[i]=new GraphViewData(i, databaseHandler.getCountMessagesOfDay(days.get(i)));
         	countOfSMS+=databaseHandler.getCountMessagesOfDay(days.get(i));
         }
    	 countSms.setText("Počet odeslaných SMS:  " + countOfSMS);
    	 GraphViewSeries exampleSeries = new GraphViewSeries(gvd);

    		// graf s dynamicky generovanými popisky - vertikální, horizontální
    		GraphView graphView;
    		if (getIntent().getStringExtra("type").equals("bar")) {
    			graphView = new BarGraphView(
    					this 
    					, "Vytížení SMS brány" 
    			);
    		} else {
    			graphView = new LineGraphView(
    					this 
    					, "Vytížení SMS brány"
    			);
    		}
    		((LineGraphView) graphView).setDrawBackground(true);
    		graphView.addSeries(exampleSeries); // data

    		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);


    		String[] arrayDays = new String[days.size()];
    		days.toArray(arrayDays);

    		//popisky osy x - datumy nevykresluj u intrevalu vše
    		if (interval!=-1) graphView.setHorizontalLabels(arrayDays);
    		
    		//přidáno kvůli obnově grafů - odstranění layoutu
    		layout.removeAllViewsInLayout();	
    		//přidání grafu do layoutu
    		layout.addView(graphView);		
    }
       
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {


	      if (iCurrentSelection != (int)arg3){
	    	  //Toto se provede, pokud nastane změna ve výběru položky
	    	   
	    	   switch (arg2){
	    	   case 0: paintGraph(1);
	    	   break;
	    	   case 1: paintGraph(6);
	    	   break;
	    	   case 2: paintGraph(13);
	    	   break;
	    	   case 3: paintGraph(0);
	    	   break;
	    	   case 4: paintGraph(-1);//pro všechna data
	    	   break;
	    	   default: paintGraph(0);
	    	   break;
	    	   }
	      }
	      iCurrentSelection = (int)arg3;

	}
	
	//uložení intervalu
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("interval", interval);
	}

	//obnovení stavu po překlopení displeje
	private void restoreMe(Bundle state){
		if(state!=null){
		interval=state.getInt("interval");	
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
	}
