import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Сервер запущен. Ожидание подключений...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился: " + clientSocket);

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            out.writeObject("Добро пожаловать в игру Memory Puzzle!");

            // Пример логики игры:

            // Отправка информации клиенту о начале игры
            out.writeObject("game_start");

            // Принятие данных от клиента о выборе карточек
            int card1 = (int) in.readObject();
            int card2 = (int) in.readObject();

            // Логика проверки совпадения карт, определение победителя и отправка результата клиентам
            // ...

            out.close();
            in.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

