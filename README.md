# DnD Buddy
![icon](https://media.github.students.cs.ubc.ca/user/28052/files/251173e8-71b6-4b5e-9588-fba0e10494c8)

This is a program written in Java for the CPSC 121 term project. This involves full stack development of a desktop application. 

## What is it?
DnD Buddy is a companion app for DnD, intended to replace the paper sheets regularly used while still remaining true to the DnD style.

## Features
- A player sheet tracks all of your stats while being easy to update, with easy to access shortcuts.  
- Spells and cantrips tracker tracks how many you have left  
- Session log tracks which sessions you've had, when you've had them, and how long  
- Dice rollers let you roll any dice up to d100  

## Who is this for?
Hobbyist DnD players who want to reduce the hassle of keeping track of all of the things that occur in a DnD session

## Why make this?
When playing DnD, I always forget to bring my player sheet and the characters that are involve in each different campaign, so I wanted an easy way of keeping track of everything

## User Stories
- As a user, I want to be able to add multiple characters
- As a user, I want to be able to view what spells I have
- As a user, I want to be able to roll several die for skill checks
- As a user, I want to be able to simulate using skills
- As a user, I want to be able to save my characters (if I so choose)
- As a user, I want to be able to be able to load my characters from file (if I so choose)

## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking "Character" on the menu bar and then clicking New Character
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking "Delete Character" in the "Character List" window
- You can locate my visual component by clicking on "Dice" and then "Roll Simulator"
- You can save the state of my application by clicking on "File" and then "Save" in the menu bar
- You can reload the state of my application by clicking on "File" and then "Load" in the menu bar

## Phase 4: Task 2
Tue Aug 06 16:28:51 PDT 2024
Added new character
Tue Aug 06 16:28:59 PDT 2024
Added new character
Tue Aug 06 16:29:02 PDT 2024
Removed a character
Tue Aug 06 16:29:09 PDT 2024
Added new character

## Phase 4: Task 3
I think the biggest refactoring I would do is change the way that the GUI operates with the app logic. Currently, the way it works is that when main is called, it creates a new GuiController, which instantializes a new DnDBuddyApp. Then, whenever I need a window to do something, like create a new character from NewCharacterWindow, I make the method get the DnDBuddyApp of GuiController and then from the DnDBuddyApp I get the character list and then add the newly created character from the GUI. I made it like this to maintain the console functionality, but if I were to have more time and develop it on my own, I would remove DnDBuddyApp and instead create a class that was specifically designed for dealing with the logic of creating characters and maintaining the list, and I would make it using the singleton design pattern. This way, I can have methods that would be accessible from any of my windows on one single instance of that logic class, which would allow for a more comprehensible design.