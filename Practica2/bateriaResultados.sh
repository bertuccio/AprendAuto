


knn=(1 3 5 10 20 30)
npercents=(10 20 30 40 50 60 70 80 90)
ncrossvalfolds=(2 3 4 5 6 7 8)

for knnvalue in "${knn[@]}" 
do
	for percent in "${npercents[@]}"
	do
		java -jar dist/Practica2.jar -input wdbc.data -particion $percent -K $knn >> wdbc_div_output
	done
	for folds in "${ncrossvalfolds[@]}"
	do
		java -jar dist/Practica2.jar -input wdbc.data -cruzada -particion $folds -K $knn  >> wdbc_cross_output
	done
done