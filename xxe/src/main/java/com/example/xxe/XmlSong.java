package com.example.xxe;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class XmlSong {

	private String artist;
	private String title;
	private String album;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("artist", artist)
				.append("title", title)
				.append("album", album)
				.toString();
	}
}
