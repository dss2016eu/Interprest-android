
[![](http://interprest.io/wp-content/uploads/2016/05/interprest-slide03.jpg)](http://interprest.io/wp-content/uploads/2016/05/interprest-slide03.jpg)

Interprest es un sistema de interpretación simultánea, portátil y de código abierto. Se vale de la tecnología inalámbrica empleando un sistema de comunicación basado en móviles. De esta forma, el móvil del intérprete (emisor) envía la señal a través de un pequeño micrófono y el oyente (receptor) lo recibe en su móvil. El proyecto es una apuesta a favor de la tecnología libre, y pone a disposición de la comunidad todo su desarrollo.

###Requerimientos

 - Arquitecturas soportadas: ARMv7, ARMv7s, ARM64 and x86-64
 - Min SDK Version: 4.1

###Librerías fundamentales

 Interprest está basado en el proyecto ffmpeg y en su herramienta ffplay para la reproducción de sonido. Para conocer más sobre este proyecto accede a [http://ffmpeg.org/](http://ffmpeg.org/).

Además para poder utilizar el proyecto ffmpeg desde una app iOS utilizamos las librerías creadas por el proyecto  [https://github.com/Bilibili/ijkplayer](https://github.com/Bilibili/ijkplayer).

La compilación del proyecto ffmeg para iOS se ha realizado siguiente las instrucciones de este proyecto. Además los parámetros de configuración que se han utilizado para la compilación están en el fichero: /module-interprest.sh


Para conocer los detalles completos sobre las licencias de estas librerías deben acceder a cada uno de los proyectos.

###Librerías adicionales

Además se han utilizado un conjunto de librerías adicionales todas ellas open source para realizar la programación de esta App. A continuación el listado de estas librerías:

 

 - [lombok:1.12.6](https://projectlombok.org/)    
 - [butterknife:7.0.1](http://jakewharton.github.io/butterknife/)
 - [picasso:2.5.2](http://square.github.io/picasso/)
 - [okhttp:2.7.0](http://square.github.io/okhttp/)
 - [okhttp-urlconnection:2.7.0](https://github.com/square/okhttp/tree/master/okhttp-urlconnection)
 - [okio:1.6.0](https://github.com/square/okio)
 - [retrofit:1.9.0](https://square.github.io/retrofit/)
 - [otto:1.3.8](http://square.github.io/otto/)
 - [gson:2.3.1](https://github.com/google/gson)
 - [android-saripaar:2.1.0-SNAPSHOT](https://github.com/ragunathjawahar/android-saripaar)
 - [dagger:2.1](https://github.com/square/dagger)
 - [jsr250-api:1.0](https://mvnrepository.com/artifact/javax.annotation/jsr250-api)
 - [PhotoView:1.2.4](https://github.com/chrisbanes/PhotoView)
 - [swipelayout:library:1.2.0@aar](https://github.com/daimajia/AndroidSwipeLayout)


###Contacto

 - Web: http://interprest.io