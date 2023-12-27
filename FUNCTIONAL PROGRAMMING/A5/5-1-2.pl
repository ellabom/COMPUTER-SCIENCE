ever_married_to(diana, charles).
ever_married_to(charles, diana).
ever_married_to(charles, camilla).
ever_married_to(camilla, charles).
ever_married_to(william, catherine).
ever_married_to(catherine, william).
ever_married_to(harry, meghan).
ever_married_to(meghan, harry).

child_of(william, diana).
child_of(william, charles).
child_of(harry, diana).
child_of(harry, charles).
child_of(george, william).
child_of(george, catherine).
child_of(charlotte, william).
child_of(charlotte, catherine).
child_of(louis, william).
child_of(louis, catherine).
child_of(archie, harry).
child_of(archie, meghan).
child_of(lilibet, harry).
child_of(lilibet, meghan).

male(charles).
male(william).
male(harry).
male(george).
male(louis).
male(archie).

female(diana).
female(camilla).
female(catherine).
female(meghan).
female(charlotte).
female(lilibet).

deceased(diana).

senior_royal(charles).
senior_royal(camilla).
senior_royal(william).
senior_royal(catherine).
senior_royal(george).
senior_royal(charlotte).
senior_royal(louis).

uncle_of(Person1, Person2) :-
    male(Person1),
    child_of(Person2, Parent),
    brother_of(person1, Parent).

brother_of(person1, person2) :- 
    male(person1),
    father_of(person, person2),
    child_of(person1, person).
    
sibling_of(Person1, Person2) :-
    ever_married_to(Person1, Spouse),
    ever_married_to(Person2, Spouse),
    Person1 \= Person2. 

grandmother_of(Person1, Person2) :-
    female(Person1),
    parent_of(Person1, Parent),
    parent_of(Parent, Person2).


grandson_of(Person1, Person2) :-
    male(Person1),
    child_of(Person1, person),
    child_of(person, Person2). 

grandchild_of(Person1, Person2) :-
    parent_of(Person1, Parent),
    parent_of(Parent, Person2).


parent_of(Person1, Person2) :-
    child_of(Person2, Person1).


mother_of(Person1, Person2) :-
    female(Person1),
    parent_of(Person1, Person2).

father_of(Person1, Person2) :-
    male(Person1),
    parent_of(Person1, Person2).


stepmother_of(Person1, Person2) :-
    ever_married_to(Person2Parent, Person1),
    parent_of(Person2Parent, Person2),
    female(Person1),
    not(mother_of(Person1, Person2)).


son_of(Person1, Person2) :- male(Person1), child_of(Person1, Person2).
