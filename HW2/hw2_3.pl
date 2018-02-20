
enrollment(1,a).
enrollment(1,b).
enrollment(2,a).
enrollment(3,b).
enrollment(4,c).
enrollment(5,d).
enrollment(6,d).
enrollment(6,a).

enrollment(7,a).
enrollment(7,b).
enrollment(8,f).
enrollment(8,d).
enrollment(9,f).
enrollment(9,c).
enrollment(10,g).
enrollment(10,e).
enrollment(11,d).
enrollment(11,g).

when(a,10).
when(b,12).
when(c,11).
when(d,16).
when(e,17).
when(f,15).
when(g,16).

where(a,101).
where(b,104).
where(c,102).
where(d,103).
where(e,103).
where(f,105).
where(g,105).

schedule(S,P,T) :- enrollment(S,Session) , where(Session,P) , when(Session,T).

usage(P,T) :- where(Session, P) , when(Session, T).

conflict(X,Y) :- when(X,T1) , when(Y,T2) , where(X,P1) , where(Y,P2) , (abs(T1-T2) < 2; =(P1,P2)).

meet(X,Y) :- enrollment(X,A) , enrollment(Y,B) , 
				when(A,T1) , when(B,T2) , where(A,P1) , where(B,P2) , 
					abs(T1-T2) < 2, =(P1,P2).
