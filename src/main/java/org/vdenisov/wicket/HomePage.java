package org.vdenisov.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class HomePage extends WebPage {
    public HomePage() {
        add(new Label("helloMessage", "Hello WicketWorld!"));
        add(new Link<Void>("nextPageLink") {
            @Override
            public void onClick() {
                HomePage.this.setResponsePage(NextPage.class);
            }
        });
    }
}
