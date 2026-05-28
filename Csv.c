
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>


typedef struct{

    int hora;
    int minuto;

}Hora; 

typedef struct{
    
    int dia;
    int mes;
    int ano; 

}Data; 

typedef struct{

    int id;
    char* nome;
    char* cidade; 
    int capacidade;
    double avaliacao;

    int n_tipos_de_cozinha;
    char** tipos_de_cozinha; 
    int faixa_preco; 

    Hora horario_abertura;
    Hora horario_fechamento; 

    Data data_abertura;
    bool aberto; 

}Restaurante; 

typedef struct{

    int tamanho; 
    Restaurante** restaurantes; 


}Colecao_restaurantes; 

//============ Novas Struct celula e Lista ==========

typedef struct Celula{

    Restaurante* restaurante;
    struct Celula* prox; 

}Celula;

typedef struct{

    Celula* primeiro;
    Celula* ultimo; 


}Lista; 

//prototipos:

// --- PROTÓTIPOS DAS FUNÇÕES ---
char* meu_strdup(char* s);
int n_tipos(char* s);
void atribuir_tipos(Restaurante* r, char* tipos_cozinha);
int parse_preco(char* s);
Hora parse_hora_div(char* s, int pos);
Data parse_data(char* s); 
bool parse_bool(char* s);
void formatar_hora(Hora* h, char* buffer);
void formatar_data(Data* d, char* buffer);


//Funcoes Hora ================


Hora parse_hora(char* s){//recebo uma string s no formato hora:minuto

    Hora h;  //crio uma nova hora

    sscanf(s,"%d:%d", &h.hora, &h.minuto); //sscanf le a propria string e separa hora e minuto da nova hora

    return h; 
}

void formatar_hora(Hora* h, char* buffer){ //vou precisar da hora e de um buffer para printar a hora formatada


    sprintf(buffer,"%02d:%02d", h->hora,h->minuto);//sprintf "printa" o resultado dentro da string buffer, com os placeholders de hora e minuto
}

//Funcoes Data ================



Data parse_data(char* s){

 Data d; //crio uma nova data para returnar

 sscanf(s,"%04d-%02d-%02d", &d.ano,&d.mes,&d.dia); //leio a string s e separo os placeholders

 return d; 
}

void formatar_data(Data* d, char* buffer){
    
    sprintf(buffer,"%02d/%02d/%04d", d->dia, d->mes,d->ano); //printo na string do buffer as / com os placeholders para atrubutos da data

}

//================== Struct restaurante ==============================



Restaurante* parse_restaurante(char* s){//nessa funcao que vou criar o restaurante a partir da string

 Restaurante* r = (Restaurante*) malloc(sizeof(Restaurante));//preciso usar malloc para retornar o ponteiro para o restaurante

 char partes[10][100]; 

 int j = 0; //j vai percorrer a string

    for(int i = 0; i < 10; i++){//a cada iteracao de i vou separar uma parte da string

        int k = 0; 

        for(; s[j] != '\0' && s[j] != ',';j++,k++){//k vai ser o indice da nova string e j vai ser o da string total

            partes[i][k] = s[j];
            
        }

        partes[i][k] = '\0'; //termino a nova string aqui
        
        if(s[j] != '\0'){j++;} //pulo a virgula aqui
        
    }//### Ate aqui ja separei a string em partes =======

    r->id = atoi(partes[0]);
    r->nome = meu_strdup(partes[1]);
    r->cidade = meu_strdup(partes[2]);
    r->capacidade = atoi(partes[3]);
    r->avaliacao = atof(partes[4]);

    r->n_tipos_de_cozinha = n_tipos(partes[5]);
    r->tipos_de_cozinha = (char**) malloc(r->n_tipos_de_cozinha * sizeof(char*));
    atribuir_tipos(r, partes[5]);

    r->faixa_preco = parse_preco(partes[6]); 

    r->horario_abertura = parse_hora_div(partes[7],1);
    r->horario_fechamento = parse_hora_div(partes[7],2); 

    r->data_abertura = parse_data(partes[8]);
    r->aberto = parse_bool(partes[9]); 

 return r; 
}

// ====== FUNCOES AUXILIARES do parse restaurante ==============

char* meu_strdup(char* s) { //#####################
    int tam = 0;
    while(s[tam] != '\0') tam++; // conta o tamanho manual 
    
    char* novo = (char*) malloc((tam + 1) * sizeof(char));
    for(int i = 0; i <= tam; i++) {
        novo[i] = s[i]; // copia caractere por caractere
    }
    return novo;
}

