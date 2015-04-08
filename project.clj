(defproject clj-sql-up "0.3.7"
  :description "A simple leiningen plugin for managing sql migrations"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [com.cemerick/pomegranate "0.3.0"]
                 [org.clojure/java.classpath "0.2.2"]]
  :url "https://github.com/ckuttruff/clj-sql-up"

  :profiles {:dev {:dependencies [[org.hsqldb/hsqldb "2.3.2"]]}
             :test {:test-paths ["target/jar-with-migrations.jar"]}
             :test-setup ^:leaky {:resource-paths ["test/test_jar_contents"]
                                  :jar-name "jar-with-migrations.jar"}}

  :aliases {"test" ["do" ["with-profile" "test-setup" "jar"] ["test"]]}
  
  :eval-in-leiningen true)
