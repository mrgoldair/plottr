(ns plottr.core)

(defonce state (atom {}))

(defonce canvas (.getElementById js/document "canvas"))

(defonce ctx (.getContext canvas "2d"))

(defn set-canvas-size! [canvas [width height]]
  (.setAttribute canvas "width" width)
  (.setAttribute canvas "height" height)
  (swap! state assoc :width width)
  (swap! state assoc :height height))

(defn background [colour]
  (set! (.-fillStyle ctx) colour)
  (.fillRect ctx 0 0 (:width @state) (:height @state)))

(defn line-colour [colour]
  (set! (.-strokeStyle ctx) colour))

(defn line-width! [w]
  (set! (.-lineWidth ctx) w))

(defn line [u v]
  (.beginPath ctx)
  (let [[x y] u]
    (.moveTo ctx x y))
  (let [[x y] v]
    (.lineTo ctx x y))
  (.stroke ctx))

(defn path
  "Using `points` (in the form `{:x :y}`) draws a line
   to the canvas"
  [{:keys [x y]} & points]
  (.beginPath ctx)
  (.moveTo ctx x y)
  (doseq [{:keys [x y]} points]
    (.lineTo ctx x y))
  (.stroke ctx))

(defn translate [x y]
  (.translate ctx x y))

(defn rotate [rad]
  (.rotate ctx rad))

(defn scale
  ([x] (scale x x))
  ([x y]
   (.scale ctx x y)))

(defn grid
  "Divide canvas up into `rows` and `cols` separated by `gap`. `cell-fn` should
   return normalised path points. grid will call cell-fn once for each cell
   defined by rows * cols. Returns a list of paths adjusted to absolute canvas size."
  [rows cols gap cell-fn]
  (let [width (:width @state)
        height (:height @state)
        col-width (/ (- width (* gap (dec cols))) cols)
        row-height (/ (- height (* gap (dec rows))) rows)]
    (for [[n r c] (map cons (range (* cols rows))
                            (for [c (range cols) r (range rows)] [c r]))
          :let [col-pos (* c (+ gap col-width))
                row-pos (* r (+ gap row-height))]]
      (map (fn [{:keys [x y]}]
             {:x (+ col-pos (* col-width x))
              :y (+ row-pos (* row-height y))}) (cell-fn n)))))

(defn point
  ([x y] 
   {:x x :y y})
  ([x y z]
   {:x x :y y :z z}))

;; path generator
(defn circle
  ([r] (circle r 360))
  ([r n]
   (let [points (->> (take n (iterate (partial + (/ Math/PI 180)) 0))
                     (map (fn [n] (point (Math/cos n) (Math/sin n)))))]
     (apply line (map (fn [{:keys [x y]}] (point (* r x) (* r y))) points)))))

(defn animate [setup-fn draw-fn]
  (letfn [(foo [time]
            (.requestAnimationFrame js/window foo)
            (.resetTransform ctx)
            (.clearRect ctx 0 0 1000 1000)
            (setup-fn)
            (let [ms (/ time 1000)]
              (draw-fn (merge {:t ms} @state))))]
    (.requestAnimationFrame js/window foo)))

(defn plot
  ""
  [{:keys [setup size draw]}]
  (set-canvas-size! canvas size)
  (.addEventListener js/window "mousemove" (fn [e] (swap! state assoc :mouse-x (* (.-clientX e) (/ (* 2 Math/PI) -1000)))
                                                   (swap! state assoc :mouse-y (* (.-clientY e) (/ (* 2 Math/PI) -1000)))))
  (animate setup draw))