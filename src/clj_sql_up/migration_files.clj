(ns clj-sql-up.migration-files
  (:require [clojure.java.io :as io]
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

(defn migration-filename [migr-id migr-files]
  "Returns the filename associated with the given migration id"
  [migr-id migr-files]
  (->> migr-files
       (filter #(re-find (re-pattern (str migr-id ".*")) %))
       (first)))