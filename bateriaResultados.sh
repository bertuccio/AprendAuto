



echo "  VALIDACION CRUZADA" > car_output
echo "  VALIDACION CRUZADA" > iris_output
echo "  VALIDACION CRUZADA" > lenses_output
echo "  VALIDACION CRUZADA" > spambase_output

inicio=2
final=8
incr=1
for i in $(seq $inicio $incr $final)
do
	java -jar dist/Clasificador.jar -cruzada -input car.data -particion $i >> car_output
	java -jar dist/Clasificador.jar -cruzada -input iris.data -particion $i >> iris_output
	java -jar dist/Clasificador.jar -cruzada -input lenses.data -particion $i >> lenses_output
	java -jar dist/Clasificador.jar -cruzada -input spambase.data -particion $i >> spambase_output
done

for i in $(seq $inicio $incr $final)
do
	java -jar dist/Clasificador.jar -laplace -cruzada -input car.data -particion $i >> car_output
	java -jar dist/Clasificador.jar -laplace -cruzada -input iris.data -particion $i >> iris_output
	java -jar dist/Clasificador.jar -laplace -cruzada -input lenses.data -particion $i >> lenses_output
	java -jar dist/Clasificador.jar -laplace -cruzada -input spambase.data -particion $i >> spambase_output
done



echo " DIVISION PORCENTUAL" >> car_output
echo " DIVISION PORCENTUAL" >> iris_output
echo " DIVISION PORCENTUAL" >> lenses_output
echo " DIVISION PORCENTUAL" >> spambase_output

inicio=10
final=90
incr=10
for i in $(seq $inicio $incr $final)
do
	java -jar dist/Clasificador.jar -input car.data -particion $i >> car_output
	java -jar dist/Clasificador.jar -input iris.data -particion $i >> iris_output
	java -jar dist/Clasificador.jar -input lenses.data -particion $i >> lenses_output
	java -jar dist/Clasificador.jar -input spambase.data -particion $i >> spambase_output
done

for i in $(seq $inicio $incr $final)
do
	java -jar dist/Clasificador.jar -laplace -input car.data -particion $i >> car_output
	java -jar dist/Clasificador.jar -laplace -input iris.data -particion $i >> iris_output
	java -jar dist/Clasificador.jar -laplace -input lenses.data -particion $i >> lenses_output
	java -jar dist/Clasificador.jar -laplace -input spambase.data -particion $i >> spambase_output
done