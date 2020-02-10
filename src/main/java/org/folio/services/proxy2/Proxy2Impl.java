package org.folio.services.proxy2;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.folio.services.proxy1.Proxy1;

import java.util.List;


class Proxy2Impl implements Proxy2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Proxy2Impl.class);
    private final Vertx vertx;
    private WorkerExecutor executor;
    private Proxy1 proxy1;

    public Proxy2Impl(Vertx vertx, Proxy1 proxy1) {
        this.vertx = vertx;
        this.proxy1 = proxy1;
        this.executor = this.vertx.createSharedWorkerExecutor("export-thread-worker");
    }

    @Override
    public void export(JsonObject jsonRequest) {
        List<String> ids = jsonRequest.getJsonArray("ids").getList();
        LOGGER.info("Proxy2 receives: " + ids);
        this.executor.executeBlocking(blockingFuture -> {
            LOGGER.info("Proxy2 asks Proxy1 to send more UUIDs");
            proxy1.sendIds();
        }, null);
    }
}
