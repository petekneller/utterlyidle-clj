(ns utterlyidle.server
  (:use utterlyidle.core
        clojure.tools.namespace
        [clojure.java.io :only [file]])

  (:import [utterlyidle ClojureBinding]
           [com.googlecode.utterlyidle Binding BasePath RestApplication ServerConfiguration UriTemplate]
           [com.googlecode.utterlyidle.httpserver RestServer]
           [com.googlecode.utterlyidle.dsl DslBindings BindingBuilder]
           [com.googlecode.utterlyidle.modules Modules Module]
           [com.googlecode.totallylazy Pair]))

(defn start
  ([port bindings] (start port "/" bindings))
  ([port base-path & bindings]
    (let [conf (. (ServerConfiguration/defaultConfiguration) port port)
          app (proxy [RestApplication] [(BasePath/basePath base-path)])]
      (.add app
        (Modules/bindingsModule
          (into-array ^Binding (map fn->binding (flatten bindings)))))
      (RestServer. app conf))))