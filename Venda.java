import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Venda {
    private double totalVenda;
    private List<String> registrosVendas; 
    private Scanner scanner = new Scanner(System.in);
    private String nomeArquivo = "Vendas.csv";

    public Venda() {
        this.totalVenda = 0.0;
        this.registrosVendas = new ArrayList<>();
    }

    public void realizarVenda(Livro livro) {
        System.out.println("------------------");
        System.out.println(" REALIZAR VENDA");
        System.out.println("------------------");
        boolean continuarVendendo = true;

        while (continuarVendendo) {
            livro.listarLivros();
            System.out.print("Digite o título do livro que deseja vender: ");
            String titulo = scanner.nextLine();
            Livro livroEncontrado = livro.buscarLivro(titulo); 

            if (livroEncontrado != null && livroEncontrado.getQuantidade() > 0) {
                System.out.print("Quantidade a ser vendida: ");
                int quantidade = scanner.nextInt();
                scanner.nextLine();

                if (quantidade <= livroEncontrado.getQuantidade()) {
                    livroEncontrado.setQuantidade(livroEncontrado.getQuantidade() - quantidade);
                    double subtotal = quantidade * livroEncontrado.getPreco();
                    totalVenda += subtotal;

                    String registro = "Título: " + livroEncontrado.getTitulo() + ", Quantidade: " + quantidade + ", Total: R$ " + subtotal;
                    registrosVendas.add(registro);

                    System.out.println("Venda realizada! Subtotal: R$ " + subtotal);
                    return;
                } else {
                    System.out.println("Quantidade disponível insuficiente.");
                }
            } else {
                System.out.println("Livro não encontrado ou indisponível.");
            }

            System.out.print("Deseja vender outro livro? (sim/não): ");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("não")) {
                continuarVendendo = false;
                return;
            }
        }
        System.out.println("Total da venda: R$ " + totalVenda);
        salvarVendas(); 
    }

    public void listarVendas() {
        System.out.println("\nREGISTROS DE VENDAS:");
        if (registrosVendas.isEmpty()) {
            System.out.println("Nenhuma venda realizada.");
        } else {
            for (String registro : registrosVendas) {
                System.out.println(registro);
            }
        }
    }

    private void salvarVendas() {
        try (FileWriter writer = new FileWriter(nomeArquivo, StandardCharsets.ISO_8859_1)) {
            writer.write("Título;Quantidade;Total\n");
            for (String registro : registrosVendas) {
                writer.write(registro.replace("Título: ", "").replace(", Quantidade: ", ";").replace(", Total: R$ ", ";") + "\n");
            }
            System.out.println("Vendas salvas em " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar as vendas: " + e.getMessage());
        }
    }

    private void lerVendas() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de vendas não encontrado. Iniciando novo registro.");
            return;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo, StandardCharsets.ISO_8859_1))) {
            String linha;
            leitor.readLine(); // Pular a linha de cabeçalho
            while ((linha = leitor.readLine()) != null) {
                registrosVendas.add(linha.replace(";", ", ").replace(",", ": Total: R$ "));
            }
            System.out.println("Vendas carregadas do arquivo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de vendas: " + e.getMessage());
        }
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public List<String> getRegistrosVendas() {
        lerVendas();
        return registrosVendas;
    }    
}
