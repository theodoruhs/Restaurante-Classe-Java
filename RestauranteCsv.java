import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//LEMBRAR DE COMENTAR TUDO

public class RestauranteCsv{


//CLASSE HORA =================================
static class Hora{

	private int hora;//todos atributos tem que ser privados e em camel case
	private int minuto;

	Hora(int hora,int min){//construtor da hora
		
		this.hora = hora;
		this.minuto = min;
	
	}
	
	public static Hora parseHora(String s) { //###### funcao pedida que cria um objeto hora a partir de uma string
 
    String hStr = "";
    String mStr = "";
    
    int i = 0;
    // Pega a parte da hora
    while (i < s.length() && s.charAt(i) != ':') { //ate o : vai ser a parte da hora
        hStr += s.charAt(i); //vou usar concatenacao de string auxiliar
        i++;
    }
    
    i++; // Pula o ':'
    
    // Pega a parte do minuto até o fim da string
    while (i < s.length()) {
        mStr += s.charAt(i);
        i++;
    }
    
    // ### caso nao puder usar parseInt, usar parseInteiro!!!!
    int h = Integer.parseInt(hStr.trim()); // USEI TRIM AQUI ###
    int m = Integer.parseInt(mStr.trim());
    
    return new Hora(h, m);
  }

  public String formatar(){ //###### funcao pedida que formata a hora e minuto do objeto atual e retorna string

    String formatado = "";
    
    String h = String.format("%02d", this.hora);
    String m = String.format("%02d", this.minuto);
    
    formatado = formatado + h + ":" + m;
    
    return formatado; 
  }

}	

// CLASSE DATA ==============================
static class Data{

	private int ano;
	private int mes;
	private int dia;

	Data(int ano,int mes,int dia){
	
		this.ano = ano;
		this.mes = mes;
		this.dia = dia;
	}

  public static Data parseData(String s){//### recebe uma string no formato YYYY-MM-DD
  
  String ano = "";
  String mes = "";
  String dia = "";
  
  int i = 0; //o i vai percorrer a string
  while(i < s.length() && s.charAt(i) != '-'){//isso basicamente e um split "-" !!
    ano += s.charAt(i);
    i++;
  }
  i++; //pula o -
  while(i < s.length() && s.charAt(i) != '-'){
   mes += s.charAt(i);
   i++;
  }
  i++; //pula o segundo -
  while(i < s.length()){
   dia += s.charAt(i);
   i++;
  }

  //##### se nao puder usar parseInt usar parseInteiro!!
  int anoInt = Integer.parseInt(ano);
  int mesInt = Integer.parseInt(mes);
  int diaInt = Integer.parseInt(dia);
  
  return new Data(anoInt,mesInt,diaInt); 
  }
  
  public String formatar(){
  
  return String.format("%02d/%02d/%04d",  this.dia, this.mes, this.ano);
  
  }
  
  }
  
  public static int parseInteiro(String s){ //metodo parse inteiro que susbtitui parse int caso nao puder usar

    java.util.Scanner scan = new java.util.Scanner(s);
    int saida = scan.nextInt();
    scan.close();
    
    return saida;

  }
  
  public static double parseDoble(String s){ //metodo parse double que substitui parse int

    java.util.Scanner scan = new java.util.Scanner(s);
    double saida = scan.nextDouble();
    scan.close();
    
    return saida;

  }

//CLASSE RESTAURANTE =======================================================
static class Restaurante{

	private int id; 
	private String nome; 
	private String cidade; 
	private int capacidade; 
	private double avaliacao;

	private String[] tiposDeCozinha;
	private int faixaDePreco;

	private Hora horaDeAbertura;
	private Hora horaDeFechamento; 

	private Data dataAbertura;
	private boolean aberto; 

	
	public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao, 
                   String[] tiposDeCozinha, int faixaDePreco, Hora horaDeAbertura, 
                   Hora horaDeFechamento, Data dataAbertura, boolean aberto) {//construtor que recebe todos os atributos como parametro
									      
    		this.id = id;
    		this.nome = nome;
    		this.cidade = cidade;
    		this.capacidade = capacidade;
    		this.avaliacao = avaliacao;
    		this.tiposDeCozinha = tiposDeCozinha;
    		this.faixaDePreco = faixaDePreco;
    		this.horaDeAbertura = horaDeAbertura;
    		this.horaDeFechamento = horaDeFechamento;
    		this.dataAbertura = dataAbertura;
    		this.aberto = aberto;
	}
	



//getters da classe restaurante

