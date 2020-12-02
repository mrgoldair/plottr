(ns plottr.sketch
  (:require [plottr.core :as p]
            [plottr.path :as path]
            [plottr.matrix :as m]))

(defn setup
  ""
  []
  (p/background "hsla(24,70%,80%,.3)")
  (p/line-colour "hsla(24,70%,80%,.75)"))

(defn draw [{:keys [t mouse-x mouse-y]}]
  (p/translate 500 500)
  ;; mouse-pos as iso(metric) angle
  (let [transform (partial m/mv (m/iso mouse-y mouse-x))]
    (p/path (map #(map transform %) (path/cube 50)))))

(p/plot {:size [1000 1000]
         :setup setup
         :draw draw})