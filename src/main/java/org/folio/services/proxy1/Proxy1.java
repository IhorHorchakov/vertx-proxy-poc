package org.folio.services.proxy1;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Vertx;
import org.folio.services.proxy2.Proxy2;

@ProxyGen
public interface Proxy1 {
  String ADDRESS = "proxy1.queue";

  static Proxy1 create(Vertx vertx, Proxy2 proxy2) {
    return new Proxy1Impl(proxy2);
  }

  static Proxy1 createProxy(Vertx vertx) {
    return new Proxy1VertxEBProxy(vertx, ADDRESS);
  }

  void sendIds();
}
