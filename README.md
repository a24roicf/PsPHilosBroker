# PSP Hilos Broker
## Descripción

PSP Hilos Broker es una aplicación en Java que simula un sistema de operaciones de compra y venta de acciones utilizando hilos para gestionar las transacciones de múltiples agentes de manera concurrente. La aplicación permite:
- Crear agentes con saldo y activos iniciales.
- Crear operaciones de compra o venta de acciones.
- Simular la evolución del precio de las acciones.
- Visualizar información en tiempo real mediante gráficos y tablas.
## Funcionalidades
### 1 Gestión de agentes:
- Crear nuevos agentes con nombre, saldo y activos.
- Listar los agentes existentes en una tabla actualizable.
### 2 Operaciones de compra/venta:
- Cada agente puede realizar operaciones de compra o venta con límite de precio y cantidad.
- Cada operación se ejecuta en un hilo separado, que monitoriza el precio de la acción y realiza la transacción automáticamente si se cumplen las condiciones.
### 3 Visualización de datos:
- Gráfica de la evolución del precio de la acción en tiempo real.
- Labels que muestran:
  - Precio actual
  - Precio máximo
  - Precio mínimo
  - Número de operaciones activas
  - Volumen total de activos
- Tabla que muestra agentes y sus activos/saldo actualizados.
### 4 Persistencia:
- Los datos de agentes y operaciones se guardan automáticamente al cerrar la aplicación.
- Al iniciar, se cargan los datos previamente guardados.
## Estructura del proyecto
```
├── MainJFrame.java          # Interfaz gráfica principal
├── MainController.java      # Controlador que maneja la lógica de la aplicación
├── PspHilosBroker.java      # Clase principal con main
├── Broker.java              # Clase que gestiona el mercado y los agentes
├── Agente.java              # Representa a cada agente del sistema
├── Operaciones.java         # Clase que representa operaciones de compra/venta (hilos)
└── GraficaPrecio.java       # Clase que genera la gráfica del precio de la acción
```
### Clases principales
- Broker:
  - Administra la lista de agentes, el precio actual, máximo y mínimo del mercado.
  - Calcula el número de operaciones activas y el volumen total de activos.
  - Métodos de persistencia (guardar() y cargar()).
- Agente:
  - Contiene datos de un agente: nombre, saldo, activos, operaciones activas.
  - Permite crear nuevas operaciones (nuevaOperacion).
- Operaciones:
  - Ejecuta operaciones de compra o venta en hilos independientes.
  - Sincroniza el acceso al Broker para actualizar precio y volumen de manera segura.
- GraficaPrecio:
  - Genera un gráfico en tiempo real del precio de la acción utilizando XChart.
- MainController:
  - Controla la interacción entre la interfaz (MainJFrame) y el modelo (Broker, Agente).
  - Refresca los labels y tablas de la aplicación periódicamente usando un Timer.
- MainJFrame:
  - Interfaz gráfica con tres pestañas:
    - Gráfica: muestra el precio de la acción y los datos de mercado.
    - Agentes: permite crear y listar agentes.
    - Operaciones: permite crear nuevas operaciones de compra/venta.
## Uso
### Ejecuta la aplicación:
`java -jar PspHilosBroker.jar`
### Crear agentes:
- Rellenar nombre, saldo y activos.
- Pulsar "Crear Agente".
### Crear operaciones:
- Seleccionar un agente.
- Elegir tipo (Compra/Venta), cantidad y precio límite.
- Pulsar "Crear".
### Visualización:
- La grafica se actualiza automaticamente con el precio actual de la acción.
- Los labels muestran datos de mercado en tiempo real.
- La tabla muestra los agentes y sus activos/saldo actualizados.
## Notas técnicas
- Se utiliza serialización Java para persistir los datos de los agentes y sus operaciones.
- Se utilizan hilos para ejecutar operaciones de forma concurrente.
- La clase Operaciones utiliza transient para los hilos, de manera que al cargar los datos desde disco, los hilos se reinician automáticamente (restartThread).
- El acceso al precio y volumen se sincroniza con un lock para evitar condiciones de carrera.
## Información a mayores
- La tabla no representa las operaciones actuales, tiene una preview.
- No se actualizan los labels de la tabla, quedan vacios (no implementado)
- Si se quiere retirar alguna operacion activa o un agente ya creado se debera eliminar el archivo creado broker.dat en la ruta de la aplicacion.
