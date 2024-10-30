import java.util.Scanner;

public class GerenciadorDeUsuarios {
    int opcao;
    Scanner ent = new Scanner(System.in);
    Cliente cliente = new Cliente();
    Funcionario funcionario = new Funcionario();
    Livro livro = new Livro();
    Venda vendas = new Venda();

    public void menucliente() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("MENU:");
            System.out.println("1. Editar cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Listar clientes");
            System.out.println("4. Remover cliente");
            System.out.println("5. Visualizar cliente");
            System.out.println("6. Acessar menu do livro");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = ent.nextInt();
            ent.nextLine();

            switch (opcao) {
                case 1:
                    cliente.editarCliente();
                    break;
                case 2:
                    System.out.print("CPF:");
                    String cpf = ent.nextLine();
                    cliente.buscarCliente(cpf);
                    break;
                case 3:
                    cliente.listarClientes();
                    break;
                case 4:
                    cliente.removerCliente();
                    break;
                case 5:
                    cliente.visualizarCliente();
                    break;
                case 6:
                    menuLivro();
                    break;
                case 7:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Saindo...");
    }

    public void menuFuncionario() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("MENU:");
            System.out.println("1. Editar Funcionario");
            System.out.println("2. Buscar Funcionario");
            System.out.println("3. Listar Funcionarios");
            System.out.println("4. Remover Funcionario");
            System.out.println("5. Visualizar Funcionario");
            System.out.println("6. Acessar menu do livro");
            System.out.println("7. Realizar uma venda");
            System.out.println("8. Listar vendas");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = ent.nextInt();

            switch (opcao) {
                case 1:
                    funcionario.editarFuncionario();
                    break;
                case 2:
                    System.out.print("CPF:");
                    String cpf = ent.nextLine();
                    funcionario.buscarFuncionario(cpf);
                    break;
                case 3:
                    funcionario.listarFuncionarios();
                    break;
                case 4:
                    funcionario.removerFuncionario();
                    break;
                case 5:
                    funcionario.visualizarFuncionario();
                    break;
                case 6:
                    System.out.println("----------------------------");
                    System.out.println("  GERENCIAMENTO DE LIVROS");
                    System.out.println("----------------------------");
                    livro.menuLivro();
                    break;
                case 7:
                    vendas.realizarVenda(livro);
                    break;
                case 8:
                    vendas.listarVendas();
                    break;
                case 9:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Saindo...");
    }

    public void menuLivro() {
        Livro livro = new Livro();
        boolean continuar = true;
        while (continuar) {
            System.out.println("1. Listar livros");
            System.out.println("2. Buscar livro");
            System.out.println("3. Sair");

            int opcao = ent.nextInt();
            ent.nextLine();

            switch (opcao) {
                case 1:
                    livro.listarLivros();
                    break;
                case 2:
                    String titulo;
                    System.out.println("TÍTULO: ");
                    titulo = ent.nextLine();
                    livro.buscarLivro(titulo);
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Saindo...");
    }

    public void realizaCadastro() {
        System.out.println("Realizar cadastro de cliente ou de funcionario? ");
        String resposta = ent.nextLine();

        if (resposta.equalsIgnoreCase("cliente")) {
            cliente.cadastrarClientes();
            cliente.salvarDadosArquivo();
            menucliente();
        } else if (resposta.equalsIgnoreCase("funcionario")) {
            funcionario.cadastrarFuncionario();
            funcionario.atualizarArquivo();
            menuFuncionario();
        }
    }

    public void verificaCadastro() {
        if (cliente.getClientes() == null || funcionario.getFuncionarios() == null) {
            System.out.println("Nenhum cliente cadastrado no sistema.");
            System.out.println("Você deve se cadastrar primeiro.");
            realizaCadastro();
        }
    }

    public void fazerLogin() {
        verificaCadastro();
        boolean loginBemSucedido = false;
        int tentativas = 0;

        while (!loginBemSucedido) {
            System.out.print("Email: ");
            String email = ent.nextLine();
            System.out.print("Senha: ");
            String senha = ent.nextLine();

            for (Cliente a : cliente.getClientes()) {
                if (a.login(email, senha)) {
                    System.out.println("Login bem-sucedido! Bem-vindo(a), " + a.getNome() + "!");
                    loginBemSucedido = true;
                    menucliente();
                    return;
                }
            }

            for (Funcionario f : funcionario.getFuncionarios()) {
                if (f.login(email, senha)) {
                    System.out.println("Login bem-sucedido! Bem-vindo(a), " + f.getNome() + "!");
                    loginBemSucedido = true;
                    menuFuncionario();
                    return;
                }
            }
            if (!loginBemSucedido) {
                System.out.println("Email ou senha incorretos. Tente novamente.");
            }
            tentativas++;
            if (tentativas == 3) {
                System.out.println("Número máximo de tentativas atingido. Tente novamente mais tarde.");
                return;
            }
        }
        ent.close();
    }
}

