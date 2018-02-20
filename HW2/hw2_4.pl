

myUnion([],A,A).
myUnion([H1|T1], L, U) :- member(H1, L) , myUnion(T1, L, U).
myUnion([H1|T1], L, [H1|T2]) :- myUnion(T1, L, T2).
union(L,S,U) :- myUnion(L,S,U).


myInter([],_,[]).
myInter([H1|T1], L, I) :- member(H1, L) , I = [H1|T2] , myInter(T1, L, T2).
myInter([_|T1], L, I) :- myInter(T1, L, I).
intersect(L1,L2,I) :- myInter(L1,L2,I).


myFlatten(L, F) :- myFlatten(L, F, []).
myFlatten([], A, A).
myFlatten([H|T], A, B) :- myFlatten(H, A, B2) , myFlatten(T, B2, B).
myFlatten(X, [X|B], B).
flatten(L,F) :- myFlatten(L,F).