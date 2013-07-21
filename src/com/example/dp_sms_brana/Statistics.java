package com.example.dp_sms_brana;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dp_sms_brana.database.DatabaseHandler;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import content.Message;

public class Statistics extends Activity{
	SimpleDateFormat dateFormatter;
	ArrayList<Message> messageList;
	

	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//setContentView(R.layout.statistics);
	setContentView(R.layout.graphs);
	
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
	/* 
	 GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
			 			 	            
				  new GraphViewData(1, 2.0d)
				, new GraphViewData(2, 1.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 2.5d)
				, new GraphViewData(4, 1.0d)
				, new GraphViewData(5, 3.0d)
		});
	 */

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

	   	   
	}

