package com.example.xxe;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSongHandler extends DefaultHandler {

	private static final Logger logger = Logger.getLogger("XmlSongHandler");

	private final List<XmlSong> xmlSongs = new ArrayList<>();
	private XmlSong currentXmlSong;
	private StringBuilder data = null;

	private boolean songFlag;
	private boolean artistFlag;
	private boolean titleFlag;
	private boolean albumFlag;

	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
		logger.fine("startElement: " + qName);
		if ("song".equalsIgnoreCase(qName)) {
			currentXmlSong = new XmlSong();
			songFlag = true;
		} else if ("artist".equalsIgnoreCase(qName)) {
			artistFlag = true;
		} else if ("title".equalsIgnoreCase(qName)) {
			titleFlag = true;
		} else if ("album".equalsIgnoreCase(qName)) {
			albumFlag = true;
		}
		data = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		logger.fine("startElement: " + qName);
		if ("song".equalsIgnoreCase(qName)) {
			songFlag = false;
			xmlSongs.add(currentXmlSong);
			currentXmlSong = null;
		} else if ("artist".equalsIgnoreCase(qName)) {
			artistFlag = false;
			currentXmlSong.setArtist(data.toString());
		} else if ("title".equalsIgnoreCase(qName)) {
			titleFlag = false;
			currentXmlSong.setTitle(data.toString());
		} else if ("album".equalsIgnoreCase(qName)) {
			albumFlag = false;
			currentXmlSong.setAlbum(data.toString());
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		String currentData = new String(ch, start, length);
		data.append(currentData);
	}

	public List<XmlSong> getXmlSongs() {
		return xmlSongs;
	}
}
