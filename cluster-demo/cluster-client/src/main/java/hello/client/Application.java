package hello.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import hello.client.crazyclient.CrazyClientApplication;

@ComponentScan
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		CrazyClientApplication crazyClientApplication = ctx.getBean(CrazyClientApplication.class);
		crazyClientApplication.run(args);
	}
}