int n_tipos(char* s){

    if (s == NULL || s[0] == '\0') {//se for vazio, tratamos como zero
        return 0;
    }

    int contador = 1;//contador vai ser o numero de ponto e virgula que separa os tipos de cozinha
    int j = 0; //j vai percorrer o array

    while (s[j] != '\0') {
        if (s[j] == ';') {
            contador++;
        }
        j++;
    }

    return contador;
}

void atribuir_tipos(Restaurante* r, char* tipos_cozinha){ //atribuir tipos de cozinha #########

    int j = 0; //j vai percorrer os tipos de cozinha

    for(int i = 0; i < r->n_tipos_de_cozinha; i++){//cada i vai representar um tipo de cozinha

        char buffer[100]; 
        int k = 0; //k vai percorrer o buffer

        while(tipos_cozinha[j] != '\0' && tipos_cozinha[j] != ';'){ //enquanto nao encontrar o fim da string ou ; 

            buffer[k] = tipos_cozinha[j]; //vou copiar para o buffer o tipo de cozinha correspondente da pos da string atual (j)
            j++;
            k++;
        }
        buffer[k] = '\0';//finaliza a string do buffer

        if(tipos_cozinha[j] == ';') {j++;} //pulo o ponto e virgula se tiver; 

        r->tipos_de_cozinha[i] = meu_strdup(buffer); //copia a nova string para o tipo_de_cozinha
    }

}

int parse_preco(char* s){

    int j = 0; //j vai percorrer a string
    int i = 0; //i vai contar os $

    while(s[j] != '\0'){

        if(s[j] == '$'){i++;}//para cada $ vou incrementar em 1 o contador
        j++;
    
    }

    return i; 
}

Hora parse_hora_div(char* s, int pos){//recebe uma string HH:HH-HH:HH

    char bufferA[100]; //vou ter dois buffers e vou escolher eles dependendo da posicao
    char bufferB[100];
    int a = 0;      //variavel correspondente ao indice de cada buffer
    int b = 0; 
    int j = 0; //j vai percorrer a string

    while(s[j] != '-'){ //vou percorrer ate o -

        bufferA[a] = s[j];//copiando os caracteres
        a++;
        j++;
    }
    bufferA[a] = '\0';
    j++;
    while(s[j] != '\0'){ //mesma coisa pra segunda parte so que ate o \0
        bufferB[b] = s[j];
        b++;
        j++;
    }
    bufferB[b] = '\0'; 

    if(pos == 1){return parse_hora(bufferA);}//agora que tenho a string separada aplico o parse_hora
    else return parse_hora(bufferB);
    
}

bool parse_bool(char* s){//parse bool basicamente pega a string true ou false e converte num valor booleano

    if(s[0] == 'f' || s[0] == 'F'){return false;}//se a primeira letra comeca com f entao e false
    else return true; 
}
//=========== FIM DAS FUNCOES AUXILIARES DO PARSE RESTAURANTE e inicio do FORMATAR RESTAURANTE =================

void formatar_restaurante(Restaurante* r, char* buffer) {

    char b_abertura[10], b_fechamento[10], b_data[15];

    formatar_hora(&(r->horario_abertura), b_abertura);
    formatar_hora(&(r->horario_fechamento), b_fechamento);
    formatar_data(&(r->data_abertura), b_data);

    // 1. Cozinhas
    char b_cozinhas[200];
    b_cozinhas[0] = '\0'; // inicia string vazia

    strncat(b_cozinhas, "[", sizeof(b_cozinhas) - strlen(b_cozinhas) - 1);

    for (int i = 0; i < r->n_tipos_de_cozinha; i++) {
        strncat(b_cozinhas, r->tipos_de_cozinha[i],
                sizeof(b_cozinhas) - strlen(b_cozinhas) - 1);

        if (i < r->n_tipos_de_cozinha - 1) {
            strncat(b_cozinhas, ",",
                    sizeof(b_cozinhas) - strlen(b_cozinhas) - 1);
        }
    }

    strncat(b_cozinhas, "]",
            sizeof(b_cozinhas) - strlen(b_cozinhas) - 1);

    // 2. Preço
    char b_preco[10];
    b_preco[0] = '\0';

    for (int i = 0; i < r->faixa_preco; i++) {
        strncat(b_preco, "$",
                sizeof(b_preco) - strlen(b_preco) - 1);
    }

    // 3. Montagem final
    snprintf(buffer, 500,  // assume que buffer tem pelo menos isso
        "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        r->id, 
        r->nome, 
        r->cidade, 
        r->capacidade, 
        r->avaliacao,
        b_cozinhas, 
        b_preco, 
        b_abertura, 
        b_fechamento, 
        b_data,
        r->aberto ? "true" : "false");
}

//=============== FIM DA CLASSE RESTAURANTE e INICIO COLECAO RESTAURANTE ===============================



