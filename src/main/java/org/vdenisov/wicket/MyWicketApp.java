package org.vdenisov.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.io.ByteArrayOutputStream;

public class MyWicketApp extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        getResourceSettings().getResourceFinders().add(
            new WebApplicationPath(getServletContext(), "markupFolder"));
        final var favicon = getFavicon();
        mountResource("favicon.ico", new ResourceReference("favicon") {
            @Override
            public IResource getResource() {
                return new DynamicImageResource() {
                    @Override
                    protected byte[] getImageData(final Attributes attributes) {
                        return favicon;
                    }
                };
            }
        });
    }

    private byte[] getFavicon() {
        try (final var bos = new ByteArrayOutputStream(); final var is = this.getClass().getResourceAsStream("/favicon.ico")) {
            final byte[] buf = new byte[1024];
            int read;
            while ((read = is.read(buf)) != -1) {
                bos.write(buf, 0, read);
            }
            return bos.toByteArray();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
