/**
 * EventImpl.java
 * Created on 08.03.2003, 13:20:13 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.interfaces.IEvent;
import main.java.memoranda.util.Local;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 * 
 */
/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventImpl implements IEvent, Comparable {
    
    private Element __elem = null;

    /**
     * Constructor for EventImpl.
     */
    public EventImpl(Element elem) {
        __elem = elem;
    }

   
    /**
     * @see main.java.memoranda.interfaces.IEvent#getHour()
     */
    public int getHour() {
        return new Integer(__elem.getAttribute("hour").getValue()).intValue();
    }

    /**
     * @see main.java.memoranda.interfaces.IEvent#getMinute()
     */
    public int getMinute() {
        return new Integer(__elem.getAttribute("min").getValue()).intValue();
    }
    
    public String getTimeString() {
        return Local.getTimeString(getHour(), getMinute());
    }
        
  
    /**
     * @see main.java.memoranda.interfaces.IEvent#getText()
     */
    public String getText() {
        return __elem.getValue();
    }

    /**
     * @see main.java.memoranda.interfaces.IEvent#getContent()
     */
    public Element getContent() {
        return __elem;
    }
    /**
     * @see main.java.memoranda.interfaces.IEvent#isRepeatable()
     */
    public boolean isRepeatable() {
        return getStartDate() != null;
    }
    /**
     * @see main.java.memoranda.interfaces.IEvent#getStartDate()
     */
    // Task 3-1 SMELL WITHIN A CLASS
    // CS:11 Too short identifier
    public CalendarDate getStartDate() {
        Attribute attr = __elem.getAttribute("startDate");
        if (attr != null) return new CalendarDate(attr.getValue());
        return null;
    }
    /**
     * @see main.java.memoranda.interfaces.IEvent#getEndDate()
     */
    public CalendarDate getEndDate() {
        Attribute a = __elem.getAttribute("endDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
    }
    /**
     * @see main.java.memoranda.interfaces.IEvent#getPeriod()
     */
    public int getPeriod() {
        Attribute a = __elem.getAttribute("period");
        if (a != null) return new Integer(a.getValue()).intValue();
        return 0;
    }
    /**
     * @see main.java.memoranda.interfaces.IEvent#getId()
     */
    public String getId() {
        Attribute a = __elem.getAttribute("id");
        if (a != null) return a.getValue();
        return null;
    }
    /**
     * @see main.java.memoranda.interfaces.IEvent#getRepeat()
     */
    public int getRepeat() {
        Attribute a = __elem.getAttribute("repeat-type");
        if (a != null) return new Integer(a.getValue()).intValue();
        return 0;
    }
    /**
     * @see main.java.memoranda.interfaces.IEvent#getTime()
     */
    public Date getTime() {
    	//Deprecated methods
		//Date d = new Date();
		//d.setHours(getHour());
		//d.setMinutes(getMinute());
		//d.setSeconds(0);
		//End deprecated methods

		Date d = new Date(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.setTime(d); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.HOUR_OF_DAY, getHour()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.MINUTE, getMinute()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.SECOND, 0); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		d = calendar.getTime(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
        return d;
    }
	
	/**
     * @see main.java.memoranda.interfaces.IEvent#getWorkinDays()
     */
	public boolean getWorkingDays() {
        Attribute a = __elem.getAttribute("workingDays");
        if (a != null && a.getValue().equals("true")) return true;
        return false;
	}
	
	public int compareTo(Object o) {
		IEvent event = (IEvent) o;
		return (getHour() * 60 + getMinute()) - (event.getHour() * 60 + event.getMinute());
	}

}
