package com.campusTalk.JavaScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.campusTalk.Beans.Event;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

// This Website Scraper code is written in Java to scrape the current day's event information from events.colorado.edu using JSoup library

public class WebScraper {
	
	List<Event> events;
	
	public WebScraper()
	{
		events = new ArrayList<Event>();
	}

	public static void main(String[] args){
		WebScraper ws = new WebScraper();
		ws.scrapeTopic("http://events.colorado.edu/EventListSyndicator.aspx");
		for(Event e: ws.events)
		{
			//Printing all the event details
			System.out.println(e.getEventName());
			System.out.println(e.getEventDescription());
			System.out.println(e.getEventLink());
			System.out.println(e.getEventStartDate());
			System.out.println(e.getEventEndDate());
			System.out.println(e.getEventLocation());
			System.out.println("----------------------------------------------");
			// code for calling a dbhelper method to insert data into DB is required
		}
	}
	public void scrapeTopic(String url){
		String htmlPage = getUrl(url);
		Document doc = Jsoup.parse(htmlPage);
		ArrayList<Element> links = doc.select("a");
		for(Element link : links)
		{
			Event e = new Event();
			//String text = doc.body().text();
			String eventLink = link.attr("href");
			eventLink = eventLink.replaceAll("\\\\\"", "");
			e.setEventLink(eventLink);
			this.parseLinkforDetails(e);
		}
	}
	private void parseLinkforDetails(Event e) {
		// TODO Auto-generated method stub
		String htmlPage = getUrl(e.getEventLink());
		Document doc = Jsoup.parse(htmlPage);
		e.setEventName(doc.select("#title1").first().attr("content"));
		e.setEventDescription(doc.select("#description1").first().attr("content"));
		//System.out.println(e.getEventDescription());
		String temporaryTableData = doc.select("td.detailsview").text();
		e = setDateFields(temporaryTableData,e);
		e.setEventLocation(doc.select("a.calendartext").first().text());
		this.events.add(e);
	}

	private static Event setDateFields(String temporaryTableData, Event e) {
		// TODO Auto-generated method stub
		String dateValue;
		Matcher match = Pattern.compile("([0-9])+/([0-9])+/([0-9]){4}").matcher(temporaryTableData);
		int count = 0;
		while(match.find())
		{
			dateValue = match.group();
			
			if(count == 0)
				e.setEventStartDate(dateValue);
			else if(count == 1)
				e.setEventEndDate(dateValue);
			else
				break;
			count = count + 1;
		}
		
		return e;
	}

	private static String getUrl(String url) {
		// TODO Auto-generated method stub
		URL urlobj = null;
		try{
			urlobj = new URL(url);
			}
			catch(MalformedURLException e){
			System.out.println("The url was malformed!");
			return "";
			}
			URLConnection urlCon = null;
			BufferedReader in = null;
			String outputText = "";
			try{
			urlCon = urlobj.openConnection();
			in = new BufferedReader(new
			InputStreamReader(urlCon.getInputStream()));
			String line = "";
			while((line = in.readLine()) != null){
			outputText += line;
			}
			in.close();
			}catch(IOException e){
			System.out.println("There was an error connecting to the URL");
			return "";
			}
			return outputText;
	}
}