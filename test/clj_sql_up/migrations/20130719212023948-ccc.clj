;; migrations/20130719212023948-ccc.clj

(defn up []
  ["create table ccc (id int)"])

(defn down []
  ["drop table ccc"])
