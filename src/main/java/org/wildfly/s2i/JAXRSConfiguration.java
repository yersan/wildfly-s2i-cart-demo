package org.wildfly.s2i;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author <a href="mailto:yborgess@redhat.com">Yeray Borges</a>
 */
@ApplicationPath("/api")
public class JAXRSConfiguration extends Application {
}
