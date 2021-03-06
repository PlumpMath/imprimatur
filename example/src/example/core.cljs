(ns example.core
  (:require [brutha.core :as br]
            [imprimatur.core :as imp]))

(def data
  (atom ["foo"
         {:time 0}
         #{:bar '(foo bar)}
         true
         (subvec [1 2 3] 0 2)
         nil
         {:foo "hello"
          :bar (js/Date.)
          :baz (random-uuid)
          :quz (into cljs.core.PersistentQueue.EMPTY [1 2 3])}]))

(def visibility
  (atom {}))

(def main
  (.getElementById js/document "main"))

(defn render []
  (br/mount
   (imp/print
    {:root @data
     :visibility @visibility
     :on-toggle #(swap! visibility imp/toggle %)})
   main))

(defn inc-time []
  (swap! data update-in [1 :time] inc)
  (js/setTimeout inc-time 1000))

(add-watch visibility :change (fn [_ _ _ _] (render)))
(add-watch data       :change (fn [_ _ _ _] (render)))

(render)
(inc-time)