	public int getId(){
	
		return this.id;
	}
	public String getNome(){
	
		return this.nome;
	}
	public String getCidade(){
	
		return this.cidade;
	}
	public int getCapacidade(){
	
		return this.capacidade;
	}
	public double getAvaliacao(){
	
		return this.avaliacao;
	}

	//##01 primeira parte basica ate aqui
	
	public String[] getTiposDeCozinha(){
		
		return this.tiposDeCozinha;
	}
	public int getFaixaDePreco(){
	
		return this.faixaDePreco;
	}

	//##02 segunda parte ate aqui
	
	public Hora getHoraDeAbertura(){
	
		return this.horaDeAbertura;
	}
	public Hora getHoraDeFechamento(){
	
		return this.horaDeFechamento;
	}

	//##03 terceira parte ate aqui
	
	public Data getDataAbertura(){
	
	  return this.dataAbertura;
	}
	public Boolean getAberto(){
  
    return this.aberto;
	} 
	
	//======= Metodo Parse restaurante ===================
 
 public static Restaurante parseRestaurante(String s){
 
  String[] partes = new String[10]; //vou criar 10 Strings, cada uma vai ser um atributo
  
  int j = 0; // j vai percorrer o array do csv que tem todos atributos
  
  for(int i = 0; i < 10; i++){
  
    partes[i] = "";//inicio a string
    
    while(j < s.length() && s.charAt(j) != ','){
    
      partes[i] += s.charAt(j); //vou concatenando ate achar uma virgula
      j++;
    }
    j++;//pulo a virgula;
    
  }
  //##### ate aqui fiz o split dos 11 atributos e tenho um array de strings ######
  
  
 
 int id = parseInteiro(partes[0]); //01 ID
 String nome = partes[1];//02 Nome
 String cidade = partes[2];//03 Cidade
 int capacidade = parseInteiro(partes[3]);//04 Capacidade
 double avaliacao = parseDoble(partes[4]); //05 avaliacao
 
 String[] tiposCozinha = parseTipos(partes[5]); //06 tipos de cozinha
 int faixaPreco = parseFaixa(partes[6]); //07 faixa de preco
 
 String[] dividirHora = dividirHora(partes[7]);
 Hora abertura = Hora.parseHora(dividirHora[0]); //08 hora de abertura 
 Hora fechamento = Hora.parseHora(dividirHora[1]); //09 hora de fechamento
 
 Data dataAbertura = Data.parseData(partes[8]); //10 data de abertura
 Boolean aberto = parseBool(partes[9]); //boolean abertura
 
 //##### agora vou criar o objeto restaurante e retorna-lo
 
 return new Restaurante(
        id, 
        nome, 
        cidade, 
        capacidade, 
        avaliacao, 
        tiposCozinha, 
        faixaPreco, 
        abertura, 
        fechamento, 
        dataAbertura, 
        aberto
    );
  
 }
 //=========== FIM PARSE RESTAURANTE - Abaixo as funcoes auxiliares deste parse ===================
 
  public static String[] dividirHora(String s){ //## AUX 01 > divide o horario de abertura e encerramento 10:10 - 18:00 

    String[] divisao = {"", ""};
    int i = 0;
    
    // Pega a primeira parte até o '-'
    while (i < s.length() && s.charAt(i) != '-') {
        divisao[0] += s.charAt(i);
        i++;
    }
    
    i++; // Pula o '-'
    
    // Pega o resto
    while (i < s.length()) {
        divisao[1] += s.charAt(i);
        i++;
    }
    return divisao;
  }
  
  public static boolean parseBool(String s){//parse bool eu vou verificar so o primeiro caractere, se for f retorno falso, senao, e verdadeiro
 
    if(s.charAt(0) == 'f'){return false;}
    else return true;
  }
 
