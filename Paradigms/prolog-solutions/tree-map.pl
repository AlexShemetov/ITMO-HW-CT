map_build(ListMap, TreeMap) :- length(ListMap, Lenght), tree_build(ListMap, TreeMap, Lenght).
tree_build([], [], 0) :- !.
tree_build([Value], node(Value, [], []), Lenght) :- !.
tree_build(ListMap, node(Value, Left, Right), Lenght) :-
    Mid is div(Lenght, 2), get_value(ListMap, Mid, Value),
	left(ListMap, Mid, Left_Node), map_build(Left_Node, Left),
	right(ListMap, Mid, Right_Node), map_build(Right_Node, Right).

get_value([H | _], 0, H) :- !.
get_value([H | T], I, Value) :- I1 is I - 1, get_value(T, I1, Value).

left(_, 0, []) :- !.
left([H | T], I, [H | Node]) :- I1 is I - 1, left(T, I1, Node).

right([H | T], 0, T) :- !.
right([H | T], I, Node) :- I1 is I - 1, right(T, I1, Node).

map_get(node((Key, Value), _, _), Key, Value) :- !.
map_get(node((Curr_Key, _), Left, _), Key, Value) :- Key < Curr_Key, map_get(Left, Key, Value).
map_get(node((Curr_Key, _), _, Right), Key, Value) :- Key > Curr_Key, map_get(Right, Key, Value).

map_replace(node((Key, Value), _, _), Key, New_Value, Result) :- node((Key, New_Value), _, _), !.
map_replace(node((Curr_Key, Value), Left, _), Key, New_Value, Result) :- Key < Curr_Key, map_replace(Left, Key, New_Value, R).
map_replace(node((Curr_Key, Value), _, Right), Key, New_Value, Result) :- Key > Curr_Key, map_replace(Right, Key, New_Value, R).