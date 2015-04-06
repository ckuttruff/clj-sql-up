(ns clj-sql-up.migration-files-test
  (:require [clj-sql-up.migration-files :as mf])
  (:use clojure.test))

(def files
  (mf/get-migration-files "test/clj_sql_up/migrations"))

(deftest get-migration-files
  (testing "finding files in local directories"
   (is (= files ["20130719212020374-zzz.clj" "20130719212023948-ccc.clj"
                 "20130719212027077-bbb.clj" "20130719212031096-aaa.clj"])))

  (testing "finding migration files in jars and directories on the  classpath "
   (is (=  ["20130719212020374-zzz-cp.clj" "20130719212023948-ccc-cp.clj"
            "20130719212027077-bbb-cp.clj" "20130719212031096-aaa-cp.clj"]
           (mf/get-migration-files "clj_sql_up/classpath_migrations")))))

(deftest migration-filename
  (are [id fname] (= (mf/migration-filename id files) fname)
       "20130719212027077" "20130719212027077-bbb.clj"
       "20130719212023948" "20130719212023948-ccc.clj"))
