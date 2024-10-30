import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Cliente extends Pessoa {
    private String nomeArquivo = "Clientes.csv";
    private List<Cliente> clientes = new ArrayList<>();

    public Cliente(String nome, int idade, String cpf, String email) {
        super(nome, idade, cpf, email);
    }

    public Cliente() {
    }

    public void cadastrarClientes() {
        System.out.print("Digite o número do CPF: ");
        String cpf = ent.nextLine();
        for (Cliente a : clientes) {
            if (a.getCpf().equals(cpf)) {
                System.out.println("CPF já cadastrado!");
                return;
            }
        }
        Funcionario func = new Funcionario();
        for (Funcionario f : func.getFuncionarios()) {
            if (f.getCpf().equals(cpf)) {
                System.out.println("CPF já cadastrado!");
                return;
            }
        }
        Cliente novoCliente = new Cliente();
        System.out.println("-----------------------------");
        System.out.println("PÁGINA DE CADASTRO DE CLIENTES");
        System.out.println("-----------------------------");

        novoCliente.cadastrarPessoa();

        clientes.add(novoCliente);
        System.out.println("Cliente cadastrado!");
    }

    public void salvarDadosArquivo() {
        try {
            boolean arquivoExiste = new File(nomeArquivo).exists();
            FileWriter escritor = new FileWriter(nomeArquivo, StandardCharsets.ISO_8859_1, true);
            if (!arquivoExiste) {
                escritor.write("Nome;Idade;CPF;Email;Senha;\n");
            }
            for (Cliente a : clientes) {
                escritor.write(
                        a.getNome() + ";" + a.getIdade() + ";" + a.getCpf() + ";" + a.getEmail() + ";" + a.getSenha()
                                + "\n");
            }
            escritor.flush();
            escritor.close();
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo: " + e.getMessage());
        }
    }

    public void atualizarArquivo() {
        try (FileWriter escritor = new FileWriter(nomeArquivo, StandardCharsets.ISO_8859_1, false)) {
            escritor.write("Nome;Idade;CPF;Email;Senha;\n");

            for (Cliente a : clientes) {
                escritor.write(a.getNome() + ";" + a.getIdade() + ";" + a.getCpf() + ";"
                        + a.getEmail() + ";" + a.getSenha() + ";\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar o arquivo: " + e.getMessage());
        }
    }

    public void editarCliente() {
        System.out.print("Digite o número do CPF: ");
        String cpf = ent.nextLine();
        for (Cliente a : clientes) {
            if (a.getCpf().equals(cpf)) {
                System.out.println("Editar dados do(a) cliente: " + a.getNome());
                a.editarPessoa();
                atualizarArquivo();
                System.out.println("Dados editados com sucesso!");
                return;
            }
        }
        System.out.println("CPF não encontrado!");
    }

    public Cliente buscarCliente(String cpf) {
        for (Cliente a : clientes) {
            if (a.getCpf().equals(cpf)) {
                return a;
            }
        }
        System.out.println("Cliente não cadastrado!");
        return null;
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum clientes cadastrado.");
        } else {
            System.out.println("\n-----------------------------");
            System.out.println("LISTA DE CLIENTES CADASTRADOS");
            System.out.println("-----------------------------");

            for (Cliente a : clientes) {
                System.out.println("NOME:" + a.getNome());
                System.out.println("IDADE: " + a.getIdade());
                System.out.println("CPF: " + a.getCpf());
                System.out.println("EMAIL: " + a.getEmail());
                System.out.println("----------------------------------");
            }
        }
    }

    public void visualizarCliente() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes para visualizar.");
            return;
        }
        System.out.print("Digite o CPF do cliente que deseja visualizar: ");
        String cpf = ent.nextLine();
        boolean clienteEncontrado = false;

        for (Cliente a : clientes) {
            if (a.getCpf().equals(cpf)) {
                clienteEncontrado = true;
                System.out.println("Detalhes do Cliente: ");
                System.out.println("NOME: " + a.getNome());
                System.out.println("IDADE: " + a.getIdade());
                System.out.println("CPF: " + a.getCpf());
                System.out.println("EMAIL: " + a.getEmail());
                System.out.println("----------------------------------");
                return;
            }
        }
        if (!clienteEncontrado) {
            System.out.println("CPF inválido.");
        }
    }

    public void removerCliente() {
        System.out.print("Digite o número do CPF: ");
        String cpf = ent.nextLine();
        boolean clienteEncontrado = false;
        for (Cliente a : clientes) {
            if (a.getCpf().equals(cpf)) {
                clienteEncontrado = true;
                System.out.println("Cliente: " + a.getNome());
                System.out.println("Deseja remove-lo? (sim/não)");
                String resposta = ent.nextLine();
                if (resposta.equalsIgnoreCase("sim")) {
                    clientes.remove(a);
                    System.out.println("Cliente removido.");
                    return;
                } else {
                    System.out.println("Remoção de cliente cancelada.");
                }
            }
        }
        if (!clienteEncontrado) {
            System.out.println("Cliente não cadastrado!");
        }
    }

    public void lerArquivo() {
        File arquivo = new File(nomeArquivo);
        // Verifica se o arquivo existe antes de fazer a leitura
        if (!arquivo.exists()) {
            System.out.println("Arquivo de clientes não encontrado. Nenhum cliente carregado.");
            return; // Sai do método se o arquivo não existir
        }
        try {
            // Abrir o leitor para ler o arquivo
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
            String linha;
            boolean primeiraLinha = true;

            // Ler cada linha inteira no arquivo, ignorando a primeira linha
            while ((linha = leitor.readLine()) != null) {
                // Ignora a primeira linha
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Dividir a linha em partes usando o ponto e vírgula como separador
                String[] partes = linha.split(";");
                String nome = partes[0];
                int idade = Integer.parseInt(partes[1]); // converte para string
                String cpf = partes[2];
                String email = partes[3];
                String senha = partes[4];

                Cliente a = new Cliente(nome, idade, cpf, email);
                a.setSenha(senha);
                clientes.add(a);
            }
            leitor.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
