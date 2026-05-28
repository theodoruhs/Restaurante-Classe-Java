import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

//======================= NOVA CLASSE CELULA DUPLA ===================

static class Celula{//nova classe celula

 Celula prox;
 Celula ant;
 Restaurante restaurante; 

 Celula(){

  this.prox = this.ant = null;
  this.restaurante = null;
 }

 Celula(Restaurante r){

  this.prox = this.ant = null;
  this.restaurante = r;
 }
 
}
//==================== LISTA DUPLA ===================================

static class ListaDupla{

  Celula primeiro;
  Celula ultimo;
  

  ListaDupla(){

    this.primeiro = this.ultimo = new Celula();
  }

  //================ metodos inserir

  void inserirInicio(Restaurante r){

    Celula nova = new Celula(r); //crio nova celula

    nova.prox = primeiro.prox; //faco nova apontar para a proxima do primeiro
    nova.ant = primeiro;    // faco a nova ant apontar para primeiro

    primeiro.prox = nova;  //primeiro aponta para nova

    if(nova.prox != null){ //faco a celula da frente apontar de volta para nova

      nova.prox.ant = nova; 
    }

    if(primeiro == ultimo){//atualizo o ultimo se primeiro for igual a ultimo

      ultimo = nova; 
    }

  }

  void inserirFim(Restaurante r){

    Celula nova = new Celula(r);

    nova.ant = ultimo; //faco a nova apontar para o ultimo
    nova.prox = null;

    ultimo.prox = nova; //e ultimo apontar para nova

    ultimo = ultimo.prox; //desloco o ultimo para nova


  }

  void inserir(Restaurante r, int pos){

    Celula nova = new Celula(r);

    Celula i = this.primeiro; 

    for(int j = 0; j < pos && i != null; j++, i = i.prox); //vou andar na celula dupla ate a posicao anterior a de insercao

    if(i.prox == null){inserirFim(r);} //vou checar se estou na ultima ou na primeira celula antes
    if(i.ant == primeiro){inserirInicio(r);}

    nova.prox = i.prox; //nova prox vai apontar para i prox
    nova.ant = i; //nova ant vai ser i

    i.prox = nova; //i prox vai ser nova
    nova.prox.ant = nova; //atualizo o ant da nova prox para ser nova, observar que ja lidei com o caso de IF

  }

  //================= metodos remover

  Restaurante removerFim(){

    if(primeiro == ultimo){System.out.println("lista vazia");  return null;}

    Restaurante resp = ultimo.restaurante; //guardo o restaurante da resposta

    Celula tmp = ultimo.ant; //guardo a posicao do anterior ao ultimo

    ultimo.ant = null; //retiro a seta para o penultimo
    tmp.prox = null;  //retiro a seta do penultimo para ultimo

    ultimo = tmp; //volto uma pos atras com o ultimo
    tmp = null;

    return resp; 
  }

  Restaurante removerInicio(){

    if(primeiro == ultimo){System.out.println("lista vazia");  return null;}

    Restaurante resp = primeiro.prox.restaurante; //vou guardar a resp

    Celula tmp = primeiro.prox; //vou criar um ponteiro pra celula que vou retirar

    primeiro.prox = primeiro.prox.prox;//vou fazer o primeiro "pular" a posicao que vou retirar

    if(tmp != ultimo){ //se a posicao retirada nao for a ultima
     tmp.prox.ant = primeiro; //a anterior da proxima a celula retirada aponta para primeiro

    }

    tmp.ant = null; //limpo as referencias da celula retirada
    tmp.prox = null;

    return resp;
  }

  Restaurante remover(int pos){

    Celula i = primeiro;

    for(int j = 0; j < pos && i != null; j++, i = i.prox);  //vou andar ate a celula anterior que vou retirar

    if(i == primeiro){return removerInicio();} //verifico se estou tentando remover a primeira ou ultima pos e redireciono
    if(i.prox == ultimo){return removerFim();}

    Celula tmp = i.prox; //tmp vai apontar para onde eu vou remover
    Restaurante resp = i.prox.restaurante; //guardo o conteudo do retorno

    i.prox = i.prox.prox; //i prox vai pular a celula que vou remover
    tmp.prox.ant = i; //o anterior a celula que eu vou remover vai pular tmp

    tmp.prox = null;
    tmp.ant = null;

    return resp;  
  }
}

//================================= FIM DA LISTA DUPLA ===================

public static Restaurante acharIdRestaurante(ColecaoRestaurantes colecao, int id){

  int i = 0;
  for(; i < colecao.tamanho && colecao.getRestaurantes()[i].id != id; i++); //vou andar com o i enquanto ele nao e igual ao id

  return colecao.getRestaurantes()[i]; //vou retornar o restaurante correspondente

}


//Nova funcao que substitui equals =====

public static boolean igual(String a, String b){

  if(a.length() != b.length()){return false;} //se sao iguais tem que ter tamanhos iguais

  for(int i = 0; i < a.length();i++){ //vou iterar pelas duas strings

    if(a.charAt(i) != b.charAt(i)){return false;} //se em algum momento eu tiver um caractere nas duas que nao e igual retorno falso

  }
  return true;
}

//================ Funcao MAIN ==============================

public static void main(String[] args) throws Exception{

  ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();//vou criar uma nova colecao usando o retorno da funcao estatica lerCsv

  Scanner sc = new Scanner(System.in);

  int idBuscado = sc.nextInt();
  sc.nextLine(); //leio ate o final da linha

  ListaDupla lista = new ListaDupla();


  //========= Primeira Entrada =====================

  while(idBuscado != -1){ //enquanto nao tiver condicao de parada

    
    lista.inserirFim(acharIdRestaurante(colecao,idBuscado));//insiro no fim o restaurante com o id buscado
  
    idBuscado = sc.nextInt();
    sc.nextLine(); //leio ate o final da linha
  }

  //======== Segunda Entrada =======================

  int n = sc.nextInt();

  for(int i = 0; i < n; i++){

    if (!sc.hasNext()) break; //### protecao contra fim do arquivo inesperado

    String s = sc.next(); //s vai ser o comando

    if(igual(s,"II")){

      Restaurante r = acharIdRestaurante(colecao,sc.nextInt()); //leio a proxima linha e obtenho o restaurante
      lista.inserirInicio(r); //insiro ele de acordo com o comando
    

    }else if(igual(s,"IF")){

      Restaurante r = acharIdRestaurante(colecao,sc.nextInt()); //leio a proxima linha e obtenho o restaurante
      lista.inserirFim(r); 


    }else if(igual(s,"I*")){

      int pos = sc.nextInt(); 
      Restaurante r = acharIdRestaurante(colecao,sc.nextInt()); 
      lista.inserir(r,pos); 

 

    }else if(igual(s,"RI")){ //no caso dos comandos de remover

      Restaurante resp = lista.removerInicio(); //eu retorno a posicao que removi na funcao remover inicio
      System.out.println("(R)" + resp.getNome());// e printo ela no resultado

  

    }else if(igual(s,"RF")){

      Restaurante resp = lista.removerFim(); 
      System.out.println("(R)" + resp.getNome());



    }else if(igual(s,"R*")){

      int pos = sc.nextInt();
      Restaurante resp = lista.remover(pos); 
      System.out.println("(R)" + resp.getNome());


    }

  }

  //========= Agora Imprimo todos os restaurantes da lista dupla ===========


  for(Celula indice = lista.primeiro.prox; indice != null; indice = indice.prox){ //loop vai mostrar os restaurantes a partir do primeiro

    System.out.println(indice.restaurante.formatar()); 


  }

  sc.close();
} 
}
