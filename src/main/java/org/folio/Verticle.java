package org.folio;

import io.vertx.core.*;
import io.vertx.serviceproxy.ServiceBinder;
import org.folio.services.proxy1.Proxy1;
import org.folio.services.proxy2.Proxy2;


public class Verticle extends AbstractVerticle {
    private Proxy1 proxy1;
    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        Proxy1 proxy1 = Proxy1.createProxy(vertx);
        Proxy2 proxy2 = Proxy2.createProxy(vertx);
        new ServiceBinder(vertx)
                .setAddress(Proxy1.ADDRESS)
                .register(Proxy1.class, Proxy1.create(vertx, proxy2));
        new ServiceBinder(vertx)
                .setAddress(Proxy2.ADDRESS)
                .register(Proxy2.class, Proxy2.create(vertx, proxy1));
        this.proxy1 = proxy1;
    }

    public void start() throws Exception {
        proxy1.sendIds();
    }
}
