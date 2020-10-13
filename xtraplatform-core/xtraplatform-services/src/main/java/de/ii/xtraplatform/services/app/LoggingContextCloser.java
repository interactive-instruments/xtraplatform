package de.ii.xtraplatform.services.app;

import de.ii.xtraplatform.runtime.domain.Logging;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zahnen
 */
@Component
@Provides
@Instantiate
public class LoggingContextCloser implements ContainerResponseFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingContextCloser.class);
    private static final String REQUEST_ID = "X-Request-Id";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (Logging.has(Logging.CONTEXT.REQUEST)) {
            responseContext.getHeaders()
                    .putSingle(REQUEST_ID, Logging.get(Logging.CONTEXT.REQUEST));

            if (responseContext.getEntity() instanceof StreamingOutput && !(responseContext.getEntityStream() instanceof OutputStreamCloseListener)) {
                responseContext.setEntityStream(new OutputStreamCloseListener(responseContext));
            } else {
                closeLoggingContext(responseContext);
            }
        }
    }

    private void closeLoggingContext(ContainerResponseContext responseContext) {
        LOGGER.debug("Sending response: {} {}", responseContext.getStatus(), responseContext.getStatusInfo());

        Logging.remove(Logging.CONTEXT.REQUEST);
    }

    private class OutputStreamCloseListener extends OutputStream {
        private final ContainerResponseContext responseContext;
        private final OutputStream entityStream;

        public OutputStreamCloseListener(ContainerResponseContext responseContext) {
            this.responseContext = responseContext;
            this.entityStream = responseContext.getEntityStream();
        }

        @Override
        public void write(int i) throws IOException {
            entityStream.write(i);
        }

        @Override
        public void write(byte[] b) throws IOException {
            entityStream.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            entityStream.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            entityStream.flush();
        }

        @Override
        public void close() throws IOException {
            closeLoggingContext(responseContext);

            entityStream.close();
        }
    }
}