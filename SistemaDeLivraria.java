import java.util.Scanner;

public class SistemaDeLivraria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorDeUsuarios pessoa = new GerenciadorDeUsuarios();
        Livro livro = new Livro();
        boolean continuar = true;
        int opcao;

        System.out.println("Bem-vindo(a) à nossa livraria. Faça login ou se cadastre no sistema.");
        while (continuar) {
            System.out.println("");
            System.out.println("1. Cadastrar");
            System.out.println("2. Fazer login");
            System.out.println("3. Listar livros");
            System.out.println("4. Buscar livro");
            System.out.println("5. Sair");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    pessoa.realizaCadastro();
                    break;
                case 2:
                    pessoa.fazerLogin();
                    break;
                case 3:
                    livro.listarLivros();
                    break;
                case 4:
                    String titulo;
                    System.out.println("TÍTULO: ");
                    titulo = scanner.nextLine();
                    livro.buscarLivro(titulo);
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
        System.out.println("Programa encerrado.");
    }
}
