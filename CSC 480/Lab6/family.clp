(deftemplate person
   (slot name)
   (slot parent)
   (slot gender))

(deffacts initial-facts
   (person (name john) (parent none) (gender male))
   (person (name mary) (parent john) (gender female))
   (person (name susan) (parent mary) (gender female))
   (person (name tom) (parent susan) (gender male))
   (person (name alice) (parent tom) (gender female))
   (person (name james) (parent john) (gender male))
   (person (name linda) (parent james) (gender female))
   (person (name kate) (parent mary) (gender female))
   (person (name paul) (parent kate) (gender male)))

(defrule find-ancestor
   (person (name ?x) (parent ?y))
   (person (name ?y) (parent ?z))
   =>
   (assert (ancestor ?x ?z)))

(defrule find-grandparent
   (person (name ?x) (parent ?y))
   (person (name ?y) (parent ?z))
   =>
   (assert (grandparent ?z ?x)))

(defrule find-grandchild
   (grandparent ?x ?y)
   =>
   (assert (grandchild ?y ?x)))

(defrule find-mother
   (person (name ?x) (parent ?y) (gender female))
   =>
   (assert (mother ?x ?y)))

(defrule find-father
   (person (name ?x) (parent ?y) (gender male))
   =>
   (assert (father ?x ?y)))

(defrule find-brother
   (person (name ?x) (parent ?z) (gender male))
   (person (name ?y) (parent ?z))
   (test (neq ?x ?y))
   =>
   (assert (brother ?x ?y)))

(defrule find-sister
   (person (name ?x) (parent ?z) (gender female))
   (person (name ?y) (parent ?z))
   (test (neq ?x ?y))
   =>
   (assert (sister ?x ?y)))

(defrule find-cousin
   (person (name ?x) (parent ?px))
   (person (name ?y) (parent ?py))
   (sibling ?px ?py)
   =>
   (assert (cousin ?x ?y)))

(defrule find-uncle
   (brother ?x ?p)
   (person (name ?y) (parent ?p))
   =>
   (assert (uncle ?x ?y)))

(defrule find-aunt
   (sister ?x ?p)
   (person (name ?y) (parent ?p))
   =>
   (assert (aunt ?x ?y)))

(defrule find-second-cousin
   (person (name ?x) (parent ?px))
   (person (name ?y) (parent ?py))
   (cousin ?px ?py)
   =>
   (assert (second-cousin ?x ?y)))