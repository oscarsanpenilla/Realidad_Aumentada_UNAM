# Realidad Aumentada

## Proyecto de Ingeniería

### Resumen
A lo largo del semestre, desarrolle con la asesoría del Ing. Yair Bautista Blanco una aplicación dirigida a smartphones de alta gamma compatibles con la recién lanzada tecnología ARCore de Google. La aplicación es capaz de obtener la ubicación del usuario y mostrar un modelo en realidad aumentada al enfocar la cámara en tres modelos distintos de tarjetas de Metrobus, dicho modelo cambia dependiendo de la posición en tiempo real del usuario.

### Objetivo General
Crear una aplicación ecapaz de obtener la ubicación del usuario y mostrar una figura utilizando la cámara del celular y realidad aumentada esta deberá ser amigable para el usuario.

### Resultados
*	La aplicación funciona correctamente en el interior de Ciudad Universitaria, teniendo como sitios de interés, el Anexo de Ingeniería, Facultad de Ingeniería, la Facultad de Arquitectura, Facultad de Contaduría, facultad de Química y finalmente la Facultad Medicina.
*	La aplicación es capaz de renderizar diferentes modelos 3D en realidad aumentada dependiendo el sitio de interés en el que se localice el usuario.
*	Se es capaz de distinguir de manera correcta tres distintos modelos de tarjetas de Metrobús, es posible agregar cientos de modelos mas, sin embargo, el diseño de las mismas debe cumplir con cierto nivel de complejidad. Tarjetas con diseños repetitivos y simples no son buenas candidatas para su detección, sin embargo, tarjetas con patrones no repetitivos y alto contraste son excelentes para su detección.
*	Una implementación con Buforia, nos permitiría emplear marcadores mucho más sencillos para la parte de realidad aumentada, sin embargo se tendría que agregar dicho marcador al diseño de las tarjetas de Metrobús y se tendría que modificar en gran medida el código de la aplicación.
*	El rendimiento de la aplicación es bueno, sin embargo, solo los dispositivos compatibles con ARcore pueden hacer uso de esta. Debido a que la realidad aumentada representa un uso considerable de recursos para el smartphone.
*	El algoritmo de geolocalización funciona correctamente y no representa una carga considerable al procesamiento de la aplicación.
*	ARcore permite escalar los modelos a diferentes tamaños para proporcionar al usuario diferentes niveles de inmersión dependiendo el tamaño que el usuario disponga para moverse generando una buena experiencia de usuario.


# Video tutorial
'https://drive.google.com/file/d/1eNqdxMQFmDMW7pIwfOhshVc7FShONLLp/view?usp=sharing'
