



npercents=(10 20 30 40 50 60 70 80 90)
ncrossvalfolds=(2 3 4 5 6 7 8)


for percent in "${npercents[@]}"
do
	java -jar dist/Practica2.jar -input wdbc.data -particion $percent >> wdbc_div_output
done
for folds in "${ncrossvalfolds[@]}"
do
	java -jar dist/Practica2.jar -input wdbc.data -cruzada -particion $folds  >> wdbc_cross_output
done
