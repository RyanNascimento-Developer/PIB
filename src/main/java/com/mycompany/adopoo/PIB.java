package com.mycompany.adopoo;

//Importe utlizados
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PIB {
    
    public static void main(String[] args) {
        Map<String, Double> pibs = new HashMap<>();
        double totalPib = 0.0;

        //Ele tem um tratamento caso não consiga ler o txt do pib
        try (BufferedReader br = new BufferedReader(new FileReader("pib.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 2) {
                    System.out.println("Formato de linha inválido: " + line);
                    continue;
                }
                String state = parts[0];
                double pib = Double.parseDouble(parts[1].replace(",", ".")); 
                pibs.put(state, pib);
                totalPib += pib;
            }
        //Aqui ele deu errado
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        for (Map.Entry<String, Double> entry : pibs.entrySet()) {
            String state = entry.getKey();
            double pib = entry.getValue();
            double percent = (pib / totalPib) * 100;
            System.out.println(state + ": " + percent + "%");
        }

        Map<String, String> regioes = new HashMap<>();

        //Aqui ele abre o txt regioes
        try (BufferedReader br = new BufferedReader(new FileReader("regioes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length != 2) {
                    System.out.println("Formato de linha inválido: " + line);
                    continue;
                }
                String state = parts[0];
                String region = parts[1];
                regioes.put(state, region);
            }
        //Aqui ele deu errado
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Map<String, Double> pibPorRegiao = new HashMap<>();
        for (Map.Entry<String, Double> entry : pibs.entrySet()) {
            String state = entry.getKey();
            double pib = entry.getValue();
            String region = regioes.get(state);

            pibPorRegiao.put(region, pibPorRegiao.getOrDefault(region, 0.0) + pib);
        }

        //Aqui ele abre o txt saida
        try (FileWriter fw = new FileWriter("saida.txt")) {
            for (Map.Entry<String, Double> entry : pibPorRegiao.entrySet()) {
                String region = entry.getKey();
                double pib = entry.getValue();
                fw.write(region + ": " + pib + "\n");
            }
        //Aqui ele deu errado
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}