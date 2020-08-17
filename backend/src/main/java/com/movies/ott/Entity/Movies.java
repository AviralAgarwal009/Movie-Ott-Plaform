package com.movies.ott.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="movie")
public class Movies {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="views")
	private int views;
	
	@Column(name="plot")
	private String plot;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name="rating")
	private float rating;
	
	@Column(name="poster")
	private String poster;
	
	public Movies() {
		
	}

	public Movies(String title, int views, String plot, String genre, float rating, String poster) {
		super();
		this.title = title;
		this.views = views;
		this.plot = plot;
		this.genre = genre;
		this.rating = rating;
		this.poster = poster;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Override
	public String toString() {
		return "Movies [id=" + id + ", title=" + title + ", views=" + views + ", plot=" + plot + ", genre=" + genre
				+ ", rating=" + rating + ", poster=" + poster + ", getId()=" + getId() + ", getTitle()=" + getTitle()
				+ ", getViews()=" + getViews() + ", getPlot()=" + getPlot() + ", getGenre()=" + getGenre()
				+ ", getRating()=" + getRating() + ", getPoster()=" + getPoster() +  "]";
	}
	
	
	
	
	

}
