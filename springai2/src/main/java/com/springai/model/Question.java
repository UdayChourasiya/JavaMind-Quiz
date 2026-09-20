package com.springai.model;

public class Question {
private int qno;
private String question;
private boolean ans;
private boolean userAns;
private int marks;
public int getMarks() {
	return marks;
}
public void setMarks(int marks) {
	this.marks = marks;
}
public int getQno() {
	return qno;
}
public void setQno(int qno) {
	this.qno = qno;
}
public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public boolean isAns() {
	return ans;
}
public void setAns(boolean ans) {
	this.ans = ans;
}
public boolean isUserAns() {
	return userAns;
}
public void setUserAns(boolean userAns) {
	this.userAns = userAns;
}

}
