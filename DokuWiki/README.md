## File Content

There is a main folder named university, which consists of nested folders. Under the main folder, there are at least two subfolders, sections and lessons. (An example with more folders can be given during the evaluation. The search should be done in all subfolders.) There are one or more txt files and many files with different extensions (.docx, .config, .png, .gif etc.) in subfolders. Each txt file contains text and tags within these texts. A tag can pass in more than one txt file.

Label format:

o If a word is a tag, its format should be:

[[word]], [[word1 word2]]

o Incorrect label formats:

[[word], [word]], [word], {{word}} etc.

Orphan tag:

o Subfolders contain txt files of tags. However, it does not have to be a txt file for each tag. Likewise, not every txt file has to have a tag.

o If a tag does not have a txt file, that tag is an orphan tag.

## Lesson Template Content

Each lesson should have a code, name and content. All lessons must conform to this template.

## Search

The user should be able to search for words and tags. The searched word can be a tag or a non-tag word. The search function must satisfy both of these checks. As a result of the search, it should be printed on the screen in which file and on which line(s) the search phrase is used. In addition, orphan tags and desired tags should be listed in the search interface.

Orphan tag: Tag without file

Desired tag: File exists but no tag. When the file name is database_management.txt, the requested tag should be: database management.

## Update

The user can change the name of a label. For example, suppose the [[databasemanagement]] tag is changed to [[database management]] in the database management.txt file. In this case, the name of the new txt file should be database_management.txt.

When creating a txt file of an orphan tag, the txt file created must be in accordance with the above-mentioned course template content. Here, the Lesson Code should be assigned incrementally, starting from 200. Orphan label should be assigned to the Name of the Lesson. Lesson content must be blank. Therefore, it should be ensured that this tag, which is no longer an orphan, is deleted from the list of orphan tags.

## Writing to File

There should be an output.txt file in the main folder and all outputs of the program should be printed to this file. All labels should be listed on the printouts, and the number of total tags should be included. In addition, a list of orphan tags should be created.

## Menu (Interface)

A menu must be created to provide all controls. From the menu, the user should be able to search, update the tag and the name of the txt file of that tag, and write to the file.
