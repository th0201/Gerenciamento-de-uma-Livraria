import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private int quantidade;
    private String corredor;
    private String prateleira;
    private double preco;

    Scanner scanner = new Scanner(System.in);
    private String nomeArquivo = "Livros.csv";
    private List<Livro> livro = new ArrayList<>();

    public Livro() {
    }

    public void cadastrarLivro() {
        System.out.print("Digite o nome do livro: ");
        String titulo = scanner.nextLine();
        System.out.print("Ano de lançamento: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        for (Livro l : livro) {
            if (l.getTitulo().equals(titulo) && l.getAno() == ano) {
                System.out.println("Livro já cadastrado!");
                return;
            }
        }

        System.out.println("\n------------------------------");
        System.out.println(" PÁGINA DE CADASTRO DE LIVROS");
        System.out.println("------------------------------");

        Livro novoLivro = new Livro();
        System.out.print("TÍTULO: ");
        novoLivro.titulo = scanner.nextLine();
        System.out.print("AUTOR: ");
        novoLivro.autor = scanner.nextLine();
        System.out.print("ANO DA EDIÇÃO: ");
        novoLivro.ano = scanner.nextInt();
        System.out.print("QUANTIDADE DISPONÍVEL: ");
        novoLivro.quantidade = scanner.nextInt();
        System.out.print("PREÇO: ");
        novoLivro.preco = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("CORREDOR: ");
        novoLivro.corredor = scanner.nextLine();
        System.out.print("PRATELEIRA: ");
        novoLivro.prateleira = scanner.nextLine();

        livro.add(novoLivro);
        atualizarArquivo();
        System.out.println("Livro cadastrado!");
    }

    private void atualizarArquivo() {
        try {
            FileWriter escritor = new FileWriter(nomeArquivo, StandardCharsets.ISO_8859_1);
            escritor.write("Titulo;Autor;Ano;Quantidade Disponível;Preço;Corredor;Prateleira\n");
            for (Livro l : livro) {
                escritor.write(l.getTitulo() + ";" + l.getAutor() + ";" + l.getAno() + ";"
                        + l.getQuantidade() + ";" + l.getPreco() + ";" + l.getCorredor() + ";"
                        + l.getPrateleira() + "\n");
            }
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editarLivro() {
        System.out.println("Digite o título do livro a ser editado: ");
        String titulo = scanner.nextLine();
        for (Livro l : livro) {
            if (l.getTitulo().equals(titulo)) {
                System.out.println("Editar o livro " + l.getTitulo());
                System.out.println("TÍTULO: ");
                l.titulo = scanner.nextLine();
                System.out.println("AUTOR: ");
                l.autor = scanner.nextLine();
                System.out.println("ANO DA EDIÇÃO: ");
                l.ano = scanner.nextInt();
                System.out.println("QUANTIDADE DISPONÍVEL: ");
                l.quantidade = scanner.nextInt();
                scanner.nextLine();
                System.out.println("PREÇO: ");
                l.preco = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("CORREDOR: ");
                l.corredor = scanner.nextLine();
                System.out.println("PRATELEIRA: ");
                l.prateleira = scanner.nextLine();
                atualizarArquivo();
                System.out.println("Livro editado com sucesso!");
                return;
            }
        }
        System.out.println("Livro não encontrado!");
    }

    public void visualizarLivro() {
        System.out.println("Digite o título do livro que deseja visualizar: ");
        String titulo = scanner.nextLine();
        for (Livro l : livro) {
            if (l.getTitulo().equals(titulo)) {
                System.out.println("TÍTULO: " + l.getTitulo());
                System.out.println("AUTOR: " + l.getAutor());
                System.out.println("ANO DA EDIÇÃO: " + l.getAno());
                System.out.println("QUANTIDADE DISPONÍVEL: " + l.getQuantidade());
                System.out.println("PREÇO: " + l.getPreco()); // Exibir preço
                System.out.println("CORREDOR: " + l.getCorredor());
                System.out.println("PRATELEIRA: " + l.getPrateleira());
                System.out.println("----------------------------------");
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    public Livro buscarLivro(String titulo) {
        for (Livro l : livro) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        System.out.println("Livro não encontrado.");
        return null;
    }

    public void listarLivros() {
        if (livro.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            System.out.println("--------------------------------");
            System.out.println(" LISTA DE LIVROS CADASTRADOS:");
            System.out.println("--------------------------------");
            for (Livro l : livro) {
                System.out.println("TÍTULO: " + l.getTitulo() + "\nAUTOR: " + l.getAutor() + "\nANO: " + l.getAno()
                        + "\nQUANTIDADE: " + l.getQuantidade() + "\nPREÇO: " + l.getPreco() + "\nCORREDOR: "
                        + l.getCorredor() + "\nPRATELEIRA: " + l.getPrateleira());
            }
        }
    }

    public void removerLivro() {
        System.out.println("Digite o título do livro a ser removido: ");
        String titulo = scanner.nextLine();
        for (Livro l : livro) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                livro.remove(l);
                atualizarArquivo();
                System.out.println("Livro removido com sucesso!");
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    public void lerArquivo() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de livros não encontrado.");
            return;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo, StandardCharsets.ISO_8859_1))) {
            String linha;
            leitor.readLine(); // Pular a linha de cabeçalho
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    String titulo = dados[0];
                    String autor = dados[1];
                    int ano = Integer.parseInt(dados[2]);
                    int quantidadeDisponivel = Integer.parseInt(dados[3]);
                    double preco = Double.parseDouble(dados[4]);
                    String corredor = dados[5];
                    String prateleira = dados[6];

                    Livro l = new Livro(titulo, autor, ano, quantidadeDisponivel, preco, corredor, prateleira);
                    livro.add(l);
                }
            }
            System.out.println("Dados carregados do arquivo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void menuLivro() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Listar livros");
            System.out.println("3. Buscar livro");
            System.out.println("4. Remover livro");
            System.out.println("5. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    String titulo;
                    System.out.println("TÍTULO: ");
                    titulo = scanner.nextLine();
                    buscarLivro(titulo);
                    break;
                case 4:
                    removerLivro();
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Programa encerrado.");
    }

    public Livro(String titulo, String autor, int ano, int quantidade, double preco, String corredor,
            String prateleira) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.quantidade = quantidade;
        this.preco = preco;
        this.corredor = corredor;
        this.prateleira = prateleira;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCorredor() {
        return corredor;
    }

    public void setCorredor(String corredor) {
        this.corredor = corredor;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public List<Livro> getLivro() {
        lerArquivo();
        return livro;
    }
}