void ler_csv_colecao(Colecao_restaurantes* colecao, char* path){

    FILE* arquivo = fopen(path, "r"); 

    if(arquivo == NULL){
        printf("Erro ao abrir arquivo\n");
        return;
    }

    colecao->restaurantes = (Restaurante**)malloc(1000 * sizeof(Restaurante*)); //crio o array de arrays colecao restaurante
    colecao->tamanho = 0;
    

    char linha[500]; //buffer para guardar linha

    //pula cabecalho
    fgets(linha,sizeof(linha),arquivo); 

  
    while(fgets(linha,sizeof(linha),arquivo) != NULL){//enquanto nao chegar no fim do arquivo,vou lendo as linhas

        for(int i = 0; linha[i] != '\0';i++){ //loop para tirar um eventual /n no final da string
            if(linha[i] == '\n' || linha[i] == '\r'){
                linha[i] = '\0'; //substituo o \n pelo fim da string
            }
        }

        Restaurante* novo = parse_restaurante(linha); 
        colecao->restaurantes[colecao->tamanho] = novo; 

        colecao->tamanho++;
    }//ate aqui preenchi o array de restaurantes com todos os restaurantes

}
Colecao_restaurantes* ler_csv() {//essa funcao vai chamar a de cima

    Colecao_restaurantes* c = (Colecao_restaurantes*) malloc(sizeof(Colecao_restaurantes));//crio um nova colecao restaurantes

    ler_csv_colecao(c, "/tmp/restaurantes.csv"); //chamo a de cima com os parametros especificados
    return c;
}

//=========================== FUNCOES LISTA - a struct foi adicionada no inicio =====================

//###### LEMBRAR DE ADD OS PROTOTIPOS DESSAS FUNCOES

Celula* nova_celula(Restaurante* r){

    Celula* nova = (Celula*)malloc(sizeof(Celula)); 
    nova->restaurante = r;
    nova->prox = NULL; 

    return nova; 
}

Lista* nova_lista(){

    Lista* lista = (Lista*) malloc(sizeof(Lista));

    lista->primeiro = (Celula*) malloc(sizeof(Celula)); 
    lista->primeiro->prox = NULL;
    lista->ultimo = lista->primeiro; 

    return lista;

} 

//Metodos para Inserir ================

void inserir_inicio(Restaurante* r, Lista* lista){

    Celula* nova = nova_celula(r);
    Celula* tmp = lista->primeiro->prox;

    lista->primeiro->prox = nova; 
    nova->prox = tmp; 

    if(lista->primeiro == lista->ultimo){

        lista->ultimo = nova;
    }

}

void inserir_fim(Restaurante* r,Lista* lista){

    Celula* nova = nova_celula(r); //crio uma nova celula

    lista->ultimo->prox = nova;
    lista->ultimo = lista->ultimo->prox; //atualiza a ultima posicao

}

void inserir(Restaurante* r,Lista* lista,int pos){

    Celula* nova = nova_celula(r);

    Celula* i = lista->primeiro;
    for(int j = 0; j < pos; j++, i = i->prox); //vou caminhar ate a posicao anterior a que vou inserir

    nova->prox = i->prox;
    i->prox = nova; 

    if(i == lista->ultimo){

        lista->ultimo = nova; 
    }

}

//Metodos Remover ==================

Restaurante* remover_inicio(Lista* lista){

    if(lista->primeiro == lista->ultimo){printf("ERRO - lista vazia");return NULL; } //lista vazia

    Restaurante* resp = lista->primeiro->prox->restaurante;  //vou retornar esse restaurante
    Celula* tmp = lista->primeiro->prox; //vou guardar ref para celula que vou remover

    lista->primeiro->prox = lista->primeiro->prox->prox; //### vou pular a celula que vou remover com a referencia 

    if(tmp == lista->ultimo){ //se estiver removendo o ultimo elemento da lista, tenho que atualizar

        lista->ultimo = lista->primeiro;
    }

    tmp->prox = NULL;
    free(tmp); 

    return resp; 

}

Restaurante* remover_fim(Lista* lista){

    if(lista->primeiro == lista->ultimo){printf("ERRO - lista vazia");return NULL; } //lista vazia

    Restaurante* resp = lista->ultimo->restaurante; //vou retornar esse restaurante

    Celula* i = lista->primeiro; //vou caminhar ate o penultimo

    for(; i->prox != lista->ultimo; i = i->prox); //i e penultimo

    Celula* tmp = lista->ultimo; //vou fazer tmp ser um ponteiro para ultimo
    lista->ultimo = i;  //###### o novo ultimo vai ser i, a celula anterior
    i->prox = NULL; //vou tirar a referencia da ultima celula

    free(tmp);  //vou liberar a ultima celula
    tmp = NULL; 
    
    return resp;
}

