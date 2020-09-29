(ns plottr.sketch
  (:require [plottr.core :as p]
            [plottr.path :as path]))

(defn setup
  ""
  []
  (p/background "hsla(24,70%,80%,.3)")
  (p/line-colour "hsla(24,70%,80%,.75)"))

(defn scale
  "Scale vector `v` by scalar `s`"
  [s v]
  (map (partial * s) v))

(defn mv
  "Transform vector `v` into the space defined by matrix `m`"
  [m v]
  (->> (map scale v m)
       (apply map (fn [& rest] (apply + rest)))))

(defn mm
  "Returns a new space defined by the composition of one matrix, `m`, by another matrix, `n`"
  [m n]
  (map (partial mv m) n))

(defn xrotate
  "3x3 matrix defining a rotation about the x-axis"
  [theta]
  [[1 0 0]
   [0 (Math/cos theta) (- (Math/sin theta))]
   [0 (Math/sin theta)  (Math/cos theta)]])

(defn yrotate
  "3x3 matrix defining a rotation about the y-axis"
  [theta]
  [[(Math/cos theta) 0 (Math/sin theta)]
   [0 1 0]
   [(- (Math/sin theta)) 0 (Math/cos theta)]])

(defn zrotate
  "3x3 matrix defining a rotation about the z-axis"
  [theta]
  [[(Math/cos theta) (Math/sin theta) 0]
   [(- (Math/sin theta)) (Math/cos theta) 0]
   [0 0 1]])

;; perspective
(defn iso [x y]
  (mm (xrotate x) (yrotate y)))

(defn draw [{:keys [t mouse-x mouse-y]}]
  (p/translate 500 500)
  (let [transform (partial mv (iso mouse-y mouse-x))]
    (doseq [[p p'] (map #(map transform %) (path/cube 50))]
      (p/line p p'))))

(p/plot {:size [1000 1000]
         :setup setup
         :draw draw})