(ns plottr.matrix)

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

(defn rotate-z
  "2x2 matrix defining a rotation about the z-axis"
  [theta]
  [[(Math/cos theta) (Math/sin theta)]
   [(- (Math/sin theta)) (Math/cos theta)]])

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
(defn iso [x-angle y-angle]
  (mm (xrotate x-angle) (yrotate y-angle)))