 public static int parseFaixa(String s){
 
 int count = 0;
 
  for(int i = 0; i < s.length();i++){//Vou contar quantos $$$ tem
  if(s.charAt(i) == '$'){count++;}
  }
 return count; 
 }
 
 public static String[] parseTipos(String s){ //metodo separado para separar os tipos de cozinha ##ATRIBUTO 06
 
   String[] partes = new String[20];
   
   int j = 0; //j vai percorrer a string
   for(int i = 0; j < s.length(); i++){ //cada loop do for vou ter criado 
   
    partes[i] = "";
    while(j < s.length() && s.charAt(j) != ';' ){ //vou concatenando ate o proximo ponto e virgula
    
      partes[i] += s.charAt(j);
      j++;
    }
    
    if(j < s.length()){
      j++; //pulo o ponto e virgula
    }
   }
   
 return partes; 
 }
 //==========================funcao formatar restaurante pedida =====================
 public String formatar() {
    // 1. Montar a string dos tipos de cozinha: [tipo1, tipo2]
    String tiposStr = "[";
    for (int i = 0; i < this.tiposDeCozinha.length; i++) {
        if (this.tiposDeCozinha[i] != null && !this.tiposDeCozinha[i].equals("")) {
            if (!tiposStr.equals("[")) {
                tiposStr += ",";
            }
            tiposStr += this.tiposDeCozinha[i];
        }
    }
    tiposStr += "]";

    // 2. Montar a representação da faixa de preço em cifrões ($)
    String cifroes = "";
    for (int i = 0; i < this.faixaDePreco; i++) {
        cifroes += "$";
    }

    // 3. Retornar a string completa usando String.format (permitido no TP2)
    // Formato: [id ## nome ## cidade ## capacidade ## avaliacao ## [tipos] ## $$$ ## HH:mm-HH:mm ## data ## aberto]
    return String.format("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %b]",
        this.id, 
        this.nome, 
        this.cidade, 
        this.capacidade, 
        this.avaliacao, 
        tiposStr, 
        cifroes, 
        this.horaDeAbertura.formatar(), 
        this.horaDeFechamento.formatar(), 
        this.dataAbertura.formatar(), 
        this.aberto
    );
}
}//============================== FIM DA CLASSE RESTARURANTE - e inicio COLECAO RESTAURANTE =============================

static class ColecaoRestaurantes{

private int tamanho;
private Restaurante[] restaurantes; 

ColecaoRestaurantes(){ //construtor, vou criar uma vez e depois ir lendo o CSV

  this.tamanho = 0; 
  this.restaurantes = new Restaurante[1000]; //array de 1000 restaurantes
}


public int getTamanho(){//retorna o tamanho da classse atual
  return this.tamanho; 
}

public Restaurante[] getRestaurantes(){
  return this.restaurantes;
}

public void adicionar(Restaurante r){

  this.restaurantes[this.tamanho] = r;
  
  this.tamanho++;
}

public void lerCsv(String path){

  try{
  
  BufferedReader br = new BufferedReader(new FileReader(path)); //cria um leitor de buffer para a path
  br.readLine();  //le o cabecalho
  
  String linha;
  while((linha = br.readLine()) != null){

    Restaurante r = Restaurante.parseRestaurante(linha); //usa o metodo parse restaurante da classe restaurante para ler e criar restaurante
    
    this.adicionar(r); //adiciona o restaurante lido ao array de restaurantes
    }
  br.close();
  }catch(IOException e){
  System.err.println("Erro ao ler o arquivo: " + e.getMessage());
  }
}

public static ColecaoRestaurantes lerCsv() {
    ColecaoRestaurantes colecao = new ColecaoRestaurantes();
    colecao.lerCsv("/tmp/restaurantes.csv"); // Caminho obrigatório no Linux da PUC 
    return colecao;
}
}
//========================== #### NOVA CLASSE ARVORE BINARIA #### ===============================
static class No {
    No esq, dir;
    Restaurante restaurante;

