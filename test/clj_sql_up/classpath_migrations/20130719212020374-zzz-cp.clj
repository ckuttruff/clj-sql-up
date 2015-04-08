;; migrations/20130719212020374-zzz.clj

(defn up []
  ["create table zzz (id int)"])

(defn down []
  ["drop table zzz"])
