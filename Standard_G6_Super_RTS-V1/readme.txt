-compile AIs to .class and put them in the customAI folder

-to create an AI, add the external jar "super rts G3.jar" and make
a class that extends AI (import ai.AI;). see "BasicAIRed.java" for
an example of a very bad AI

-to play any AI, double click and start the "super rts G3.jar" and
select the desired AIs to play against eachother. then click the
start button

-if jars wont run for some reason (daniel) then run the .bat file. If that
still doesn't work, install java jre




-Step by step instructions:

Setup
1. create new project in eclipse (name it whatever), and choose to 
create project from existing source
2. It will ask for a directory, use the one this readme was extracted into
3. Now edit the BasicAIRed.java file (in the default package) to make your AI

Testing AI
4. now to test it, get your AI's .class file from this folder
5. copy it into the customAI folder
6. run jar file and choose AIs to use


-troubleshooting

1. game's not starting when I click the batch file:
I was probably lazy, check the batch file (right click, then edit) and see if it even opens the correct file. It should read {   java -jar "jar name here"   }.
Inside "jar name here" put the full name of the jar ("asd.jar  dont forget the ".jar").