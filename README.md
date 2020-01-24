# WordSeperator
Java Source Code and Dictionary that can be used to seperate Compound words into it's subwords.

Compile and run Seperator with a dictionary as argument. The dictionary has to be line seperated and should ideally not contain any compound words.
## Estimate of Complexity
#### Space Complexity

##### Trie Data Structure
The dictionary is being stored as a Trie-Data Structure. The dictionary is stored as a Tree, whereas each node is a letter.
By traversing through the tree you iterate over a word in the dictionary until you reach a node which boolean value is set to true.
This implies that the path from the root to this word forms a word which is included in the dictionary.

So instead of saving each word as a string, which we'll simplify to the amount of characters it contains, and using a HashIndex to acces the words,
We have a Tree structure where each node stores a boolean, a character and potentially a HashMap with up to 29 keyvalue pairs.
We use a HashMap here to not waste space with an array which could contain only nullpointers.
In this case we need to store ~16000 Nodes within our Tree, and we only have 33.000 characters in our dictionary.
The advantage is that if words share same prefixes we don't need to store those characters several times. It seems like with growing dictionary size, this data structure would pay off, in this case it might be too excessive.

#### Time Complexity
If looking for a substring in our word of length n we start at the very first letter working our way up until we run into a node which is not a valid word and doesn't
allow us to go any further, or we find the word in our tree. so in worst case we have a path of length n-1 if we don't find any valid word but have to go the
very least character. Then we shift our beginning by one.. In worst case this should be about (n-1) + (n-2) + ... + 1. So we take ((n-1)(n))/2 
steps through out tree. In every step we have to find the right node, so we have to look up our char in our HashMap which has a maximum of 30 key value pairs.
Technically a lookup hast worst case O(logn) time, but since we only have 30 pairs it does'nt matter if it's constanct or not. So we'd have about
2,5*(n^2-n) which is in O(n^2)

## Corner Cases and potential bugs

* #### Compound Words
If our dictionary contains compound words and one of the subwords, it will identify the subword and extract it leading to never being able to find the compund word itself.

* #### Fake Compound Words
There is a potential issue, depending on the dictioanry. If we, for example look at the word "hinein" we have two valid german words "hin" and "ein". Even though this might be a proper compound word, I don't think so, it does not make sense to split this apart. It seems like, especially in the context of search engines, this is best aplicable to nouns.

* #### Non Compound words containing another word
Although words sharing prefixes is no issue, and even helps us argue for using a trie data structure. No word should be the prefix of another if it's a compound word. For Example "Schach" and "Schacht". We would identify "Schach" as the only subword. This might be fixable. If only one subword is identified, it should be the word itself, although this does not make alot of sense if our dictionary is incomplete. "Dampfschiff" only returns "Dampf" with the dictionary used. Also if there is a spelling mistake, eg. "Anwältskanzlei", we could at least identify "kanzlei" 

* #### Dictionary not containing certain words in there base form
Eg. Only "abschnitts" and not "abschnitt" is in the dictionary. So if the Input were "Teilabschnitt", "abschnitt" would not be identified as subword. I can't think of any solution for this problem, since we can't accept words that are a subword of words in our dictionary.

* #### Issues that are not being handled
Those issues are not being handled on purpose. They shoud be handled by the client using a WordSeperator. The CLI, mostly         implemented for testing purposes does not handle those either and rely on proper usage.
Those are:
  *  German umlauts could be entered as eg. 'ä' or 'ae', it has to be the same as the dictionary
  *  The CLI expects a utf8 file, it can be problematic with umlauts. It can potentially raise issues if umlauts are encoded    differently in the tree.
  *  There should be a check for wordlength
