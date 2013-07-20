(ns clj-sql-up.migration-files-test
  (:require [clj-sql-up.migration-files :as mf])
  (:use clojure.test))

(deftest get-migration-files
  (is (= (mf/get-migration-files "test/clj_sql_up/migrations")
         ["20130719212020374-zzz.clj" "20130719212023948-ccc.clj"
          "20130719212027077-bbb.clj" "20130719212031096-aaa.clj"])))
