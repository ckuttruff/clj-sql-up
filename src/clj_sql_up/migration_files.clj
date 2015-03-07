(ns clj-sql-up.migration-files
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def ^:dynamic *default-migration-dir* "migrations")

(defn migration-id [migr-filename]
  (last (re-find #"^([0-9]+)-" migr-filename)))

(defn- migration-file? [filename]
  (re-find #"([0-9]+)-.*\.clj$" filename))

(defn get-migration-files
  ([] (get-migration-files *default-migration-dir*))
  ([dir-name]
   (->> (io/file dir-name)
        (.listFiles)
        (map #(.getName %))
        (filter migration-file?)
        sort)))

(defn load-migration-file
  ([file] (load-migration-file *default-migration-dir* file))
  ([dir-name file]
   (load-file (str dir-name "/" file))))

(defn migration-filename [migr-id migr-files]
  "Returns the filename associated with the given migration id"
  [migr-id migr-files]
  (->> migr-files
       (filter #(re-find (re-pattern (str migr-id ".*")) %))
       (first)))
