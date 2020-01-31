package demo.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.testng.annotations.Test;

@Test
public class TokenizationTest {

	private static final Logger logger = LogManager.getLogger(TokenizationTest.class);

	public void testTokenization() throws IOException {
		Analyzer analyzer = new StandardAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("myfield", new StringReader("some text goes here"));
		// The Analyzer class will construct the Tokenizer, TokenFilter(s), and CharFilter(s),
		//   and pass the resulting Reader to the Tokenizer.
		OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		try {
			tokenStream.reset(); // Resets this stream to the beginning. (Required)
			while (tokenStream.incrementToken()) {
				// Use AttributeSource.reflectAsString(boolean)
				// for token stream debugging.
				logger.info("token: {}", tokenStream.reflectAsString(true));

				logger.info("\ttoken start offset: {}", Integer.valueOf(offsetAttribute.startOffset()));
				logger.info("\ttoken end offset: {}", Integer.valueOf(offsetAttribute.endOffset()));

				String termString = String.valueOf(charTermAttribute.buffer());
				String termHex = Hex.encodeHexString(termString.getBytes());
				logger.info("\tcharTermAttribute buffer: {} - {}", termString, termHex);
			}
			tokenStream.end();   // Perform end-of-stream operations, e.g. set the final offset.
		} finally {
			tokenStream.close(); // Release resources associated with this stream.
		}
	}
}
