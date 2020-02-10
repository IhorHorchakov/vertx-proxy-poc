package org.folio.services.proxy2;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.folio.services.proxy1.Proxy1;

@ProxyGen
public interface Proxy2 {
  String ADDRESS = "proxy2.queue";

  static Proxy2 create(Vertx vertx, Proxy1 proxy1) {
    return new Proxy2Impl(vertx, proxy1);
  }

  static Proxy2 createProxy(Vertx vertx) {
    return new Proxy2VertxEBProxy(vertx, ADDRESS);
  }

  void export(JsonObject request);
}
