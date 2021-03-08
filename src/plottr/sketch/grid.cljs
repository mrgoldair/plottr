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
  (doseq [path (p/grid 15 3 5 path/square)]
    (p/path path))

 (p/path [(p/point 0 0) (p/point 750 740)]))

(p/plot {:size [1500 1500]
         :setup setup
         :draw draw})