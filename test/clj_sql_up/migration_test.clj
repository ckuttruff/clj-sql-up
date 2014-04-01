(ns clj-sql-up.migration-test
  (:require [clj-sql-up.migrate :as m]
            [clj-sql-up.migration-files :refer [DEFAULT_MIGRATION_DIR]]
            [clojure.java.jdbc :as sql])
  (:use clojure.test))


(def db-spec {:subprotocol "hsqldb"
              :subname "mem:testdb"})

(defn- count-records [db table-name]
  (->
    (sql/query db-spec [(str "select count(*) from " table-name)])
    first
    :c1))

(deftest test-migrate-and-rollback

  (with-redefs [DEFAULT_MIGRATION_DIR "test/clj_sql_up/migrations"]

    (testing "migrate"
       (m/migrate db-spec)

       (let [completed-migrations (m/completed-migrations db-spec)]
         (is (= 4 (count completed-migrations)))
         (is (= 0 (count-records db-spec "aaa")))
         (is (= 0 (count-records db-spec "bbb")))
         (is (= 0 (count-records db-spec "ccc")))
         (is (= 0 (count-records db-spec "zzz")))))

    (testing "rollback: 1"
      (m/rollback db-spec 1)
      (let [completed-migrations (m/completed-migrations db-spec)]
        (is (= 3 (count completed-migrations)))
        (is (= 0 (count-records db-spec "bbb")))
        (is (= 0 (count-records db-spec "ccc")))
        (is (= 0 (count-records db-spec "zzz")))

        (is (thrown? Exception (count-records db-spec "aaa")))))

    (testing "rollback: 2"

      (m/rollback db-spec 2)
      (let [completed-migrations (m/completed-migrations db-spec)]
        (is (= 1 (count completed-migrations)))
        (is (= 0 (count-records db-spec "zzz")))

        (is (thrown? Exception (count-records db-spec "ccc")))
        (is (thrown? Exception (count-records db-spec "bbb")))
        (is (thrown? Exception (count-records db-spec "aaa")))))

    (testing "migrate again"
       (m/migrate db-spec)

       (let [completed-migrations (m/completed-migrations db-spec)]
         (is (= 4 (count completed-migrations)))
         (is (= 0 (count-records db-spec "aaa")))
         (is (= 0 (count-records db-spec "bbb")))
         (is (= 0 (count-records db-spec "ccc")))
         (is (= 0 (count-records db-spec "zzz")))))


  ))






