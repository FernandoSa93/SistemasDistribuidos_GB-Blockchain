Versão do java utilizada para compilação: JavaSE-1.6

Os programas devem ser executados na seguinte ordem:
1 - Servidor.jar
2 - Minerador.jar
3 - Aplicação.jar

Para executar os programas, execute o CMD e abra a pasta onde estão localizados os arquivos .jar e em seguida execute o comando: java -jar nomearquivo.jar

Passo a passo:
1 - No computador que será o servidor, abra o cmd e digite "ipconfig" e anote o ip dele. execute o arquivo Servidor.jar. Ao iniciar o servidor, será solicitado a dificuldade de mineração desejada, ela corresponde a quantidade de zeros a esquerda que o hash de cada bloco deverá possuir. Após informar a dificuldade desejada o servidor será inicializado e aguardará a conexão do minerador;
2 - No computador que será o minerador, execute o arquivo "Minerador.jar". Será solicitado o ip do servidor, digite corretamente. O minerador ficará aguardando uma solicitação do servidor;
3 - No computador que será a aplicação, execute o arquivo "Aplicação.jar". Será solicitado o ip do servidor, digite corretamente. 
4 - A partir deste ponto, as 3 aplicações estarão conectadas entre si. Escolher 1 para enviar um texto ao blockchain ou 2 para finalizar todas as aplicações ao mesmo tempo.