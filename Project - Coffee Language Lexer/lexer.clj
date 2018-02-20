;;; EMRE CELIK - 141044024 - CSE 341 - PROJECT 1
;;; ################################# README #################################
;;; interpreter olarak calisir,$ -> clojure lexer.clj yaptiktan sonra
;;; coffee veya coffee dosya.coffee girdisi yapilir.
;;; coffee dosya.coffee girildiginde tokenleri ekrana basar ve interpreter devam eder
;;; coffee olarak girildiginde direk interpreter acilir.
;;; ################################# README #################################

(require '[clojure.string :as str])
(use 'clojure.java.io)

(defn isDigit [val] (and (>= (compare val "0") 0) (<= (compare val "9") 0)))
(def keywords ["and" "or " "not" "equal" "append" "concat" "deffun" "for" "while" "if" "then" "else"])
(def operators ["+" "-" "/" "*" "(" ")"])
(def binaryValue ["true" "false"])
(defn isIntegerValue [val] 
		(if(= (count val) 1) (isDigit val)
		(do
		(loop [m 0]
			(cond
			(and (= m 0)(= (nth val 0) \-)) (inc m)
			(= (isDigit val) false) false
			(= m (- (count val) 1)) true
			(< m (count val)) (recur (inc m))))
		
		)))

(defn isIdentifier [val] 
	(loop [i 0]
	(cond 
		(= false (or (and (>= (int (nth val i)) (int \A)) (<= (int (nth val i)) (int \Z))) (and (>= (int (nth val i)) (int \a)) (<= (int (nth val i)) (int \z))))) false
		(= i (- (count val) 1)) true
	:else
	(recur (inc i)))))

(defn lexer [arg]

	(def result [])
	(def res "")

  (def leftPt "(")
  (def rightPt ")")

  (def text (str/replace arg leftPt " ( "))
  (def text (str/replace text rightPt " ) "))
  (def text (str/split text #"\s+"))
  (def text (subvec text 1))

  (doseq [x text] 
  	(do
  		(cond
  		(>= (.indexOf keywords x) 0) (def result(conj result (str x " : keyword")))
  		(>= (.indexOf operators x) 0) (def result(conj result (str x " : operator")))
  		(>= (.indexOf binaryValue x) 0) (def result(conj result (str x " : binaryValue")))
  		(isIntegerValue x) (def result(conj result (str x " : integerValue"))) 		
  		(isIdentifier	x)(def result(conj result (str x " : identifier")))
  		:else (def result(conj result (str x " : invalid identifier"))))))
 	(doseq [y result] (def res(str res y "\n")))
 	res
)

(defn lexerFile [filename]
  (lexer (slurp filename))
)


(defn interpreter []
	(print "> ")(flush)
	(def line(read-line))
	(println line)(flush)
	(println (lexer line))(flush)
	(interpreter)
)

(defn main []

  (print "$ ")(flush)

  (def strvec (str/split (read-line) #"\s+"))

  (cond
    (not= (nth strvec 0) "coffee") (do (println "error! enter again.")(flush) (main))
    (and (=(nth strvec 0) "coffee") (= (count strvec) 1)) (interpreter)
    (= (count strvec) 2) (do (println (lexerFile (nth strvec 1)))(flush) (interpreter)))
  	:else
  		(do
  		(println "error!") (main)) 
)	


(main)

