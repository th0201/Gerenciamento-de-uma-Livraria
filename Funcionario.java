import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Pessoa {
    private boolean administrador;
    private List<Funcionario> funcionarios = new ArrayList<>();
    private String arquivoFuncionarios = "Funcionarios.csv";

    public void cadastrarFuncionario() {
        System.out.print("Digite o número do CPF: ");
        String cpf = ent.nextLine();
        for (Funcionario a : funcionarios) {
            if (a.getCpf().equals(cpf)) {
                System.out.println("CPF já cadastrado!");
                return;
            }
        }
        Cliente cliente = new Cliente();
        for (Cliente c : cliente.getClientes()) {
            if (c.getCpf().equals(cpf)) {
                System.out.println("CPF já cadastrado!");
                return;
            }
        }
        Funcionario novoFuncionario = new Funcionario();
        System.out.println("-----------------------------");
        System.out.println("PÁGINA DE CADASTRO DE FUNCIONARIOS");
        System.out.println("-----------------------------");

        novoFuncionario.cadastrarPessoa();

        funcionarios.add(novoFuncionario);
        System.out.println("Funcionario cadastrado!");
    }

    public void editarFuncionario() {
        System.out.print("Digite o numero do CPF: ");
        String cpf = ent.nextLine();
        for (Funcionario a : funcionarios) {
            if (a.getCpf().equals(cpf)) {
                System.out.println("Editar dados do(a) funcionário(a): " + a.getNome());
                a.editarPessoa();
                atualizarArquivo();
                System.out.println("Dados editados com sucesso!");
                return;
            }
        }
        System.out.println("CPF não encontrado.");
    }

    public Funcionario buscarFuncionario(String cpf) {
        for (Funcionario a : funcionarios) {
            if (a.getCpf().equals(cpf)) {
                return a;
            }
        }
        System.out.println("Funcionário não cadastrado!");
        return null;
    }

    public void listarFuncionarios() {
        lerArquivo();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            System.out.println("\n-------------------------------");
            System.out.println("LISTA DE FUNCIONÁRIOS CADASTRADOS");
            System.out.println("----------------------------------");

            for (Funcionario a : funcionarios) {
                System.out.println("NOME: " + a.getNome());
                System.out.println("IDADE: " + a.getIdade());
                System.out.println("CPF: " + a.getCpf());
                System.out.println("EMAIL: " + a.getEmail());
                System.out.println("SENHA: " + a.getSenha());
                System.out.println("----------------------------------");
            }
        }
    }

    public void removerFuncionario() {
        System.out.print("Digite o numero do CPF: ");
        String cpf = ent.nextLine();
        boolean funcionarioEncontrado = false;

        for (Funcionario a : funcionarios) {
            if (a.getCpf().equals(cpf)) {
                funcionarioEncontrado = true;
                System.out.println("Funcionário(a):" + a.getNome());
                System.out.println("Deseja remove-lo? (sim/não)");
                String resposta = ent.nextLine();
                if (resposta.equalsIgnoreCase("sim")) {
                    funcionarios.remove(a);
                    atualizarArquivo();
                    System.out.println("Funcionario removido.");
                    return;
                } else {
                    System.out.println("Remoção de funcionario cancelada.");
                }
            }
        }

        if (!funcionarioEncontrado) {
            System.out.println("Funcionário não cadastrado!");
        }
    }

    public void atualizarArquivo() {
        try {
            boolean arquivoExiste = new File(arquivoFuncionarios).exists();
            FileWriter escritor = new FileWriter(arquivoFuncionarios, StandardCharsets.ISO_8859_1, true);
            if (!arquivoExiste) {
                escritor.write("Nome;Idade;CPF;Email;Senha;\n");
            }
            for (Funcionario a : funcionarios) {
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

    private void lerArquivo() {
        File arquivo = new File(arquivoFuncionarios);
        // Verifica se o arquivo existe antes de fazer a leitura
        if (!arquivo.exists()) {
            System.out.println("Arquivo de clientes não encontrado. Nenhum cliente carregado.");
            return; // Sai do método se o arquivo não existir
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoFuncionarios))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(";");
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(dados[0]);
                funcionario.setIdade(Integer.parseInt(dados[1]));
                funcionario.setCpf(dados[2]);
                funcionario.setEmail(dados[3]);
                funcionario.setSenha(dados[4]);
                funcionarios.add(funcionario);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void visualizarFuncionario() {
        if (funcionarios.isEmpty()) {
            System.out.println("Não há funcionários para visualizar.");
            return;
        }

        System.out.print("Digite o CPF do funcionário que deseja visualizar: ");
        String cpf = ent.nextLine();
        boolean funcionarioEncontrado = false;

        for (Funcionario a : funcionarios) {
            if (a.getCpf().equals(cpf)) {
                funcionarioEncontrado = true;
                System.out.println("\nDetalhes do Funcionário:");
                System.out.println("NOME: " + a.getNome());
                System.out.println("IDADE: " + a.getIdade());
                System.out.println("CPF: " + a.getCpf());
                System.out.println("EMAIL: " + a.getEmail());
                System.out.println("ADMINISTRADOR: " + (a.isAdministrador() ? "Sim" : "Não"));
                System.out.println("----------------------------------");
                return;
            }
        }

        if (!funcionarioEncontrado) {
            System.out.println("Funcionário com o CPF " + cpf + " não encontrado.");
        }
    }

    public List<Funcionario> getFuncionarios() {
        lerArquivo();
        return funcionarios;
    }

    public boolean isAdministrador() {
        return administrador;
    }
}
