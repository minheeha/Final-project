package com.kh.fp.schedule.model.vo;

public class Schedule implements java.io.Serializable{
	private int scheduleNo;
	private String title;
	private String allDay;
	private String start;
	private String end;
	private String backgroundColor;
	private String type;
	private int classNo;
	private int kinderNo;
	private String scheduleContent;
	
	public Schedule() {}

	public Schedule(int scheduleNo, String title, String allDay, String start, String end, String backgroundColor,
			String type, int classNo, int kinderNo, String scheduleContent) {
		super();
		this.scheduleNo = scheduleNo;
		this.title = title;
		this.allDay = allDay;
		this.start = start;
		this.end = end;
		this.backgroundColor = backgroundColor;
		this.type = type;
		this.classNo = classNo;
		this.kinderNo = kinderNo;
		this.scheduleContent = scheduleContent;
	}

	public int getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAllDay() {
		return allDay;
	}

	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getClassNo() {
		return classNo;
	}

	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}

	public int getKinderNo() {
		return kinderNo;
	}

	public void setKinderNo(int kinderNo) {
		this.kinderNo = kinderNo;
	}

	public String getScheduleContent() {
		return scheduleContent;
	}

	public void setScheduleContent(String scheduleContent) {
		this.scheduleContent = scheduleContent;
	}

	@Override
	public String toString() {
		return "Schedule [scheduleNo=" + scheduleNo + ", title=" + title + ", allDay=" + allDay + ", start=" + start
				+ ", end=" + end + ", backgroundColor=" + backgroundColor + ", type=" + type + ", classNo=" + classNo
				+ ", kinderNo=" + kinderNo + ", scheduleContent=" + scheduleContent + "]";
	}

	
	
	
}