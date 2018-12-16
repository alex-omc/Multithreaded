# Factorial Multi-Hilos
Script Multi-Hilos para calcular n! en Java 

El proposito del presente script es hacer un uso total del CPU para calcular la factorial de números grandes

# ¿Cómo Funciona?
## 	Manera trdicional
La manera tradicional de calcular la factorial de un número es hacerlo de manera iterativa con un for-loop similar al siguiente
```java
long f = 1;
int i;
for(i=1;i<=num;i++){
  f *= i;
} 
System.out.println(f);
```
Sin embargo, existe un problema; y es que solo estaremos usando un núcleo de nuestro CPU. Para solucionar este problema se propone 
distribuir la carga entre todos los núcleos del CPU. 

Para las siguientes propuestas se tomará como referencia:
- num = 100
- CPU: Intel i5-6500 de 4 núcleos y 4 hilos


##	Primera propuesta
... subir gráfico ...
Aún existe un problema con esto, y es que el hilo 0 tendrá una carga significativamente más ligera que el hilo 3.
En un número relativamente pequeño como 100! la diferencia es mínima; pero si se desea calcular la factorial de números mucho más grandes
esto representará un problema


##	Propuesta final
