(ns plottr.sketch.grid
  (:require [plottr.core :as p]
            [plottr.path :as path]))

(defn setup
  "Single-time use"
  []
  (p/background "hsla(24,33%,20%,.2)")
  (p/line-colour "black")
  (p/line-width 1))

(defn draw
  "Repeatedly called"
  [_]
  (doseq [path (p/grid 1 15 5 path/square)]
    (p/path path)))

(p/plot {:size [1500 1500]
         :setup setup
         :draw draw})