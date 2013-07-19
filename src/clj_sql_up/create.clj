(ns clj-sql-up.create)

(defn migration-text [path]
  (str ";; " path "\n\n"
       "(defn up []\n"
       "  [])\n\n"
       "(defn down []\n"
       "  [])\n"))

(defn migration-path [name]
  (let [date  (.format (java.text.SimpleDateFormat. "yyyyMMddHHmmssSSS")
                       (java.util.Date.))]
    (str "migrations/" date "-" name ".clj")))

(defn create-migration-file [name]
  (let [path (migration-path name)]
    (println (str "Creating file: " path))
    (spit path (migration-text path))))

(defn create-migration-dir []
  (.mkdir (java.io.File. "migrations")))

(defn create [args]
  (create-migration-dir)
  (let [name (first args)]
    (create-migration-file name)))


