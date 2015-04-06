(ns clj-sql-up.migration-files
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.java.classpath :as cp])
  (:import [java.util.jar JarFile]))

(def ^:dynamic *migration-dir* "migrations")

(defn migration-id [migr-filename]
  (last (re-find #"^([0-9]+)-" migr-filename)))

(defn- migration-file? [filename]
  (re-find #"([0-9]+)-.*\.clj$" filename))

(defn- classpath-migration-dirs [migration-dir]
  (filter #(.isDirectory  %)
          (map #(io/file % migration-dir)
               (cp/classpath-directories))))

(defn- migration-files-in-jar [migration-dir ^JarFile jar-file]
  (->> (cp/filenames-in-jar jar-file)
       (map io/file)
       (filter #(= migration-dir (.getParent %)))
       (map #(.getName %))
       (filter migration-file?)))

(defn- migration-files-in-dir [dir-name]
  (->> (io/file dir-name)
       (.listFiles)
       (map #(.getName %))
       (filter migration-file?)))

(defn- strip-trailing-slashes [a-str]
  (str/replace a-str #"/+$" ""))

(defn get-migration-files
  ([] (get-migration-files *migration-dir*))
  ([dir-name]
   (let [dir-name (strip-trailing-slashes dir-name)]
    (sort
     (distinct
      (concat
       (migration-files-in-dir dir-name)
       (mapcat migration-files-in-dir (classpath-migration-dirs dir-name))
       (mapcat (partial migration-files-in-jar dir-name) (cp/classpath-jarfiles))))))))

(defn load-migration-file
  ([file] (load-migration-file *migration-dir* file))
  ([dir-name file]
   (let [resource-path (str dir-name "/" file)]
    (-> (or (io/resource resource-path) resource-path)
        slurp
        load-string))))

(defn migration-filename [migr-id migr-files]
  "Returns the filename associated with the given migration id"
  [migr-id migr-files]
  (->> migr-files
       (filter #(re-find (re-pattern (str migr-id ".*")) %))
       (first)))
