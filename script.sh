
for file in test-files/*.md;
do
	echo -n $file
	echo -n ": "
  	java MarkdownParse $file
done
