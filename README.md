# Dynamic-Connectivity-Analysis
Implementation of Disjoint Sets in Object Oriented Programming(Java)

Given a network of n nodes, a set of the pairs of nodes that are initially connected, and a sequence of steps
where connections are destroyed, one by one, calculate the connectivity after each connection is destroyed.


Input:

The first line of input contains three space-separated integers, n (1 ≤ n ≤ 105), m (1 ≤ m ≤ 3x105), and d (1 ≤ d ≤ m),
representing the number of computers in the enemy network, the number of connections between pairs of computers in
the enemy network, and the number of those connections which you will destroy, respectively. The computers are
numbered 1 through n, inclusive, and the connections are numbered 1 through m respectively.
The following m lines will each contain a pair of distinct integers, u and v (1 ≤ u, v ≤ n, u ≠ v), representing that computers
u and v are connected, initially. (Note: we denote the first of these lines as connection 1, the second as connection 2, and
so forth.) It is guaranteed that each of the pairs listed will be unique pairs; namely, the same two values of u and v will
never appear on two different lines as separate connections. (Of course, many individual computers will be connected to
more than one other computer, so a particular value u may appear on more than one of these m lines.)
The following d lines will each contain a unique integer in between 1 and m, inclusive, representing a connection number
that gets destroyed. These will appear in the order that they get destroyed.

Output:

Output d+1 lines of output. The first line should be the initial connectivity of the network. The following d lines should
have the connectivity of the network after each connection is destroy, one by one.
