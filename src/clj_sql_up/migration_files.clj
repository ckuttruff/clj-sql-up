(ns clj-sql-up.migration-files
  (:require [clojure.java.io :as io]
            ;;[clojure.java.jdbc :as sql]
            [clojure.string :as str]))

(defn migration-id [migr-filename]
  (last (re-find #"^([0-9]+)-" migr-filename)))

(defn- migration-file? [filename]
  (re-find #"([0-9]+)-.*\.clj$" filename))

;; TODO: find a way to set migration dir dynamically
(defn get-migration-files 
  ([] (get-migration-files "migrations"))
  ([dir-name] (->> (io/file dir-name)
                   (.listFiles)
                   (map #(.getName %))
                   (filter migration-file?)
                   sort)))
