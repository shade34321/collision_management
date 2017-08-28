# collision_management
KSU CS 7827 class project for real time collision management

----------------------

Performs double buffering with process A pushing values into buffer at random intervals and processes B and C
pulling values out. Sample output below.<br />
<br />
STARTING<br />
A PUSHED: 1<br />
A PUSHED: 2<br />
A PUSHED: 3<br />
A PUSHED: 4<br />
A PUSHED: 5<br />
A PUSHED: 6<br />
A PUSHED: 7<br />
A PUSHED: 8<br />
A PUSHED: 9<br />
A PUSHED: 10<br />
B pulled: 1<br />
C pulled: 2<br />
A PUSHED: 11<br />
A PUSHED: 12<br />
C pulled: 3<br />
A PUSHED: 13<br />
B pulled: 4<br />
A PUSHED: 14<br />
A PUSHED: 15<br />
C pulled: 5<br />
B pulled: 6<br />
A PUSHED: 16<br />
C pulled: 7<br />
B pulled: 8<br />
C pulled: 9<br />
A PUSHED: 17<br />
A PUSHED: 18<br />
C pulled: 10<br />
A PUSHED: 19<br />
A PUSHED: 20<br />
C pulled: 12<br />
B pulled: 11<br />
B pulled: 13<br />
A PUSHED: 21<br />
B pulled: 14<br />
C pulled: 15<br />
A PUSHED: 22<br />
A PUSHED: 23<br />
C pulled: 16<br />
B pulled: 17<br />
A PUSHED: 24<br />
B pulled: 18<br />
C pulled: 19<br />
A PUSHED: 25<br />
B pulled: 20<br />
A PUSHED: 26<br />
A PUSHED: 27<br />
A PUSHED: 28<br />
A PUSHED: 29<br />
A PUSHED: 30<br />
C pulled: 21<br />
B pulled: 22<br />
C pulled: 23<br />
A PUSHED: 31<br />
A PUSHED: 32<br />
A PUSHED: 33<br />
B pulled: 24<br />
A PUSHED: 34<br />
A PUSHED: 35<br />
C pulled: 25<br />
C pulled: 26<br />
A PUSHED: 36<br />
C pulled: 27<br />
B pulled: 28  <br />
A PUSHED: 37<br />
C pulled: 29<br />
A PUSHED: 38<br />
B pulled: 30<br />
A PUSHED: 39<br />
A PUSHED: 40<br />
C pulled: 31<br />
B pulled: 32<br />
A PUSHED: 41<br />
C pulled: 33<br />
A PUSHED: 42<br />
A PUSHED: 43<br />
B pulled: 34<br />
C pulled: 35<br />
A PUSHED: 44<br />
C pulled: 36<br />
B pulled: 37<br />
A PUSHED: 45<br />
C pulled: 38<br />
B pulled: 39<br />
A PUSHED: 46<br />
A PUSHED: 47<br />
B pulled: 40<br />
A PUSHED: 48<br />
A PUSHED: 49<br />
A PUSHED: 50<br />
A FINISHED - NO MORE INBOUND VALUES<br />
C pulled: 41<br />
B pulled: 42<br />
C pulled: 43<br />
B pulled: 44<br />
C pulled: 45<br />
B pulled: 46<br />
B pulled: 47<br />
C pulled: 48<br />
B pulled: 49<br />
C pulled: 50<br />
PULLED VALUES: should be 50, was 50, SUCCESS<br />
UNIQUE VALUES: should be 50, was 50, SUCCESS<br />
MIN SIZE: should be 1, was 1, SUCCESS<br />
MAX: should be 50, was 50, SUCCESS<br />