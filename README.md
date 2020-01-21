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
The advantage is that if words share same prefixes we don't need to store those characters several times. It seems like with growing dictionary size,
this data structure pays off.

### Time Complexity
If looking for a substring in our word of length n we start at the very first letter working our way up until we run into a node which is not a valid word and doesn't
allow us to go any further, or we find the word in our tree. so in worst case we have a path of length n-1 if we don't find any valid word but have to go the
very least character. Then we shift our beginning by one.. In worst case this should be about (n-1) + (n-2) + ... + 1. So we take ((n-1)(n))/2 
steps through out tree. In every step we have to find the right node, so we have to look up our char in our HashMap which ahs a maximum of 30 key value pairs.
Technically a lookup hast worst case O(logn) time, but since we only have 30 pairs it does'nt matter if it's constanct or not. So we'd have about
2,5*(n^2-n) which is in O(n^2)
