(ns clj-sql-up.create)

(def ^:dynamic *default-migration-dir* "migrations")

(defn migration-text [path]
  (str ";; " path "\n\n"
       "(defn up []\n"
       "  [])\n\n"
       "(defn down []\n"
       "  [])\n"))

(defn migration-path [name]
  (let [date  (.format (java.text.SimpleDateFormat. "yyyyMMddHHmmssSSS")
                       (java.util.Date.))]
    (str *default-migration-dir* "/" date "-" name ".clj")))

(defn create-migration-file [name]
  (let [path (migration-path name)]
    (println (str "Creating file: " path))
    (spit path (migration-text path))
    path))

(defn create-migration-dir []
  (.mkdir (java.io.File. *default-migration-dir*)))

(defn create [args]
  (create-migration-dir)
  (let [name (first args)]
    (create-migration-file name)))
