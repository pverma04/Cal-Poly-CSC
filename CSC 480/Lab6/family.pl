   % Facts
   parent(john, mary).
   parent(mary, susan).
   parent(susan, tom).
   male(john).
   male(tom).
   female(mary).
   female(susan).

   % Rules
   ancestor(X, Y) :- parent(X, Y).
   ancestor(X, Y) :- parent(X, Z), ancestor(Z, Y).

   mother(X, Y) :- parent(X, Y), female(X).
   father(X, Y) :- parent(X, Y), male(X).
   brother(X, Y) :- parent(Z, X), parent(Z, Y), male(X), X \== Y.
   sister(X, Y) :- parent(Z, X), parent(Z, Y), female(X), X \== Y.
   grandparent(X, Y) :- parent(X, Z), parent(Z, Y).
   grandchild(X, Y) :- grandparent(Y, X).
   cousin(X, Y) :- parent(Z, X), parent(W, Y), sibling(Z, W).
   sibling(X, Y) :- parent(Z, X), parent(Z, Y), X \== Y.

   grandmother(X, Y) :- grandparent(X, Y), female(X).
   grandfather(X, Y) :- grandparent(X, Y), male(X).
   uncle(X, Y) :- brother(X, Z), parent(Z, Y).
   aunt(X, Y) :- sister(X, Z), parent(Z, Y).