Restaurante* remover(Lista* lista, int pos){

    if(lista->primeiro == lista->ultimo){printf("ERRO - lista vazia");return NULL; } //lista vazia

    Celula* i = lista->primeiro; //vou comecar da celula cabeca

    for(int j = 0; j < pos; j++, i = i->prox); //vou chegar na posicao anterior a que vou remover com o i

    Restaurante* resp = i->prox->restaurante;
    Celula* tmp = i->prox; //vou guardar referencia para i prox

    if(i->prox == lista->ultimo){ //se estiver removendo da ultima posicao

        lista->ultimo = i; 
    }
    i->prox = i->prox->prox; //### vou pular a celula que vou remover

    free(tmp);
    tmp = NULL; 

    return resp; 

}

Restaurante* achar_restaurante(Colecao_restaurantes* col, int indice){

 int i = 0;

 for(;col->restaurantes[i]->id != indice;){i++;} //vou chegar no indice do restaurante que busco

 return col->restaurantes[i]; //retorno o restaurante do indice correto
}


//================================ FUNCAO MAIN =========================================================


int main (){


    Colecao_restaurantes* colecao = ler_csv(); //as funcoes de cima fazem todo o trabalho, e retornam o ponteiro para colecao

    Lista* lista = nova_lista();


 //======================== PRIMEIRA ENTRADA ===========================

    int id_buscado; 
    scanf("%d", &id_buscado);

    while(id_buscado != -1) { //vou ler as entradas e enquanto a entrada nao for -1 continuo

        Restaurante* r = achar_restaurante(colecao,id_buscado); 
        
        inserir_fim(r,lista); 

        scanf("%d", &id_buscado); 
    }     

    //===================== Ate aqui criei a lista da 1 entrada =======

 //======================= SEGUNDA ENTRADA =================================

    int n;
    scanf("%d", &n); 

    for(int i = 0; i < n; i++){//vou fazer as insercoes e remocoes pedidas


        char buffer[20];//vai ser a entrada para operacao

        scanf("%s",buffer); 

        if(strcmp(buffer,"II") == 0){

            int id;  
            scanf("%d", &id); //vou escanear a proxima entrada
            inserir_inicio(achar_restaurante(colecao,id),lista); //vou procurar o restaurante com id certo e inserir na colecao 

        }else if(strcmp(buffer,"IF") == 0){

            int id;
            scanf("%d", &id); 
            inserir_fim(achar_restaurante(colecao,id),lista); //mesma logica da anterior so que para inserir fim
            
        }else if(strcmp(buffer,"I*") == 0){

            int pos;
            scanf("%d", &pos); //vou escanear a posicao antes do restaurante
            int id;
            scanf("%d", &id); 
            inserir(achar_restaurante(colecao,id),lista,pos); //vou inserir na pos correspondente
            
        }else if(strcmp(buffer,"RI") == 0){

            Restaurante* resp = remover_inicio(lista);
            printf("(R)%s\n",resp->nome); 
            
        }else if(strcmp(buffer,"RF") == 0){

            Restaurante* resp = remover_fim(lista);
            printf("(R)%s\n",resp->nome); 
            
        }else if(strcmp(buffer,"R*") == 0){

            int pos;
            scanf("%d", &pos); //vou escanear a posicao antes do restaurante
            Restaurante* resp = remover(lista,pos); 
            printf("(R)%s\n",resp->nome); 
            
        }

    }

 //================== PRINT FINAL DA LISTA - mostra os elementos do primeiro ao ultimo ===================

  for (Celula* i = lista->primeiro->prox; i != NULL; i = i->prox) {
    char buffer[500]; 
    formatar_restaurante(i->restaurante, buffer); 
    printf("%s\n", buffer); 
}



    // --- vou dar FREE em todos os malloc possivelmente utilizados ---
    for (int k = 0; k < colecao->tamanho; k++) {

        // Libera as strings de dentro de cada restaurante (nome, cidade, cozinhas...)
        free(colecao->restaurantes[k]->nome);
        free(colecao->restaurantes[k]->cidade);
        for(int l = 0; l < colecao->restaurantes[k]->n_tipos_de_cozinha; l++) {
            free(colecao->restaurantes[k]->tipos_de_cozinha[l]);
        }
        free(colecao->restaurantes[k]->tipos_de_cozinha);
        free(colecao->restaurantes[k]); // Libera a struct do restaurante
    }
    free(colecao->restaurantes); // Libera o array de ponteiros
    free(colecao); // Libera a caixa da coleção


    return 0; 
}