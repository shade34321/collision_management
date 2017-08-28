# collision_management
KSU CS 7827 class project for real time collision management

----------------------

Performs double buffering with process A pushing values into buffer at random intervals and processes B and C
pulling values out. Sample output below.

STARTING
A PUSHED: 1
A PUSHED: 2
A PUSHED: 3
A PUSHED: 4
A PUSHED: 5
A PUSHED: 6
A PUSHED: 7
A PUSHED: 8
A PUSHED: 9
A PUSHED: 10
B pulled: 1
C pulled: 2
A PUSHED: 11
A PUSHED: 12
C pulled: 3
A PUSHED: 13
B pulled: 4
A PUSHED: 14
A PUSHED: 15
C pulled: 5
B pulled: 6
A PUSHED: 16
C pulled: 7
B pulled: 8
C pulled: 9
A PUSHED: 17
A PUSHED: 18
C pulled: 10
A PUSHED: 19
A PUSHED: 20
C pulled: 12
B pulled: 11
B pulled: 13
A PUSHED: 21
B pulled: 14
C pulled: 15
A PUSHED: 22
A PUSHED: 23
C pulled: 16
B pulled: 17
A PUSHED: 24
B pulled: 18
C pulled: 19
A PUSHED: 25
B pulled: 20
A PUSHED: 26
A PUSHED: 27
A PUSHED: 28
A PUSHED: 29
A PUSHED: 30
C pulled: 21
B pulled: 22
C pulled: 23
A PUSHED: 31
A PUSHED: 32
A PUSHED: 33
B pulled: 24
A PUSHED: 34
A PUSHED: 35
C pulled: 25
C pulled: 26
A PUSHED: 36
C pulled: 27
B pulled: 28  
A PUSHED: 37
C pulled: 29
A PUSHED: 38
B pulled: 30
A PUSHED: 39
A PUSHED: 40
C pulled: 31
B pulled: 32
A PUSHED: 41
C pulled: 33
A PUSHED: 42
A PUSHED: 43
B pulled: 34
C pulled: 35
A PUSHED: 44
C pulled: 36
B pulled: 37
A PUSHED: 45
C pulled: 38
B pulled: 39
A PUSHED: 46
A PUSHED: 47
B pulled: 40
A PUSHED: 48
A PUSHED: 49
A PUSHED: 50
A FINISHED - NO MORE INBOUND VALUES
C pulled: 41
B pulled: 42
C pulled: 43
B pulled: 44
C pulled: 45
B pulled: 46
B pulled: 47
C pulled: 48
B pulled: 49
C pulled: 50
PULLED VALUES: should be 50, was 50, SUCCESS
UNIQUE VALUES: should be 50, was 50, SUCCESS
MIN SIZE: should be 1, was 1, SUCCESS
MAX: should be 50, was 50, SUCCESS