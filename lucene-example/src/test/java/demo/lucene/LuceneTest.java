package demo.lucene;

import static org.apache.lucene.search.BooleanClause.Occur.MUST;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LuceneTest {

	private static final Logger logger = LogManager.getLogger(LuceneTest.class);

	private Path tempDirectory;
	private Directory memoryIndex;
	private Analyzer analyzer;

	@BeforeMethod
	public void beforeMethod() throws IOException {
		tempDirectory = Files.createTempDirectory("lucene-example-");
		memoryIndex = new MMapDirectory(tempDirectory);
		analyzer = new StandardAnalyzer();
	}

	@AfterMethod
	public void afterMethod() throws IOException {
		FileUtils.deleteDirectory(tempDirectory.toFile());
	}

	@Test
	public void testSimpleDocumentSearch() throws IOException {
		// Given
		addDocument(analyzer, document -> {
			document.add(new TextField("test", "1234", Field.Store.YES));
			document.add(new TextField("test", "0000", Field.Store.YES));
			document.add(new TextField("asd", "0000", Field.Store.YES));
			document.add(new TextField("asd", "1234", Field.Store.NO));
		});

		// When
		Query query = new BooleanQuery.Builder()
				.add(new TermQuery(new Term("asd", "1234")), MUST)
				.build();
		List<Document> documents = searchIndex(query);

		// Then
		assertFalse(documents.isEmpty());
	}

	@Test
	public void testFieldSectionBoundaries() throws IOException {
		// Given
		addDocument(analyzer, document -> {
			document.add(new TextField("f", "first ends", Field.Store.YES));
			document.add(new TextField("f", "starts two", Field.Store.YES));
		});

		// When
		Query query = new PhraseQuery("f", "ends", "starts");
		List<Document> documents = searchIndex(query);

		// Then
		assertFalse(documents.isEmpty());
	}

	@Test
	public void testFieldSectionBoundaries2() throws IOException {
		// Given
		Analyzer analyzer = new StopwordAnalyzerBase() {

			@Override
			public int getPositionIncrementGap(String fieldName) {
				return 1;
			}

			@Override
			public int getOffsetGap(String fieldName) {
				return 1;
			}

			// copied from org.apache.lucene.analysis.standard.StandardAnalyzer.createComponents
			@Override
			protected TokenStreamComponents createComponents(String fieldName) {
				final StandardTokenizer src = new StandardTokenizer();
				src.setMaxTokenLength(255);
				TokenStream tokenStream = new LowerCaseFilter(src);
				tokenStream = new StopFilter(tokenStream, stopwords);
				return new TokenStreamComponents(r -> {
					src.setMaxTokenLength(255);
					src.setReader(r);
				}, tokenStream);
			}
		};
		addDocument(analyzer, document -> {
			document.add(new TextField("f", "first ends", Field.Store.YES));
			document.add(new TextField("f", "starts two", Field.Store.YES));
		});

		// When-Then
		assertTrue(searchIndex(new PhraseQuery("f", "ends", "starts")).isEmpty());
		assertFalse(searchIndex(new PhraseQuery("f", "first", "ends")).isEmpty());
		assertFalse(searchIndex(new PhraseQuery("f", "starts", "two")).isEmpty());
	}

	public List<Document> searchIndex(Query query) throws IOException {
		logger.info("query: {}", query.toString());

		IndexReader indexReader = DirectoryReader.open(memoryIndex);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		TopDocs topDocs = searcher.search(query, 1000);

		List<Document> documents = new ArrayList<>();
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			documents.add(searcher.doc(scoreDoc.doc));
		}

		documents.forEach(document -> logger.info("document: {}", document));

		return documents;
	}

	private void addDocument(Analyzer analyzer, Consumer<Document> documentConsumer) throws IOException {
		try (IndexWriter writer = new IndexWriter(memoryIndex, new IndexWriterConfig(analyzer))) {
			Document document = new Document();
			documentConsumer.accept(document);
			writer.addDocument(document);
		}
	}
}
