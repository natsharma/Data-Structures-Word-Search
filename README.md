# Data Structures Project: Word Search
## Analyzes text from classical books to identify common words between them and determine if they have an appropriate vocabulary to help high school students improve their reading skills. 

* The output of the program will be the number of words in the book that are 1) in common with another book entered by user and 2) on a provided target vocabulary list.

## Program Logic
* Uses hashmaps to store key value pairs
* Uses a merge-sort algorithm
* Segmented string data for analysis

There are two input files. 

First file is named books.txt and contains n records. First record will be the focus book of analysis. Remaining records will be all the books for comparison to the focus book. <br>
For example, if books.txt contains: <br>
JaneEyre.txt <br>
War_peace.txt <br>
Gone_with_wind.txt <br>
David_copperfield.txt <br>

Then the solution will compare Jane Eyre against all the following books on the list. 

Second file is named words.txt and contains a set of words for comparison against the focus book only. For example, program will find all the words that are in both Jane Eyre and words.txt.

### How to Run Program

* Repo contains Main.java
* Simply run the Main class as long as the .txt files are in the same directory. 

## Expected Results:
Focus book  JaneEyre   Total Words:  2933  Proper Nouns:  43  Comparison book: War_peace  Total Words: 2633   Proper Nouns: 65 <br>     
Words in both:  3332     Words in JaneEyre only:  323    Words in War_peace only: 832 <br>
WordList JaneEyre only:   alpha, baker, charlie <br>
WordList war_peace only:   alpha, baker, charlie <br>
(Repeat above 4 lines for Gone_with_wind.txt   David_copperfield.txt) <br>
Words in focus book: JaneEyre  also in vocabulary list:    apple, banana, cherry,….. <br>
Words in vocabulary list not in focus book:       aaa, bbb, ccc <br>



