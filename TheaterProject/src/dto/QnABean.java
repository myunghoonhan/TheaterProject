package dto;

import java.sql.Date;

public class QnABean {
	private int qno;
	private String qid;
	private String qpw;
	private String qsubject;
	private String qcontents;
	private Date qdate;
	private int qgroup;
	private int qstep;
	private int qlevel;
	private int qcount;
	
	public int getQno() {
		return qno;
	}
	public void setQno(int qno) {
		this.qno = qno;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getQpw() {
		return qpw;
	}
	public void setQpw(String qpw) {
		this.qpw = qpw;
	}
	public String getQsubject() {
		return qsubject;
	}
	public void setQsubject(String qsubject) {
		this.qsubject = qsubject;
	}
	public String getQcontents() {
		return qcontents;
	}
	public void setQcontents(String qcontents) {
		this.qcontents = qcontents;
	}
	public Date getQdate() {
		return qdate;
	}
	public void setQdate(Date qdate) {
		this.qdate = qdate;
	}
	public int getQgroup() {
		return qgroup;
	}
	public void setQgroup(int qgroup) {
		this.qgroup = qgroup;
	}
	public int getQstep() {
		return qstep;
	}
	public void setQstep(int qstep) {
		this.qstep = qstep;
	}
	public int getQlevel() {
		return qlevel;
	}
	public void setQlevel(int qlevel) {
		this.qlevel = qlevel;
	}
	public int getQcount() {
		return qcount;
	}
	public void setQcount(int qcount) {
		this.qcount = qcount;
	}
}
