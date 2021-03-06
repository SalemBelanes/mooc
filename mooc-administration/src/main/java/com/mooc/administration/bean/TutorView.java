package com.mooc.administration.bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mooc.administration.util.SessionUtils;
import com.mooc.domain.Course;
import com.mooc.domain.Person;
import com.mooc.domain.Tutor;
import com.mooc.services.UserRemoteService;

@ManagedBean @SessionScoped
public class TutorView {

	@EJB
	private UserRemoteService userService;
	private Tutor tutor = new Tutor();
	private String editor;
	private Course course = new Course();

	public String login() {
		Person user = userService.findUser(tutor.getEmail(), tutor.getPassword());
		if (user == null || user instanceof Tutor == false) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Incorrect Email or Passowrd", "Incorrect Email or Passowrd"));
			return "/views/tutor_signin";
		}
		tutor = (Tutor) user;
		SessionUtils.setTutor(tutor);
		return "/views/tutor_home";
	}

	public String createTutor() {
		Person user = userService.findUser(tutor.getEmail(), tutor.getPassword());
		if (user != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Tutor already exists!"));
		}
		userService.create(tutor);
		tutor = new Tutor();
		return "/views/tutor_home";
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setEditor(String text) {
		this.editor = text;
	}

	public String getEditor() {
		return editor;
	}

	public String createCourse() {
		return "/views/create_course";
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
