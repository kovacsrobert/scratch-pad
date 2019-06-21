package com.example.customdependencyresolver;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("htmlRenderer")
public class HtmlRenderer implements Renderer<HtmlWidget> {
}