    No(Restaurante r) {
        this.esq = null;
        this.dir = null;
        this.restaurante = r;
    }
}

static class Arvore {
    No raiz;
    public static int numComparacoes = 0; // Variavel global para log

    Arvore() {
        this.raiz = null;
    }

    //============= metodos inserir =========================
    public void inserir(Restaurante r) {
        if (raiz == null) {
            raiz = new No(r); //se a raiz for nula eu insiro na raiz
        } else {
            inserirRec(raiz, r); //caso contrario eu chamo a funcao recursiva
        }
    }

    public No inserirRec(No no, Restaurante r) {
        if (no == null) { //caso base onde crio um novo No
            return new No(r); 
        }
        int comp = r.nome.compareTo(no.restaurante.nome); //vou comparar a chave nome e inserir de acordo

        if (comp == 0) { //se ja tiver registro nao adiciono
            return no; 
        }
        if (comp > 0) { //se for maior...
            no.dir = inserirRec(no.dir, r); //o no atual vai ser modificado na direita
            return no;
        } else { //caso que a comp < 0
            no.esq = inserirRec(no.esq, r); //o no atual e modificado na esquerda
            return no; 
        }
    }

    //============= Metodo de pesquisar ===================
    public void pesquisar(String nome) {
        System.out.print("raiz ");
        pesquisarRec(nome, this.raiz);
    }

    public void pesquisarRec(String nome, No no) {
        numComparacoes++; // Contador de comparacoes para o log
        if (no == null) { //se cheguei ate null e nao encontrei, nao tenho o registro
            System.out.println("NAO"); 
            return;
        }

        int comp = nome.compareTo(no.restaurante.nome); //vou comparar o nome atual com o nome do no atual

        if (comp == 0) { //se a comparacao deu 0, o registro existe
            System.out.println("SIM"); 
            return; 
        } else if (comp > 0) { //se deu > 0 tenho que ir para direita
            System.out.print("dir ");
            pesquisarRec(nome, no.dir);
        } else if (comp < 0) { // se deu < 0 tenho que ir para esquerda
            System.out.print("esq ");
            pesquisarRec(nome, no.esq);
        }
    }

    //================ caminhamento central =============
    public void caminharCentral() {
        caminharCentralRec(this.raiz); 
    }

    private void caminharCentralRec(No no) {
        if (no != null) {
            caminharCentralRec(no.esq);
            System.out.println(no.restaurante.formatar());
            caminharCentralRec(no.dir);
        }
    }
}

    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();//vou criar uma nova colecao 
        Scanner sc = new Scanner(System.in);

        int idBuscado = sc.nextInt();
        sc.nextLine(); //leio ate o final da linha

        Arvore arvore = new Arvore();

        //=========== Primeira Entrada ==================
        while (idBuscado != -1) {
            int i = 0; //vai ser o indice do id buscado
            int tam = colecao.getTamanho();
            
            for (; i < tam && colecao.getRestaurantes()[i].getId() != idBuscado; i++);//vou achar o indice do id no csv

            if (i < tam) {
                Restaurante r = colecao.getRestaurantes()[i]; //pego o restaurante do id
                arvore.inserir(r);
            }

            idBuscado = sc.nextInt();
            sc.nextLine();
        }//ate aqui fiz todas as insercoes na arvore

        //================= Segunda Entrada =======================
        long tempoInicio = System.nanoTime(); // Medicao de tempo total das pesquisas

        String entrada = sc.nextLine(); 
        while (entrada.compareTo("FIM") != 0) {
            arvore.pesquisar(entrada); //chamo a funcao para pesquisar a entrada e printo o resultado 
            entrada = sc.nextLine(); 
        }

        long tempoFim = System.nanoTime();
        double tempoTotal = (tempoFim - tempoInicio) / 1_000_000_000.0;

        //=============== Print final dos restaurantes ================
        arvore.caminharCentral();

        //=============== Geracao do arquivo de log ================
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("1066294_arvore_binaria.txt")); 
            writer.printf("1066294\t%d\t%.8f", Arvore.numComparacoes, tempoTotal); // Separado por tabulacao
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }

        sc.close();
    }

}
