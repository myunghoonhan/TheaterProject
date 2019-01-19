package dto;

import java.sql.Date;

public class ShowSeatBean {
	private String sssno;
	private Date ssdate;
	private int ssseat;
	
	public String getSssno() {
		return sssno;
	}
	public void setSssno(String sssno) {
		this.sssno = sssno;
	}
	public Date getSsdate() {
		return ssdate;
	}
	public void setSsdate(Date ssdate) {
		this.ssdate = ssdate;
	}
	public int getSsseat() {
		return ssseat;
	}
	public void setSsseat(int ssseat) {
		this.ssseat = ssseat;
	}
}
