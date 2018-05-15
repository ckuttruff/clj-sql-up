(defproject clj-sql-up "0.4.0"
  :description "A simple leiningen plugin for managing sql migrations"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/java.jdbc "0.7.6"]
                 [com.cemerick/pomegranate "1.0.0"]
                 [org.clojure/java.classpath "0.3.0"]]
  :url "https://github.com/ckuttruff/clj-sql-up"

  :profiles {:dev {:dependencies [[org.hsqldb/hsqldb "2.4.0"]]}
             :test {:test-paths ["target/jar-with-migrations.jar"]}
             :test-setup ^:leaky {:resource-paths ["test/test_jar_contents"]
                                  :jar-name "jar-with-migrations.jar"}}

  :aliases {"test" ["do" ["with-profile" "test-setup" "jar"] ["test"]]}

  :eval-in-leiningen true)
