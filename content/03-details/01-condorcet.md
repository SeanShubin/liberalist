# Condorcet
The Condorcet Method of resolving votes

## Goal
If a candidate would win a two-candidate election against each of the other candidates in a plurality vote, that candidate must be the winner.  This is known as the “Condorcet Winner Criterion”.  Condorcet methods are good at ranking candidates in an order that accurately reflects voter preference.  The first step is allowing voters to express the entirety of their preference rather than just their #1 candidate.  A voter can do this by ranking candidates in order of preference.

For example, lets say you have this situation

- 30% of the population prefers A, then B, then C
- 30% of the population prefers B, then A, then C
- 40% of the population prefers C, then A, then B

If you are only counting who has the most first place votes, C would win, even though this does not accurately reflect voter preference.

To see why consider this:

- 70% of the voters would rather have A than B
- 60% of the voters would rather have A than C
- 60% of the voters would rather have B than C

When the candidates are compared in pairs using a Condorcet method, it is obvious the accurate voter preference is A, then B, then C.  But when only the voter’s top most candidate is considered, we have thrown away so much information that the least preferred candidate, C, actually wins.  Since Condorcet method evaluate every candidate against every other candidate, the candidates in this same situation would be ranked A, then B, then C, which is consistent with the actual voter preference.

Now consider this dilemma

- 3 voters prefer A, then B, then C
- 4 voters prefer B, then A, then C
- 2 voters prefer C, then A, then B

If we are counting the most first place votes (first past the post), although the 2 voters prefer C, they don’t dare vote that way because that would throw the election to B rather than A.  It is in the best interest of the 2 voters to not express their preference accurately.  This unfairly misrepresents the number of voters who actually preferred C, as that information about their preference was lost.

A Condorcet method would compare the candidates in pairs, like so:

- A defeats B 5 to 4
- A defeats C 7 to 2
- B defeats C 7 to 2

So the ranking becomes A, then B, then C.  In a Condorcet method, the 2 voters could accurately express their vote, and not cause B to defeat A.

Instant runoff voting does not meet Condorcet criteria, instead, it works like this:

    Ballots are initially counted for each elector’s top choice,
    losing candidates are eliminated, and ballots for losing
    candidates are redistributed until one candidate is the top
    remaining choice of a majority of the voters.

The problem with this is that since only the top choices are counted each round, candidates closer to the consensus preference can be eliminated before being evaluated against other candidates.  To illustrate this, consider a contrived case with 10 candidates.  Each voter has a relatively unknown preference.  Each voter would settle for B instead of their preference.

For example

- 2 A B C
- 1 C B A
- 1 D B A
- 1 E B A
- 1 F B A
- 1 G B A
- 1 J B A
- 1 I B A
- 1 J B A

In both instant runoff voting and first past the post voting, A wins, even though 80% of the voters prefer B to A.  In instant runoff voting this is because, B, the one every single voter preferred over someone else’s top candidate, was the first one eliminated, having the least first place votes.  In first past the post voting it is because the vote was split by so much a single candidate was able win despite having a small minority of support.

There are other voting methods, but before advocating for one, it is a good idea to check how they resolve these examples compared to a Condorcet method, and make sure that is what you want.
