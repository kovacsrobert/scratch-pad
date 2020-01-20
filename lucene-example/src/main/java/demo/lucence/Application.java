package demo.lucence;

import static org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE;
import static org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE_OR_APPEND;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(Application.class);

	private static final Path INDEX_PATH = Paths.get("./lucene-index");

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("App started...");

		IndexWriterConfig indexWriterConfig = initialize();
		processCommands(indexWriterConfig);

		logger.info("App stopping...");
	}

	private IndexWriterConfig initialize() {
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		if (Files.exists(INDEX_PATH)) {
			indexWriterConfig.setOpenMode(CREATE_OR_APPEND);
		} else {
			indexWriterConfig.setOpenMode(CREATE);
		}
		return indexWriterConfig;
	}

	private void processCommands(IndexWriterConfig indexWriterConfig) throws IOException {
		final Directory directory = FSDirectory.open(INDEX_PATH);
		try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
				Scanner scanner = new Scanner(System.in)) {
			while (!Thread.currentThread().isInterrupted()) {
				String[] nextLineParts = scanner.nextLine().split("\\s");
				if (nextLineParts.length == 2) {
					Document doc = new Document();
					doc.add(new StringField(nextLineParts[0], nextLineParts[1], Field.Store.YES));
					indexWriter.addDocument(doc);
				} else if (nextLineParts.length == 1) {
					String nextLine = nextLineParts[0];
					if (nextLine.equalsIgnoreCase("quit")) {
						break;
					} else {
						try (IndexReader indexReader = DirectoryReader.open(directory)) {
							final IndexSearcher searcher = new IndexSearcher(indexReader);
							TopDocs topDocs = searcher.search(new TermQuery(new Term(nextLine)), Integer.MAX_VALUE);
							logger.info("topDocs: {}/{}", topDocs.totalHits, topDocs.scoreDocs);
						}
					}
				}
			}
		}
	}
}
