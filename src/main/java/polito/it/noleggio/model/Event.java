package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{

	public enum EventType {
		NEW_CLIENT, CAR_RETURNED //Costanti simboliche
	}
	private LocalTime time;
	private EventType type;
	/**
	 * @param time
	 * @param type
	 */
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}
	/**
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(LocalTime time) {
		this.time = time;
	}
	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}
	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	
}
