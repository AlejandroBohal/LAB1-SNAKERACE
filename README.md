# Snake Race

Esta libreria está implementada en **JAVA** como laboratorio de **ARSW**

Su funcionamiento se centra en mejorar el conocimiento de los estudiantes para aplicar el conocimiento sobre los conceptos de la programación concurrente.


Para conocer la información de las respuestas, puede verlo en [RESPUESTAS](SNAKE_RACE\RESPUESTAS.txt)

## Ejecutar

El funcionamiento se puede ver a partir de ejecutar el comando
```mvn package```
Segido de la siguiente linea para correr la ejecución:
``` java -cp target\Snake-race-thread-1.0-SNAPSHOT.jar snakepackage.SnakeApp ```

Al correr el programa podran ver el siguiente frame:

<img width="621" alt="SnakeRace" src="https://user-images.githubusercontent.com/49318314/90593683-25070e80-e1ae-11ea-83df-78e51e15c626.png">


### Fragmento de código

Como ejemplo del funcionamiento de código, les presentamos este ejemplo de como muestra los datos en cada pausa:

```
private void pauseThreads() {
        for(Snake s:snakes){
            s.setPaused(true);
            if(s.getSize()>maxSize){
                maxSize = s.getSize();
            }

        }
        String dead = firstDead.get()!=-1?String.valueOf(firstDead.get()):"No dead snakes yet";
        messages = new String[]{String.valueOf("Max Size: "+maxSize),String.valueOf("First Dead: "+dead)};
    }
```
 
### Prerequisitos.

Es necesario/recomendable que posea las siguientes herramientas:

- git instalado en su computador.
- Maven configurado en sus **Variables de Entorno**.
- Interprete de lenguaje de programacion **JAVA** (Eclipse, netbeans, Intellij, etc.)

si necesita instalar algunos de los servicios mencionados puede encontrarlos aquí:

- **Git** puede descargarlo [aqui.](https://git-scm.com/downloads)

- **Maven** puede descargarlo [aqui.](https://maven.apache.org/download.cgi)

- **IntelliJ** puede descargarlo [aqui.](https://www.jetbrains.com/es-es/idea/download/)



## Built With

* [IntelliJ](https://www.jetbrains.com/es-es/idea/) - The develop enviroment
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](https://junit.org/junit5/) - Used to generate Unitary Test


## Authors

* **Juan Carlos García** - *Finalización Laboratorio* - [IJuanchoG](https://github.com/IJuanchoG)

* **Alejandro Bohorquez ** - *Finalización Laboratorio* - [AlejandroBohal](https://github.com/AlejandroBohal)

## License

Este proyecto es de libre uso y distribución, para más detalles vea el archivo [Snake LICENSE.md](SNAKE_RACE\LICENSE.md).