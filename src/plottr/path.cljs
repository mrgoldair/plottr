(ns plottr.path)

;; list of line segments?
(defn cube
  ([] (cube 1))
  ([r]
   (let [seg [[[-1 -1 -1] [1 -1 -1]]
              [[-1 -1 -1] [-1 -1 1]]
              [[-1 -1 -1] [-1 1 -1]]
              [[-1 1 -1] [1 1 -1]]
              [[-1 1 -1] [-1 1 1]]
              [[1 -1 1] [1 1 1]]
              [[-1 -1 1] [1 -1 1]]]]
     (map (fn [[p p']] (partition 3 (map (partial * r) (concat p p')))) seg))))

(defn square
  ""
  []
  [[0 0] [0 1] [1 1] [1 0] [0 0]])

;; (defn circle
;;   ([r] (circle r 360))
;;   ([r n]
;;    (let [points (->> (take n (iterate (partial + (/ Math/PI 180)) 0))
;;                      (map (fn [n] (point (Math/cos n) (Math/sin n)))))]
;;      (map (fn [{:keys [x y]}] (point (* r x) (* r y))) points))))

