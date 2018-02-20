flight(istanbul,izmir,3).
flight(istanbul,ankara,5).
flight(istanbul,trabzon,3).

flight(izmir,istanbul,3).
flight(izmir,ankara,6).
flight(izmir,antalya,1).

flight(antalya,izmir,1).
flight(antalya,diyarbakir,5).

flight(edirne,edremit,5).
flight(edremit,edirne,5).

flight(edremit,erzincan,7).
flight(erzincan,edremit,7).

flight(ankara,istanbul,5).
flight(ankara,izmir,6).
flight(ankara,trabzon,2).
flight(ankara,konya,8).

flight(trabzon,istanbul,3).
flight(trabzon,ankara,2).

flight(konya,ankara,8).
flight(konya,kars,5).
flight(konya,diyarbakir,1).

flight(kars,konya,5).
flight(kars,gaziantep,3).

flight(gaziantep,kars,3).

flight(diyarbakir,konya,1).
flight(diyarbakir,antalya,5).



route(X,Y,C) :- flight(X,Y,C).
%route(X,Y,C) :- route(X,Y,C,[]).

% sonsuz cycle i onleyemedim
route(Source,Dest,Cost) :-  flight(Source,Way,C1), 
						   		not(Source=Way), route(Way,Dest,C2),
								   	not(Source=Dest),
								   		Cost is C1 + C2.
