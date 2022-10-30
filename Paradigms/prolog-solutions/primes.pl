init(N) :- search_primes(2, N).
search_primes(Curr, N) :- Curr * Curr > N, !.
search_primes(Curr, N) :- not composites_numbers(Curr), Next is Curr * Curr, is_composite(Next, Curr, N).
search_primes(2, N) :- search_primes(3, N), !.
search_primes(Curr, N) :- Next is Curr + 2, search_primes(Next, N).

is_composite(Cur, Step, N) :- Cur < N, assertz(composites_numbers(Cur)), Next is Cur + Step, is_composite(Next, Step, N).

prime(N) :- N > 1, not composites_numbers(N).
composite(N) :- composites_numbers(N).

nth_prime(N, P) :- found_prime(N, P, 2).
found_prime(N, P, C) :-
	N =:= 0,
	P is C - 1, !.
found_prime(N, P, C) :-
	prime(C),
	N1 is N - 1,
	C1 is C + 1,
	found_prime(N1, P, C1), !.
found_prime(N, P, C) :-
	C1 is C + 1,
	found_prime(N, P, C1).

prime_divisors(N, M) :- number(N), N > 1, make_divisors(N, M, 2), !.
prime_divisors(1, []) :- !.
make_divisors(N, [N], D) :- D * D > N, !.
make_divisors(N, M, D) :-
	mod(N, D) > 0,
	D1 is D + 1,
	make_divisors(N, M, D1).
make_divisors(N, [H | T], D) :-
	mod(N, D) =:= 0,
	N1 is div(N, D),
	H is D,
	make_divisors(N1, T, D).

prime_divisors(N, M) :- list(M), make_number(N, M, 2), !.
make_number(1, [], _) :- !.
make_number(N, [H | T], C) :-
	C =< H,
	make_number(N1, T, H),
	N is N1 * H.

lcm(First, Second, LCM) :- prime_divisors(First, L1), prime_divisors(Second, L2), merge(L1, L2, PD), prime_divisors(LCM, PD).
merge([], Res, Res) :- !.
merge([H | T1], [H | T2], [H | Res]) :- merge(T1, T2, Res), !.
merge([H1 | T1], [H2 | T2], Res) :- H1 > H2, merge([H2 | T2], [H1 | T1], Res), !.
merge([H1 | T1], L2, [H1 | Res]) :- merge(T1, L2, Res), !.