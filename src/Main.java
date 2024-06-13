import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int opcion = 0;
        double cantidad = 0.0;
        GetUrl getUrl = new GetUrl();
        DatosApi datosApi = new DatosApi();
        ResultExtractor extractor = new ResultExtractor();
        String menu = """
          ******************** Hello world ********************
                   Bienvenido/a al Conversor de Monedas
          1) Dólar >> Peso Mexicano 
          2) Peso Mexicano >> Dólar
          3) Dólar >> Peso argentino
          4) Peso argentino >> Dólar
          5) Dólar >> Peso colombiano
          6) Peso colombiano >> Dólar
          7) Salir
          ******************** **** ******************** *******       
          """;

        Scanner lectura = new Scanner(System.in);

        while (opcion != 7) {
            System.out.println(menu);
            System.out.println("Qué conversión desea realizar: ");
            try {
                opcion = lectura.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del 1 al 7.");
                lectura.next();
                continue;
            }

            if (opcion >= 1 && opcion <= 6) {
                System.out.println("Cantidad a convertir: ");
                try {
                    cantidad = lectura.nextDouble();
                } catch (InputMismatchException e){
                    System.out.println("Ingrese una cantidad correcta!!");
                    lectura.next();
                    continue;
                }
                lectura.nextLine(); // Consumir la nueva línea pendiente

                String fromCurrency = "";
                String toCurrency = "";

                switch (opcion) {
                    case 1:
                        fromCurrency = "USD";
                        toCurrency = "MXN";
                        break;
                    case 2:
                        fromCurrency = "MXN";
                        toCurrency = "USD";
                        break;
                    case 3:
                        fromCurrency = "USD";
                        toCurrency = "ARS";
                        break;
                    case 4:
                        fromCurrency = "ARS";
                        toCurrency = "USD";
                        break;
                    case 5:
                        fromCurrency = "USD";
                        toCurrency = "COP";
                        break;
                    case 6:
                        fromCurrency = "COP";
                        toCurrency = "USD";
                        break;
                }

                String url = getUrl.generaUrl(fromCurrency, toCurrency, cantidad);
                //System.out.println("URL generada: " + url);
                String respuesta = datosApi.enviarGet(url);
                //System.out.println(respuesta);
                String resultado = extractor.extract(respuesta);
                System.out.println("El valor de " + Double.toString(cantidad) + "["+ fromCurrency+"]  es de: " + resultado  + "["+toCurrency + "]");

            } else if (opcion == 7) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción no válida. Por favor, seleccione una opción del 1 al 7.");
            }
        }

        lectura.close();
    }
}