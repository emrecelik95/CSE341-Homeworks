; *********************************************
; *  341 Programming Languages                *
; *  Fall 2017                                *
; *  Author: Emre Çelik - 141044024           *
; *********************************************

;; utility functions
(load-file "include.clj") ;; "c2i and "i2c"

(use 'clojure.java.io)


;; -----------------------------------------------------
;; HELPERS

;; convert a string into list of symbols
(defn to-char-symbol [word]
  (def sym [])
  (let [rec (fn [i]
    (if (= i (count word))
      (apply list sym)
      (do
      (def sym (conj sym (symbol(nth(map str word)i))))
      (recur (inc i)))))]

    (rec 0)))

;; makes list of list of symbols (paragraph)
(defn to-list [vect]
  (def sym[])
  (let [rec (fn [i]
    (if (= i (count vect))
      (apply list sym)
      (do
        (def sym (conj sym (to-char-symbol (nth vect i))))
        (recur (inc i)))))]

      (rec 0)))


;; get random map alphabet
(defn get-new-alph []
  ;;(def alph '(a b c d e f g h i j k l m n o p q r s t u v w x y z))
  (def alph '(r c a t e))  ;; for testing
  (zipmap (shuffle alph) alph))

;; rest of alphabet that was removed most occur 6 letters
(defn get-rest-alph []
  (def alph '(b c d f g h j k l m p q r s u v w x y z))
  (zipmap (shuffle alph) alph))

;; freq alphabet
(defn get-freq-alph [paragraph]
  (def letters ())
  (doseq [word paragraph]
    (doseq [x word] (def letters (conj letters x))))

  (def most-freq '(e t a o i n))
  (def freqs (frequencies letters))
  (def freqs (sort-by val > freqs))
  (def most-occur (filter (fn [x] (< (.indexOf (keys freqs) x) 6)) (keys freqs)))
  (zipmap most-occur most-freq))

;; -----------------------------------------------------
;; Read Functions
;; read line by line
(defn read-as-list
  "Reads a file containing one word per line and returns a list of words (each word is in turn a list of characters)."
  [filename]

  (def words-vector (with-open [rdr (reader filename)]
   (reduce conj [] (line-seq rdr))))
  (to-list words-vector)

	;; '((a b c) (d e f))
)

;; -----------------------------------------------------
;; HELPERS
;; *** PLACE YOUR HELPER FUNCTIONS BELOW ***


;; lineer search (very slow)
(defn spell-checker-0
	[word]
	(def words (read-as-list "dictionary2.txt"))

  (let [rec (fn [i]
    (cond
      (= word (to-char-symbol (nth words i))) true
      (= i (- (count words) 1)) false
      :else
    (do
      ;;(println (to-char-symbol (nth words i)))
      (recur (inc i)))))]

    (rec 0))
)

;; (indexOf java function,) quick , used in decoders
(defn spell-checker-1
	[word]
  (if (< (.indexOf (read-as-list "dictionary2.txt") word) 0)
  false
  true ))

;; decode word using alph as parameter
(defn get-decoded [word alph]
  (def enc [])
  (let [rec (fn [i]
    (if (= i (count word))
      (apply list enc)
      (do
      (def enc (conj enc (get alph (nth word i))))
      (recur(inc i)))))]

    (rec 0)))

;; -----------------------------------------------------
;; DECODE FUNCTIONS

;; brute force decoder , returns decoder function
(defn Gen-Decoder-A
	[paragraph]

  (def alph (get-new-alph))
  (let [rec (fn [i]
    (if (< i (count paragraph))
    (do
      (def word (nth paragraph i))
      (def dec-word (get-decoded word alph))
      ;;(println word "->" dec-word)
      ))
    (cond
      (>= i (count paragraph))
       (do
          (defn out-fn [w]
            (get-decoded w alph))
           out-fn)
       (= true (spell-checker-1 dec-word))
        (recur (inc i))
      :else
        (do
          (def alph (get-new-alph))
          (recur 0))))]

    (rec 0)))

;; frequence decoder , returns decoder function
(defn Gen-Decoder-B-0
	[paragraph]

  (def rest-alph (get-rest-alph))
  (def freq-alph (get-freq-alph paragraph))
  (def alph (merge rest-alph freq-alph))
  (let [rec (fn [i]
    (if (< i (count paragraph))
    (do
      (def word (nth paragraph i))
      (def dec-word (get-decoded word alph))
      ;;(println word "->" dec-word)
      ))
    (cond
      (>= i (count paragraph))
       (do
          (defn out-fn [w]
            (get-decoded w alph))
           out-fn)
      (= true (spell-checker-1 dec-word))
        (recur (inc i))
      :else
    (do
      (def rest-alph (get-rest-alph))
      (def freq-alph(zipmap (shuffle(keys freq-alph)) (vals freq-alph)))
      (def alph (merge rest-alph freq-alph))
      (recur 0))))]

    (rec 0))
)

(defn Gen-Decoder-B-1
	[paragraph]
  	;you should implement this function
)

;; returns all decoded text using the function that decoder returns
(defn Code-Breaker
	[document decoder]
  (def decAll (decoder document))
  (def text [])
  (doseq [word document] (def text (conj text (decAll word))))
  (apply list text)
)

;; -----------------------------------------------------
;; Test code...

;; prints doc , return value of decoders  (all decoded text)
(defn test_on_test_data
	[]
	(let [doc (read-as-list "document1.txt")]
		(println doc)
    (println "Gen-Decoder-A : " (Code-Breaker doc Gen-Decoder-A))
    (println "Gen-Decoder-B-0 : " (Code-Breaker doc Gen-Decoder-B-0))
	)
)

;; test code...

(test_on_test_data)
