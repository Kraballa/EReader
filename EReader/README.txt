This is version 1.1 of this File

Current Version 1.0
Last Version -


Table of Content:
1. Goals, Motivation and Purpose
2. Version 2.x
3. Future Plans
4. License

1. Goals, Motivation and Purpose:
This project was created as a learning experience and it did teach me a lot about Maven, Documentation, Structure and
Java 8. The original Idea came up when I was reading books that I had as .pdf files and had to deal with problems that
I thought I could easily fix. My two biggest problems were:
a) there is no default way to bookmark your progress, and
b) the din a4 format (that most book-type pdf files use) doesn't work well with standard monitors.
I also had some minor Improvements in mind:
c) I want to edit the Font and the Background colour
d) I want to reformat the Text
e) I want to export data.
f) highlight special patterns like spoken language

In it's current state the program solves my problems and achieves my goals. However there are some problems that either
come from wanting the program to work for more than book-like pdf files or from the pdf-Format itself.

a) The problem with .pdf-Files:
You see, pdf files store text and images much different than regular txt files. That's why it is extremely difficult
(some may even say nigh impossible) to extract text from such a file. The Library PDFBox that I'm using does an
exceptional job in reading the characters and combining them into strings. The first problem was choosing a font that
can write most unicode characters (easy solution). The second problem was formatting the text as if it were, say,
a word document. That means you have to manually decide when and a new paragraph starts (and when it doesn't) since that
is not information PDFBox provides with my current implementation. And I didn't look too deep into the problem and fixed
it a bit rudimentary.
The third problem is that the order you see the text in does NOT equal the order it is stored and consequentially read
by PDFBox. I had files that included a header and a section for sources as well as a text part in the middle, where the
text part came after the sources. I unfortunately cannot fix this and similar issues right now.
Next, I found a multitude of problems that only occur in some pdf files, some examples: Every Space character was
read as a Tab character, There are 2-5 Space characters inbetween every word. Since I have no way of knowing every
little problem that can occur I can't fix them all so if you find similar problems, please contact me at
[vinz.corno@web.de] with a detailed explanation or even the file itself.

b) Using the software for non-book-like files:
When loading, lets say, a manuscript containing a combination of images and text, this program only reads the text and
skips the rest. That's why I excluded those types of files from my target usage. However I have provided a feature to
show a completed page like your default Pdf Reader. I do not plan on expanding this feature.
Also text may be parsed out of order for the reasons mentioned above.

Now what does it to WELL and better than the Chrome Pdf-Reader I was using before?
a) show text in a manner that is easy on the eye and space-efficient (depending on the text structure of the source file)
b) allow you to bookmark your progress with a loading feature
c) lets you export the full pdf-file to txt

All in all I'm very happy how the project turned out as it works very well for my intended purposes.

2. Version 2.x
I rewrote the entire thing because I had issues both with swing and my design was atrocious. Right now I'm using
javafx which is much cleaner in my opinion (looking at my main class is fun again!). Also I outsourced the text
correction into a module as it should have been from the start. The Reader Interface makes a lot more sense and
the AbstractReader handles all the data storing stuff. As of 20. november 2017 I implemented a parallel version of
the AbstractReader. While it didn't improve the speed of the loading process by a ton it's still nice to have.
The best thing about it is that you don't have to do anything special when implementing more Reader classes!
Right now the entire document is loaded straight away which can take anywhere between almost 0 to 10 seconds with
moderately sized files. You can run the PDFSpeedTest which loads the java language specification (670 pages full of text)
10 times (5 times a sequencial implementation and 5 times a parallel implementation). For me they took an average of
87 and 69 seconds. This number seems worrying but this really was the worst case possible.

3. Future plans:
-Implement the ServiceLoaderAPI for easier extension management.
-Make the Properties window functional
-


4. License
THIS PROJECT IS LICENSED UNDER THE GNU GENERAL PUBLIC LICENSE ver.3 WHICH CAN BE FOUND HERE:
                    https://www.gnu.org/licenses/gpl-3.0.de.html
little excerpt:
"[...], the GNU General Public License is intended to guarantee your freedom to share and change all versions
of a program--to make sure it remains free software for all its users."