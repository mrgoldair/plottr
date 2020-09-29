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

