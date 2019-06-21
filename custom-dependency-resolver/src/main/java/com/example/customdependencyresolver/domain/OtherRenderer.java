package com.example.customdependencyresolver.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("otherRenderer")
public class OtherRenderer implements Renderer<OtherWidget> {
}
