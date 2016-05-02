# JFlock

A simple flocking simulator written in Java using the JSFML library

Black sparrows will try to do 4 things:
* Align themselves with the general direction of movement of nearby sparrows
* Not collide with any other nearby sparrows
* Move towards nearby sparrows that are not within the collision avoidance range
* Move away from the red hawks with top priority

Red hawks will:
* select a single sparrow and pursue it until it gets outside of its chase range
* never reach sparrows due to the difference in speed

![alt tag] (resources/img/demo.png)
