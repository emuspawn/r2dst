# Introduction #

As of [r117](https://code.google.com/p/r2dst/source/detail?r=117), my TCP classes can send and receive objects. There are a few rules you must follow when sending and receiving objects. I'm doing the impossible here so don't complain ;).

# Rules #

1. You must return a valid ArrayList object from toSendStringList() which can be passed to the constructer of the object that implements Sendable to reconstruct the exact object on the receiving computer.

2. You can't send an object directly. You must use the methods I have provided otherwise you are sending the pointer which would point to an invalid memory location on the receiving computer.

3. **You can only store strings and primitive data types in the ArrayList object.** If you want to send an object that is stored within another object, it will have to implement Sendable and you can add what it returns from toSendStringList() (not the ArrayList itself but the data it contains) to the ArrayList that your class is assembling and then reassemble that object using the stored data in the ArrayList which is passed to your constructor.