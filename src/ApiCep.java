import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ApiCep {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String cepUser = scanner.nextLine();
        buscarEnderecoPorCEP(cepUser);
    }

    public static void buscarEnderecoPorCEP(String cep) {
        String urlString = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null){
                resposta.append(linha);
            }
            reader.close();

            JSONObject json = new JSONObject(resposta.toString());

            if (json.has("erro")) {
                System.out.println("CEP invalido!");
            } else {
                String logradouro = json.getString("logradouro");
                String bairro = json.getString("bairro");
                String cidade = json.getString("localidade");
                String estado = json.getString("uf");

                System.out.println("Endereço: " + logradouro);
                System.out.println("Bairro: " + bairro);
                System.out.println("Cidade: " + cidade);
                System.out.println("Estado: " + estado);
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar o CEP: " + e.getMessage());
        }
    }


}
