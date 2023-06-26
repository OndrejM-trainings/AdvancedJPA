package common;

import jakarta.inject.Inject;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.Unbound;

public abstract class WeldTestBase {

    @Inject
    @Unbound
    private RequestContext requestContext;

    protected void startRequest() {
        requestContext.activate();
    }

    protected void stopRequest() {
        requestContext.invalidate();
        requestContext.deactivate();
    }

}
