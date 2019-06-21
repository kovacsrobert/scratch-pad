package com.example.customdependencyresolver.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class WidgetFactory {

	private final Renderer<OtherWidget> otherWidgetRenderer;
	private final Renderer<HtmlWidget> htmlWidgetRenderer;

	public WidgetFactory(
			@Qualifier("otherRenderer") Renderer<OtherWidget> otherWidgetRenderer,
			@Qualifier("htmlRenderer") Renderer<HtmlWidget> htmlWidgetRenderer) {
		this.otherWidgetRenderer = otherWidgetRenderer;
		this.htmlWidgetRenderer = htmlWidgetRenderer;
	}
}
