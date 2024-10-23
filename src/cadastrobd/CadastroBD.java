package cadastrobd;

/**
 *
 * @author edson-202308892185
 */
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;

import java.util.List;
import java.util.Scanner;

public class CadastroBD {

    public static void main(String[] args) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        Scanner scanner = new Scanner(System.in);

        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("\n==============================");
                System.out.println("1 - Incluir Pessoa");
                System.out.println("2 - Alterar Pessoa");
                System.out.println("3 - Excluir Pessoa");
                System.out.println("4 - Buscar pelo Id");
                System.out.println("5 - Exibir Todos");
                System.out.println("0 - Finalizar Programa");
                System.out.println("==============================");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 ->
                        incluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 2 ->
                        alterar(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 3 ->
                        excluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 4 ->
                        obter(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 5 ->
                        obterTodos(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 0 -> {
                        continuar = false;
                        System.out.println("Programa finalizado.");
                    }
                    default ->
                        System.out.println("Opcao invalida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    private static void incluir(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws Exception {
        char tipo = lerTipo(scanner);

        switch (tipo) {
            case 'F' -> {
                PessoaFisica pf = lerDadosPessoaFisica(scanner);
                pfDAO.incluir(pf);
                System.out.println("Pessoa Fisica incluida com sucesso.");
            }
            case 'J' -> {
                PessoaJuridica pj = lerDadosPessoaJuridica(scanner);
                pjDAO.incluir(pj);
                System.out.println("Pessoa Juridica incluida com sucesso.");
            }
            default ->
                System.out.println("Tipo invalido.");
        }
    }

    private static void alterar(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws Exception {
        char tipo = lerTipo(scanner);

        System.out.print("Informe o ID da pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        switch (tipo) {
            case 'F':
                PessoaFisica pf = pfDAO.getPessoa(id);
                if (pf != null) {
                    System.out.println("Dados atuais: ");
                    pf.exibir();
                    System.out.println("Informe os novos dados:");
                    PessoaFisica novosDados = lerDadosPessoaFisica(scanner);
                    novosDados.setId(pf.getId());
                    pfDAO.alterar(novosDados);
                    System.out.println("Pessoa Fisica alterada com sucesso.");
                } else {
                    System.out.println("Pessoa Fisica nao encontrada.");
                }
                break;
            case 'J':
                PessoaJuridica pj = pjDAO.getPessoa(id);
                if (pj != null) {
                    System.out.println("Dados atuais: ");
                    pj.exibir();
                    System.out.println("Informe os novos dados:");
                    PessoaJuridica novosDados = lerDadosPessoaJuridica(scanner);
                    novosDados.setId(pj.getId());
                    pjDAO.alterar(novosDados);
                    System.out.println("Pessoa Juridica alterada com sucesso.");
                } else {
                    System.out.println("Pessoa Juridica nao encontrada.");
                }
                break;
            default:
                System.out.println("Tipo invalido.");
                break;
        }
    }

    private static void excluir(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws Exception {
        char tipo = lerTipo(scanner);

        System.out.print("Informe o ID da pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        switch (tipo) {
            case 'F' -> {
                pfDAO.excluir(id);
                System.out.println("Pessoa Fisica excluida com sucesso.");
            }
            case 'J' -> {
                pjDAO.excluir(id);
                System.out.println("Pessoa Juridica excluida com sucesso.");
            }
            default ->
                System.out.println("Tipo invalido.");
        }
    }

    private static void obter(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws Exception {
        char tipo = lerTipo(scanner);

        System.out.print("Informe o ID da pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        switch (tipo) {
            case 'F':
                PessoaFisica pf = pfDAO.getPessoa(id);
                if (pf != null) {
                    pf.exibir();
                } else {
                    System.out.println("Pessoa Fisica nao encontrada.");
                }
                break;
            case 'J':
                PessoaJuridica pj = pjDAO.getPessoa(id);
                if (pj != null) {
                    pj.exibir();
                } else {
                    System.out.println("Pessoa Juridica nao encontrada.");
                }
                break;
            default:
                System.out.println("Tipo invalido.");
                break;
        }
    }

    private static void obterTodos(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws Exception {
        char tipo = lerTipo(scanner);

        switch (tipo) {
            case 'F' -> {
                List<PessoaFisica> pessoas = pfDAO.getPessoas();
                pessoas.forEach(PessoaFisica::exibir);
            }
            case 'J' -> {
                List<PessoaJuridica> pessoas = pjDAO.getPessoas();
                pessoas.forEach(PessoaJuridica::exibir);
            }
            default ->
                System.out.println("Tipo invalido.");
        }
    }

    private static char lerTipo(Scanner scanner) {
        char tipo;
        do {
            System.out.print("F - Pessoa Fisica | J - Pessoa Juridica: ");
            String entrada = scanner.nextLine().toUpperCase();

            if (entrada.length() == 1 && (entrada.equals("F") || entrada.equals("J"))) {
                tipo = entrada.charAt(0);
                break;
            } else {
                System.out.println("Entrada invalida. Digite apenas 'F' ou 'J'.");
            }
        } while (true);

        return tipo;
    }

    private static PessoaFisica lerDadosPessoaFisica(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        String estado;
        do {
            System.out.print("Estado: ");
            estado = scanner.nextLine().toUpperCase();
            if (estado.length() != 2) {
                System.out.println("O estado deve ter exatamente 2 caracteres.");
            }
        } while (estado.length() != 2);
        String telefone;
        do {
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
            if (telefone.length() > 11) {
                System.out.println("O telefone deve ter no maximo 11 caracteres.");
            }
        } while (telefone.length() > 11);
        System.out.print("Email: ");
        String email = scanner.nextLine();
        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
            if (cpf.length() > 14) {
                System.out.println("O CPF deve ter no maximo 14 caracteres.");
            }
        } while (cpf.length() > 14);
        return new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
    }

    private static PessoaJuridica lerDadosPessoaJuridica(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        String estado;
        do {
            System.out.print("Estado: ");
            estado = scanner.nextLine().toUpperCase();
            if (estado.length() != 2) {
                System.out.println("O estado deve ter exatamente 2 caracteres.");
            }
        } while (estado.length() != 2);
        String telefone;
        do {
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
            if (telefone.length() > 11) {
                System.out.println("O telefone deve ter no maximo 11 caracteres.");
            }
        } while (telefone.length() > 11);
        System.out.print("Email: ");
        String email = scanner.nextLine();
        String cnpj;
        do {
            System.out.print("CNPJ: ");
            cnpj = scanner.nextLine();
            if (cnpj.length() > 18) {
                System.out.println("O CNPJ deve terA no maximo 14 caracteres.");
            }
        } while (cnpj.length() > 18);
        return new PessoaJuridica(0, nome, logradouro, cidade, estado, telefone, email, cnpj);
    }
}
