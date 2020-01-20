package demo.lucene;

import static org.apache.lucene.search.BooleanClause.Occur.MUST;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LuceneTest {

	private static final Logger logger = LogManager.getLogger(LuceneTest.class);

	private Directory memoryIndex;
	private Analyzer analyzer;

	@BeforeMethod
	public void beforeMethod() {
		memoryIndex = new RAMDirectory();
		analyzer = new StandardAnalyzer();
	}

	@Test
	public void testSimpleDocumentSearch() throws IOException {
		// Given
		addDocument(document -> document.add(new TextField("test", "1234", Field.Store.YES)));

		// When
		List<Document> documents = searchIndex("test", "1234");

		// Then
		assertFalse(documents.isEmpty());
	}

	public List<Document> searchIndex(String searchField, String searchTerm) throws IOException {
		Query query = new BooleanQuery.Builder()
				.add(new TermQuery(new Term(searchField, searchTerm)), MUST)
				.build();
		logger.info("query: {}", query.toString());

		IndexReader indexReader = DirectoryReader.open(memoryIndex);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		TopDocs topDocs = searcher.search(query, 1000);

		List<Document> documents = new ArrayList<>();
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			documents.add(searcher.doc(scoreDoc.doc));
		}
		return documents;
	}

	private void addDocument(Consumer<Document> documentConsumer) throws IOException {
		try (IndexWriter writer = new IndexWriter(memoryIndex, new IndexWriterConfig(analyzer))) {
			Document document = new Document();
			documentConsumer.accept(document);
			writer.addDocument(document);
		}
	}
}
