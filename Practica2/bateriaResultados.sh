nneighbors=(1 3 5 10 20 30)
npercents=(10 20 30 40 50 60 70 80 90)
ncrossvalfolds=(2 3 4 5 6 7 8)
for nn in "${nneighbors[@]}"
do
for percent in "${npercents[@]}"
do
java -jar dist/Practica2.jar -input wdbc.data -particion $percent -K $nn >> wdbc_div_output
done
for folds in "${ncrossvalfolds[@]}"
do
java -jar dist/Practica2.jar -input wdbc.data -cruzada -particion $folds -K $nn >> wdbc_cross_output
done
done