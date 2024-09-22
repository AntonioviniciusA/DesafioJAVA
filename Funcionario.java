import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Funcionario {
    static Map<String, Map<String, Double>> vendas = new HashMap<>();
    static Double valor;
    static String mesDesejado;
    static LocalDate dataDesejada;

    private String nome;
    private LocalDate dataDeContratacao;
    private String nomeCargo;
    private double salarioBase;
    private double somaPAno;
    private String beneficio;

    private double beneficioPago;
    private double valorTotalPago;
    private double salario;

    protected static List<Funcionario> funcionarios = new ArrayList<>();

    public Funcionario(String nome, String nomeCargo, double salarioBase, double somaPAno, String beneficio,
            LocalDate dataDeContratacao) {
        this.dataDeContratacao = dataDeContratacao;
        this.nome = nome;
        this.somaPAno = somaPAno;
        this.beneficio = beneficio;
        this.nomeCargo = nomeCargo;
        this.salarioBase = salarioBase;

        this.beneficioPago = 0;
        this.setBeneficioPago();
        this.setSalario();
        this.setValorTotalPago();
    }

    private Funcionario(String vendedor) {
        this.nome = nome;
        this.salario = getSalario();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeContratacao() {
        return dataDeContratacao;
    }

    public void setDataDeContratacao(LocalDate dataDeContratacao) {
        this.dataDeContratacao = dataDeContratacao;
    }

    public int getAnosDeServico() {
        dataDesejada = LocalDate.now();
        if (dataDeContratacao == null) {
            throw new IllegalArgumentException("A data de contratação não pode ser nula.");
        }
        if (dataDesejada == null) {
            throw new IllegalArgumentException("A data final não pode ser nula.");
        }
        return Period.between(dataDeContratacao, dataDesejada).getYears();
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario() {
        if (getNomeCargo().equals("Secretário")) {
            this.salario = salarioBase + (getAnosDeServico() * getSomaPAno());
        } else if (getNomeCargo().equals("Vendedor")) {
            this.salario = salarioBase + (getAnosDeServico() * getSomaPAno());
        } else if (getNomeCargo().equals("Gerente")) {
            this.salario = salarioBase + (getAnosDeServico() * getSomaPAno());
        }
    }

    public String getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(String beneficio) {
        this.beneficio = beneficio;
    }

    public double getSomaPAno() {
        return somaPAno;
    }

    public void setSomaPAno(double somaPAno) {
        this.somaPAno = somaPAno;
    }

    public double getBeneficioPago() {
        return beneficioPago;
    }

    public void setBeneficioPago() {
        if (getNomeCargo().equals("Secretário")) {
            this.beneficioPago = salarioBase * 0.2;
        }
    }

    public double getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago() {
        if (getNomeCargo().equals("Secretário")) {
            this.valorTotalPago = getSalario() + getBeneficioPago();
        } else if (getNomeCargo().equals("Gerente")) {
            this.valorTotalPago = getSalario();
        }
    }

    public static void inicializar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o mês (ex: '01/2022'): ");
        String dataDesejadaStr = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

            LocalDate dataDesejada = LocalDate.parse("01/" + dataDesejadaStr,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            mesDesejado = "mês " + dataDesejadaStr;

            System.out.println("Vendas no " + mesDesejado + ":");
            for (String vendedor : vendas.keySet()) {
                valor = vendas.get(vendedor).get(mesDesejado);
                if (valor != null) {
                    System.out.println(vendedor + ": R$ " + valor);
                } else {
                    System.out.println(vendedor + ": Não vendeu nesse mês.");
                }
            }

        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Por favor, use o formato MM/yyyy.");
        }

        scanner.close();
    }

    public static void calcularBeneficioParaVend() {
        System.out.println("\nCalculando o benefício para todos os vendedores no mês " + mesDesejado + ":");
        for (String vendedor : vendas.keySet()) {

            valor = vendas.get(vendedor).get(mesDesejado);
            if (valor != null) {
                double beneficioPagoVend = valor * 0.3;
                System.out.println("Benefício pago para " + vendedor + ": R$ " + beneficioPagoVend);

                Funcionario funcionario = new Funcionario(vendedor);
                double Salario = funcionario.getSalario();
                double ValorTotalPago = Salario + beneficioPagoVend;

                System.out.println("Valor total pago para " + vendedor + ": R$ " + ValorTotalPago);
            } else {
                System.out.println(vendedor + ": Não vendeu no " + mesDesejado + ".");
            }
        }
        return;

    }

    private static double calcularBeneficioTotal() {
        double beneficioTotal = 0.0;
        double beneficioAcumulado = 0.0;
        double beneficioTotalVend = 0.0;

        System.out.println("\nCalculando o benefício total para o mês " + mesDesejado + ":");
        for (Funcionario funcionario : funcionarios) {
            beneficioAcumulado += funcionario.getBeneficioPago();
            // System.out.println(funcionario.getNome() + funcionario.nomeCargo +
            // beneficioAcumulado);
        }
        for (String vendedor : vendas.keySet()) {
            valor = vendas.get(vendedor).get(mesDesejado);
            if (valor != null) {
                double beneficioPagoVend = valor * 0.3;

                beneficioTotalVend += beneficioPagoVend;

            } else {
                System.out.println(vendedor + ": Não vendeu no " + mesDesejado + ".");
            }
        }
        beneficioTotal = beneficioTotalVend + beneficioAcumulado;

        System.out.println("\nBenefício total: R$ " + beneficioTotal);
        System.out.println(
                "Beneficio total dado aos vendedores: R$ " + beneficioTotalVend);
        System.out.println(
                "Beneficio total dado aos secretários: R$ " + beneficioAcumulado);

        return beneficioTotal;
    }

    private static void vendeuMais() {
        System.out.println(mesDesejado + ":");
        for (String vendedor1 : vendas.keySet()) {
            Double valor1 = vendas.get(vendedor1).get(mesDesejado);
            for (String vendedor2 : vendas.keySet()) {
                Double valor2 = vendas.get(vendedor2).get(mesDesejado);
                if (valor1 != null && valor2 != null && !vendedor1.equals(vendedor2)) {
                    if (valor1 > valor2) {
                        System.out
                                .println(vendedor1 + " vendeu mais que " + vendedor2);
                        break;
                    } else if (valor1 < valor2) {
                        System.out
                                .println(vendedor1 + " vendeu menos que " + vendedor2);
                        break;
                    } else {
                        System.out.println(
                                vendedor1 + " vendeu o mesmo que " + vendedor2);
                        break;
                    }
                }
            }
        }

    }

    private static void maiorBeneficio() {
        double beneficioMaiorVend = 0.0;
        String vendedorComMaiorBeneficio = "";

        for (Funcionario funcionario : funcionarios) {
            String vendedor = funcionario.getNome();

            Map<String, Double> vendasDoVendedor = vendas.get(vendedor);
            if (vendasDoVendedor != null) {
                Double valor = vendasDoVendedor.get(mesDesejado);
                if (valor != null) {
                    double beneficioPagoVend = valor * 0.3;

                    if (beneficioPagoVend > beneficioMaiorVend) {
                        beneficioMaiorVend = beneficioPagoVend;
                        vendedorComMaiorBeneficio = vendedor;
                    }
                }
            }
            /*
             * System.out.println(
             * "Maior benefício do vendedor: " + vendedorComMaiorBeneficio + " = R$ " +
             * beneficioMaiorVend);
             */
        }

        double beneficioMaiorFunc = 0.0;
        String FuncComMaiorBeneficio = "";

        for (Funcionario funcionario : funcionarios) {
            String func = funcionario.getNome();
            double beneficioPagoFunc = funcionario.getBeneficioPago();

            if (beneficioPagoFunc > beneficioMaiorFunc) {
                beneficioMaiorFunc = beneficioPagoFunc;
                FuncComMaiorBeneficio = func;
            }
            /*
             * System.out.println(
             * "Maior benefício: " + FuncComMaiorBeneficio + " = R$ " + beneficioMaiorFunc);
             */
        }

        if (beneficioMaiorVend > beneficioMaiorFunc) {
            if (!vendedorComMaiorBeneficio.isEmpty()) {
                System.out.println(
                        "que recebeu o valor mais alto em benefícios no mês: " + vendedorComMaiorBeneficio + " = R$ "
                                + beneficioMaiorVend);
            } else {
                System.out.println("erro!!");
            }
        } else if (beneficioMaiorVend < beneficioMaiorFunc) {
            if (!FuncComMaiorBeneficio.isEmpty()) {
                System.out.println(
                        "que recebeu o valor mais alto em benefícios no mês: " + FuncComMaiorBeneficio + " = R$ "
                                + beneficioMaiorFunc);
            } else {
                System.out.println("erro!!");
            }
        }
    }

    private static void recebeuMais() {
        double salario = 0;
        double salarioMaior = 0;
        String R = "", RMais = null;
        for (Funcionario funcionario : funcionarios) {

            if (funcionario.getNomeCargo().equals("Secretário")) {
                salario = funcionario.getBeneficioPago() + funcionario.getSalario();
                R = funcionario.getNome();
                System.out.println(" salario com beneficios: " + funcionario.getNome() +
                        " R$ " + salario);
            } else if (funcionario.getNomeCargo().equals("Vendedor")) {

                double beneficioPagoVend = 0;

                String vendedorNome = funcionario.getNome();
                Double valor = vendas.get(vendedorNome).get(mesDesejado);
                if (valor != null) {
                    beneficioPagoVend = valor * 0.3;
                }
                salario = beneficioPagoVend + funcionario.getSalario();
                R = funcionario.getNome();
                System.out.println(" salario com beneficios: " + funcionario.getNome() +
                        " R$ " + salario);

            } else if (funcionario.getNomeCargo().equals("Gerente")) {
                salario = funcionario.getSalario();
                R = funcionario.getNome();
                System.out.println(" salario com beneficios: " + funcionario.getNome() +
                        " R$ " + salario);
            }

            if (salario > salarioMaior) {
                salarioMaior = salario;
                RMais = R;
            }

        }
        System.out.println(" recebeu mais: " + RMais);

    }

    public static void status() {
        double totalA, totalDeSalario = 0;
        for (Funcionario funcionario : funcionarios) {
            totalDeSalario += funcionario.getSalario();
            System.out.println(" salario com beneficios: " + funcionario.getNome() +
                    " R$ " + (funcionario.getSalario() + funcionario.getBeneficioPago()));
        }
        totalA = totalDeSalario + calcularBeneficioTotal();

        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");

        System.out.println("Total pago de salario com beneficios a todos os funcionários: R$ " + totalA);

        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        double totalB = 0;

        for (Funcionario funcionario : funcionarios) {
            totalB += funcionario.getSalario();
        }
        System.out.println("Total pago em salarios para todos os funcionarios: R$ " + totalB);

        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        maiorBeneficio();
        vendeuMais();
        recebeuMais();
    }

    public static void main(String[] args) {
        funcionarios.add(new Funcionario("Jorge Carvalho", "Secretário", 7000.00, 1000.00, "20% sobre o salário",
                LocalDate.of(2018, 1, 1)));
        funcionarios.add(new Funcionario("Maria Souza", "Secretário", 7000.00, 1000.00, "20% sobre o salário",
                LocalDate.of(2015, 12, 1)));
        funcionarios.add(new Funcionario("Juliana Alves", "Gerente", 20000.00, 3000.00, "Não tem benefícios",
                LocalDate.of(2017, 7, 1)));
        funcionarios.add(new Funcionario("Bento Albino", "Gerente", 20000.00, 3000.00, "Não tem benefícios",
                LocalDate.of(2014, 3, 1)));
        funcionarios.add(new Funcionario("Ana Silva", "Vendedor", 12000.00, 1800.00, "30% sobre o valor vendido",
                LocalDate.of(2021, 12, 1)));
        funcionarios.add(new Funcionario("João Mendes", "Vendedor", 12000.00, 1800.00, "30% sobre o valor vendido",
                LocalDate.of(2021, 12, 1)));

        // Dados das vendas
        vendas.put("Ana Silva", Map.of(
                "mês 12/2021", 5200.00,
                "mês 01/2022", 4000.00,
                "mês 02/2022", 4200.00,
                "mês 03/2022", 5850.00,
                "mês 04/2022", 7000.00));

        vendas.put("João Mendes", Map.of(
                "mês 12/2021", 3400.00,
                "mês 01/2022", 7700.00,
                "mês 02/2022", 5000.00,
                "mês 03/2022", 5900.00,
                "mês 04/2022", 6500.00));
        inicializar();
        status();

    }

}
