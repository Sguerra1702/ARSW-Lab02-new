## Escuela Colombiana de Ingeniería
### Arquitecturas de Software

### Laboratorio - Programación concurrente, condiciones de carrera, esquemas de sincronización, colecciones sincronizadas y concurrentes.
## INTEGRANTES:
### Santiago Guerra
### Andrés Rodríguez

## Introducción 

El presente laboratorio tiene como objetivo profundizar en los conceptos de programación concurrente, manejo de condiciones de carrera, esquemas de sincronización y el uso adecuado de colecciones sincronizadas y concurrentes. 
A través de la implementación y modificación de dos ejercicios prácticos, 
se busca que los estudiantes comprendan la importancia de gestionar la concurrencia en aplicaciones multihilo y cómo evitar errores comunes relacionados con el acceso concurrente a recursos compartidos.

## Parte I Control de hilos con wait/notify

Descargue el proyecto PrimeFinder. Este es un programa que calcula números primos entre 0 y M (Control.MAXVALUE), concurrentemente, distribuyendo la búsqueda de los mismos entre n (Control.NTHREADS) hilos independientes.

Se necesita modificar la aplicación de manera que cada t milisegundos de ejecución de los threads,
se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego,
se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismos. Utilice los mecanismos de sincronización provistos por el lenguaje (wait y notify, notifyAll).

Tenga en cuenta:

1. La construcción synchronized se utiliza para obtener acceso exclusivo a un objeto.

2. La instrucción A.wait() ejecutada en un hilo B pone en modo suspendido al hilo B (independientemente de qué objeto 'A' sea usado como 'lock'). Para reanudarlo, otro hilo activo puede reanudar a B haciendo 'notify()' al objeto usado como 'lock' (es decir, A).

3. La instrucción notify(), despierta el primer hilo que hizo wait() sobre el objeto.

4. La instrucción notifyAll(), despierta todos los hilos que estan esperando por el objeto (hicieron wait()sobre el objeto).

### Desarrollo parte I

