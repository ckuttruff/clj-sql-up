;; migrations/20130719212027077-bbb.clj

(defn up []
  ["create table bbb (id int)"])

(defn down []
  ["drop table bbb"])
