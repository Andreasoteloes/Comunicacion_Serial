/*Este código Java utiliza la biblioteca jssc para la comunicación serial con un Arduino 
conectado al puerto COM11. El programa solicita al usuario que ingrese números del 1 al 4 
para controlar los LEDs del Arduino y muestra mensajes mediante ventanas emergentes (JOptionPane).
La entrada del usuario se obtiene a través de cuadros de diálogo emergentes, y la información se envía
al Arduino a través del puerto serial.*/

import jssc.SerialPort;
import jssc.SerialPortException;
import javax.swing.JOptionPane;

public class SerialCommunication {

    private static final String PORT_NAME = "COM11"; // Se define el nombre del puerto COM que se utilizará para la comunicación serial
    private static final int BAUD_RATE = SerialPort.BAUDRATE_9600; // Se define la velocidad de baudios para la comunicación serial

    public static void main(String[] args) {
         // Se crea una instancia de SerialPort para manejar la comunicación serial
        SerialPort serialPort = new SerialPort(PORT_NAME);

        try {
            // Se abre el puerto serial y configurar los parámetros de comunicación
            serialPort.openPort();
            serialPort.setParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            
            // Mostrar un mensaje inicial al usuario utilizando una ventana emergente
            showMessage("Presiona los números ↑1, ↓2, ←3,→4 para controlar las flechas. Presiona 's' para salir.");
           
             // Bucle principal del programa
            while (true) {
                // Solicitar al usuario que ingrese un número o 's' utilizando una ventana emergente
                String input = showInputMessage("Presiona los números ↑1, ↓2, ←3,→4 para controlar las flechas. Presiona 's' para salir.");
                char command = input.charAt(0);
// Salir del bucle si el usuario ingresa 's'
                if (command == 's') {
                    break;
                }
 // Calcular el índice del LED y el código de flecha correspondiente
                int ledIndex = command - '1';
                if (ledIndex >= 0 && ledIndex <= 3) {
                    int arrowKeyCode = getArrowKeyCode(command);
                    if (arrowKeyCode != -1) {
                          // Enviar el código de flecha al Arduino a través del puerto serial
                        String arrowKey = Integer.toString(arrowKeyCode);
                        serialPort.writeString(arrowKey);
                    }
                }
            }
        } catch (SerialPortException e) {
     // Manejar cualquier excepción de comunicación serial imprimiendo el rastreo de la pila

            e.printStackTrace();
        } finally {
            try {
      // Cerrar el puerto serial al salir del programa

                serialPort.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }
 // Manejar cualquier excepción al cerrar el puerto serial imprimiendo el rastreo de la pila
    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    // Método para solicitar al usuario que ingrese información utilizando una ventana emergente

    private static String showInputMessage(String message) {
        return JOptionPane.showInputDialog(null, message);
    }
    // Método para obtener el código de flecha correspondiente a un número ingresado

    private static int getArrowKeyCode(char input) {
        switch (input) {
            case '1':
                return 1; 
            case '2':
                return 2; 
            case '3':
                return 3; 
            case '4':
                return 4; 
            default:
                return -1;
        }
    }
}
