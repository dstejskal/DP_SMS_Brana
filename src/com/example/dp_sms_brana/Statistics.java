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
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//setContentView(R.layout.statistics);
	setContentView(R.layout.graphs);
	

	spinner1 = (Spinner) findViewById(R.id.spinner1);
	spinner1.setOnItemSelectedListener(this);
	iCurrentSelection = spinner1.getSelectedItemPosition();
	
	List<String> days=new ArrayList<String>();
	try {
		//days = CalendarFunctions.getDaysFromBeginOfMonth("31.1.2013");
		days = CalendarFunctions.getDaysFromBeginOfMonth(CalendarFunctions.now());
		
		  
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	DatabaseHandler databaseHandler =new DatabaseHandler(getApplicationContext());
	TextView countSms = (TextView) findViewById(R.id.countSms);
	countSms.setText("Poèet odeslaných SMS:  " + Integer.toString(databaseHandler.getMessagesCount()));
	
	dateFormatter=new SimpleDateFormat("dd.MM.yyyy");
	Date date=new Date();
	try {
		date=(Date)dateFormatter.parse("1.01.2013");
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 messageList=databaseHandler.getAllMessages();
	// messageList.get(0).getDate();
	 int countOfDays=days.size();
	 GraphViewData[] gvd=new GraphViewData[countOfDays];	 
     
	 for(int i=0;i<countOfDays;i++){
     	gvd[i]=new GraphViewData(i, databaseHandler.getCountMessagesOfDay(days.get(i)));		            	
     }
	 GraphViewSeries exampleSeries = new GraphViewSeries(gvd);

		// graph with dynamically genereated horizontal and vertical labels
		GraphView graphView;
		if (getIntent().getStringExtra("type").equals("bar")) {
			graphView = new BarGraphView(
					this // context
					, "Vytížení SMS brány" // heading
			);
		} else {
			graphView = new LineGraphView(
					this // context
					, "Vytížení SMS brány" // heading
			);
		}
		((LineGraphView) graphView).setDrawBackground(true);
		graphView.addSeries(exampleSeries); // data

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		
		
		String[] arrayDays = new String[days.size()];
		days.toArray(arrayDays);
		
		//graphView.setHorizontalLabels(new String[] {messageList.get(0).getDate(),"yesterday", "today", "tomorrow"});
		graphView.setHorizontalLabels(arrayDays);
		layout.addView(graphView);
	   }
	
	  /*public void addListenerOnSpinnerItemSelection() {
			spinner1 = (Spinner) findViewById(R.id.spinner1);
			spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		  }*/
	   
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		

	      if (iCurrentSelection != (int)arg3){
	    	  //Toto se provede, pokud nastane zmìna ve výbìru položky
	    	  Toast.makeText(Statistics.this, "Zmìna: arg2:" + arg2 +" arg3: "+arg3+" selection: " + iCurrentSelection , Toast.LENGTH_LONG).show();
	      }
	      iCurrentSelection = (int)arg3;
	      
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	}

