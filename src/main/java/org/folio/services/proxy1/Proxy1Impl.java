package org.folio.services.proxy1;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.collections4.ListUtils;
import org.folio.services.proxy2.Proxy2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


class Proxy1Impl implements Proxy1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Proxy1Impl.class);
    private int partitionSize = 2;
    private List<String> allFileIds = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private Iterator<List<String>> partitionIterator = ListUtils.partition(allFileIds, partitionSize).iterator();
    private Proxy2 proxy2;

    public Proxy1Impl(Proxy2 proxy2) {
        this.proxy2 = proxy2;
    }

    @Override
    public void sendIds() {
        LOGGER.info("Proxy1 receives request, reading more ids...");
        if (partitionIterator.hasNext()) {
            List<String> ids = partitionIterator.next();
            JsonObject request = new JsonObject().put("ids", ids);
            LOGGER.info("Proxy1 sends more UUIDs to Proxy2: " + ids);
            proxy2.export(request);
        } else {
            LOGGER.info("Export is done, no more ids to send");
        }
    }
}
