(ns clj-sql-up.create)

(defn migration-text [path]
  (str ";; " path "\n\n"
       "(defn up []\n"
       "  [])\n\n"
       "(defn down []\n"
       "  [])\n"))

(defn migration-path [name]
  (let [date (-> (java.text.SimpleDateFormat. "yyyyMMddHHmmssSSS")
                 (.format (java.util.Date.)))]
    (str "migrations/" date "-" name ".clj")))

(defn create-migration-dir []
  (.mkdir (java.io.File. "migrations")))

(defn create-migration-file [name]
  (create-migration-dir)
  (let [path (migration-path name)]
    (spit path (migration-text path))))

(defn create [args]
  (let [name (first args)]
    (create-migration-file name)))


