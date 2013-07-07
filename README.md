# clj-sql-up

A Leiningen plugin to manage SQL migrations as simply as possible. 

## Usage

Put `[clj-sql-up "0.1.0]` into the `:plugins` vector of your project.clj.

Basic usage (though it doesn't get much more complicated):

    $ lein clj-sql-up create create-posts

## TODO

	$ lein clj-sql-up migrate
    $ lein clj-sql-up rollback

## License

Copyright © 2013 Christopher Kuttruff

Distributed under the Eclipse Public License, the same as Clojure.
