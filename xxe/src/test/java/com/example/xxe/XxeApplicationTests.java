package com.example.xxe;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;
import org.xml.sax.SAXNotRecognizedException;

@Test
public class XxeApplicationTests {

	private static final Logger logger = Logger.getLogger("XxeApplicationTests");

	public void readValidFile() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		XmlSongHandler xmlSongHandler = new XmlSongHandler();
		try (InputStream inputStream = new ClassPathResource("song.xml").getInputStream()) {
			saxParser.parse(inputStream, xmlSongHandler);
		}
		List<XmlSong> xmlSongs = xmlSongHandler.getXmlSongs();
		logger.info("xmlSongs: " + xmlSongs);
	}

	public void readInvalidFile() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		XmlSongHandler xmlSongHandler = new XmlSongHandler();
		try (InputStream inputStream = new ClassPathResource("xxe_song.xml").getInputStream()) {
			saxParser.parse(inputStream, xmlSongHandler);
		}
		List<XmlSong> xmlSongs = xmlSongHandler.getXmlSongs();
		logger.info("xmlSongs: " + xmlSongs);
	}

	@Test(expectedExceptions = SAXNotRecognizedException.class)
	public void readInvalidFileWithProtection() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		XmlSongHandler xmlSongHandler = new XmlSongHandler();
		factory.setFeature("https://xml.org/sax/features/external-general-entities", false);
		saxParser.getXMLReader().setFeature("https://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("https://apache.org/xml/features/disallow-doctype-decl", true);
		try (InputStream inputStream = new ClassPathResource("xxe_song.xml").getInputStream()) {
			saxParser.parse(inputStream, xmlSongHandler);
		}
		List<XmlSong> xmlSongs = xmlSongHandler.getXmlSongs();
		logger.info("xmlSongs: " + xmlSongs);
	}
}
