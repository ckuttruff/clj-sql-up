;; migrations/20130719212031096-aaa.clj

(defn up []
  ["create table aaa (id int)"])

(defn down []
  ["drop table aaa"])
